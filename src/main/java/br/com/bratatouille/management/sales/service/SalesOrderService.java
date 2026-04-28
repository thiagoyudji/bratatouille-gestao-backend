package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseItemRepository;
import br.com.bratatouille.management.sales.domain.SalesOrderItemData;
import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.mapper.SalesOrderMapper;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderMapper salesOrderMapper;
    private final ItemRepository itemRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductionRepository productionRepository;
    private final SellableStockService sellableStockService;
    private final StockService stockService;

    public SalesOrderService(
            SalesOrderRepository salesOrderRepository,
            SalesOrderMapper salesOrderMapper,
            ItemRepository itemRepository,
            PurchaseItemRepository purchaseItemRepository,
            ProductionRepository productionRepository,
            SellableStockService sellableStockService,
            StockService stockService
    ) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderMapper = salesOrderMapper;
        this.itemRepository = itemRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.productionRepository = productionRepository;
        this.sellableStockService = sellableStockService;
        this.stockService = stockService;
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

        itemsData.forEach(itemData -> {
            sellableStockService.decreaseAfterSale(itemData.item(), itemData.quantity());
            stockService.removeForSale(itemData.item(), itemData.quantity(), saved.getId());
        });

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

        BigDecimal unitCost = findUnitCost(item);

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cost history not found for item: " + item.getName());
        }

        return new SalesOrderItemData(
                item,
                request.getQuantity(),
                request.getUnitPrice(),
                unitCost
        );
    }

    private BigDecimal findUnitCost(Item item) {
        if (item.getType() == ItemType.FINISHED_PRODUCT) {
            return productionRepository.findAverageUnitCostByOutputItemId(item.getId());
        }

        return purchaseItemRepository.findAverageUnitCostByItemId(item.getId());
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