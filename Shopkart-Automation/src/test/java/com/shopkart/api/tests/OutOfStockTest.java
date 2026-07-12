package com.shopkart.api.tests;

import com.shopkart.api.AuthClient;
import com.shopkart.api.CartClient;
import com.shopkart.data.secret.Secrets;
import com.shopkart.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OutOfStockTest extends BaseApiTest {

    private final AuthClient authClient = new AuthClient();
    private final CartClient cartClient = new CartClient();

    private String token;
    private int cartId;

    @BeforeEach
    void setUp() {

        Response login = authClient.login(
                "alice@shopkart.test",
                Secrets.getAlicePassword()
        );

        assertEquals(200, login.statusCode());

        token = login.jsonPath().getString("token");

        Response cart = cartClient.createCart(token);

        assertEquals(201, cart.statusCode());

        cartId = cart.jsonPath().getInt("id");
    }

    @Test
    void shouldRejectQuantityGreaterThanStock() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-BAG",
                        100
                );

        System.out.println(response.asPrettyString());

        assertEquals(409, response.statusCode());

        assertNotNull(
                response.jsonPath().getString("error.code")
        );
    }

    @Test
    void shouldRejectOutOfStockProduct() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-CAP",
                        1
                );

        System.out.println(response.asPrettyString());

        assertEquals(409, response.statusCode());

        assertNotNull(
                response.jsonPath().getString("error.code")
        );
    }

    @Test
    void shouldRejectNegativeQuantity() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-BAG",
                        -1
                );

        System.out.println(response.asPrettyString());

        assertTrue(
                response.statusCode() == 400 ||
                        response.statusCode() == 422
        );
    }

    @Test
    void shouldRejectUnknownSku() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-UNKNOWN",
                        1
                );

        System.out.println(response.asPrettyString());

        assertEquals(404, response.statusCode());
    }
}