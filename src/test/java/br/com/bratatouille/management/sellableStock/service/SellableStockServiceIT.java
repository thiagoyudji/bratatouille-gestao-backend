package br.com.bratatouille.management.sellableStock.service;

import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SellableStockServiceIT {

    @Autowired
    private SellableStockService sellableStockService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SellableStockRepository sellableStockRepository;

    @Test
    void upsertCreatesThenUpdatesSellableStock() {
        long initialCount = sellableStockRepository.count();

        Item pizza = saveItem("Pizza", ItemType.FINISHED_PRODUCT);

        SellableStockUpsertRequest createRequest = new SellableStockUpsertRequest();
        createRequest.setAvailableQuantity(new BigDecimal("12.000"));
        createRequest.setInfinite(false);
        createRequest.setEnabled(true);

        SellableStockResponse created = sellableStockService.upsert(pizza.getId(), createRequest);

        assertEquals(pizza.getId(), created.getItemId());
        assertEquals("Pizza", created.getItemName());
        assertEquals(0, new BigDecimal("12.000").compareTo(created.getAvailableQuantity()));
        assertEquals(0, new BigDecimal("0.000").compareTo(created.getCurrentStockQuantity()));
        assertEquals(new BigDecimal("18.50"), created.getPricePf());
        assertEquals(new BigDecimal("24.90"), created.getPricePj());
        assertEquals(Boolean.FALSE, created.getInfinite());
        assertEquals(Boolean.TRUE, created.getEnabled());

        SellableStockUpsertRequest updateRequest = new SellableStockUpsertRequest();
        updateRequest.setAvailableQuantity(new BigDecimal("7.500"));
        updateRequest.setInfinite(true);
        updateRequest.setEnabled(false);

        SellableStockResponse updated = sellableStockService.upsert(pizza.getId(), updateRequest);

        assertEquals(pizza.getId(), updated.getItemId());
        assertEquals(0, new BigDecimal("7.500").compareTo(updated.getAvailableQuantity()));
        assertEquals(new BigDecimal("18.50"), updated.getPricePf());
        assertEquals(new BigDecimal("24.90"), updated.getPricePj());
        assertEquals(Boolean.TRUE, updated.getInfinite());
        assertEquals(Boolean.FALSE, updated.getEnabled());
        assertEquals(initialCount + 1, sellableStockRepository.count());
    }

    @Test
    void upsertRejectsNonFinishedProduct() {
        long initialCount = sellableStockRepository.count();

        Item flour = saveItem("Flour", ItemType.INGREDIENT);

        SellableStockUpsertRequest request = new SellableStockUpsertRequest();
        request.setAvailableQuantity(new BigDecimal("1.000"));
        request.setInfinite(false);
        request.setEnabled(true);

        assertThrows(IllegalArgumentException.class, () -> sellableStockService.upsert(flour.getId(), request));
        assertEquals(initialCount, sellableStockRepository.count());
    }

    private Item saveItem(String name, ItemType type) {
        Item item = new Item(
                name,
                type,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        );

        return itemRepository.save(item);
    }
}
