package br.com.bratatouille.management.purchase.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.domain.PartnerPercentageData;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitCalculator;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import br.com.bratatouille.management.purchase.mapper.PurchaseMapper;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final PartnerRepository partnerRepository;
    private final ItemRepository itemRepository;
    private final StockService stockService;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            PurchaseMapper purchaseMapper,
            PartnerRepository partnerRepository,
            ItemRepository itemRepository,
            StockService stockService
    ) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.partnerRepository = partnerRepository;
        this.itemRepository = itemRepository;
        this.stockService = stockService;
    }

    @Transactional
    public PurchaseResponse create(PurchaseCreateRequest request) {
        validate(request);

        Partner payer = partnerRepository.findById(request.getPaidByPartnerId())
                .orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        if (!Boolean.TRUE.equals(payer.getActive())) {
            throw new IllegalArgumentException("payer partner must be active");
        }

        List<PurchaseItemData> items = request.getItems()
                .stream()
                .map(this::toItemData)
                .toList();

        BigDecimal totalAmount = calculateTotalAmount(items);

        List<PurchaseSplitData> splits = resolveSplits(request, totalAmount);

        Purchase purchase = Purchase.create(
                request.getPurchaseDate(),
                payer,
                request.getSupplier(),
                request.getNote(),
                items,
                splits
        );

        Purchase saved = purchaseRepository.save(purchase);

        registerStockEntries(saved);

        return purchaseMapper.toResponse(saved);
    }

    public List<PurchaseResponse> findAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(purchaseMapper::toResponse)
                .toList();
    }

    public PurchaseResponse findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

        return purchaseMapper.toResponse(purchase);
    }

    private List<PurchaseSplitData> resolveSplits(
            PurchaseCreateRequest request,
            BigDecimal totalAmount
    ) {
        if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            return List.of();
        }

        if (request.getSplits() == null || request.getSplits().isEmpty()) {
            List<Partner> activePartners = partnerRepository.findByActiveTrue();

            return PurchaseSplitCalculator.calculateFromDefaultPercentages(
                    totalAmount,
                    activePartners
            );
        }

        List<PartnerPercentageData> customPercentages = request.getSplits()
                .stream()
                .map(this::toPartnerPercentageData)
                .toList();

        return PurchaseSplitCalculator.calculateFromCustomPercentages(
                totalAmount,
                customPercentages
        );
    }

    private PartnerPercentageData toPartnerPercentageData(PurchaseSplitRequest request) {
        Partner partner = partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        return new PartnerPercentageData(
                partner,
                request.getPercentage()
        );
    }

    private PurchaseItemData toItemData(PurchaseItemRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        return new PurchaseItemData(
                item,
                request.getQuantity(),
                String.valueOf(request.getUnit()),
                request.getTotalValue()
        );
    }

    private BigDecimal calculateTotalAmount(List<PurchaseItemData> items) {
        return MoneyUtils.normalize(
                items.stream()
                        .map(PurchaseItemData::totalValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    private void validate(PurchaseCreateRequest request) {
        if (request.getPaidByPartnerId() == null) {
            throw new IllegalArgumentException("paidByPartnerId is required");
        }

        if (request.getPurchaseDate() == null) {
            throw new IllegalArgumentException("purchaseDate is required");
        }

        if (request.getSupplier() == null || request.getSupplier().isBlank()) {
            throw new IllegalArgumentException("supplier is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("items are required");
        }
    }

    private void registerStockEntries(Purchase purchase) {
        for (PurchaseItem item : purchase.getItems()) {
            stockService.addFromPurchase(
                    item.getItem(),
                    item.getQuantity(),
                    purchase.getId()
            );
        }
    }
}