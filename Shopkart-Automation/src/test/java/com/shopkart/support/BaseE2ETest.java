package com.shopkart.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static com.shopkart.config.AppConfig.*;

public abstract class BaseE2ETest {

    @BeforeAll
    static void setUpFramework() {

        // ---------- UI ----------
        com.codeborne.selenide.Configuration.browser = browser();
        com.codeborne.selenide.Configuration.baseUrl = baseUrl();
        com.codeborne.selenide.Configuration.headless = headless();
        com.codeborne.selenide.Configuration.timeout = timeout() * 1000;
        com.codeborne.selenide.Configuration.browserSize = "1920x1080";
        com.codeborne.selenide.Configuration.pageLoadTimeout = 30000;

        // ---------- API ----------
        RestAssured.baseURI = apiBaseUrl();
    }
}