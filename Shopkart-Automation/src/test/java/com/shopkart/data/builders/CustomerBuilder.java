package com.shopkart.data.builders;

import com.shopkart.api.AuthClient;
import com.shopkart.data.secret.Secrets;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class CustomerBuilder {

    private String persona = "alice";

    private CustomerBuilder() {
    }

    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    public CustomerBuilder named(String persona) {
        this.persona = persona.toLowerCase();
        return this;
    }

    public Session login() {

        Response response = new AuthClient().login(
                persona + "@shopkart.test",
                passwordForPersona()
        );

        assertEquals(
                200,
                response.statusCode(),
                "Customer login should be successful."
        );

        return new Session(
                response.jsonPath().getString("token"),
                response.jsonPath().getLong("customer.id"),
                persona
        );
    }

    private String passwordForPersona() {

        return switch (persona) {

            case "alice" -> Secrets.getAlicePassword();

            case "bob" -> Secrets.getBobPassword();

            case "carol" -> Secrets.getCarolPassword();

            default ->
                    throw new IllegalArgumentException(
                            "Unknown customer persona: " + persona
                    );
        };
    }

    public record Session(
            String token,
            long customerId,
            String persona
    ) {
    }
}