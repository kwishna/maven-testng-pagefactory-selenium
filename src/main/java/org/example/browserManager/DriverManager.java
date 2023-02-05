package org.example.browserManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.WebDriver;
import java.util.Objects;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = ThreadLocal.withInitial(() -> null);
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    private DriverManager() {
    }

    public static WebDriver driver() {
        if (Objects.isNull(DRIVER.get())) {
            logger.info("Creating a new driver.");
            WebDriver _driver = BrowserDrivers.createDriver(System.getProperty("BROWSER", "chrome").toLowerCase());
            setDriver(_driver);
        }
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void quit() {
        if (Objects.nonNull(DRIVER.get())) {
            logger.info("Destroying the driver.");
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
