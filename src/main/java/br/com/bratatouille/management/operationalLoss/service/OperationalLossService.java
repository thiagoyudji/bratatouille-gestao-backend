package br.com.bratatouille.management.operationalLoss.service;

import br.com.bratatouille.management.generated.model.OperationalLossCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalLossResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.operationalLoss.entity.OperationalLoss;
import br.com.bratatouille.management.operationalLoss.entity.OperationalLossReason;
import br.com.bratatouille.management.operationalLoss.mapper.OperationalLossMapper;
import br.com.bratatouille.management.operationalLoss.repository.OperationalLossRepository;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseItemRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OperationalLossService {

    private final OperationalLossRepository operationalLossRepository;
    private final ItemRepository itemRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductionRepository productionRepository;
    private final StockService stockService;
    private final SellableStockService sellableStockService;
    private final OperationalLossMapper operationalLossMapper;

    public OperationalLossService(
            OperationalLossRepository operationalLossRepository,
            ItemRepository itemRepository,
            PurchaseItemRepository purchaseItemRepository,
            ProductionRepository productionRepository,
            StockService stockService,
            SellableStockService sellableStockService,
            OperationalLossMapper operationalLossMapper
    ) {
        this.operationalLossRepository = operationalLossRepository;
        this.itemRepository = itemRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.productionRepository = productionRepository;
        this.stockService = stockService;
        this.sellableStockService = sellableStockService;
        this.operationalLossMapper = operationalLossMapper;
    }

    @Transactional
    public OperationalLossResponse create(OperationalLossCreateRequest request) {
        validateRequest(request);

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        BigDecimal unitCost = findUnitCost(item);

        stockService.removeForOperationalLoss(item, request.getQuantity());

        if (item.getType() == ItemType.FINISHED_PRODUCT) {
            sellableStockService.decreaseAfterLossIfConfigured(item, request.getQuantity());
        }

        OperationalLoss loss = OperationalLoss.create(
                request.getLossDate(),
                item,
                request.getQuantity(),
                OperationalLossReason.valueOf(String.valueOf(request.getReason())),
                unitCost,
                request.getNote()
        );

        OperationalLoss saved = operationalLossRepository.save(loss);

        return operationalLossMapper.toResponse(saved);
    }

    public List<OperationalLossResponse> findAll() {
        return operationalLossRepository.findAll()
                .stream()
                .map(operationalLossMapper::toResponse)
                .toList();
    }

    public OperationalLossResponse findById(Long id) {
        OperationalLoss loss = operationalLossRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operational loss not found"));

        return operationalLossMapper.toResponse(loss);
    }

    private void validateRequest(OperationalLossCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        if (request.getLossDate() == null) {
            throw new IllegalArgumentException("lossDate is required");
        }

        if (request.getItemId() == null) {
            throw new IllegalArgumentException("itemId is required");
        }

        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (request.getReason() == null) {
            throw new IllegalArgumentException("reason is required");
        }

        try {
            OperationalLossReason.valueOf(String.valueOf(request.getReason()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid operational loss reason");
        }
    }

    private BigDecimal findUnitCost(Item item) {
        BigDecimal unitCost = item.getType() == ItemType.FINISHED_PRODUCT
                ? productionRepository.findAverageUnitCostByOutputItemId(item.getId())
                : purchaseItemRepository.findAverageUnitCostByItemId(item.getId());

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Item has no cost history: " + item.getName());
        }

        return unitCost;
    }
}