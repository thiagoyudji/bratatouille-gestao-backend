package br.com.bratatouille.management.item.service;

import br.com.bratatouille.management.generated.model.CreateItemRequest;
import br.com.bratatouille.management.generated.model.ItemResponse;
import br.com.bratatouille.management.item.repository.ItemRepository;
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
class ItemServiceIT {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void createAndFindItemPersistsInventoryRules() {
        long initialCount = itemRepository.count();

        CreateItemRequest request = new CreateItemRequest();
        request.setName("Flour");
        request.setType(CreateItemRequest.TypeEnum.INGREDIENT);
        request.setBaseUnit(CreateItemRequest.BaseUnitEnum.G);
        request.setLowStockThreshold(new BigDecimal("10.000"));
        request.setCriticalStockThreshold(new BigDecimal("5.000"));
        request.setPricePf(new BigDecimal("18.50"));
        request.setPricePj(new BigDecimal("24.90"));

        ItemResponse created = itemService.create(request);

        assertNotNull(created.getId());
        assertEquals("Flour", created.getName());
        assertEquals(CreateItemRequest.TypeEnum.INGREDIENT, CreateItemRequest.TypeEnum.valueOf(created.getType().name()));
        assertEquals(0, new BigDecimal("10.000").compareTo(created.getLowStockThreshold()));
        assertEquals(0, new BigDecimal("5.000").compareTo(created.getCriticalStockThreshold()));
        assertEquals(new BigDecimal("18.50"), created.getPricePf());
        assertEquals(new BigDecimal("24.90"), created.getPricePj());

        ItemResponse found = itemService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Flour", found.getName());
        assertEquals(initialCount + 1, itemRepository.count());
    }

    @Test
    void createRejectsInvalidThresholdHierarchy() {
        long initialCount = itemRepository.count();

        CreateItemRequest request = new CreateItemRequest();
        request.setName("Invalid Item");
        request.setType(CreateItemRequest.TypeEnum.PACKAGING);
        request.setBaseUnit(CreateItemRequest.BaseUnitEnum.UN);
        request.setLowStockThreshold(new BigDecimal("2.000"));
        request.setCriticalStockThreshold(new BigDecimal("3.000"));
        request.setPricePf(new BigDecimal("18.50"));
        request.setPricePj(new BigDecimal("24.90"));

        assertThrows(IllegalArgumentException.class, () -> itemService.create(request));
        assertEquals(initialCount, itemRepository.count());
    }

    @Test
    void createRejectsMissingPrices() {
        long initialCount = itemRepository.count();

        CreateItemRequest request = new CreateItemRequest();
        request.setName("No Price Item");
        request.setType(CreateItemRequest.TypeEnum.PACKAGING);
        request.setBaseUnit(CreateItemRequest.BaseUnitEnum.UN);
        request.setLowStockThreshold(new BigDecimal("2.000"));
        request.setCriticalStockThreshold(new BigDecimal("1.000"));

        assertThrows(IllegalArgumentException.class, () -> itemService.create(request));
        assertEquals(initialCount, itemRepository.count());
    }
}
