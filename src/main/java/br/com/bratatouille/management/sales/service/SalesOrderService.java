package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.sales.domain.SalesOrderItemData;
import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.mapper.SalesOrderMapper;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

        List<SalesOrderItemData> itemsData = request.getItems()
                .stream()
                .map(this::toItemData)
                .toList();

        SalesOrder salesOrder = SalesOrder.create(
                request.getSaleDate(),
                request.getCustomerName(),
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
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found"));

        return salesOrderMapper.toResponse(salesOrder);
    }

    private SalesOrderItemData toItemData(SalesOrderItemRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        BigDecimal unitCost = costService.findUnitCostOrZero(item);
        boolean costIncomplete = costService.isCostIncomplete(unitCost);

        return new SalesOrderItemData(
                item,
                request.getQuantity(),
                request.getUnitPrice(),
                unitCost,
                costIncomplete
        );
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