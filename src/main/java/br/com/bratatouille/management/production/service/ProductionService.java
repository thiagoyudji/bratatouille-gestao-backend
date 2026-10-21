package br.com.bratatouille.management.production.service;

import br.com.bratatouille.management.generated.model.ProductionCreateRequest;
import br.com.bratatouille.management.generated.model.ProductionResponse;
import br.com.bratatouille.management.production.domain.ProductionItemData;
import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.production.mapper.ProductionMapper;
import br.com.bratatouille.management.production.repository.ProductionRepository;
import br.com.bratatouille.management.cost.service.CostService;
import br.com.bratatouille.management.recipe.entity.Recipe;
import br.com.bratatouille.management.recipe.entity.RecipeItem;
import br.com.bratatouille.management.recipe.repository.RecipeRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import br.com.bratatouille.management.stock.service.StockService;
import org.springframework.stereotype.Service;
import br.com.bratatouille.management.lot.entity.Lot;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final RecipeRepository recipeRepository;
    private final ProductionMapper productionMapper;
    private final CostService costService;
    private final StockRepository stockRepository;
    private final StockService stockService;

    public ProductionService(
            ProductionRepository productionRepository,
            RecipeRepository recipeRepository,
            ProductionMapper productionMapper,
            CostService costService,
            StockRepository stockRepository,
            StockService stockService
    ) {
        this.productionRepository = productionRepository;
        this.recipeRepository = recipeRepository;
        this.productionMapper = productionMapper;
        this.costService = costService;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public ProductionResponse create(ProductionCreateRequest request) {
        validate(request);

        Recipe recipe = recipeRepository.findById(request.getRecipeId())
                .orElseThrow(() -> new NoSuchElementException("Recipe not found"));

        if (!Boolean.TRUE.equals(recipe.getActive())) {
            throw new IllegalArgumentException("Recipe is inactive");
        }

        BigDecimal producedQuantity = request.getProducedQuantity();

        List<ProductionItemData> itemsData = buildProductionItems(recipe, request, producedQuantity);

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
                .orElseThrow(() -> new NoSuchElementException("Production not found"));

        return productionMapper.toResponse(production);
    }

    private List<ProductionItemData> buildProductionItems(
            Recipe recipe, ProductionCreateRequest request, BigDecimal producedQuantity
    ) {
        java.util.Map<Long, BigDecimal> supplied = new java.util.HashMap<>();
        if (request.getItems() != null) {
            request.getItems().forEach(input -> {
                if (input.getItemId() == null || input.getQuantity() == null
                        || input.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Every consumed item needs an id and positive quantity");
                }
                if (supplied.putIfAbsent(input.getItemId(), input.getQuantity()) != null) {
                    throw new IllegalArgumentException("Duplicated consumed item: " + input.getItemId());
                }
            });
        }

        java.util.Set<Long> recipeItemIds = recipe.getItems().stream()
                .map(item -> item.getItem().getId())
                .collect(java.util.stream.Collectors.toSet());
        if (!recipeItemIds.containsAll(supplied.keySet())) {
            throw new IllegalArgumentException("Actual consumption includes items outside the recipe");
        }

        boolean manual = !supplied.isEmpty();
        if (!manual && recipe.getYieldQuantity() == null) {
            throw new IllegalArgumentException(
                    "Actual ingredient consumption is required when recipe yield is unknown");
        }
        return recipe.getItems().stream().map(item -> {
            boolean packaging = item.getItem().getType()
                    == br.com.bratatouille.management.item.entity.ItemType.PACKAGING;
            BigDecimal required = packaging
                    ? item.getQuantity().multiply(producedQuantity)
                    : recipe.getYieldQuantity() == null ? null
                    : item.getQuantity().multiply(producedQuantity)
                        .divide(recipe.getYieldQuantity(), 6, RoundingMode.HALF_UP);

            BigDecimal actual = supplied.get(item.getItem().getId());
            if (packaging) {
                if (actual == null) {
                    actual = required;
                } else if (actual.compareTo(required) < 0) {
                    throw new IllegalArgumentException(
                            "Packaging consumption cannot be lower than finished units: "
                                    + item.getItem().getName());
                }
                if (actual.stripTrailingZeros().scale() > 0) {
                    throw new IllegalArgumentException("Packaging consumption must be whole units");
                }
            } else if (actual == null) {
                if (manual) {
                    throw new IllegalArgumentException("All recipe ingredients must be supplied");
                }
                actual = required;
            }
            return buildProductionItemData(item, actual);
        }).toList();
    }

    private ProductionItemData buildProductionItemData(RecipeItem recipeItem, BigDecimal consumedQuantity) {
        validateStock(recipeItem, consumedQuantity);
        BigDecimal unitCost = costService.findUnitCostOrZero(recipeItem.getItem());
        BigDecimal totalCost = unitCost.multiply(consumedQuantity);
        return new ProductionItemData(
                recipeItem.getItem(), consumedQuantity, consumedQuantity,
                BigDecimal.ZERO, BigDecimal.ONE, unitCost, totalCost
        );
    }

    private void validateStock(RecipeItem recipeItem, BigDecimal requiredQuantity) {
        Stock stock = stockRepository.findByItemId(recipeItem.getItem().getId())
                .orElseThrow(() -> new NoSuchElementException(
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
        if (request.getProducedQuantity().stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("producedQuantity must be whole units");
        }
    }
}
