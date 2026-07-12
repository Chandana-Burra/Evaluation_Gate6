package com.shopkart.stepdefs;

import com.shopkart.api.CartClient;
import com.shopkart.api.OrderClient;
import com.shopkart.data.builders.CartBuilder;
import com.shopkart.data.db.DbSupport;
import com.shopkart.support.World;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {

    private final World world;
    private final DbSupport dbSupport = new DbSupport();
    private final OrderClient orderClient = new OrderClient();
    private final CartClient cartClient = new CartClient();

    public CheckoutSteps(World world) {
       this.world = world;
    }

    @Given("she adds {int} x {string} to her cart")
    public void addProductToCart(int quantity, String sku) {

       if (world.cartId == 0) {
           Response cartResp = cartClient.createCart(world.currentUser.token());
           world.cartId = cartResp.jsonPath().getInt("id");
       }
        

       cartClient.addItemToCart(
           world.currentUser.token(),
           world.cartId,
           sku,
           quantity
       );
    }

    @When("she checks out with a valid address")
    public void checkout() {
       Response response = orderClient.placeOrder(
           world.currentUser.token(),
           world.cartId,
           "123 Main Street, Hyderabad 500001"
       );
        
       assertEquals(201, response.statusCode(), "Checkout should succeed");
       world.orderId = response.jsonPath().getInt("id");
    }

    @Then("the order confirmation shows status {string}")
    public void verifyUi(String expectedStatus) {
       Response response = orderClient.getOrder(
           world.currentUser.token(),
           world.orderId
       );
        
       assertEquals(expectedStatus, response.jsonPath().getString("status"));
    }

    @Then("the API returns status {string} and totalPaise {int}")
    public void verifyApi(String expectedStatus, int expectedTotal) {
       Response response = orderClient.getOrder(
           world.currentUser.token(),
           world.orderId
       );

       assertEquals(200, response.statusCode());
       assertEquals(expectedStatus, response.jsonPath().getString("status"));
       assertEquals(expectedTotal, response.jsonPath().getInt("totalPaise"));
    }

    @Then("the orders table has exactly one {string} row for {string}")
    public void verifyDatabase(String expectedStatus, String persona) throws Exception {
       int count = dbSupport.getOrderCount(persona + "@shopkart.test");
       assertTrue(count >= 1, "Should have at least one order for " + persona);
       assertEquals(expectedStatus, dbSupport.getLatestOrderStatus(persona + "@shopkart.test"));
    }
}