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

public class OrderSecurityTest extends BaseApiTest {

    private final AuthClient authClient = new AuthClient();
    private final OrderClient orderClient = new OrderClient();

    private String aliceToken;
    private String bobToken;

    @BeforeEach
    void loginUsers() {

        Response aliceLogin = authClient.login(
                "alice@shopkart.test",
                Secrets.getAlicePassword());

        assertEquals(200, aliceLogin.statusCode());

        aliceToken = aliceLogin.jsonPath().getString("token");

        Response bobLogin = authClient.login(
                "bob@shopkart.test",
                Secrets.getBobPassword());

        assertEquals(200, bobLogin.statusCode());

        bobToken = bobLogin.jsonPath().getString("token");
    }

    @Test
    void ownerShouldAccessOwnOrder() {

        int orderId = new OrderBuilder(aliceToken)
                .addProduct("SKU-BAG", 2)
                .checkout("123 Main Road, Hyderabad")
                .getOrderId();

        Response response =
                orderClient.getOrder(aliceToken, orderId);

        assertEquals(200, response.statusCode());

        assertEquals(
                orderId,
                response.jsonPath().getInt("id")
        );
    }

    @Test
    void anotherCustomerShouldReceive403() {

        int orderId = new OrderBuilder(aliceToken)
                .addProduct("SKU-BAG", 2)
                .checkout("123 Main Road, Hyderabad")
                .getOrderId();

        Response response =
                orderClient.getOrder(bobToken, orderId);

        assertEquals(403, response.statusCode());

        assertEquals(
                "ORDER_FORBIDDEN",
                response.jsonPath().getString("error.code")
        );

        assertEquals(
                "The order belongs to another customer",
                response.jsonPath().getString("error.message")
        );
    }
}