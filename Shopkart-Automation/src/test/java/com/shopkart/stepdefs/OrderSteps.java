package com.shopkart.stepdefs;

import com.shopkart.api.OrderClient;
import com.shopkart.data.builders.CustomerBuilder;
import com.shopkart.data.builders.OrderBuilder;
import com.shopkart.data.db.DbSupport;
import com.shopkart.support.World;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderSteps {

    private final World world;
    private final OrderClient orderClient = new OrderClient();
    private final DbSupport dbSupport = new DbSupport();

    private Response response;
    private Response cancelResponse;

    public OrderSteps(World world) {
        this.world = world;
    }


    @Given("{string} has a PLACED order")
    public void customerHasPlacedOrder(String persona) {

        world.currentUser =
                CustomerBuilder.aCustomer()
                        .named(persona)
                        .login();

        OrderBuilder builder =
                new OrderBuilder(world.currentUser.token());

        builder
                .addProduct("SKU-BAG", 2)
                .checkout("Hyderabad");

        world.orderId = builder.getOrderId();
    }

    @When("{string} requests the order")
    public void anotherCustomerRequestsOrder(String persona) {

        CustomerBuilder.Session anotherCustomer =
                CustomerBuilder.aCustomer()
                        .named(persona)
                        .login();

        world.response =
                orderClient.getOrder(
                        anotherCustomer.token(),
                        world.orderId
                );
    }



    @When("she cancels the order")
    public void cancelOrder() {

        cancelResponse =
                orderClient.cancelOrder(
                        world.currentUser.token(),
                        world.orderId
                );
    }

    @Then("the order status becomes {string}")
    public void verifyCancelledStatus(String expectedStatus)
            throws Exception {

        assertEquals(
                200,
                cancelResponse.statusCode(),
                "Cancel request should succeed."
        );

        assertEquals(
                expectedStatus,
                dbSupport.getOrderStatus(world.orderId),
                "Order status in database is incorrect."
        );
    }

    @Then("cancelling the same order again returns {int}")
    public void cancelAgain(int expectedStatus) {

        Response secondResponse =
                orderClient.cancelOrder(
                        world.currentUser.token(),
                        world.orderId
                );

        assertEquals(
                expectedStatus,
                secondResponse.statusCode(),
                "Second cancel should return the expected status."
        );
    }
}