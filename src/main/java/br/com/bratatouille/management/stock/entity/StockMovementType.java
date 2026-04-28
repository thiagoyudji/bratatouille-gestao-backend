package br.com.bratatouille.management.stock.entity;

public enum StockMovementType {

    // Entrada por compra de insumo/embalagem
    PURCHASE_ENTRY,

    // Saída de insumo para produção
    PRODUCTION_CONSUMPTION,

    // Entrada de produto final produzido
    PRODUCTION_OUTPUT,

    // Ajuste manual (erro, correção, perda não controlada)
    MANUAL_ADJUSTMENT,

    SALE_OUTPUT,

    LOSS_OUTPUT

}