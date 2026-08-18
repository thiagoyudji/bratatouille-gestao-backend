package br.com.bratatouille.management.purchase.domain;

import br.com.bratatouille.management.partner.entity.Partner;
import java.math.BigDecimal;

public record PartnerAmountData(Partner partner, BigDecimal amount) {}
