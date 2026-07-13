package com.shopkart.data.db;

import com.shopkart.data.db.FlywaySupport;
import com.shopkart.data.db.PostgresSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public abstract class AbstractPostgresIntegrationTest {

    @BeforeAll
    static void migrateDatabase() {
        FlywaySupport.migrate();
    }

    @BeforeEach
    void resetDatabase() throws Exception {

        DataSource dataSource =
                PostgresSupport.createDataSource();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    TRUNCATE TABLE
                    orders,
                    order_items,
                    carts,
                    cart_items
                    RESTART IDENTITY CASCADE
                    """);
        }
    }
}