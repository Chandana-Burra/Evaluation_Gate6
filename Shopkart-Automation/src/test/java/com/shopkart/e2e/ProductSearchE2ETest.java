package com.shopkart.e2e;

import com.shopkart.api.ProductClient;
import com.shopkart.data.secret.Secrets;
import com.shopkart.support.BaseE2ETest;
import com.shopkart.ui.pages.CatalogPage;
import com.shopkart.ui.pages.LoginPage;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSearchE2ETest extends BaseE2ETest {

    private final ProductClient productClient = new ProductClient();

    @Test
    void shouldValidateProductSearchFromUiAndApi() {


        CatalogPage catalogPage = new LoginPage().login(
                "alice@shopkart.test",
                Secrets.getAlicePassword()
        );

        assertTrue(
                catalogPage.isCatalogDisplayed(),
                "Catalog page should be displayed after successful login."
        );


        catalogPage.searchProduct("Bag");

        assertTrue(
                catalogPage.isProductVisible("Metro Carryall"),
                "Metro Carryall should be displayed in the catalog."
        );


        Response response = productClient.getAllProducts("Bag");

        assertEquals(
                200,
                response.statusCode(),
                "Products API should return HTTP 200."
        );

        List<?> products = response.jsonPath().getList("$");

        assertFalse(
                products.isEmpty(),
                "Products list should not be empty."
        );

        assertEquals(
                "SKU-BAG",
                response.jsonPath().getString("[0].sku"),
                "Unexpected SKU returned."
        );

        assertEquals(
                "Metro Carryall",
                response.jsonPath().getString("[0].name"),
                "Unexpected product name."
        );

        assertEquals(
                49900,
                response.jsonPath().getInt("[0].pricePaise"),
                "Unexpected product price."
        );

        assertTrue(
                response.jsonPath().getInt("[0].stock") > 0,
                "Product should be in stock."
        );
    }
}