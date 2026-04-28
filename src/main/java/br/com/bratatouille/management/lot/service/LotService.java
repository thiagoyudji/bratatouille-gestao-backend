package br.com.bratatouille.management.lot.service;

import br.com.bratatouille.management.generated.model.LotResponse;
import br.com.bratatouille.management.lot.entity.Lot;
import br.com.bratatouille.management.lot.mapper.LotMapper;
import br.com.bratatouille.management.lot.repository.LotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LotService {

    private final LotRepository lotRepository;
    private final LotMapper lotMapper;

    public LotService(
            LotRepository lotRepository,
            LotMapper lotMapper
    ) {
        this.lotRepository = lotRepository;
        this.lotMapper = lotMapper;
    }

    public List<LotResponse> findAll() {
        return lotRepository.findAll()
                .stream()
                .map(lotMapper::toResponse)
                .toList();
    }

    public LotResponse findById(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lot not found"));

        return lotMapper.toResponse(lot);
    }

    public LotResponse findByProductionId(Long productionId) {
        Lot lot = lotRepository.findByProductionId(productionId)
                .orElseThrow(() -> new IllegalArgumentException("Lot not found for production"));

        return lotMapper.toResponse(lot);
    }

    public List<LotResponse> findByItemId(Long itemId) {
        return lotRepository.findByItemIdOrderByExpirationDateAsc(itemId)
                .stream()
                .map(lotMapper::toResponse)
                .toList();
    }

    public List<LotResponse> findExpiringBetween(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        return lotRepository.findByExpirationDateBetweenOrderByExpirationDateAsc(startDate, endDate)
                .stream()
                .map(lotMapper::toResponse)
                .toList();
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is required");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("endDate is required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate cannot be after endDate");
        }
    }
}