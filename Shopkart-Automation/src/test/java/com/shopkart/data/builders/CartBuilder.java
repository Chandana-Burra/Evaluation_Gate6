package com.shopkart.data.builders;

import com.shopkart.api.CartClient;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartBuilder {

    private final CartClient cartClient = new CartClient();

    private final String token;

    private int cartId;

    public CartBuilder(String token) {
        this.token = token;
    }

    public CartBuilder createCart() {

        Response response =
                cartClient.createCart(token);

        assertEquals(
                201,
                response.statusCode(),
                "Cart should be created successfully."
        );

        cartId = response.jsonPath().getInt("id");

        return this;
    }

    public CartBuilder addItem(String sku,
                               int quantity) {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        sku,
                        quantity
                );

        assertEquals(
                200,
                response.statusCode(),
                "Item should be added to the cart."
        );

        return this;
    }

    public int cartId() {
        return cartId;
    }

    public Response getCart() {

        return cartClient.getCart(
                token,
                cartId
        );
    }
}