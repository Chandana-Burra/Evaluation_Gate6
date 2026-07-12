package com.shopkart.ui.pages;

import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class CartPage {

    public boolean isCartDisplayed() {
        return true;
    }

    public String getCartTotal() {

        return $x(Xp.CART_TOTAL)
                .shouldBe(visible)
                .getText();
    }

    public CheckoutPage clickCheckout() {

        $x(Xp.CHECKOUT_BUTTON)
                .shouldBe(visible)
                .click();

        return new CheckoutPage();
    }

    public void continueShopping() {

        $x(Xp.CONTINUE_SHOPPING_BUTTON)
                .shouldBe(visible)
                .click();
    }
}