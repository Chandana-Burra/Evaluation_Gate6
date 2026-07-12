package com.shopkart.ui.pages;

import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class CheckoutPage {
    public void enterAddress(String address) {
        $x(Xp.ADDRESS).shouldBe(visible).clear();
        $x(Xp.ADDRESS).setValue(address);
    }

    public OrderPage placeOrder() {
        $x(Xp.PLACE_ORDER).shouldBe(visible).click();
        return new OrderPage();
    }

}