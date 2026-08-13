package br.com.bratatouille.management.support;

import br.com.bratatouille.management.auth.repository.AuthUserRepository;
import br.com.bratatouille.management.customer.repository.CustomerProfileRepository;
import br.com.bratatouille.management.item.repository.ItemRepository;
import br.com.bratatouille.management.partner.repository.PartnerRepository;
import br.com.bratatouille.management.sellableStock.repository.SellableStockRepository;
import br.com.bratatouille.management.stock.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TestDatabaseSeederIT {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SellableStockRepository sellableStockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loadsYamlSeedWithMixedCustomerAndCatalogData() {
        TestDatabaseSeeder seeder = new TestDatabaseSeeder(
                authUserRepository,
                customerProfileRepository,
                partnerRepository,
                itemRepository,
                stockRepository,
                sellableStockRepository
        );

        TestDatabaseSeeder.TestDatabaseSnapshot snapshot = seeder.seedDefault();

        assertEquals(3, snapshot.authUsers().size());
        assertEquals(2, snapshot.customers().size());
        assertEquals(2, snapshot.partners().size());
        assertEquals(3, snapshot.items().size());
        assertEquals(3, snapshot.stocks().size());
        assertEquals(1, snapshot.sellableStocks().size());

        assertEquals("Pizza Margherita", snapshot.item("pizzaMargherita").getName());
        assertEquals("Cliente PF Teste", snapshot.customer("customerPf").getFullName());
        assertNull(snapshot.customer("customerPf").getAddresses().get(0).getNumber());
        assertNotNull(snapshot.sellableStock("pizzaSellable").getPricePf());
    }
}
