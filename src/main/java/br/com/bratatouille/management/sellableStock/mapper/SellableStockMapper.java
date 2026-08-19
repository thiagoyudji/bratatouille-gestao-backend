package br.com.bratatouille.management.sellableStock.mapper;

import br.com.bratatouille.management.generated.model.SellableStockAdminResponse;
import br.com.bratatouille.management.generated.model.SellableStockCatalogResponse;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.stock.entity.Stock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SellableStockMapper {

    public SellableStockCatalogResponse toCatalogResponse(SellableStock sellableStock) {
        SellableStockCatalogResponse response = new SellableStockCatalogResponse();

        response.setItemId(sellableStock.getItem().getId());
        response.setItemName(sellableStock.getItem().getName());
        response.setPricePf(sellableStock.getPricePf() != null ? sellableStock.getPricePf() : sellableStock.getItem().getPricePf());
        response.setPricePj(sellableStock.getPricePj() != null ? sellableStock.getPricePj() : sellableStock.getItem().getPricePj());
        response.setInfinite(sellableStock.getInfinite());
        response.setActive(sellableStock.getActive());
        return response;
    }

    public SellableStockAdminResponse toAdminResponse(SellableStock sellableStock, Stock stock) {
        SellableStockAdminResponse response = new SellableStockAdminResponse();
        response.setItemId(sellableStock.getItem().getId());
        response.setItemName(sellableStock.getItem().getName());
        response.setPricePf(sellableStock.getPricePf() != null ? sellableStock.getPricePf() : sellableStock.getItem().getPricePf());
        response.setPricePj(sellableStock.getPricePj() != null ? sellableStock.getPricePj() : sellableStock.getItem().getPricePj());
        response.setInfinite(sellableStock.getInfinite());
        response.setActive(sellableStock.getActive());
        response.setCurrentStockQuantity(stock == null ? BigDecimal.ZERO : stock.getQuantity());
        return response;
    }
}
