package br.com.bratatouille.management.purchase.entity;

import br.com.bratatouille.management.support.builder.ItemBuilder;
import br.com.bratatouille.management.support.builder.PartnerBuilder;
import br.com.bratatouille.management.support.builder.PurchaseBuilder;
import br.com.bratatouille.management.item.entity.UnitType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseTest {

    @Test
    void createBuildsPurchaseAndCalculatesTotalFromItems() {
        Purchase purchase = new PurchaseBuilder()
                .withPurchaseDate(LocalDate.of(2026, 8, 1))
                .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                .withSupplier("Supplier X")
                .withNote("note")
                .addItem(new ItemBuilder().withId(10L).withName("Flour").withBaseUnit(UnitType.UN).build(), new BigDecimal("2"), "UN", new BigDecimal("12.50"))
                .addItem(new ItemBuilder().withId(11L).withName("Sugar").withBaseUnit(UnitType.UN).build(), new BigDecimal("1"), "UN", new BigDecimal("7.50"))
                .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("60.00"), new BigDecimal("12.00"))
                .addSplit(new PartnerBuilder().withId(3L).withName("Partner B").build(), new BigDecimal("40.00"), new BigDecimal("8.00"))
                .build();

        assertEquals(new BigDecimal("20.00"), purchase.getTotalAmount());
        assertEquals(2, purchase.getItems().size());
        assertEquals(2, purchase.getSplits().size());
        assertEquals("Supplier X", purchase.getSupplier());
    }

    @Test
    void createRejectsMissingItems() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PurchaseBuilder()
                        .withPurchaseDate(LocalDate.of(2026, 8, 1))
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withSupplier("Supplier X")
                        .withNote("note")
                        .build()
        );
    }

    @Test
    void createRejectsDuplicatedItems() {
        var flour = new ItemBuilder().withId(10L).withName("Flour").withBaseUnit(UnitType.UN).build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PurchaseBuilder()
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withSupplier("Supplier X")
                        .addItem(flour, new BigDecimal("1"), "UN", new BigDecimal("5.00"))
                        .addItem(flour, new BigDecimal("2"), "UN", new BigDecimal("10.00"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("100.00"), new BigDecimal("15.00"))
                        .build()
        );

        assertEquals("purchase cannot have duplicated items", exception.getMessage());
    }

    @Test
    void createRejectsMismatchedItemUnit() {
        var flour = new ItemBuilder().withId(10L).withName("Flour").withBaseUnit(UnitType.ML).build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PurchaseBuilder()
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withSupplier("Supplier X")
                        .addItem(flour, new BigDecimal("1"), "UN", new BigDecimal("5.00"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("100.00"), new BigDecimal("5.00"))
                        .build()
        );

        assertEquals("purchase item unit must match item base unit", exception.getMessage());
    }

    @Test
    void createRejectsDuplicatedSplitPartners() {
        var flour = new ItemBuilder().withId(10L).withName("Flour").withBaseUnit(UnitType.UN).build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PurchaseBuilder()
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withSupplier("Supplier X")
                        .addItem(flour, new BigDecimal("1"), "UN", new BigDecimal("5.00"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("50.00"), new BigDecimal("2.50"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("50.00"), new BigDecimal("2.50"))
                        .build()
        );

        assertEquals("purchase split cannot have duplicated partners", exception.getMessage());
    }

    @Test
    void createRejectsWhenSplitTotalDiffersFromPurchaseTotal() {
        var flour = new ItemBuilder().withId(10L).withName("Flour").withBaseUnit(UnitType.UN).build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PurchaseBuilder()
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withSupplier("Supplier X")
                        .addItem(flour, new BigDecimal("1"), "UN", new BigDecimal("5.00"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("60.00"), new BigDecimal("2.00"))
                        .addSplit(new PartnerBuilder().withId(3L).withName("Partner B").build(), new BigDecimal("40.00"), new BigDecimal("2.00"))
                        .build()
        );

        assertEquals("split total must be equal to purchase total", exception.getMessage());
    }
}
