package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.sales.domain.SalesOrderItemData;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.mapper.SalesOrderMapper;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ItemRepository itemRepository;
    private final ProductionRepository productionRepository;
    private final StockService stockService;
    private final SellableStockService sellableStockService;
    private final SalesOrderMapper salesOrderMapper;

    public SalesOrderService(
            SalesOrderRepository salesOrderRepository,
            ItemRepository itemRepository,
            ProductionRepository productionRepository,
            StockService stockService,
            SellableStockService sellableStockService,
            SalesOrderMapper salesOrderMapper
    ) {
        this.salesOrderRepository = salesOrderRepository;
        this.itemRepository = itemRepository;
        this.productionRepository = productionRepository;
        this.stockService = stockService;
        this.sellableStockService = sellableStockService;
        this.salesOrderMapper = salesOrderMapper;
    }

    @Transactional
    public SalesOrderResponse create(SalesOrderCreateRequest request) {
        validateRequest(request);
        validateDuplicatedItems(request.getItems());

        List<SalesOrderItemData> itemsData = request.getItems()
                .stream()
                .sorted(Comparator.comparing(SalesOrderItemRequest::getItemId))
                .map(this::buildItemData)
                .toList();

        itemsData.forEach(itemData -> {
            sellableStockService.decreaseAfterSale(itemData.item(), itemData.quantity());
            stockService.removeForSale(itemData.item(), itemData.quantity());
        });

        SalesOrder salesOrder = SalesOrder.create(
                request.getSaleDate(),
                request.getCustomerName(),
                request.getNote(),
                itemsData
        );

        SalesOrder saved = salesOrderRepository.save(salesOrder);

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

    private SalesOrderItemData buildItemData(SalesOrderItemRequest itemRequest) {
        Item item = itemRepository.findById(itemRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        validateFinishedProduct(item);
        validateItemRequest(itemRequest);

        BigDecimal unitCost = productionRepository.findAverageUnitCostByOutputItemId(item.getId());

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Item has no production cost history: " + item.getName());
        }

        return new SalesOrderItemData(
                item,
                itemRequest.getQuantity(),
                itemRequest.getUnitPrice(),
                unitCost
        );
    }

    private void validateRequest(SalesOrderCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        if (request.getSaleDate() == null) {
            throw new IllegalArgumentException("saleDate is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("sale must have at least one item");
        }
    }

    private void validateItemRequest(SalesOrderItemRequest itemRequest) {
        if (itemRequest.getQuantity() == null || itemRequest.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (itemRequest.getUnitPrice() == null || itemRequest.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitPrice must be greater than zero");
        }
    }

    private void validateFinishedProduct(Item item) {
        if (item.getType() != ItemType.FINISHED_PRODUCT) {
            throw new IllegalArgumentException("Only finished products can be sold");
        }

        if (!Boolean.TRUE.equals(item.isActive())) {
            throw new IllegalArgumentException("Item is inactive: " + item.getName());
        }
    }

    private void validateDuplicatedItems(List<SalesOrderItemRequest> items) {
        long distinctItems = items.stream()
                .map(SalesOrderItemRequest::getItemId)
                .distinct()
                .count();

        if (distinctItems != items.size()) {
            throw new IllegalArgumentException("sale cannot have duplicated items");
        }
    }
}