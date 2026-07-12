package com.shopkart.api.tests;

import com.shopkart.api.AuthClient;
import com.shopkart.api.OrderClient;
import com.shopkart.data.builders.OrderBuilder;
import com.shopkart.data.secret.Secrets;
import com.shopkart.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CancelOrderTest extends BaseApiTest {

    private final AuthClient authClient = new AuthClient();
    private final OrderClient orderClient = new OrderClient();

    private String token;

    @BeforeEach
    void login() {

        Response login = authClient.login(
                "alice@shopkart.test",
                Secrets.getAlicePassword());

        assertEquals(200, login.statusCode());

        token = login.jsonPath().getString("token");

        assertNotNull(token);
    }

    @Test
    void shouldCancelPlacedOrder() {

        int orderId = new OrderBuilder(token)
                .addProduct("SKU-BAG", 2)
                .checkout("123 Main Road, Hyderabad")
                .getOrderId();

        Response response =
                orderClient.cancelOrder(token, orderId);

        System.out.println(response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertEquals(
                "CANCELLED",
                response.jsonPath().getString("status")
        );
    }

    @Test
    void shouldNotCancelOrderTwice() {

        int orderId = new OrderBuilder(token)
                .addProduct("SKU-BAG", 2)
                .checkout("123 Main Road, Hyderabad")
                .getOrderId();

        orderClient.cancelOrder(token, orderId);

        Response secondAttempt =
                orderClient.cancelOrder(token, orderId);

        System.out.println(secondAttempt.asPrettyString());

        assertEquals(409, secondAttempt.statusCode());

        assertNotNull(
                secondAttempt.jsonPath().getString("error.code")
        );
    }

    @Test
    void shouldReturn404ForUnknownOrder() {

        Response response =
                orderClient.cancelOrder(token, 999999);

        System.out.println(response.asPrettyString());

        assertEquals(404, response.statusCode());

        assertNotNull(
                response.jsonPath().getString("error.code")
        );
    }
}