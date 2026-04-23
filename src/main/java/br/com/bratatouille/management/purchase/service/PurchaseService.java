package br.com.bratatouille.management.purchase.service;

import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.dto.PurchaseCreateRequest;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.dto.PurchaseItemRequest;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import br.com.bratatouille.management.purchase.dto.PurchaseSplitRequest;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PartnerRepository partnerRepository;
    private final ItemRepository itemRepository;
    private final StockService stockService;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            PartnerRepository partnerRepository,
            ItemRepository itemRepository,
            StockService stockService
    ) {
        this.purchaseRepository = purchaseRepository;
        this.partnerRepository = partnerRepository;
        this.itemRepository = itemRepository;
        this.stockService = stockService;
    }

    @Transactional
    public Purchase create(PurchaseCreateRequest request) {
        Partner payer = getValidPartner(request.paidByPartnerId());

        List<PurchaseItemData> items = request.items()
                .stream()
                .map(this::toItemData)
                .toList();

        List<PurchaseSplitData> splits = request.splits()
                .stream()
                .map(this::toSplitData)
                .toList();

        Purchase purchase = Purchase.create(
                request.purchaseDate(),
                payer,
                request.note(),
                items,
                splits
        );

        Purchase savedPurchase = purchaseRepository.save(purchase);

        registerStockEntries(savedPurchase);

        return savedPurchase;
    }

    private PurchaseItemData toItemData(PurchaseItemRequest request) {
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        return new PurchaseItemData(
                item,
                request.quantity(),
                request.unit(),
                request.totalValue()
        );
    }

    private PurchaseSplitData toSplitData(PurchaseSplitRequest request) {
        Partner partner = getValidPartner(request.partnerId());

        return new PurchaseSplitData(
                partner,
                request.amount()
        );
    }

    private Partner getValidPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        if (!Boolean.TRUE.equals(partner.getActive())) {
            throw new IllegalArgumentException("Partner is inactive");
        }

        return partner;
    }

    private void registerStockEntries(Purchase purchase) {
        for (PurchaseItem item : purchase.getItems()) {
            stockService.add(
                    item.getItem(),
                    item.getQuantity(),
                    "PURCHASE",
                    purchase.getId()
            );
        }
    }
}