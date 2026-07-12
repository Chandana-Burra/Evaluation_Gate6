package com.shopkart.api.tests;

import com.shopkart.api.AuthClient;
import com.shopkart.api.CartClient;
import com.shopkart.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Cart API tests covered by Cucumber BDD tests")
public class CartApiTest extends BaseApiTest {

    private final AuthClient authClient = new AuthClient();
    private final CartClient cartClient = new CartClient();

    private String token;
    private int cartId;

    @BeforeEach
    void setUpCart() {

        Response loginResponse =
                authClient.login("alice@shopkart.test", "password");

        assertEquals(200, loginResponse.statusCode());

        token = loginResponse.jsonPath().getString("token");

        Response cartResponse =
                cartClient.createCart(token);

        assertEquals(201, cartResponse.statusCode());

        cartId = cartResponse.jsonPath().getInt("id");
    }

    @Test
    void shouldCreateCart() {

        assertNotNull(token);

        assertTrue(cartId > 0);
    }

    @Test
    void shouldGetCart() {

        Response response =
                cartClient.getCart(token, cartId);

        assertEquals(200, response.statusCode());

        assertEquals(cartId,
                response.jsonPath().getInt("id"));
    }

    @Test
    void shouldAddSingleItemToCart() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-BAG",
                        1
                );

        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldAddMultipleItemsToCart() {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        "SKU-BAG",
                        2
                );

        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken() {

        Response response =
                cartClient.createCart("");

        assertEquals(401, response.statusCode());
    }

    @Test
    void shouldReturnNotFoundForInvalidCart() {

        Response response =
                cartClient.getCart(token, 999999);

        assertEquals(404, response.statusCode());
    }

}
