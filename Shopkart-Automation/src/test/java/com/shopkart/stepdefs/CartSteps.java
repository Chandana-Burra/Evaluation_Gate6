package com.shopkart.stepdefs;

import com.shopkart.api.CartClient;
import com.shopkart.data.builders.CartBuilder;
import com.shopkart.data.builders.CustomerBuilder;
import com.shopkart.data.db.DbSupport;
import com.shopkart.support.World;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {

    private final World world;

    private final DbSupport dbSupport = new DbSupport();

    private final CartClient cartClient = new CartClient();

    private Response response;

    private CartBuilder cartBuilder;

    private Response addResponse;

    public CartSteps(World world) {
        this.world = world;
    }



    @Given("{string} has a cart")
    public void customerHasCart(String persona) {

        world.currentUser =
                CustomerBuilder.aCustomer()
                        .named(persona)
                        .login();

        cartBuilder =
                new CartBuilder(world.currentUser.token());

        cartBuilder.createCart();

        world.cartId = cartBuilder.cartId();
    }



    @When("she adds {int} x {string}")
    public void addItems(int quantity,
                         String sku) {

        addResponse =
                cartClient.addItemToCart(
                        world.currentUser.token(),
                        world.cartId,
                        sku,
                        quantity
                );
    }



    @Then("the cart total should be {int} paise")
    public void verifyCartTotal(int expectedTotal) {

        Response cartResponse =
                cartClient.getCart(
                        world.currentUser.token(),
                        world.cartId
                );

        assertEquals(
                expectedTotal,
                cartResponse.jsonPath().getInt("totalPaise"),
                "Incorrect cart total."
        );
    }

    @Then("the database stores total {int}")
    public void verifyDatabaseTotal(int expectedTotal)
            throws Exception {

        assertEquals(
                expectedTotal,
                dbSupport.getCartTotal(world.cartId),
                "Incorrect cart total stored in database."
        );
    }



    @Then("the response status should be {int}")
    public void verifyStatus(int expectedStatus) {

        Response resp = addResponse != null ? addResponse : world.response;

        assertEquals(
                expectedStatus,
                resp.statusCode(),
                "Unexpected response status."
        );
    }

}