package br.com.bratatouille.management.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private static final int MONEY_SCALE = 2;
    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_UP;

    private MoneyUtils() {
    }

    public static BigDecimal normalize(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("money value is required");
        }

        return value.setScale(MONEY_SCALE, MONEY_ROUNDING);
    }

    public static boolean equals(BigDecimal left, BigDecimal right) {
        return normalize(left).compareTo(normalize(right)) == 0;
    }

    public static boolean isPositive(BigDecimal value) {
        return normalize(value).compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(BigDecimal value) {
        return normalize(value).compareTo(BigDecimal.ZERO) < 0;
    }
}