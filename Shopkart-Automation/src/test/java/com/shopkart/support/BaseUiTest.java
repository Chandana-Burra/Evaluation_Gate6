package com.shopkart.support;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

import static com.shopkart.config.AppConfig.*;

public abstract class BaseUiTest {

    @BeforeAll
    static void setUpFramework() {
        Configuration.browser = browser();
        Configuration.baseUrl = baseUrl();
        Configuration.headless = headless();
        Configuration.timeout = timeout() * 1000;
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 30000;
    }

}