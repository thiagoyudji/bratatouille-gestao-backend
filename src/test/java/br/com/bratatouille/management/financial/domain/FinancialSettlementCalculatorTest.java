package br.com.bratatouille.management.financial.domain;

import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.support.builder.PartnerBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinancialSettlementCalculatorTest {

    private final FinancialSettlementCalculator calculator = new FinancialSettlementCalculator();

    @Test
    void calculatePairsDebtorsAndCreditorsByLargestAmountsFirst() {
        var creditor = new PartnerBuilder().withId(1L).withName("Creditor").build();
        var debtorOne = new PartnerBuilder().withId(2L).withName("Debtor One").build();
        var debtorTwo = new PartnerBuilder().withId(3L).withName("Debtor Two").build();

        List<PixTransfer> transfers = calculator.calculate(
                List.of(
                        new PartnerBalance(creditor, new BigDecimal("100.00")),
                        new PartnerBalance(debtorOne, new BigDecimal("-60.00")),
                        new PartnerBalance(debtorTwo, new BigDecimal("-40.00"))
                )
        );

        assertEquals(2, transfers.size());
        assertTransfer(transfers.get(0), debtorOne, creditor, "60.00");
        assertTransfer(transfers.get(1), debtorTwo, creditor, "40.00");
    }

    @Test
    void calculateReturnsEmptyListWhenThereIsNoOppositeBalance() {
        var creditor = new PartnerBuilder().withId(1L).withName("Creditor").build();

        List<PixTransfer> transfers = calculator.calculate(
                List.of(new PartnerBalance(creditor, new BigDecimal("25.00")))
        );

        assertEquals(List.of(), transfers);
    }

    private void assertTransfer(PixTransfer transfer, Partner from, Partner to, String amount) {
        assertEquals(from, transfer.from());
        assertEquals(to, transfer.to());
        assertEquals(new BigDecimal(amount), transfer.amount());
    }
}
