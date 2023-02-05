package org.example.reportings;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.example.utils.Constants.*;

public final class ExtentReport {

    private static ExtentReports EXTENT;

    private ExtentReport() {
    }

    public static void initReports() {
        if (Objects.isNull(EXTENT)) {

            EXTENT = new ExtentReports();

            ExtentSparkReporter spark = new ExtentSparkReporter(EXTENT_REPORT_FOLDER_PATH + EXTENT_REPORT_NAME);
            EXTENT.attachReporter(spark);

            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(PROJECT_NAME + " - ALL");
            spark.config().setReportName(PROJECT_NAME + " - ALL");

            EXTENT.setSystemInfo("Organization", PROJECT_NAME);
            EXTENT.setSystemInfo("Employee", "<b> " + System.getProperty("user.name") + " </b>" + " " + ICON_SOCIAL_LINKEDIN + " " + ICON_SOCIAL_GITHUB);
            EXTENT.setSystemInfo("Domain", "Software" + "  " + ICON_LAPTOP);
            EXTENT.setSystemInfo("Skill", "Automation");
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(EXTENT)) {
            EXTENT.flush();
        }
        ExtentManager.unload();
        try {
            Desktop.getDesktop().browse(new File(EXTENT_REPORT_FOLDER_PATH + EXTENT_REPORT_NAME).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testCaseName) {
        if (Objects.isNull(EXTENT)) {
            initReports();
        }
        ExtentManager.setExtentTest(EXTENT.createTest(testCaseName));
    }

    synchronized public static void addAuthors(String[] authors) {
        for (String author : authors) {
            ExtentManager.getExtentTest().assignAuthor(author);
        }
    }

    synchronized public static void addCategories(String[] categories) {
        for (String category : categories) {
            ExtentManager.getExtentTest().assignCategory(category);
        }
    }

    synchronized public static void addDevices() {
        ExtentManager.getExtentTest().assignDevice();
    }
}
