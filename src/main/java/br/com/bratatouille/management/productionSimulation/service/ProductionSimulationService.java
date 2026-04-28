package br.com.bratatouille.management.productionSimulation.service;

import br.com.bratatouille.management.generated.model.ProductionSimulationItemResponse;
import br.com.bratatouille.management.generated.model.ProductionSimulationResponse;
import br.com.bratatouille.management.purchase.repository.PurchaseItemRepository;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.entity.RecipeItem;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductionSimulationService {

    private final RecipeRepository recipeRepository;
    private final StockRepository stockRepository;
    private final PurchaseItemRepository purchaseItemRepository;

    public ProductionSimulationService(
            RecipeRepository recipeRepository,
            StockRepository stockRepository,
            PurchaseItemRepository purchaseItemRepository
    ) {
        this.recipeRepository = recipeRepository;
        this.stockRepository = stockRepository;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    public ProductionSimulationResponse simulate(Long recipeId, BigDecimal quantity) {
        validateRequest(recipeId, quantity);

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

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

    private ProductionSimulationItemResponse simulateItem(RecipeItem recipeItem, BigDecimal quantity) {
        BigDecimal usableQuantity = recipeItem.getQuantity().multiply(quantity);

        BigDecimal requiredQuantity = usableQuantity.divide(
                recipeItem.getYieldPercentage(),
                6,
                RoundingMode.HALF_UP
        );

        BigDecimal lossQuantity = requiredQuantity.subtract(usableQuantity);

        Stock stock = stockRepository.findByItemId(recipeItem.getItem().getId()).orElse(null);

        BigDecimal currentStock = stock == null ? BigDecimal.ZERO : stock.getQuantity();

        BigDecimal missing = requiredQuantity.subtract(currentStock).max(BigDecimal.ZERO);

        BigDecimal unitCost = purchaseItemRepository
                .findAverageUnitCostByItemId(recipeItem.getItem().getId());

        if (unitCost == null) {
            unitCost = BigDecimal.ZERO;
        }

        BigDecimal totalCost = unitCost.multiply(requiredQuantity);

        ProductionSimulationItemResponse response = new ProductionSimulationItemResponse();

        response.setItemId(recipeItem.getItem().getId());
        response.setItemName(recipeItem.getItem().getName());
        response.setRequiredQuantity(requiredQuantity);
        response.setUsableQuantity(usableQuantity);
        response.setLossQuantity(lossQuantity);
        response.setYieldPercentage(recipeItem.getYieldPercentage());
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