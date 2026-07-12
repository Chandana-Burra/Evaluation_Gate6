package com.shopkart.config;

public final class AppConfig {

    private AppConfig() {
    }

    public static String baseUrl() {
        return Env.optional("base.url", "http://localhost:5173");
    }

    public static String apiBaseUrl() {
        return Env.optional("api.base.url", "http://localhost:8080/api");
    }

    public static String browser() {
        return Env.optional("browser", "chrome");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                Env.optional("headless", "false")
        );
    }

    public static int timeout() {
        return Integer.parseInt(
                Env.optional("timeout", "10")
        );
    }
}