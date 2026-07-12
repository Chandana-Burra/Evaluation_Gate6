package com.shopkart.stepdefs;

import com.shopkart.api.ProductClient;
import com.shopkart.support.World;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductSearchSteps {

    private final World world;
    private final ProductClient productClient = new ProductClient();
    private Response productResponse;

    public ProductSearchSteps(World world) {
        this.world = world;
    }

    @When("she searches for {string}")
    public void searchForProduct(String productName) {
        world.catalogPage.searchProduct(productName);
    }

    @Then("matching products are displayed")
    public void verifyMatchingProductsDisplayed() {
        assertFalse(world.catalogPage.getProductCount() == 0, "No matching products found in UI"
        );
    }

    @Then("the Product API returns {string}")
    public void verifyProductApiResponse(String productName) {
        productResponse = productClient.getAllProducts(productName);
        assertTrue(
                productResponse.statusCode() == 200,
                "Product API search should return 200"
        );

        // Verify that at least one product matches
        int productCount = productResponse.jsonPath().getList("products").size();
        assertFalse(
                productCount == 0,
                "API should return matching products for: " + productName
        );
    }
}
