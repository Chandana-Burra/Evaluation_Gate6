package com.shopkart.data.builders;
import com.shopkart.api.CartClient;
import com.shopkart.api.OrderClient;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderBuilder {

    private final CartClient cartClient = new CartClient();
    private final OrderClient orderClient = new OrderClient();

    private final String token;

    private int cartId;
    private int orderId;

    public OrderBuilder(String token) {

        this.token = token;

        Response cartResponse = cartClient.createCart(token);

        assertEquals(201, cartResponse.statusCode());

        cartId = cartResponse.jsonPath().getInt("id");
    }

    public OrderBuilder addProduct(String sku, int quantity) {

        Response response =
                cartClient.addItemToCart(
                        token,
                        cartId,
                        sku,
                        quantity
                );

        assertEquals(200, response.statusCode());

        return this;
    }

    public OrderBuilder checkout(String address) {

        String fullAddress = address.length() < 10 
            ? "123 Main Street, " + address + " 500001"
            : address;

        Response response =
                orderClient.placeOrder(
                        token,
                        cartId,
                        fullAddress
                );

        assertEquals(201, response.statusCode());

        orderId = response.jsonPath().getInt("id");

        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCartId() {
        return cartId;
    }
}