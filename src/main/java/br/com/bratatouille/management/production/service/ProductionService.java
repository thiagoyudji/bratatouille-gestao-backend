package br.com.bratatouille.management.production.service;

import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.domain.ProductionItemData;
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
import java.math.RoundingMode;
import java.util.Comparator;
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
        validateRequest(request);

        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        validateRecipe(recipe);

        BigDecimal producedQuantity = request.getProducedQuantity();

        List<ProductionItemData> itemsData = buildProductionItemsData(recipe, producedQuantity);

        BigDecimal totalCost = itemsData.stream()
                .map(ProductionItemData::totalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        consumeRecipeItems(itemsData);

        stockService.addFromProduction(recipe.getOutputItem(), producedQuantity);

        Production production = Production.create(
                recipe,
                producedQuantity,
                totalCost,
                itemsData
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

    private void validateRequest(ProductionCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        if (request.getRecipeId() == null) {
            throw new IllegalArgumentException("recipeId is required");
        }

        if (request.getProducedQuantity() == null || request.getProducedQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("producedQuantity must be greater than zero");
        }
    }

    private void validateRecipe(Recipe recipe) {
        if (!Boolean.TRUE.equals(recipe.getActive())) {
            throw new IllegalArgumentException("Recipe is inactive");
        }

        if (recipe.getItems() == null || recipe.getItems().isEmpty()) {
            throw new IllegalArgumentException("Recipe has no items");
        }
    }

    private List<ProductionItemData> buildProductionItemsData(Recipe recipe, BigDecimal producedQuantity) {
        return recipe.getItems()
                .stream()
                .sorted(Comparator.comparing(recipeItem -> recipeItem.getItem().getId()))
                .map(recipeItem -> buildProductionItemData(recipeItem, producedQuantity))
                .toList();
    }

    private ProductionItemData buildProductionItemData(RecipeItem recipeItem, BigDecimal producedQuantity) {
        BigDecimal usableQuantity = recipeItem.getQuantity()
                .multiply(producedQuantity);

        BigDecimal consumedQuantity = usableQuantity.divide(
                recipeItem.getYieldPercentage(),
                6,
                RoundingMode.HALF_UP
        );

        BigDecimal lossQuantity = consumedQuantity.subtract(usableQuantity);

        BigDecimal averageUnitCost = purchaseItemRepository
                .findAverageUnitCostByItemId(recipeItem.getItem().getId());

        if (averageUnitCost == null || averageUnitCost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Item has no cost history: " + recipeItem.getItem().getName()
            );
        }

        BigDecimal totalCost = averageUnitCost.multiply(consumedQuantity);

        return new ProductionItemData(
                recipeItem.getItem(),
                consumedQuantity,
                usableQuantity,
                lossQuantity,
                recipeItem.getYieldPercentage(),
                averageUnitCost,
                totalCost
        );
    }

    private void consumeRecipeItems(List<ProductionItemData> itemsData) {
        itemsData.forEach(itemData -> stockService.removeForProduction(
                itemData.item(),
                itemData.consumedQuantity()
        ));
    }
}