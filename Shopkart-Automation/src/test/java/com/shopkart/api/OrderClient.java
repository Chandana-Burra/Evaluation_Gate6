package com.shopkart.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response placeOrder(String token,
                               int cartId,
                               String address) {

        String requestBody = """
                {
                  "cartId": %d,
                  "address": "%s"
                }
                """.formatted(cartId, address);

        System.out.println("========== PLACE ORDER REQUEST ==========");
        System.out.println(requestBody);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/orders");

        System.out.println("========== PLACE ORDER RESPONSE ==========");
        System.out.println("Status : " + response.statusCode());
        System.out.println(response.asPrettyString());

        return response;
    }

    public Response getOrder(String token, int orderId) {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", orderId)
                .when()
                .get("/orders/{id}");

        System.out.println(response.asPrettyString());

        return response;
    }

    public Response cancelOrder(String token, int orderId) {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", orderId)
                .when()
                .post("/orders/{id}/cancel");

        System.out.println(response.asPrettyString());

        return response;
    }
}