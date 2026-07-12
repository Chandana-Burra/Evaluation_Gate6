package com.shopkart.ui.pages;

import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {

    public String getProductName() {

        return $x(Xp.PRODUCT_NAME)
                .shouldBe(visible)
                .getText();
    }

    public String getProductPrice() {

        return $x(Xp.PRODUCT_PRICE)
                .shouldBe(visible)
                .getText();
    }

    public void enterQuantity(int quantity) {

        $x(Xp.QUANTITY)
                .shouldBe(visible)
                .clear();

        $x(Xp.QUANTITY)
                .setValue(String.valueOf(quantity));
    }

    public CartPage addToCart() {

        $x(Xp.ADD_TO_CART_BUTTON)
                .shouldBe(visible)
                .click();

        return new CartPage();
    }
}