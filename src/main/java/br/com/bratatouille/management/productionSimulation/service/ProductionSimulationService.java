package br.com.bratatouille.management.productionSimulation.service;

import br.com.bratatouille.management.generated.model.ProductionSimulationItemResponse;
import br.com.bratatouille.management.generated.model.ProductionSimulationInputRequest;
import br.com.bratatouille.management.generated.model.ProductionSimulationRequest;
import br.com.bratatouille.management.generated.model.ProductionSimulationResponse;
import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.entity.RecipeItem;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductionSimulationService {

    private final RecipeRepository recipeRepository;
    private final StockRepository stockRepository;
    private final CostService costService;

    public ProductionSimulationService(
            RecipeRepository recipeRepository,
            StockRepository stockRepository,
            CostService costService
    ) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
        this.costService = costService;
    }

    public ProductionSimulationResponse simulate(Long recipeId, BigDecimal quantity) {
        validateRequest(recipeId, quantity);

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found"));

        if (!Boolean.TRUE.equals(recipe.getActive())) {
            throw new IllegalArgumentException("Recipe is inactive");
        }

        List<ProductionSimulationItemResponse> items = recipe.getItems()
                .stream()
                .map(item -> simulateItem(item, quantity))
                .toList();

        BigDecimal totalCost = items.stream()
                .map(ProductionSimulationItemResponse::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ProductionSimulationResponse response = new ProductionSimulationResponse();

        response.setRecipeId(recipeId);
        response.setRecipeName(recipe.getName());
        response.setOutputItemId(recipe.getOutputItem().getId());
        response.setOutputItemName(recipe.getOutputItem().getName());
        response.setQuantity(quantity);
        response.setItems(items);
        response.setEstimatedTotalCost(totalCost);

        return response;
    }

    public ProductionSimulationResponse simulateFromInputs(ProductionSimulationRequest request) {
        if (request == null || request.getRecipeId() == null || request.getInputs() == null || request.getInputs().isEmpty()) {
            throw new IllegalArgumentException("recipeId and inputs are required");
        }
        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new NoSuchElementException("Recipe not found"));
        if (!Boolean.TRUE.equals(recipe.getActive())) {
            throw new IllegalArgumentException("Recipe is inactive");
        }
        Map<Long, BigDecimal> supplied = request.getInputs().stream().collect(Collectors.toMap(
                ProductionSimulationInputRequest::getItemId,
                ProductionSimulationInputRequest::getQuantity,
                BigDecimal::add
        ));
        BigDecimal factor = recipe.getItems().stream()
                .map(item -> supplied.getOrDefault(item.getItem().getId(), BigDecimal.ZERO)
                        .divide(item.getQuantity(), 6, RoundingMode.DOWN))
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal pots = factor.multiply(recipe.getYieldQuantity());
        List<ProductionSimulationItemResponse> items = recipe.getItems().stream()
                .map(item -> simulateInputItem(item, supplied.getOrDefault(item.getItem().getId(), BigDecimal.ZERO), factor))
                .toList();
        ProductionSimulationResponse response = new ProductionSimulationResponse();
        response.setRecipeId(recipe.getId());
        response.setRecipeName(recipe.getName());
        response.setOutputItemId(recipe.getOutputItem().getId());
        response.setOutputItemName(recipe.getOutputItem().getName());
        response.setQuantity(pots);
        response.setItems(items);
        response.setEstimatedTotalCost(items.stream().map(ProductionSimulationItemResponse::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return response;
    }

    private ProductionSimulationItemResponse simulateInputItem(RecipeItem recipeItem, BigDecimal supplied, BigDecimal factor) {
        BigDecimal required = recipeItem.getQuantity().multiply(factor);
        BigDecimal excess = supplied.subtract(required).max(BigDecimal.ZERO);
        BigDecimal missing = required.subtract(supplied).max(BigDecimal.ZERO);
        BigDecimal unitCost = costService.findUnitCostOrZero(recipeItem.getItem());
        ProductionSimulationItemResponse response = new ProductionSimulationItemResponse();
        response.setItemId(recipeItem.getItem().getId());
        response.setItemName(recipeItem.getItem().getName());
        response.setRequiredQuantity(required);
        response.setUsableQuantity(supplied);
        response.setLossQuantity(excess);
        response.setYieldPercentage(BigDecimal.ONE);
        response.setCurrentStock(supplied);
        response.setMissingQuantity(missing);
        response.setUnitCost(unitCost);
        response.setTotalCost(unitCost.multiply(required));
        return response;
    }

    private ProductionSimulationItemResponse simulateItem(RecipeItem recipeItem, BigDecimal quantity) {
        BigDecimal usableQuantity = recipeItem.getQuantity().multiply(quantity);

        BigDecimal requiredQuantity = usableQuantity.divide(
                BigDecimal.ONE,
                6,
                RoundingMode.HALF_UP
        );

        BigDecimal lossQuantity = requiredQuantity.subtract(usableQuantity);

        Stock stock = stockRepository.findByItemId(recipeItem.getItem().getId()).orElse(null);

        BigDecimal currentStock = stock == null ? BigDecimal.ZERO : stock.getQuantity();

        BigDecimal missing = requiredQuantity.subtract(currentStock).max(BigDecimal.ZERO);

        BigDecimal unitCost = costService.findUnitCostOrZero(recipeItem.getItem());

        BigDecimal totalCost = unitCost.multiply(requiredQuantity);

        ProductionSimulationItemResponse response = new ProductionSimulationItemResponse();

        response.setItemId(recipeItem.getItem().getId());
        response.setItemName(recipeItem.getItem().getName());
        response.setRequiredQuantity(requiredQuantity);
        response.setUsableQuantity(usableQuantity);
        response.setLossQuantity(lossQuantity);
        response.setYieldPercentage(BigDecimal.ONE);
        response.setCurrentStock(currentStock);
        response.setMissingQuantity(missing);
        response.setUnitCost(unitCost);
        response.setTotalCost(totalCost);

        return response;
    }

    private void validateRequest(Long recipeId, BigDecimal quantity) {
        if (recipeId == null) {
            throw new IllegalArgumentException("recipeId is required");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }
}
