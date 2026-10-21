package br.com.bratatouille.management.item.domain;

import br.com.bratatouille.management.item.entity.UnitType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuantityUnitConverterTest {
    @Test
    void massAndVolumeConvertToCanonicalUnits() {
        assertEquals(0, new BigDecimal("2000").compareTo(
                QuantityUnitConverter.toBase(new BigDecimal("2"), "KG", UnitType.G)));
        assertEquals(0, new BigDecimal("750").compareTo(
                QuantityUnitConverter.toBase(new BigDecimal("0.75"), "KG", UnitType.G)));
        assertEquals(0, new BigDecimal("1500").compareTo(
                QuantityUnitConverter.toBase(new BigDecimal("1.5"), "L", UnitType.ML)));
        assertEquals(0, new BigDecimal("250").compareTo(
                QuantityUnitConverter.toBase(new BigDecimal("250"), "ML", UnitType.ML)));
        assertEquals(0, new BigDecimal("1").compareTo(
                QuantityUnitConverter.toBase(new BigDecimal("0.001"), "L", UnitType.ML)));
    }

    @Test
    void invalidDimensionsAndFractionalCountsAreRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("1"), "G", UnitType.ML));
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("1"), "KG", UnitType.UN));
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("1.5"), "UN", UnitType.UN));
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("0"), "KG", UnitType.G));
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("1.0001"), "G", UnitType.G));
        assertThrows(IllegalArgumentException.class, () ->
                QuantityUnitConverter.toBase(new BigDecimal("1"), "L", UnitType.G));
    }
}
