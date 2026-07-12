package com.shopkart.data.db;

import org.flywaydb.core.Flyway;

public final class FlywaySupport {

    private FlywaySupport() {
    }

    public static void migrate() {

        Flyway.configure()
                .dataSource(PostgresSupport.createDataSource())
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }
}