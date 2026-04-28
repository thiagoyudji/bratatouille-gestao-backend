package br.com.bratatouille.management.financialClosing.service;

import br.com.bratatouille.management.financialClosing.repository.FinancialClosingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FinancialClosingValidationService {

    private final FinancialClosingRepository repository;

    public FinancialClosingValidationService(FinancialClosingRepository repository) {
        this.repository = repository;
    }

    public void validateNotClosed(LocalDate date) {
        boolean exists = repository.existsOverlapping(date, date);

        if (exists) {
            throw new IllegalStateException("Cannot modify data from a closed period");
        }
    }
}