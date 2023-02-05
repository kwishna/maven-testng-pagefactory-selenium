package org.example.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GooglePage extends BasePage {

    Logger logger = LogManager.getLogger(GooglePage.class);
    @FindBy(name = "q")
    private WebElement searchBox;
    @FindBy(name = "btnK")
    private List<WebElement> searchBtns;
    @FindBy(css = "div.g a h3")
    private List<WebElement> results;

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    public void navigate() {
        logger.info("Navigating To Google GooglePage");
        this.goTo("https://google.com/");
    }

    public boolean isSearchPageOpen() {
        logger.info("Wait For Successful Navigation To Google Search Page.");
        return this.getWait().until((d) -> this.searchBox.isDisplayed());
    }

    public void performSearch(final String keyword) {
        this.searchBox.clear();
        this.searchBox.sendKeys(keyword);
        this.searchBox.sendKeys(Keys.TAB);
        this.searchBtns
                .stream()
                .filter(_el -> _el.isDisplayed() && _el.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public int getResultCount() {
        logger.info("Fetching Search Result Count: " + results.size());
        return results.size();
    }

    public boolean isResultPageLoaded() {
        logger.info("Wait For Successful Navigation To Google Result Page.");
        return this.getWait().until((d) -> !this.results.isEmpty());
    }
}
