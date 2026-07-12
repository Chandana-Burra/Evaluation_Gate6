package com.shopkart.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public Response login(String email, String password) {

        String requestBody = """
                {
                  "email":"%s",
                  "password":"%s"
                }
                """.formatted(email, password);

        return given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login");
    }

    public String loginAndGetToken(String email, String password) {

        Response response = login(email, password);

        return response.jsonPath().getString("token");
    }
}