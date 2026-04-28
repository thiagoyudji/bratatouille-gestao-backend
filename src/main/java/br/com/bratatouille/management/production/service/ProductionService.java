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
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final RecipeRepository recipeRepository;
    private final ProductionMapper productionMapper;
    private final PurchaseItemRepository purchaseItemRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;

    public ProductionService(
            ProductionRepository productionRepository,
            RecipeRepository recipeRepository,
            ProductionMapper productionMapper,
            PurchaseItemRepository purchaseItemRepository,
            StockRepository stockRepository,
            StockService stockService
    ) {
        this.productionRepository = productionRepository;
        this.recipeRepository = recipeRepository;
        this.productionMapper = productionMapper;
        this.purchaseItemRepository = purchaseItemRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public ProductionResponse create(ProductionCreateRequest request) {
        validate(request);

        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        if (!Boolean.TRUE.equals(recipe.getActive())) {
            throw new IllegalArgumentException("Recipe is inactive");
        }

        BigDecimal producedQuantity = request.getProducedQuantity();

        List<ProductionItemData> itemsData = recipe.getItems()
                .stream()
                .map(recipeItem -> buildProductionItemData(recipeItem, producedQuantity))
                .toList();

        BigDecimal totalCost = itemsData.stream()
                .map(ProductionItemData::totalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Production production = Production.create(
                recipe,
                LocalDate.now(),
                producedQuantity,
                totalCost,
                itemsData
        );

        Production saved = productionRepository.save(production);

        itemsData.forEach(itemData -> stockService.removeForProduction(
                itemData.item(),
                itemData.consumedQuantity(),
                saved.getId()
        ));

        stockService.addFromProduction(
                recipe.getOutputItem(),
                producedQuantity,
                saved.getId()
        );

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
                .orElseThrow(() -> new IllegalArgumentException("Production not found"));

        return productionMapper.toResponse(production);
    }

    private ProductionItemData buildProductionItemData(RecipeItem recipeItem, BigDecimal producedQuantity) {
        BigDecimal usableQuantity = recipeItem.getQuantity().multiply(producedQuantity);

        BigDecimal consumedQuantity = usableQuantity.divide(
                recipeItem.getYieldPercentage(),
                6,
                RoundingMode.HALF_UP
        );

        validateStock(recipeItem, consumedQuantity);

        BigDecimal unitCost = purchaseItemRepository.findAverageUnitCostByItemId(
                recipeItem.getItem().getId()
        );

        if (unitCost == null || unitCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cost history not found for item: " + recipeItem.getItem().getName());
        }

        BigDecimal lossQuantity = consumedQuantity.subtract(usableQuantity);
        BigDecimal totalCost = unitCost.multiply(consumedQuantity);

        return new ProductionItemData(
                recipeItem.getItem(),
                consumedQuantity,
                usableQuantity,
                lossQuantity,
                recipeItem.getYieldPercentage(),
                unitCost,
                totalCost
        );
    }

    private void validateStock(RecipeItem recipeItem, BigDecimal requiredQuantity) {
        Stock stock = stockRepository.findByItemId(recipeItem.getItem().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Stock not found for item: " + recipeItem.getItem().getName()
                ));

        if (stock.getQuantity().compareTo(requiredQuantity) < 0) {
            throw new IllegalArgumentException("Insufficient stock for item: " + recipeItem.getItem().getName());
        }
    }

    private void validate(ProductionCreateRequest request) {
        if (request.getRecipeId() == null) {
            throw new IllegalArgumentException("recipeId is required");
        }

        if (request.getProducedQuantity() == null || request.getProducedQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("producedQuantity must be greater than zero");
        }
    }
}