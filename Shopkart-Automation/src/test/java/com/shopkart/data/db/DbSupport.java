package com.shopkart.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.shopkart.config.Env.optional;

public class DbSupport {

    private final String url;
    private final String username;
    private final String password;

    public DbSupport() {

        url = String.format(
                "jdbc:postgresql://%s:%s/%s",
                optional("DB_HOST", "localhost"),
                optional("DB_PORT", "5000"),
                optional("DB_NAME", "shopkart")
        );

        username = optional("DB_USER", "postgres");
        password = optional("DB_PASSWORD", "");
    }

    private Connection connection() throws Exception {

        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }

    // ==========================================================
    // ORDER COUNT
    // ==========================================================

    public int getOrderCount(String email) throws Exception {

        String sql = """
                SELECT COUNT(*)
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    public boolean orderExists(String email) throws Exception {

        return getOrderCount(email) > 0;
    }

    // ==========================================================
    // ORDER ID
    // ==========================================================

    public int getLatestOrderId(String email) throws Exception {

        String sql = """
                SELECT o.id
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                ORDER BY o.created_at DESC
                LIMIT 1
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

            return 0;
        }
    }

    // ==========================================================
    // ORDER STATUS
    // ==========================================================

    public String getLatestOrderStatus(String email)
            throws Exception {

        String sql = """
                SELECT o.status
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                ORDER BY o.created_at DESC
                LIMIT 1
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status");
            }

            return null;
        }
    }

    // ==========================================================
    // ORDER TOTAL
    // ==========================================================

    public int getLatestOrderTotal(String email)
            throws Exception {

        String sql = """
                SELECT o.total_paise
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                ORDER BY o.created_at DESC
                LIMIT 1
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total_paise");
            }

            return 0;
        }
    }

    // ==========================================================
    // ORDER ADDRESS
    // ==========================================================

    public String getLatestOrderAddress(String email)
            throws Exception {

        String sql = """
                SELECT o.address
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                ORDER BY o.created_at DESC
                LIMIT 1
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("address");
            }

            return null;
        }
    }

    // ==========================================================
    // CUSTOMER ID
    // ==========================================================

    public int getLatestCustomerId(String email)
            throws Exception {

        String sql = """
                SELECT o.customer_id
                FROM orders o
                JOIN customers c
                ON o.customer_id = c.id
                WHERE c.email = ?
                ORDER BY o.created_at DESC
                LIMIT 1
                """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("customer_id");
            }

            return 0;
        }

    }
    public String getOrderStatus(int orderId)
            throws Exception {

        String sql = """
            SELECT status
            FROM orders
            WHERE id = ?
            """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status");
            }

            return null;
        }
    }
    public int getCartTotal(int cartId)
            throws Exception {

        String sql = """
            SELECT COALESCE(SUM(qty * unit_price_paise), 0) AS total_paise
            FROM cart_items
            WHERE cart_id = ?
            """;

        try (Connection connection = connection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cartId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total_paise");
            }

            return 0;
        }
    }
}