package com.shopkart.ui.components;

import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.visible;

public class ProductCard {

    private final String productName;

    public ProductCard(String productName) {
        this.productName = productName;
    }

    public void openProduct() {

        Xp.openProduct(productName)
                .shouldBe(visible)
                .click();
    }

    public String getPrice() {

        return Xp.productPrice(productName)
                .shouldBe(visible)
                .getText();
    }

    public String getStock() {

        return Xp.productStock(productName)
                .shouldBe(visible)
                .getText();
    }

    public void addToCart() {

        Xp.addToCart(productName)
                .shouldBe(visible)
                .click();
    }
}