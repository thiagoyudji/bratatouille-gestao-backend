package br.com.bratatouille.management.production.service;

import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.production.mapper.ProductionMapper;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.purchase.repository.PurchaseItemRepository;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.entity.RecipeItem;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final RecipeRepository recipeRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final StockService stockService;
    private final ProductionMapper productionMapper;

    public ProductionService(
            ProductionRepository productionRepository,
            RecipeRepository recipeRepository,
            PurchaseItemRepository purchaseItemRepository,
            StockService stockService,
            ProductionMapper productionMapper
    ) {
        this.productionRepository = productionRepository;
        this.recipeRepository = recipeRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.stockService = stockService;
        this.productionMapper = productionMapper;
    }

    @Transactional
    public ProductionResponse create(ProductionCreateRequest request) {
        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        BigDecimal producedQuantity = request.getProducedQuantity();

        BigDecimal totalCost = calculateTotalCost(recipe, producedQuantity);

        consumeRecipeItems(recipe, producedQuantity);

        stockService.addFromProduction(recipe.getOutputItem(), producedQuantity);

        Production production = Production.create(
                recipe,
                producedQuantity,
                totalCost
        );

        Production saved = productionRepository.save(production);

        return productionMapper.toResponse(saved);
    }

    public List<ProductionResponse> findAll() {
        return productionRepository.findAll()
                .stream()
                .map(productionMapper::toResponse)
                .toList();
    }

    public ProductionResponse findById(Long id) {
        Production production = productionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production not found"));

        return productionMapper.toResponse(production);
    }

    private BigDecimal calculateTotalCost(Recipe recipe, BigDecimal producedQuantity) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (RecipeItem recipeItem : recipe.getItems()) {
            BigDecimal consumedQuantity = recipeItem.getQuantity()
                    .multiply(producedQuantity);

            BigDecimal averageUnitCost = purchaseItemRepository
                    .findAverageUnitCostByItemId(recipeItem.getItem().getId());

            if (averageUnitCost == null || averageUnitCost.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Item has no cost history: " + recipeItem.getItem().getId()
                );
            }

            BigDecimal itemCost = averageUnitCost.multiply(consumedQuantity);

            totalCost = totalCost.add(itemCost);
        }

        return totalCost;
    }

    private void consumeRecipeItems(Recipe recipe, BigDecimal producedQuantity) {
        for (RecipeItem recipeItem : recipe.getItems()) {
            BigDecimal consumedQuantity = recipeItem.getQuantity()
                    .multiply(producedQuantity);

            stockService.removeForProduction(
                    recipeItem.getItem(),
                    consumedQuantity
            );
        }
    }
}