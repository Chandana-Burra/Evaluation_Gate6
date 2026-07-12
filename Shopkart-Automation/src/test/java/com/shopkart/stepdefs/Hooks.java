package com.shopkart.stepdefs;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Configuration;
import com.shopkart.support.World;
import com.shopkart.ui.pages.*;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

import static com.shopkart.config.AppConfig.*;

public class Hooks {

    private final World world;
    private static boolean apiConfigured = false;

    public Hooks(World world) {
       this.world = world;
    }

    @Before
    public void setupFramework() {
       if (!apiConfigured) {
           RestAssured.baseURI = apiBaseUrl();
           apiConfigured = true;
       }
    }

    @Before(value = "@ui")
    public void setupUiTest() {
       Configuration.browser = browser();
       Configuration.baseUrl = baseUrl();
       Configuration.headless = headless();
       Configuration.timeout = timeout() * 1000;
       Configuration.browserSize = "1920x1080";
       Configuration.pageLoadTimeout = 30000;

       Selenide.open("/");

       world.catalogPage = new CatalogPage();
       world.productPage = new ProductPage();
       world.cartPage = new CartPage();
       world.checkoutPage = new CheckoutPage();
       world.orderPage = new OrderPage();
    }

    @Before(value = "@e2e")
    public void setupE2eTest() {
       Configuration.browser = browser();
       Configuration.baseUrl = baseUrl();
       Configuration.headless = headless();
       Configuration.timeout = timeout() * 1000;
       Configuration.browserSize = "1920x1080";
       Configuration.pageLoadTimeout = 30000;

       Selenide.open("/");

       world.catalogPage = new CatalogPage();
       world.productPage = new ProductPage();
       world.cartPage = new CartPage();
       world.checkoutPage = new CheckoutPage();
       world.orderPage = new OrderPage();
    }
}
