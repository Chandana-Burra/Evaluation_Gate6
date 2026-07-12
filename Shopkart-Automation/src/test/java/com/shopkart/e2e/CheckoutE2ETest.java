package com.shopkart.e2e;

import com.shopkart.data.db.DbSupport;
import com.shopkart.data.secret.Secrets;
import com.shopkart.support.BaseE2ETest;
import com.shopkart.ui.pages.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutE2ETest extends BaseE2ETest {

    private static final String EMAIL = "alice@shopkart.test";
    private static final String ADDRESS = "123 Main Road, Hyderabad";

    private final DbSupport dbSupport = new DbSupport();

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {

        CatalogPage catalogPage =
                new LoginPage().login(
                        EMAIL,
                        Secrets.getAlicePassword()
                );

        assertTrue(
                catalogPage.isCatalogDisplayed(),
                "Catalog page should be displayed after successful login."
        );
   catalogPage.searchProduct("Bag");

        assertTrue(
                catalogPage.isProductVisible("Metro Carryall"),
                "Metro Carryall should appear in search results."
        );


        ProductPage productPage =
                catalogPage.openProduct("Metro Carryall");

        assertEquals(
                "Metro Carryall",
                productPage.getProductName(),
                "Incorrect product page opened."
        );


        productPage.enterQuantity(2);



        CartPage cartPage =
                productPage.addToCart();

        assertTrue(
                cartPage.isCartDisplayed(),
                "Cart page should be displayed."
        );

        assertTrue(
                cartPage.getCartTotal().contains("998"),
                "Cart total should be ₹998.00."
        );


        CheckoutPage checkoutPage =
                cartPage.clickCheckout();

        checkoutPage.enterAddress(ADDRESS);



        OrderPage orderPage =
                checkoutPage.placeOrder();

        assertEquals(
                "PLACED",
                orderPage.getOrderStatus(),
                "Order status should be PLACED."
        );

        assertTrue(
                orderPage.getOrderTotal().contains("998"),
                "Incorrect order total shown."
        );

        assertTrue(
                dbSupport.orderExists(EMAIL),
                "Order should exist in database."
        );

        assertEquals(
                "PLACED",
                dbSupport.getLatestOrderStatus(EMAIL),
                "Incorrect order status stored in database."
        );

        assertEquals(
                99800,
                dbSupport.getLatestOrderTotal(EMAIL),
                "Incorrect order total stored in database."
        );

        assertEquals(
                ADDRESS,
                dbSupport.getLatestOrderAddress(EMAIL),
                "Incorrect address stored in database."
        );

        assertEquals(
                1,
                dbSupport.getLatestCustomerId(EMAIL),
                "Incorrect customer id stored in database."
        );
    }
}