package br.com.bratatouille.management.purchase.domain;

import br.com.bratatouille.management.common.util.MoneyUtils;
import br.com.bratatouille.management.partner.entity.Partner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class PurchaseSplitCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");

    private PurchaseSplitCalculator() {
    }

    public static List<PurchaseSplitData> calculateFromDefaultPercentages(
            BigDecimal totalAmount,
            List<Partner> partners
    ) {
        if (partners == null || partners.isEmpty()) {
            throw new IllegalArgumentException("active partners are required to calculate purchase split");
        }

        List<PurchaseSplitData> splits = partners.stream()
                .map(partner -> calculate(totalAmount, partner, partner.getDefaultSplitPercentage()))
                .toList();

        validatePercentageTotal(splits);
        validateAmountTotal(totalAmount, splits);

        return splits;
    }

    public static List<PurchaseSplitData> calculateFromCustomPercentages(
            BigDecimal totalAmount,
            List<PartnerPercentageData> percentages
    ) {
        if (percentages == null || percentages.isEmpty()) {
            throw new IllegalArgumentException("custom splits are required");
        }

        List<PurchaseSplitData> splits = percentages.stream()
                .map(data -> calculate(totalAmount, data.partner(), data.percentage()))
                .toList();

        validatePercentageTotal(splits);
        validateAmountTotal(totalAmount, splits);

        return splits;
    }

    private static PurchaseSplitData calculate(
            BigDecimal totalAmount,
            Partner partner,
            BigDecimal percentage
    ) {
        validatePartner(partner);
        validatePercentage(percentage);

        BigDecimal owedAmount = MoneyUtils.normalize(
                totalAmount
                        .multiply(percentage)
                        .divide(ONE_HUNDRED, 6, RoundingMode.HALF_UP)
        );

        return new PurchaseSplitData(partner, percentage, owedAmount);
    }

    private static void validatePercentageTotal(List<PurchaseSplitData> splits) {
        BigDecimal totalPercentage = splits.stream()
                .map(PurchaseSplitData::percentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        if (totalPercentage.compareTo(ONE_HUNDRED) != 0) {
            throw new IllegalArgumentException("purchase split percentages must sum 100");
        }
    }

    private static void validateAmountTotal(
            BigDecimal totalAmount,
            List<PurchaseSplitData> splits
    ) {
        BigDecimal splitTotal = MoneyUtils.normalize(
                splits.stream()
                        .map(PurchaseSplitData::owedAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        if (!MoneyUtils.equals(totalAmount, splitTotal)) {
            throw new IllegalArgumentException("purchase split amount total must be equal to purchase total");
        }
    }

    private static void validatePartner(Partner partner) {
        if (partner == null) {
            throw new IllegalArgumentException("partner is required");
        }

        if (!Boolean.TRUE.equals(partner.getActive())) {
            throw new IllegalArgumentException("inactive partner cannot be used in purchase split");
        }
    }

    private static void validatePercentage(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("split percentage must be greater than zero");
        }

        if (percentage.compareTo(ONE_HUNDRED) > 0) {
            throw new IllegalArgumentException("split percentage cannot be greater than 100");
        }
    }
}