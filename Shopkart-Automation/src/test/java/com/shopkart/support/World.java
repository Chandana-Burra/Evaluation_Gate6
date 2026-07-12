package com.shopkart.support;

import com.shopkart.data.builders.CustomerBuilder;
import com.shopkart.ui.pages.*;
import io.restassured.response.Response;

public class World {

    // Logged in customer
    public CustomerBuilder.Session currentUser;

    // Page Objects
    public CatalogPage catalogPage;
    public ProductPage productPage;
    public CartPage cartPage;
    public CheckoutPage checkoutPage;
    public OrderPage orderPage;

    // Shared IDs
    public int cartId;
    public int orderId;
    
    // Shared response (for API tests)
    public Response response;
}