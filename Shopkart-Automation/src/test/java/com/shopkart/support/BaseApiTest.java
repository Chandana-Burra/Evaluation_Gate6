package com.shopkart.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static com.shopkart.config.AppConfig.apiBaseUrl;

public class BaseApiTest {

    @BeforeAll
    static void setUpApi() {

        RestAssured.baseURI = apiBaseUrl();

    }

}