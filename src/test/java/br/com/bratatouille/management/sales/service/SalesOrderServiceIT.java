package br.com.bratatouille.management.sales.service;

import br.com.bratatouille.management.generated.model.SalesOrderCreateRequest;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerAddress;
import br.com.bratatouille.management.generated.model.SalesOrderCustomerType;
import br.com.bratatouille.management.generated.model.SalesOrderItemRequest;
import br.com.bratatouille.management.generated.model.SalesOrderResponse;
import br.com.bratatouille.management.generated.model.SellableStockUpsertRequest;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.sales.repository.SalesOrderRepository;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import br.com.bratatouille.management.sellableStock.service.SellableStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SalesOrderServiceIT {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SellableStockService sellableStockService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SellableStockRepository sellableStockRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Test
    void createSalesOrderDecreasesSellableStockAndCalculatesProfit() {
        long initialSalesOrderCount = salesOrderRepository.count();

        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setInfinite(true);
        sellableStockRequest.setActive(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 4));
        request.setCustomerName("Walk-in customer");
        request.setNote("First sale");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(pizza.getId());
        itemRequest.setQuantity(new BigDecimal("2"));
        request.setItems(List.of(itemRequest));

        SalesOrderResponse response = salesOrderService.create(request);

        assertNotNull(response.getId());
        assertEquals(LocalDate.of(2026, 8, 4), response.getSaleDate());
        assertEquals(new BigDecimal("37.00"), response.getTotalAmount());
        assertEquals(new BigDecimal("0.00"), response.getTotalCost());
        assertEquals(new BigDecimal("37.00"), response.getGrossProfit());
        assertEquals(1, response.getItems().size());
        assertEquals(Boolean.TRUE, response.getItems().get(0).getCostIncomplete());

        SellableStock sellableStock = sellableStockRepository.findByItemId(pizza.getId()).orElseThrow();

        assertEquals(initialSalesOrderCount + 1, salesOrderRepository.count());
        assertEquals(pizza.getId(), response.getItems().get(0).getItemId());
    }

    @Test
    void createSalesOrderUsesPfPriceForGuestAndPjPriceForBusinessCustomer() {
        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setInfinite(true);
        sellableStockRequest.setActive(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        SalesOrderCreateRequest guestRequest = new SalesOrderCreateRequest();
        guestRequest.setSaleDate(LocalDate.of(2026, 8, 5));
        guestRequest.setCustomerName("Guest customer");
        guestRequest.setItems(List.of(orderItem(pizza.getId())));

        SalesOrderResponse guestResponse = salesOrderService.create(guestRequest);

        assertEquals(SalesOrderCustomerType.GUEST, guestResponse.getCustomerType());
        assertEquals(new BigDecimal("18.50"), guestResponse.getItems().get(0).getUnitPrice());
        assertEquals(new BigDecimal("18.50"), guestResponse.getItems().get(0).getUnitPricePf());
        assertEquals(new BigDecimal("24.90"), guestResponse.getItems().get(0).getUnitPricePj());

        SalesOrderCreateRequest pjRequest = new SalesOrderCreateRequest();
        pjRequest.setSaleDate(LocalDate.of(2026, 8, 5));
        pjRequest.setCustomerType(SalesOrderCustomerType.PJ);
        pjRequest.setCustomerName("Business customer");
        pjRequest.setItems(List.of(orderItem(pizza.getId())));

        SalesOrderResponse pjResponse = salesOrderService.create(pjRequest);

        assertEquals(SalesOrderCustomerType.PJ, pjResponse.getCustomerType());
        assertEquals(new BigDecimal("24.90"), pjResponse.getItems().get(0).getUnitPrice());
        assertEquals(new BigDecimal("18.50"), pjResponse.getItems().get(0).getUnitPricePf());
        assertEquals(new BigDecimal("24.90"), pjResponse.getItems().get(0).getUnitPricePj());
    }

    @Test
    void createSalesOrderUsesBackendPricesWithoutClientPriceFields() {
        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setInfinite(true);
        sellableStockRequest.setActive(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 5));
        request.setCustomerType(SalesOrderCustomerType.PJ);
        request.setCustomerName("Business customer");

        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(pizza.getId());
        itemRequest.setQuantity(new BigDecimal("1"));
        request.setItems(List.of(itemRequest));

        SalesOrderResponse response = salesOrderService.create(request);

        assertEquals(new BigDecimal("24.90"), response.getItems().get(0).getUnitPrice());
        assertEquals(new BigDecimal("18.50"), response.getItems().get(0).getUnitPricePf());
        assertEquals(new BigDecimal("24.90"), response.getItems().get(0).getUnitPricePj());
    }

    @Test
    void createSalesOrderKeepsPartialDeliveryAddressSnapshot() {
        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setInfinite(true);
        sellableStockRequest.setActive(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 5));
        request.setCustomerName("Walk-in customer");
        request.setDeliveryAddress(new SalesOrderCustomerAddress()
                .zipCode("01001000")
                .city("Sao Paulo")
                .defaultAddress(true));
        request.setItems(List.of(orderItem(pizza.getId())));

        SalesOrderResponse response = salesOrderService.create(request);

        assertNotNull(response.getDeliveryAddress());
        assertEquals("01001000", response.getDeliveryAddress().getZipCode());
        assertEquals("Sao Paulo", response.getDeliveryAddress().getCity());
        assertEquals(Boolean.TRUE, response.getDeliveryAddress().getDefaultAddress());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void createSalesOrderRollsBackWhenSellableStockIsMissingForSecondItem() {
        long initialSalesOrderCount = salesOrderRepository.count();

        Item pizza = itemRepository.save(new Item(
                "Pizza Margherita",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        Item burger = itemRepository.save(new Item(
                "Burger",
                ItemType.FINISHED_PRODUCT,
                UnitType.UN,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        ));

        SellableStockUpsertRequest sellableStockRequest = new SellableStockUpsertRequest();
        sellableStockRequest.setInfinite(true);
        sellableStockRequest.setActive(true);
        sellableStockService.upsert(pizza.getId(), sellableStockRequest);

        SalesOrderCreateRequest request = new SalesOrderCreateRequest();
        request.setSaleDate(LocalDate.of(2026, 8, 4));
        request.setCustomerName("Walk-in customer");
        request.setNote("Rollback sale");

        SalesOrderItemRequest firstItem = new SalesOrderItemRequest();
        firstItem.setItemId(pizza.getId());
        firstItem.setQuantity(new BigDecimal("2"));

        SalesOrderItemRequest secondItem = new SalesOrderItemRequest();
        secondItem.setItemId(burger.getId());
        secondItem.setQuantity(new BigDecimal("1"));

        request.setItems(List.of(firstItem, secondItem));

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> salesOrderService.create(request));

        assertEquals(initialSalesOrderCount, salesOrderRepository.count());
        assertTrue(sellableStockRepository.findByItemId(burger.getId()).isEmpty());
    }

    private SalesOrderItemRequest orderItem(Long itemId) {
        SalesOrderItemRequest itemRequest = new SalesOrderItemRequest();
        itemRequest.setItemId(itemId);
        itemRequest.setQuantity(new BigDecimal("1"));
        return itemRequest;
    }
}
