package com.shopkart.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CartClient {

    public Response createCart(String token) {

        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/carts");
    }

    public Response getCart(String token, int cartId) {

        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", cartId)
                .when()
                .get("/carts/{id}");
    }

    public Response addItemToCart(String token,
                                  int cartId,
                                  String sku,
                                  int quantity) {

        String requestBody = """
                {
                  "sku":"%s",
                  "qty":%d
                }
                """.formatted(sku, quantity);

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .pathParam("id", cartId)
                .body(requestBody)
                .when()
                .post("/carts/{id}/items");
    }

}