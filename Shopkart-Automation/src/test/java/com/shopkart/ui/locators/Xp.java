package com.shopkart.ui.locators;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public final class Xp {

    private Xp() {
    }

    // ==========================================================
    // HEADER
    // ==========================================================

    public static final String CATALOG =
            "//nav//button[normalize-space()='Catalog']";

    public static final String CART =
            "//nav//button[normalize-space()='Cart']";

    public static final String API_DOCS =
            "//nav//a[normalize-space()='API docs']";

    public static final String SIGN_OUT =
            "//button[@aria-label='Sign out']";

    public static final String SIGNED_IN_USER =
            "//span[contains(@class,'signed-in')]";


    // ==========================================================
    // LOGIN PAGE
    // ==========================================================

    public static final String EMAIL =
            "//input[@name='email']";

    public static final String PASSWORD =
            "//input[@name='password']";

    public static final String SIGN_IN =
            "//button[normalize-space()='Sign in']";


    // ==========================================================
    // CATALOG PAGE
    // ==========================================================

    public static SelenideElement productCard(String productName) {

        return $x(
                "//div[contains(@class,'product-card')]" +
                        "[.//h2/button[normalize-space()='" +
                        productName +
                        "']]"
        );
    }

    public static SelenideElement openProduct(String productName) {

        return productCard(productName)
                .$x(".//button[contains(@aria-label,'Open')]");
    }

    public static SelenideElement addToCart(String productName) {

        return productCard(productName)
                .$x(".//button[normalize-space()='Add to cart']");
    }

    public static SelenideElement productPrice(String productName) {

        return productCard(productName)
                .$x(".//div[contains(@class,'product-footer')]//strong");
    }

    public static SelenideElement productStock(String productName) {

        return productCard(productName)
                .$x(".//span[contains(@class,'stock')]");
    }

    public static SelenideElement productTitle(String productName) {

        return productCard(productName)
                .$x(".//h2/button");
    }


    // ==========================================================
    // PRODUCT DETAILS PAGE
    // ==========================================================

    public static final String PRODUCT_NAME =
            "//h1";

    public static final String PRODUCT_PRICE =
            "//strong[@data-role='product-price']";

    public static final String QUANTITY =
            "//input[@id='quantity']";

    public static final String ADD_TO_CART_BUTTON =
            "//button[normalize-space()='Add to cart']";


    // ==========================================================
    // CART PAGE
    // ==========================================================

    public static final String CART_TOTAL =
            "//strong[@data-role='cart-total']";

    public static final String CHECKOUT_BUTTON =
            "//button[normalize-space()='Checkout']";

    public static final String CONTINUE_SHOPPING_BUTTON =
            "//button[normalize-space()='Continue shopping']";


    // ==========================================================
    // CHECKOUT PAGE
    // ==========================================================

    public static final String ADDRESS =
            "//textarea[@name='address']";

    public static final String PLACE_ORDER =
            "//button[normalize-space()='Place order']";


    // ==========================================================
    // ORDER CONFIRMATION PAGE
    // ==========================================================

    public static final String ORDER_STATUS =
            "//*[@data-field='order-status']";

    public static final String ORDER_TOTAL =
            "//*[@data-field='order-total']";

    public static final String RETURN_TO_CATALOG =
            "//button[normalize-space()='Return to catalog']";
}