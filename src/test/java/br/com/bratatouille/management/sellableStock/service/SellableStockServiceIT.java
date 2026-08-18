package br.com.bratatouille.management.sellableStock.service;

import br.com.bratatouille.management.generated.model.SellableStockAdminResponse;
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
        createRequest.setInfinite(true);
        createRequest.setActive(true);

        SellableStockAdminResponse created = sellableStockService.upsert(pizza.getId(), createRequest);

        assertEquals(pizza.getId(), created.getItemId());
        assertEquals("Pizza", created.getItemName());
        assertEquals(new BigDecimal("18.50"), created.getPricePf());
        assertEquals(new BigDecimal("24.90"), created.getPricePj());
        assertEquals(Boolean.TRUE, created.getInfinite());
        assertEquals(Boolean.TRUE, created.getActive());

        SellableStockUpsertRequest updateRequest = new SellableStockUpsertRequest();
        updateRequest.setInfinite(true);
        updateRequest.setActive(false);

        SellableStockAdminResponse updated = sellableStockService.upsert(pizza.getId(), updateRequest);

        assertEquals(pizza.getId(), updated.getItemId());
        assertEquals(new BigDecimal("18.50"), updated.getPricePf());
        assertEquals(new BigDecimal("24.90"), updated.getPricePj());
        assertEquals(Boolean.TRUE, updated.getInfinite());
        assertEquals(Boolean.FALSE, updated.getActive());
        assertEquals(initialCount + 1, sellableStockRepository.count());
    }

    @Test
    void upsertRejectsNonFinishedProduct() {
        long initialCount = sellableStockRepository.count();

        Item flour = saveItem("Flour", ItemType.INGREDIENT);

        SellableStockUpsertRequest request = new SellableStockUpsertRequest();
        request.setInfinite(true);
        request.setActive(true);

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
