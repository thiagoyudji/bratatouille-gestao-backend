package br.com.bratatouille.management.purchase.service;

import br.com.bratatouille.management.generated.model.PurchaseCreateRequest;
import br.com.bratatouille.management.generated.model.PurchaseItemRequest;
import br.com.bratatouille.management.generated.model.PurchaseResponse;
import br.com.bratatouille.management.generated.model.PurchaseSplitRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.entity.Purchase;
import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import br.com.bratatouille.management.purchase.mapper.PurchaseMapper;
import br.com.bratatouille.management.purchase.repository.PurchaseRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;

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
        Partner payer = getValidPartner(request.getPaidByPartnerId());

        List<PurchaseItemData> items = request.getItems()
                .stream()
                .map(this::toItemData)
                .toList();

        List<PurchaseSplitData> splits = request.getSplits()
                .stream()
                .map(this::toSplitData)
                .toList();

        Purchase purchase = Purchase.create(
                request.getPurchaseDate(),
                payer,
                request.getNote(),
                items,
                splits
        );

        Purchase savedPurchase = purchaseRepository.save(purchase);

        registerStockEntries(savedPurchase);

        return purchaseMapper.toResponse(savedPurchase);
    }

    private PurchaseItemData toItemData(PurchaseItemRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        return new PurchaseItemData(
                item,
                request.getQuantity(),
                request.getUnit(),
                request.getTotalValue()
        );
    }

    private PurchaseSplitData toSplitData(PurchaseSplitRequest request) {
        Partner partner = getValidPartner(request.getPartnerId());

        return new PurchaseSplitData(
                partner,
                request.getAmount()
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
            stockService.addFromPurchase(
                    item.getItem(),
                    item.getQuantity()
            );
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
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found"));

        return purchaseMapper.toResponse(purchase);
    }
}