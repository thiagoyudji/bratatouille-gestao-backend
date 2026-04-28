package br.com.bratatouille.management.operationalCost.mapper;

import br.com.bratatouille.management.generated.model.OperationalCostResponse;
import br.com.bratatouille.management.generated.model.OperationalCostSplitResponse;
import br.com.bratatouille.management.operationalCost.entity.OperationalCost;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostSplit;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class OperationalCostMapper {

    public OperationalCostResponse toResponse(OperationalCost operationalCost) {
        OperationalCostResponse response = new OperationalCostResponse();

        response.setId(operationalCost.getId());
        response.setCostDate(operationalCost.getCostDate());
        response.setCategory(OperationalCostResponse.CategoryEnum.valueOf(operationalCost.getCategory().name()));
        response.setPaidByPartnerId(operationalCost.getPaidBy().getId());
        response.setPaidByPartnerName(operationalCost.getPaidBy().getName());
        response.setAmount(operationalCost.getAmount());
        response.setDescription(operationalCost.getDescription());
        response.setCreatedAt(operationalCost.getCreatedAt().atOffset(ZoneOffset.UTC));

        response.setSplits(
                operationalCost.getSplits()
                        .stream()
                        .map(this::toSplitResponse)
                        .toList()
        );

        return response;
    }

    private OperationalCostSplitResponse toSplitResponse(OperationalCostSplit split) {
        OperationalCostSplitResponse response = new OperationalCostSplitResponse();

        response.setId(split.getId());
        response.setPartnerId(split.getPartner().getId());
        response.setPartnerName(split.getPartner().getName());
        response.setOwedAmount(split.getOwedAmount());

        return response;
    }
}