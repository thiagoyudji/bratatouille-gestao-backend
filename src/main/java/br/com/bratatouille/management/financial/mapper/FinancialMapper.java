package br.com.bratatouille.management.financial.mapper;

import br.com.bratatouille.management.financial.domain.PartnerBalance;
import br.com.bratatouille.management.financial.domain.PixTransfer;
import br.com.bratatouille.management.generated.model.PartnerBalanceResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
import br.com.bratatouille.management.financial.domain.PartnerFinancialSummaryAccumulator;
import br.com.bratatouille.management.generated.model.FinancialPartnerSummaryResponse;
import br.com.bratatouille.management.partner.entity.Partner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FinancialMapper {

    public PartnerBalanceResponse toBalanceResponse(PartnerBalance balance) {
        PartnerBalanceResponse response = new PartnerBalanceResponse();

        response.setPartnerId(balance.partner().getId());
        response.setPartnerName(balance.partner().getName());
        response.setBalance(balance.amount());

        return response;
    }

    public PixSettlementResponse toPixSettlementResponse(PixTransfer transfer) {
        PixSettlementResponse response = new PixSettlementResponse();

        response.setFromPartnerId(transfer.from().getId());
        response.setFromPartnerName(transfer.from().getName());
        response.setToPartnerId(transfer.to().getId());
        response.setToPartnerName(transfer.to().getName());
        response.setAmount(transfer.amount());

        return response;
    }

    public FinancialPartnerSummaryResponse toPartnerSummaryResponse(PartnerFinancialSummaryAccumulator accumulator) {
        FinancialPartnerSummaryResponse response = new FinancialPartnerSummaryResponse();

        response.setPartnerId(accumulator.getPartner().getId());
        response.setPartnerName(accumulator.getPartner().getName());
        response.setTotalPaid(accumulator.getTotalPaid());
        response.setTotalOwed(accumulator.getTotalOwed());
        response.setBalance(accumulator.getBalance());

        return response;
    }
}