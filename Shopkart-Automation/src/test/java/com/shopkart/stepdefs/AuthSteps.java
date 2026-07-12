package com.shopkart.stepdefs;

import com.shopkart.data.builders.CustomerBuilder;
import com.shopkart.support.World;
import io.cucumber.java.en.Given;

public class AuthSteps {

    private final World world;

    public AuthSteps(World world) {
        this.world = world;
    }

    @Given("{string} is logged in")
    public void login(String persona) {

        world.currentUser =
                CustomerBuilder.aCustomer()
                        .named(persona)
                        .login();
    }
}