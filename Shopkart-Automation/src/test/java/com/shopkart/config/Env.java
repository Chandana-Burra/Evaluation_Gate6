package com.shopkart.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Env {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private Env() {
    }

    public static String optional(String key, String defaultValue) {

        String value = System.getProperty(key);

        if (value != null && !value.isBlank()) {
            return value;
        }

        value = System.getenv(key);

        if (value != null && !value.isBlank()) {
            return value;
        }

        value = dotenv.get(key);

        if (value != null && !value.isBlank()) {
            return value;
        }

        return defaultValue;
    }
}