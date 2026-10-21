package br.com.bratatouille.management.item.domain;

import br.com.bratatouille.management.item.entity.UnitType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converte unidades de entrada para a unidade canônica do item.
 * Nenhuma conversão implícita de massa para volume é permitida.
 */
public final class QuantityUnitConverter {

    private static final BigDecimal THOUSAND = new BigDecimal("1000");

    private QuantityUnitConverter() {
    }

    public static BigDecimal toBase(BigDecimal quantity, String suppliedUnit, UnitType baseUnit) {
        if (quantity == null || quantity.signum() <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        if (suppliedUnit == null || baseUnit == null) {
            throw new IllegalArgumentException("quantity unit and base unit are required");
        }

        BigDecimal normalized = switch (baseUnit) {
            case G -> switch (suppliedUnit) {
                case "G" -> quantity;
                case "KG" -> quantity.multiply(THOUSAND);
                default -> throw new IllegalArgumentException("only G or KG are accepted for mass items");
            };
            case ML -> switch (suppliedUnit) {
                case "ML" -> quantity;
                case "L" -> quantity.multiply(THOUSAND);
                default -> throw new IllegalArgumentException("only ML or L are accepted for volume items");
            };
            case UN -> {
                if (!"UN".equals(suppliedUnit)) {
                    throw new IllegalArgumentException("only UN is accepted for countable items");
                }
                if (quantity.stripTrailingZeros().scale() > 0) {
                    throw new IllegalArgumentException("unit count must be an integer");
                }
                yield quantity;
            }
        };
        try {
            normalized = normalized.setScale(3, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("quantity has more than 3 decimal places in base units", e);
        }
        if (normalized.precision() > 19) {
            throw new IllegalArgumentException("quantity exceeds database precision");
        }
        return normalized;
    }
}
