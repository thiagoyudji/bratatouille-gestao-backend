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
import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OperationalLossService {

    private final OperationalLossRepository operationalLossRepository;
    private final OperationalLossMapper operationalLossMapper;
    private final ItemRepository itemRepository;
    private final CostService costService;
    private final StockService stockService;

    public OperationalLossService(
            OperationalLossRepository operationalLossRepository,
            OperationalLossMapper operationalLossMapper,
            ItemRepository itemRepository,
            CostService costService,
            StockService stockService
    ) {
        this.operationalLossRepository = operationalLossRepository;
        this.operationalLossMapper = operationalLossMapper;
        this.itemRepository = itemRepository;
        this.costService = costService;
        this.stockService = stockService;
    }

    @Transactional
    public OperationalLossResponse create(OperationalLossCreateRequest request) {
        validate(request);

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NoSuchElementException("Item not found"));

        BigDecimal unitCost = costService.findRequiredUnitCost(item);

        OperationalLoss loss = OperationalLoss.create(
                request.getLossDate(),
                item,
                request.getQuantity(),
                OperationalLossReason.valueOf(String.valueOf(request.getReason())),
                unitCost,
                request.getNote()
        );

        OperationalLoss saved = operationalLossRepository.save(loss);

        stockService.removeForOperationalLoss(item, request.getQuantity(), saved.getId());

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
                .orElseThrow(() -> new NoSuchElementException("Operational loss not found"));

        return operationalLossMapper.toResponse(loss);
    }

    private void validate(OperationalLossCreateRequest request) {
        if (request.getLossDate() == null) {
            throw new IllegalArgumentException("lossDate is required");
        }

        if (request.getItemId() == null) {
            throw new IllegalArgumentException("itemId is required");
        }

        if (request.getQuantity() == null || request.getQuantity().signum() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        if (request.getReason() == null) {
            throw new IllegalArgumentException("reason is required");
        }
    }
}
