package br.com.bratatouille.management.sellableStock.mapper;

import br.com.bratatouille.management.generated.model.SellableStockResponse;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.stock.entity.Stock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SellableStockMapper {

    public SellableStockResponse toResponse(SellableStock sellableStock, Stock stock) {
        SellableStockResponse response = new SellableStockResponse();

        response.setItemId(sellableStock.getItem().getId());
        response.setItemName(sellableStock.getItem().getName());
        response.setAvailableQuantity(sellableStock.getAvailableQuantity());
        response.setPricePf(sellableStock.getPricePf() != null ? sellableStock.getPricePf() : sellableStock.getItem().getPricePf());
        response.setPricePj(sellableStock.getPricePj() != null ? sellableStock.getPricePj() : sellableStock.getItem().getPricePj());
        response.setInfinite(sellableStock.getInfinite());
        response.setEnabled(sellableStock.getEnabled());
        response.setCurrentStockQuantity(stock == null ? BigDecimal.ZERO : stock.getQuantity());

        return response;
    }
}
