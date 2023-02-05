package org.example.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.DriverManager;
import org.example.enums.WaitStrategy;
import org.example.pages.GooglePage;
import org.example.reportings.ExtentLogger;
import org.example.utils.Constants;
import org.example.utils.ScreenshotUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    Logger logger = LogManager.getLogger(GooglePage.class);
    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return this.driver;
    }

    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriverWait getWait() {
        return new WebDriverWait(this.driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
    }

    protected WebElement explicitWait(WaitStrategy strategy, By by) {
        logger.info("Explict Wait For: " + strategy + " For Element -> " + by);

        Duration WAIT_TIME = Duration.ofSeconds(Constants.EXPLICIT_WAIT);

        WebElement _element = null;
        if (strategy == WaitStrategy.CLICKABLE) {
            _element = new WebDriverWait(this.driver, WAIT_TIME).until(ExpectedConditions.elementToBeClickable(by));
        } else if (strategy == WaitStrategy.PRESENCE) {
            _element = new WebDriverWait(this.driver, WAIT_TIME).until(ExpectedConditions.presenceOfElementLocated(by));
        } else if (strategy == WaitStrategy.VISIBLE) {
            _element = new WebDriverWait(this.driver, WAIT_TIME).until(ExpectedConditions.visibilityOfElementLocated(by));
        } else if (strategy == WaitStrategy.NONE) {
            _element = DriverManager.driver().findElement(by);
        }
        return _element;
    }

    protected void goTo(String url) {
        logger.info("Navigating To URL: " + url);
        getDriver().get(url);
    }

    protected void waitForOverlaysToDisappear(By overlay) {
        List<WebElement> overlays = getDriver().findElements(overlay);
        logger.info("OVERLAY SIZE" + overlays.size());
        if (overlays.size() > 0) {
            getWait().until(ExpectedConditions.invisibilityOfAllElements(overlays));
            logger.info("OVERLAY INVISIBLE NOW");
        } else {
            logger.error("OVERLAY NOT FOUND");
        }
    }

    protected void click(By by, WaitStrategy waitStrategy, String elementName) {
        explicitWait(waitStrategy, by).click();
        ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
    }

    protected void sendKeys(By by, String value, WaitStrategy waitStrategy, String elementName) {
        explicitWait(waitStrategy, by).sendKeys(value);
        ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + elementName + "</b>", true);
    }

    protected void clear(By by, WaitStrategy waitStrategy, String elementName) {
        explicitWait(waitStrategy, by).clear();
        ExtentLogger.info("Clearing the field  <b>" + elementName + "</b>");
    }

    protected void clearAndSendKeys(By by, String value, WaitStrategy waitStrategy, String elementName) {
        WebElement element = explicitWait(waitStrategy, by);
        element.clear();
        element.sendKeys(value);
        ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + elementName + "</b>", true);
    }

    protected String getPageTitle() {
        return getDriver().getTitle();
    }

    protected void captureScreenshot() {
        ExtentLogger.info("Capturing Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()));
    }

    protected void waitFor(long time) {
        Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
    }
}
