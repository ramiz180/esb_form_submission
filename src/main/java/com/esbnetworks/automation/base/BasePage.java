package com.esbnetworks.automation.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void click(By locator, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        logger.info("Clicked on " + elementName);
    }

    protected void sendKeys(By locator, String text, String elementName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '" + text + "' into " + elementName);
    }

    protected void selectByVisibleText(By locator, String text, String elementName) {
        logger.info("Selecting '" + text + "' from dropdown " + elementName);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value, String elementName) {
        logger.info("Selecting value '" + value + "' from dropdown " + elementName);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    // Helper to handle radio buttons
    protected void clickRadioButton(By locator, String elementName) {
        logger.info("Clicking radio button " + elementName);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            logger.warn("Standard click failed for " + elementName + ", trying JS click");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected String getText(By locator, String elementName) {
        logger.info("Getting text from " + elementName);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // public void handleCookies() {
    // // Use a short timeout to check for cookies to avoid slowing down tests if
    // not
    // // present
    // driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    // try {
    // // Common cookie button XPaths
    // String[] cookieXpaths = {
    // "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
    // 'abcdefghijklmnopqrstuvwxyz'), 'accept')]",
    // "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
    // 'abcdefghijklmnopqrstuvwxyz'), 'allow')]",
    // "//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
    // 'abcdefghijklmnopqrstuvwxyz'), 'accept')]",
    // "//div[contains(@class, 'cookie')]//button"
    // };

    // for (String xpath : cookieXpaths) {
    // try {
    // java.util.List<WebElement> elements = driver.findElements(By.xpath(xpath));
    // for (WebElement element : elements) {
    // if (element.isDisplayed()) {
    // logger.info("Found cookie button: " + element.getText());
    // element.click();
    // // If we clicked one, we might need a brief pause for the overlay to
    // disappear
    // Thread.sleep(500);
    // return;
    // }
    // }
    // } catch (Exception ignore) {
    // }
    // }
    // } catch (Exception e) {
    // logger.warn("Cookie handling encountered an error: " + e.getMessage());
    // } finally {
    // // Restore default implicit wait (10 seconds as per BaseTest/Config)
    // // Ideally should read from config, but hardcoding safe default for now to
    // // restore state
    // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    // }
    // }

    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void selectYesNo(By yesLocator, By noLocator,
            boolean selectYes, String fieldName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By target = selectYes ? yesLocator : noLocator;

        WebElement el = wait.until(
                ExpectedConditions.elementToBeClickable(target));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", el);

        logger.info(fieldName + " set to " + (selectYes ? "YES" : "NO"));
    }

}
