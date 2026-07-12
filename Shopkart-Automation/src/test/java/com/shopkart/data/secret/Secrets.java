package com.shopkart.data.secret;

import com.shopkart.config.Env;

public final class Secrets {
    private Secrets() {
    }

    public static String getAlicePassword() {
        return Env.optional("SHOPKART_ALICE_PASSWORD", "");
    }

    public static String getBobPassword() {
        return Env.optional("SHOPKART_BOB_PASSWORD", "");
    }

    public static String getCarolPassword() {
        return Env.optional("SHOPKART_CAROL_PASSWORD", "");
    }

    public static String getTokenSecret() {
        return Env.optional("SHOPKART_TOKEN_SECRET", "");
    }
}