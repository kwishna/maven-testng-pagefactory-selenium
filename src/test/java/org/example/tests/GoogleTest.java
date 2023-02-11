package org.example.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;
import org.example.pages.GooglePage;
import org.example.utils.Assertions;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class GoogleTest extends BaseTest {

    Logger logger = LogManager.getLogger(BaseTest.class);

    @Test
    public void homePageTest() {
        System.out.println("EMAIL_TO: "+System.getProperty("EMAIL_TO"));
        logger.info("Home Page Navigation");
        GooglePage page = new GooglePage(getDriver());
        page.navigate();
        Assertions.assertTrue(page.isSearchPageOpen(), "Search Page Is Not Opened.");
        page.performSearch("2047");
        Assertions.assertTrue(page.isResultPageLoaded(), "Result Page Is Not Opened.");
        Assertions.matches(page.getResultCount(), is(greaterThanOrEqualTo(5)));
    }
}