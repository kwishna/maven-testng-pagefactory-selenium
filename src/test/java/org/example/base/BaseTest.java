package org.example.base;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.browserManager.DriverManager;
import org.example.utils.PropertyReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BaseTest {

    Logger logger = LogManager.getLogger(BasePage.class);

    public BaseTest() {
        PropertyReader.loadAsSystemProperties(Paths.get(System.getProperty("user.dir")+"/config.properties"));
    }

    protected WebDriver getDriver() {
        return DriverManager.driver();
    }

    @Parameters("browser")
    @BeforeTest
    public synchronized void startDriver(@Optional String browser) {
        logger.info("Starting Driver");
        getDriver();
        logger.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getDriver());
    }

    @Parameters("browser")
    @AfterTest
    public synchronized void quitDriver(@Optional String browser) {
        logger.info("Quitting Driver");
        DriverManager.quit();
    }

    private void takeScreenshotOnTestFailure(String browser, ITestResult result) throws IOException {
        logger.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getDriver());
        if (result.getStatus() == ITestResult.FAILURE) {
            File destFile = new File("Screenshots" + File.separator + browser + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName() + ".png");
            takeScreenshot(destFile);
        }
    }

    private void takeScreenshot(File destFile) throws IOException {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }
}
