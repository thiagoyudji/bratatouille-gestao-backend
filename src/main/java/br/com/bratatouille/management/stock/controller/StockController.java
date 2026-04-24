package br.com.bratatouille.management.stock.controller;

import br.com.bratatouille.management.stock.dto.StockMovementResponse;
import br.com.bratatouille.management.stock.dto.StockResponse;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockMovementRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;
    private final StockMovementRepository movementRepository;

    public StockController(
        StockRepository stockRepository,
        StockMovementRepository movementRepository
    ) {
        this.stockRepository = stockRepository;
        this.movementRepository = movementRepository;
    }

    @GetMapping
    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(StockResponse::from)
                .toList();
    }

    @GetMapping("/{itemId}")
    public StockResponse findByItem(@PathVariable Long itemId) {
        Stock stock = stockRepository.findByItemId(itemId)
                .orElseThrow();

        return StockResponse.from(stock);
    }

    @GetMapping("/movements")
    public List<StockMovementResponse> movements() {
        return movementRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(StockMovementResponse::from)
                .toList();
    }
}