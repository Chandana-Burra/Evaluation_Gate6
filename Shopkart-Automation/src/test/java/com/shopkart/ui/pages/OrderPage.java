package com.shopkart.ui.pages;
import com.shopkart.ui.locators.Xp;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class OrderPage {
    public String getOrderStatus() {
        return $x(Xp.ORDER_STATUS).shouldBe(visible).getText();
    }

    public String getOrderTotal() {
        return $x(Xp.ORDER_TOTAL).shouldBe(visible).getText();
    }
    public void returnToCatalog() {
        $x(Xp.RETURN_TO_CATALOG).shouldBe(visible).click();
    }
}