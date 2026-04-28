package br.com.bratatouille.management.operationalLoss.mapper;

import br.com.bratatouille.management.generated.model.OperationalLossResponse;
import br.com.bratatouille.management.operationalLoss.entity.OperationalLoss;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class OperationalLossMapper {

    public OperationalLossResponse toResponse(OperationalLoss loss) {
        OperationalLossResponse response = new OperationalLossResponse();

        response.setId(loss.getId());
        response.setLossDate(loss.getLossDate());
        response.setItemId(loss.getItem().getId());
        response.setItemName(loss.getItem().getName());
        response.setQuantity(loss.getQuantity());
        response.setReason(loss.getReason().name());
        response.setUnitCost(loss.getUnitCost());
        response.setTotalCost(loss.getTotalCost());
        response.setNote(loss.getNote());
        response.setCreatedAt(loss.getCreatedAt().atOffset(ZoneOffset.UTC));

        return response;
    }
}