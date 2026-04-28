package br.com.bratatouille.management.operationalCost.service;

import br.com.bratatouille.management.financialClosing.service.FinancialClosingValidationService;
import br.com.bratatouille.management.generated.model.OperationalCostCreateRequest;
import br.com.bratatouille.management.generated.model.OperationalCostResponse;
import br.com.bratatouille.management.generated.model.OperationalCostSplitRequest;
import br.com.bratatouille.management.operationalCost.domain.OperationalCostSplitData;
import br.com.bratatouille.management.operationalCost.entity.OperationalCost;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostCategory;
import br.com.bratatouille.management.operationalCost.mapper.OperationalCostMapper;
import br.com.bratatouille.management.operationalCost.repository.OperationalCostRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OperationalCostService {

    private final OperationalCostRepository operationalCostRepository;
    private final PartnerRepository partnerRepository;
    private final OperationalCostMapper operationalCostMapper;
    private final FinancialClosingValidationService closingValidationService;

    public OperationalCostService(
            OperationalCostRepository operationalCostRepository,
            PartnerRepository partnerRepository,
            OperationalCostMapper operationalCostMapper,
            FinancialClosingValidationService closingValidationService
    ) {
        this.operationalCostRepository = operationalCostRepository;
        this.partnerRepository = partnerRepository;
        this.operationalCostMapper = operationalCostMapper;
        this.closingValidationService = closingValidationService;
    }

    @Transactional
    public OperationalCostResponse create(OperationalCostCreateRequest request) {
        validateRequest(request);

        closingValidationService.validateNotClosed(request.getCostDate());
        Partner payer = getValidPartner(request.getPaidByPartnerId());

        List<OperationalCostSplitData> splits = request.getSplits()
                .stream()
                .map(this::toSplitData)
                .toList();

        OperationalCost operationalCost = OperationalCost.create(
                request.getCostDate(),
                OperationalCostCategory.valueOf(request.getCategory().name()),
                payer,
                request.getAmount(),
                request.getDescription(),
                splits
        );

        OperationalCost saved = operationalCostRepository.save(operationalCost);

        return operationalCostMapper.toResponse(saved);
    }

    private void validateRequest(OperationalCostCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        if (request.getCostDate() == null) {
            throw new IllegalArgumentException("costDate is required");
        }

        if (request.getCategory() == null) {
            throw new IllegalArgumentException("category is required");
        }

        if (request.getPaidByPartnerId() == null) {
            throw new IllegalArgumentException("paidByPartnerId is required");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        if (request.getSplits() == null || request.getSplits().isEmpty()) {
            throw new IllegalArgumentException("operational cost must have at least one split");
        }
    }

    @Transactional(readOnly = true)
    public List<OperationalCostResponse> findAll() {
        return operationalCostRepository.findAll()
                .stream()
                .map(operationalCostMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OperationalCostResponse findById(Long id) {
        OperationalCost operationalCost = operationalCostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operational cost not found"));

        return operationalCostMapper.toResponse(operationalCost);
    }

    private OperationalCostSplitData toSplitData(OperationalCostSplitRequest request) {
        Partner partner = getValidPartner(request.getPartnerId());

        return new OperationalCostSplitData(
                partner,
                request.getAmount()
        );
    }

    private Partner getValidPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found"));

        if (!Boolean.TRUE.equals(partner.getActive())) {
            throw new IllegalArgumentException("Partner is inactive");
        }

        return partner;
    }
}