package com.shopkart.api;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProductClient {

    public Response getAllProducts(String searchText) {

        return given()
                .queryParam("q", searchText)
                .when()
                .get("/products");
    }

    public Response getProduct(String sku) {

        return given()
                .pathParam("sku", sku)
                .when()
                .get("/products/{sku}");
    }

}