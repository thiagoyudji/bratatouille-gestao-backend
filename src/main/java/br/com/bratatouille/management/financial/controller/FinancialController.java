package br.com.bratatouille.management.financial.controller;

import br.com.bratatouille.management.financial.dto.PartnerBalanceResponse;
import br.com.bratatouille.management.financial.dto.PixSettlementResponse;
import br.com.bratatouille.management.financial.service.FinancialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {

    private final FinancialService financialService;

    public FinancialController(FinancialService financialService) {
        this.financialService = financialService;
    }

    @GetMapping("/balances")
    public List<PartnerBalanceResponse> getBalances() {
        return financialService.getBalances();
    }

    @GetMapping("/pix-settlement")
    public List<PixSettlementResponse> getPixSettlement() {
        return financialService.getPixSettlement();
    }
}