package org.example.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.reportings.ExtentLogger;
import org.example.reportings.ExtentReport;
import org.example.utils.EmailSendUtils;
import org.example.utils.ZipUtils;
import org.testng.*;

import java.util.Arrays;

import static org.example.utils.Constants.*;

public class ListenerClass implements ITestListener, ISuiteListener {

    static int COUNT_PASSED_TCS;
    static int COUNT_SKIPPED_TCS;
    static int COUNT_FAILED_TCS;
    static int COUNT_TOTAL_TCS;
    Logger logger = LogManager.getLogger(ListenerClass.class);

    @Override

    public void onStart(ISuite suite) {
        logger.info("onStart " + suite.getName());
        ExtentReport.initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("onFinish " + suite.getName());
        ExtentReport.flushReports();
        ZipUtils.zip();
        if (SEND_MAIL) EmailSendUtils.sendEmail(COUNT_TOTAL_TCS, COUNT_PASSED_TCS, COUNT_FAILED_TCS, COUNT_SKIPPED_TCS);
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("onTestStart" + result.getName());
        COUNT_TOTAL_TCS = COUNT_TOTAL_TCS + 1;
        ExtentReport.initReports();
        ExtentReport.createTest(result.getMethod().getMethodName());
//		ExtentReport.addAuthors(result.getMethod().getConstructorOrMethod().getMethod()
//				.getAnnotation(FrameworkAnnotation.class).author());
//
//		ExtentReport.addCategories(result.getMethod().getConstructorOrMethod().getMethod()
//				.getAnnotation(FrameworkAnnotation.class).category());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("onTestSuccess" + result.getName());
        COUNT_PASSED_TCS = COUNT_PASSED_TCS + 1;
        String logText = "<b>" + result.getMethod().getMethodName() + " is passed.</b>" + "  " + ICON_SMILEY_PASS;
        Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        ExtentLogger.pass(markup_message, true);
        ExtentReport.flushReports();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("onTestFailure" + result.getName());
        COUNT_FAILED_TCS = COUNT_FAILED_TCS + 1;
        ExtentLogger.fail(ICON_BUG + "  " + "<b><i>" + result.getThrowable().toString() + "</i></b>");
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        String message = "<details><summary><b><font color=red> Exception occured, click to see details: "
                + ICON_SMILEY_FAIL + " </font></b>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")
                + "</details> \n";

        ExtentLogger.fail(message);
        String logText = "<b>" + result.getMethod().getMethodName() + " is failed.</b>" + "  " + ICON_SMILEY_FAIL;
        Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.RED);
        ExtentLogger.fail(markup_message, true);
        ExtentReport.flushReports();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("onTestSkipped" + result.getName());
        COUNT_SKIPPED_TCS = COUNT_SKIPPED_TCS + 1;
        ExtentLogger.skip(ICON_BUG + "  " + "<b><i>" + result.getThrowable().toString() + "</i></b>");
        String logText = "<b>" + result.getMethod().getMethodName() + " is skipped.</b>" + "  " + ICON_SMILEY_FAIL;
        Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        ExtentLogger.skip(markup_message, true);
        ExtentReport.flushReports();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext result) {
        logger.info("onStart" + result.getName());
    }

    @Override
    public void onFinish(ITestContext result) {
        logger.info("onFinish" + result.getName());
    }

}
