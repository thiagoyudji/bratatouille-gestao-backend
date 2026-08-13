package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.sales.domain.SalesOrderItemData;
import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.entity.SalesCustomerType;
import br.com.bratatouille.management.sales.mapper.SalesOrderMapper;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderMapper salesOrderMapper;
    private final ItemRepository itemRepository;
    private final SellableStockService sellableStockService;
    private final CostService costService;

    public SalesOrderService(
            SalesOrderRepository salesOrderRepository,
            SalesOrderMapper salesOrderMapper,
            CostService costService,
            ItemRepository itemRepository,
            SellableStockService sellableStockService
    ) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderMapper = salesOrderMapper;
        this.costService = costService;
        this.itemRepository = itemRepository;
        this.sellableStockService = sellableStockService;
    }

    @Transactional
    public SalesOrderResponse create(SalesOrderCreateRequest request) {
        validate(request);

        SalesCustomerType customerType = toCustomerType(request.getCustomerType());

        List<SalesOrderItemData> itemsData = request.getItems()
                .stream()
                .map(itemRequest -> toItemData(itemRequest, customerType))
                .toList();

        SalesOrder salesOrder = SalesOrder.create(
                request.getSaleDate(),
                customerType,
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getCustomerPhone(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getZipCode(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getStreet(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getNumber(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getNeighborhood(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getState(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getCity(),
                request.getDeliveryAddress() == null ? null : request.getDeliveryAddress().getComplement(),
                request.getNote(),
                itemsData
        );

        SalesOrder saved = salesOrderRepository.save(salesOrder);

        itemsData.forEach(itemData ->
                sellableStockService.decreaseAfterSale(itemData.item(), itemData.quantity())
        );

        return salesOrderMapper.toResponse(saved);
    }

    public List<SalesOrderResponse> findAll() {
        return salesOrderRepository.findAll()
                .stream()
                .map(salesOrderMapper::toResponse)
                .toList();
    }

    public SalesOrderResponse findById(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sales order not found"));

        return salesOrderMapper.toResponse(salesOrder);
    }

    @Transactional
    public void updateCheckoutMetadata(Long orderId, String checkoutUrl, String invoiceSlug) {
        SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Sales order not found"));

        salesOrder.updateCheckoutMetadata(checkoutUrl, invoiceSlug);
        salesOrderRepository.save(salesOrder);
    }

    private SalesOrderItemData toItemData(SalesOrderItemRequest request, SalesCustomerType customerType) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NoSuchElementException("Item not found"));

        if (item.getPricePf() == null || item.getPricePj() == null) {
            throw new IllegalArgumentException("item prices are required for sales");
        }

        BigDecimal expectedUnitPrice = switch (customerType) {
            case PJ -> item.getPricePj();
            case PF, GUEST -> item.getPricePf();
        };

        BigDecimal unitCost = costService.findUnitCostOrZero(item);
        boolean costIncomplete = costService.isCostIncomplete(unitCost);

        return new SalesOrderItemData(
                item,
                request.getQuantity(),
                expectedUnitPrice,
                item.getPricePf(),
                item.getPricePj(),
                unitCost,
                costIncomplete
        );
    }

    private SalesCustomerType toCustomerType(br.com.bratatouille.management.generated.model.SalesOrderCustomerType customerType) {
        if (customerType == null) {
            return SalesCustomerType.GUEST;
        }

        return SalesCustomerType.valueOf(customerType.name());
    }


    private void validate(SalesOrderCreateRequest request) {
        if (request.getSaleDate() == null) {
            throw new IllegalArgumentException("saleDate is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("items are required");
        }
    }
}
