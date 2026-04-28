package br.com.bratatouille.management.dashboard.controller;

import br.com.bratatouille.management.dashboard.service.DashboardService;
import br.com.bratatouille.management.generated.api.DashboardApiDelegate;
import br.com.bratatouille.management.generated.model.DashboardOverviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DashboardApiDelegateImpl implements DashboardApiDelegate {

    private final DashboardService dashboardService;

    public DashboardApiDelegateImpl(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public ResponseEntity<DashboardOverviewResponse> getDashboardOverview(LocalDate startDate, LocalDate endDate) {
        return ResponseEntity.ok(dashboardService.getOverview(startDate, endDate));
    }
}