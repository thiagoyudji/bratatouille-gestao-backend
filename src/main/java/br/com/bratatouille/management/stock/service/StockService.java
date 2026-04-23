package br.com.bratatouille.management.stock.service;

import br.com.bratatouille.management.item.entity.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockService {

    public void add(Item item, BigDecimal qty, String source, Long sourceId) {}

    public void remove(Item item, BigDecimal qty, String source, Long sourceId) {}

}