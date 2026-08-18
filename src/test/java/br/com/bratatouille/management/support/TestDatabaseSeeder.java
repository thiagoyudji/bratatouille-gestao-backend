package br.com.bratatouille.management.support;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.customer.entity.CustomerAddress;
import br.com.bratatouille.management.customer.entity.CustomerProfile;
import br.com.bratatouille.management.customer.entity.CustomerType;
import br.com.bratatouille.management.customer.repository.CustomerProfileRepository;
import br.com.bratatouille.management.item.entity.Item;
import br.com.bratatouille.management.item.entity.ItemType;
import br.com.bratatouille.management.item.entity.UnitType;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.entity.Partner;
import br.com.bratatouille.management.partner.entity.PartnerRole;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import br.com.bratatouille.management.stock.entity.Stock;
import br.com.bratatouille.management.stock.repository.StockRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestDatabaseSeeder {

    public static final String DEFAULT_RESOURCE = "test-data/test-database.yml";

    private final ObjectMapper yamlObjectMapper;
    private final AuthUserRepository authUserRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final PartnerRepository partnerRepository;
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;
    private final SellableStockRepository sellableStockRepository;

    public TestDatabaseSeeder(
            AuthUserRepository authUserRepository,
            CustomerProfileRepository customerProfileRepository,
            PartnerRepository partnerRepository,
            ItemRepository itemRepository,
            StockRepository stockRepository,
            SellableStockRepository sellableStockRepository
    ) {
        this.yamlObjectMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
        this.authUserRepository = authUserRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.partnerRepository = partnerRepository;
        this.itemRepository = itemRepository;
        this.stockRepository = stockRepository;
        this.sellableStockRepository = sellableStockRepository;
    }

    public TestDatabaseSnapshot seedDefault() {
        return seed(DEFAULT_RESOURCE);
    }

    public TestDatabaseSnapshot seed(String classpathResource) {
        SeedDefinition definition = readDefinition(classpathResource);

        Map<String, AuthUser> authUsers = new LinkedHashMap<>();
        for (AuthUserSeed seed : defaultList(definition.authUsers())) {
            authUsers.put(seed.key(), authUserRepository.save(new AuthUser(seed.username(), seed.passwordHash(), seed.role())));
        }

        Map<String, CustomerProfile> customers = new LinkedHashMap<>();
        for (CustomerProfileSeed seed : defaultList(definition.customers())) {
            AuthUser authUser = require(authUsers, seed.authUser(), "authUser");
            CustomerProfile customerProfile = new CustomerProfile(
                    authUser,
                    seed.customerType(),
                    seed.fullName(),
                    seed.email(),
                    seed.phone(),
                    mapAddresses(seed.addresses())
            );
            customers.put(seed.key(), customerProfileRepository.save(customerProfile));
        }

        Map<String, Partner> partners = new LinkedHashMap<>();
        for (PartnerSeed seed : defaultList(definition.partners())) {
            partners.put(seed.key(), partnerRepository.save(new Partner(
                    seed.name(),
                    seed.active(),
                    seed.defaultSplitPercentage(),
                    null,
                    seed.roles() == null ? Set.of() : Set.copyOf(seed.roles())
            )));
        }

        Map<String, Item> items = new LinkedHashMap<>();
        for (ItemSeed seed : defaultList(definition.items())) {
            items.put(seed.key(), itemRepository.save(new Item(
                    seed.name(),
                    seed.type(),
                    seed.baseUnit(),
                    seed.lowStockThreshold(),
                    seed.criticalStockThreshold(),
                    seed.pricePf(),
                    seed.pricePj()
            )));
        }

        Map<String, Stock> stocks = new LinkedHashMap<>();
        for (StockSeed seed : defaultList(definition.stocks())) {
            Item item = require(items, seed.item(), "item");
            Stock stock = new Stock(item, seed.quantity(), seed.pricePf(), seed.pricePj());
            stock.syncPrices(seed.pricePf(), seed.pricePj());
            stocks.put(seed.key(), stockRepository.save(stock));
        }

        Map<String, SellableStock> sellableStocks = new LinkedHashMap<>();
        for (SellableStockSeed seed : defaultList(definition.sellableStocks())) {
            Item item = require(items, seed.item(), "item");
            sellableStocks.put(seed.key(), sellableStockRepository.save(SellableStock.create(
                    item,
                    seed.infinite(),
                    seed.active(),
                    seed.pricePf(),
                    seed.pricePj()
            )));
        }

        return new TestDatabaseSnapshot(authUsers, customers, partners, items, stocks, sellableStocks);
    }

    private SeedDefinition readDefinition(String classpathResource) {
        String normalizedResource = StringUtils.hasText(classpathResource) && classpathResource.startsWith("classpath:")
                ? classpathResource.substring("classpath:".length())
                : classpathResource;

        try (InputStream inputStream = new ClassPathResource(normalizedResource).getInputStream()) {
            return yamlObjectMapper.readValue(inputStream, SeedDefinition.class);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load test database fixture: " + normalizedResource, exception);
        }
    }

    private static <T> List<T> defaultList(List<T> values) {
        return values == null ? List.of() : values;
    }

    private static List<CustomerAddress> mapAddresses(List<CustomerAddressSeed> addresses) {
        return defaultList(addresses).stream()
                .map(address -> new CustomerAddress(
                        address.label(),
                        address.zipCode(),
                        address.street(),
                        address.number(),
                        address.neighborhood(),
                        address.state(),
                        address.city(),
                        address.complement(),
                        address.defaultAddress()
                ))
                .toList();
    }

    private static <T> T require(Map<String, T> values, String key, String label) {
        T value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException(label + " key not found: " + key);
        }
        return value;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SeedDefinition(
            List<AuthUserSeed> authUsers,
            List<CustomerProfileSeed> customers,
            List<PartnerSeed> partners,
            List<ItemSeed> items,
            List<StockSeed> stocks,
            List<SellableStockSeed> sellableStocks
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AuthUserSeed(
            String key,
            String username,
            String passwordHash,
            UserRole role,
            Boolean active
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CustomerProfileSeed(
            String key,
            String authUser,
            CustomerType customerType,
            String fullName,
            String email,
            String phone,
            List<CustomerAddressSeed> addresses
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CustomerAddressSeed(
            String label,
            String zipCode,
            String street,
            String number,
            String neighborhood,
            String state,
            String city,
            String complement,
            Boolean defaultAddress
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PartnerSeed(
            String key,
            String name,
            Boolean active,
            BigDecimal defaultSplitPercentage,
            Set<PartnerRole> roles
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ItemSeed(
            String key,
            String name,
            ItemType type,
            UnitType baseUnit,
            BigDecimal lowStockThreshold,
            BigDecimal criticalStockThreshold,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StockSeed(
            String key,
            String item,
            BigDecimal quantity,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SellableStockSeed(
            String key,
            String item,
            Boolean infinite,
            Boolean active,
            BigDecimal pricePf,
            BigDecimal pricePj
    ) {
    }

    public record TestDatabaseSnapshot(
            Map<String, AuthUser> authUsers,
            Map<String, CustomerProfile> customers,
            Map<String, Partner> partners,
            Map<String, Item> items,
            Map<String, Stock> stocks,
            Map<String, SellableStock> sellableStocks
    ) {
        public AuthUser authUser(String key) {
            return require(authUsers, key, "authUser");
        }

        public CustomerProfile customer(String key) {
            return require(customers, key, "customer");
        }

        public Partner partner(String key) {
            return require(partners, key, "partner");
        }

        public Item item(String key) {
            return require(items, key, "item");
        }

        public Stock stock(String key) {
            return require(stocks, key, "stock");
        }

        public SellableStock sellableStock(String key) {
            return require(sellableStocks, key, "sellableStock");
        }
    }
}
