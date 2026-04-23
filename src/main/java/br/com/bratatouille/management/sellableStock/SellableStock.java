package br.com.bratatouille.management.sellableStock;

import br.com.bratatouille.management.item.entity.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

@Entity
public class SellableStock {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Item item;

    private BigDecimal availableQuantity;

    private Boolean infinite;

    private Boolean enabled;
}