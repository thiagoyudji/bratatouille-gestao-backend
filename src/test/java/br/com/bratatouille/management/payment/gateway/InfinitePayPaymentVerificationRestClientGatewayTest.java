package br.com.bratatouille.management.payment.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class InfinitePayPaymentVerificationRestClientGatewayTest {

    private MockRestServiceServer server;
    private InfinitePayPaymentVerificationRestClientGateway gateway;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        gateway = new InfinitePayPaymentVerificationRestClientGateway(
                builder,
                "https://api.checkout.infinitepay.io",
                "bratatouille"
        );
    }

    @Test
    void verifiesPaymentUsingProviderIdentifiers() {
        server.expect(requestTo("https://api.checkout.infinitepay.io/payment_check"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("""
                        {
                          "handle": "bratatouille",
                          "order_nsu": "123",
                          "transaction_nsu": "transaction-123",
                          "slug": "invoice-123"
                        }
                        """))
                .andRespond(withSuccess("""
                        {
                          "success": true,
                          "paid": true,
                          "amount": 1850,
                          "paid_amount": 1900,
                          "capture_method": "credit_card"
                        }
                        """, MediaType.APPLICATION_JSON));

        var result = gateway.verify("123", "transaction-123", "invoice-123");

        assertTrue(result.paid());
        assertEquals(1850, result.amount());
        assertEquals(1900, result.paidAmount());
        assertEquals("credit_card", result.captureMethod());
        server.verify();
    }

    @Test
    void rejectsProviderResponseThatCannotVerifyPayment() {
        server.expect(requestTo("https://api.checkout.infinitepay.io/payment_check"))
                .andRespond(withSuccess("""
                        {"success": false, "paid": false}
                        """, MediaType.APPLICATION_JSON));

        assertThrows(
                IllegalArgumentException.class,
                () -> gateway.verify("123", "transaction-123", "invoice-123")
        );
        server.verify();
    }
}
