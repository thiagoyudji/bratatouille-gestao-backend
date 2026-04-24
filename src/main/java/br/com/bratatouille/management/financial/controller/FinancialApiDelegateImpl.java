package br.com.bratatouille.management.financial.controller;

import br.com.bratatouille.management.generated.model.PartnerBalanceResponse;
import br.com.bratatouille.management.generated.model.PixSettlementResponse;
import br.com.bratatouille.management.financial.service.FinancialService;
import br.com.bratatouille.management.generated.api.FinancialApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinancialApiDelegateImpl implements FinancialApiDelegate {

    private final FinancialService financialService;

    public FinancialApiDelegateImpl(FinancialService financialService) {
        this.financialService = financialService;
    }

    @Override
    public ResponseEntity<List<PartnerBalanceResponse>> getBalances() {
        return ResponseEntity.ok(financialService.getBalances());
    }

    @Override
    public ResponseEntity<List<PixSettlementResponse>> getPixSettlement() {
        return ResponseEntity.ok(financialService.getPixSettlement());
    }
}