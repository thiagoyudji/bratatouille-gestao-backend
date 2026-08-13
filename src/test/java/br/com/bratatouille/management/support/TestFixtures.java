package br.com.bratatouille.management.support;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.generated.model.FinancialSummaryResponse;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.operationalCost.domain.OperationalCostSplitData;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.purchase.domain.PurchaseItemData;
import br.com.bratatouille.management.purchase.domain.PurchaseSplitData;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public final class TestFixtures {

    private TestFixtures() {
    }

    public static Partner partner(Long id, String name) {
        Partner partner = new Partner(name, true, new BigDecimal("0.00"), LocalDateTime.now(), Set.of(PartnerRole.ADMIN));
        ReflectionTestUtils.setField(partner, "id", id);
        return partner;
    }

    public static AuthUser authUser(String username, String passwordHash, UserRole role, boolean active) {
        AuthUser user = new AuthUser(username, passwordHash, role);
        ReflectionTestUtils.setField(user, "active", active);
        return user;
    }

    public static FinancialSummaryResponse financialSummary(
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal totalPurchases,
            BigDecimal totalOperationalCosts,
            BigDecimal totalSpent
    ) {
        FinancialSummaryResponse summary = new FinancialSummaryResponse();
        summary.setStartDate(startDate);
        summary.setEndDate(endDate);
        summary.setTotalPurchases(totalPurchases);
        summary.setTotalOperationalCosts(totalOperationalCosts);
        summary.setTotalSpent(totalSpent);
        return summary;
    }

    public static Item item(Long id, String name, UnitType baseUnit) {
        Item item = new Item(
                name,
                ItemType.INGREDIENT,
                baseUnit,
                new BigDecimal("10.000"),
                new BigDecimal("5.000"),
                new BigDecimal("18.50"),
                new BigDecimal("24.90")
        );
        ReflectionTestUtils.setField(item, "id", id);
        return item;
    }

    public static PurchaseItemData purchaseItemData(Item item, BigDecimal quantity, String unit, BigDecimal totalValue) {
        return new PurchaseItemData(item, quantity, unit, totalValue);
    }

    public static PurchaseSplitData purchaseSplitData(Partner partner, BigDecimal percentage, BigDecimal owedAmount) {
        return new PurchaseSplitData(partner, percentage, owedAmount);
    }

    public static OperationalCostSplitData operationalCostSplitData(Partner partner, BigDecimal amount) {
        return new OperationalCostSplitData(partner, amount);
    }
}
