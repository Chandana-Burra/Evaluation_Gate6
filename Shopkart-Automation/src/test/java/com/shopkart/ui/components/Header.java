package com.shopkart.ui.components;

import com.shopkart.ui.locators.Xp;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Header {

    public void openCatalog() {
        $x(Xp.CATALOG).shouldBe(visible).click();
    }

    public void openCart() {
        $x(Xp.CART).shouldBe(visible).click();
    }

    public void openApiDocs() {
        $x(Xp.API_DOCS).shouldBe(visible).click();
    }

    public String getLoggedInUser() {
        return $x(Xp.SIGNED_IN_USER).shouldBe(visible).getText();
    }

    public void signOut() {
        $x(Xp.SIGN_OUT).shouldBe(visible).click();
    }
}