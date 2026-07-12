package com.shopkart.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    private final String searchBox = "#catalog-search";
    private final String searchButton = "//button[normalize-space()='Search']";
    private final String productCards = ".product-card";

    public boolean isCatalogDisplayed() {
        return $(searchBox)
                .shouldBe(visible)
                .exists();
    }

    public void searchProduct(String productName) {

        $(searchBox)
                .shouldBe(visible)
                .clear();

        $(searchBox)
                .setValue(productName);

        $x(searchButton)
                .shouldBe(enabled)
                .click();
    }

    public ProductPage openProduct(String productName) {

        Xp.openProduct(productName)
                .shouldBe(visible)
                .click();

        return new ProductPage();
    }

    public void addProductToCart(String productName) {

        Xp.addToCart(productName)
                .shouldBe(visible)
                .click();
    }

    public boolean isProductVisible(String productName) {

        return Xp.productCard(productName)
                .shouldBe(visible)
                .exists();
    }

    public String getProductPrice(String productName) {

        return Xp.productPrice(productName)
                .shouldBe(visible)
                .getText();
    }

    public String getProductStock(String productName) {

        return Xp.productStock(productName)
                .shouldBe(visible)
                .getText();
    }

    public int getProductCount() {

        ElementsCollection products = $$(productCards);

        return products.size();
    }
}