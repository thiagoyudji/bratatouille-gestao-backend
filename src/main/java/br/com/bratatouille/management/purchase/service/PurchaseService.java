package br.com.bratatouille.management.purchase.service;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseNewItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.domain.PartnerAmountData;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitCalculator;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import br.com.bratatouille.management.purchase.entity.PurchasePayerType;
import br.com.bratatouille.management.purchase.mapper.PurchaseMapper;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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

        Partner payer = resolvePayer(request);

        if (payer != null && !Boolean.TRUE.equals(payer.getActive())) {
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
                toPayerType(request.getPayerType()),
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

    private Partner resolvePayer(PurchaseCreateRequest request) {
        PurchaseCreateRequest.PayerTypeEnum payerType = request.getPayerType();

        if (payerType == PurchaseCreateRequest.PayerTypeEnum.BRATATOUILLE
                || (payerType == null && request.getPaidByPartnerId() == null)) {
            return null;
        }

        if (payerType == PurchaseCreateRequest.PayerTypeEnum.PARTNER
                && request.getPaidByPartnerId() == null) {
            throw new IllegalArgumentException("paidByPartnerId is required when payerType is PARTNER");
        }

        Partner partner = partnerRepository.findById(request.getPaidByPartnerId())
                .orElseThrow(() -> new NoSuchElementException("Partner not found"));

        validateEligiblePartner(partner);
        return partner;
    }

    private PurchasePayerType toPayerType(PurchaseCreateRequest.PayerTypeEnum payerType) {
        return payerType == PurchaseCreateRequest.PayerTypeEnum.PARTNER
                ? PurchasePayerType.PARTNER
                : PurchasePayerType.BRATATOUILLE;
    }

    private void validateEligiblePartner(Partner partner) {
        if (!Boolean.TRUE.equals(partner.getActive())) {
            throw new IllegalArgumentException("payer partner must be active");
        }

        if (partner.getAuthUser() == null
                || partner.getAuthUser().getRole() != br.com.bratatouille.management.auth.entity.UserRole.ADMIN
                || !Boolean.TRUE.equals(partner.getAuthUser().getActive())) {
            throw new IllegalArgumentException("partner must be associated with an active ADMIN dashboard user");
        }
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> findAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(purchaseMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseResponse findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Purchase not found"));

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
            return List.of();
        }

        List<PartnerAmountData> reimbursements = request.getSplits()
                .stream()
                .map(split -> toPartnerAmountData(split, totalAmount))
                .toList();

        return PurchaseSplitCalculator.calculateFromAmounts(
                totalAmount,
                reimbursements
        );
    }

    private PartnerAmountData toPartnerAmountData(PurchaseSplitRequest request, BigDecimal totalAmount) {
        Partner partner = partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new NoSuchElementException("Partner not found"));
        validateEligiblePartner(partner);
        BigDecimal amount = request.getAmount();
        if (amount == null && request.getPercentage() != null) {
            amount = totalAmount.multiply(request.getPercentage())
                    .divide(new BigDecimal("100"), 6, java.math.RoundingMode.HALF_UP);
        }
        return new PartnerAmountData(partner, amount);
    }

    private PurchaseItemData toItemData(PurchaseItemRequest request) {
        Item item;
        if (request.getItemId() != null) {
            item = itemRepository.findById(request.getItemId())
                    .orElseThrow(() -> new NoSuchElementException("Item not found"));
        } else if (request.getNewItem() != null) {
            PurchaseNewItemRequest newItem = request.getNewItem();
            if (itemRepository.existsByNameIgnoreCase(newItem.getName())) {
                throw new IllegalArgumentException("item name already exists");
            }
            item = itemRepository.save(new Item(
                    newItem.getName(),
                    ItemType.valueOf(newItem.getType().name()),
                    UnitType.valueOf(newItem.getBaseUnit().name()),
                    newItem.getLowStockThreshold(),
                    newItem.getCriticalStockThreshold()
            ));
        } else {
            throw new IllegalArgumentException("itemId or newItem is required");
        }

        if (item.getType() == ItemType.FINISHED_PRODUCT) {
            throw new IllegalArgumentException("finished products cannot be purchased");
        }

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
