package br.com.bratatouille.management.financialClosing.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_closings")
public class FinancialClosing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Lob
    @Column(nullable = false)
    private String summaryJson;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected FinancialClosing() {}

    public FinancialClosing(LocalDate startDate, LocalDate endDate, String summaryJson) {
        validate(startDate, endDate, summaryJson);

        this.startDate = startDate;
        this.endDate = endDate;
        this.summaryJson = summaryJson;
    }

    private void validate(LocalDate startDate, LocalDate endDate, String summaryJson) {
        if (startDate == null) throw new IllegalArgumentException("startDate required");
        if (endDate == null) throw new IllegalArgumentException("endDate required");
        if (startDate.isAfter(endDate)) throw new IllegalArgumentException("invalid period");
        if (summaryJson == null || summaryJson.isBlank()) throw new IllegalArgumentException("summary required");
    }

    public Long getId() { return id; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getSummaryJson() { return summaryJson; }
}