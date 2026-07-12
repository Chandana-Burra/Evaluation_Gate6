package com.shopkart.ui.pages;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final String emailTextBox = "#email";
    private final String passwordTextBox = "#password";
    private final String signInButton = "button[type='submit']";

    public void openLoginPage() {
        open("/login");
    }
    public void enterEmail(String email) {
        $(emailTextBox).shouldBe(visible).setValue(email);
    }

    public void enterPassword(String password) {
        $(passwordTextBox).shouldBe(visible).setValue(password);
    }

    public void clickSignIn() {
        $(signInButton).shouldBe(visible).click();
    }


    public CatalogPage login(String email, String password) {

        openLoginPage();

        enterEmail(email);
        enterPassword(password);
        clickSignIn();

        return new CatalogPage();
    }

}