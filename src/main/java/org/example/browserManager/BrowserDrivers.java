package org.example.browserManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.example.utils.Constants.*;

public final class BrowserDrivers {
    private BrowserDrivers() {

    }
    private static final Supplier<WebDriver> GET_CHROME_DRIVER = () -> {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
//        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(new ChromeOptions() {{
            setHeadless(HEADLESS);
            addArguments("--start-maximized");
            setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT));
            setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        }});
    };

    private static final Supplier<WebDriver> GET_FIREFOX_DRIVER = () -> {
        System.setProperty("webdriver.firefox.driver", FIREFOX_DRIVER_PATH);
//        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(new FirefoxOptions() {{
            setHeadless(HEADLESS);
            addArguments("--start-maximized");
            setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT));
            setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        }});
    };

    private static final Supplier<WebDriver> GET_EDGE_DRIVER = () -> {
        System.setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver(new EdgeOptions() {{
            setHeadless(HEADLESS);
            addArguments("--start-maximized");
            setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT));
            setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        }});
    };

    private static final Supplier<WebDriver> GET_IE_DRIVER = () -> {
        System.setProperty("webdriver.ie.driver", IE_DRIVER_PATH);
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver(new InternetExplorerOptions() {{
            setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT));
            setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        }});
    };

    private static final Map<String, Supplier<WebDriver>> MAP = new HashMap<String, Supplier<WebDriver>>() {{
        put("chrome", GET_CHROME_DRIVER);
        put("firefox", GET_FIREFOX_DRIVER);
        put("edge", GET_EDGE_DRIVER);
        put("ie", GET_IE_DRIVER);
    }};

    public static WebDriver createDriver(String browser) {
        return MAP.get(browser.toLowerCase()).get();
    }
}

