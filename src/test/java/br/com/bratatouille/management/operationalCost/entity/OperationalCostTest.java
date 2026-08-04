package br.com.bratatouille.management.operationalCost.entity;

import br.com.bratatouille.management.support.builder.OperationalCostBuilder;
import br.com.bratatouille.management.support.builder.PartnerBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationalCostTest {

    @Test
    void createBuildsOperationalCostAndValidatesSplitTotal() {
        OperationalCost cost = new OperationalCostBuilder()
                .withCostDate(LocalDate.of(2026, 8, 1))
                .withCategory(OperationalCostCategory.FIXED)
                .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                .withAmount(new BigDecimal("100.00"))
                .withDescription("rent")
                .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("70.00"))
                .addSplit(new PartnerBuilder().withId(3L).withName("Partner B").build(), new BigDecimal("30.00"))
                .build();

        assertEquals(new BigDecimal("100.00"), cost.getAmount());
        assertEquals(2, cost.getSplits().size());
        assertEquals(OperationalCostCategory.FIXED, cost.getCategory());
        assertEquals("rent", cost.getDescription());
    }

    @Test
    void createRejectsInvalidAmount() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new OperationalCostBuilder()
                        .withCostDate(LocalDate.of(2026, 8, 1))
                        .withCategory(OperationalCostCategory.FIXED)
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withAmount(BigDecimal.ZERO)
                        .withDescription("rent")
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("10.00"))
                        .build()
        );

        assertEquals("amount must be greater than zero", exception.getMessage());
    }

    @Test
    void createRejectsMissingSplits() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new OperationalCostBuilder()
                        .withCostDate(LocalDate.of(2026, 8, 1))
                        .withCategory(OperationalCostCategory.FIXED)
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withAmount(new BigDecimal("100.00"))
                        .withDescription("rent")
                        .build()
        );

        assertEquals("operational cost must have at least one split", exception.getMessage());
    }

    @Test
    void createRejectsDuplicatedSplitPartners() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new OperationalCostBuilder()
                        .withCostDate(LocalDate.of(2026, 8, 1))
                        .withCategory(OperationalCostCategory.FIXED)
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withAmount(new BigDecimal("100.00"))
                        .withDescription("rent")
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("50.00"))
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("50.00"))
                        .build()
        );

        assertEquals("operational cost split cannot have duplicated partners", exception.getMessage());
    }

    @Test
    void createRejectsWhenSplitTotalDiffersFromAmount() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new OperationalCostBuilder()
                        .withCostDate(LocalDate.of(2026, 8, 1))
                        .withCategory(OperationalCostCategory.FIXED)
                        .withPaidBy(new PartnerBuilder().withId(1L).withName("Main Partner").build())
                        .withAmount(new BigDecimal("100.00"))
                        .withDescription("rent")
                        .addSplit(new PartnerBuilder().withId(2L).withName("Partner A").build(), new BigDecimal("60.00"))
                        .addSplit(new PartnerBuilder().withId(3L).withName("Partner B").build(), new BigDecimal("20.00"))
                        .build()
        );

        assertEquals("split total must be equal to operational cost amount", exception.getMessage());
    }
}
