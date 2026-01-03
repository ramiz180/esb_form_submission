package com.esbnetworks.automation.pages;

import com.esbnetworks.automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By usernameField = By.id("signInName");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("next");
    private final By errorMessage = By.className("error-message"); // Default, might need adjusting based on Config

    public void enterUsername(String username) {
        sendKeys(usernameField, username, "Username Field");
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password, "Password Field");
    }

    public void clickLogin() {
        click(loginButton, "Login Button");
    }

    public void login(String username, String password) {
        // handleCookies(); // Use BasePage implementation
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        logger.info("Login action completed successfully.");
    }

    public String getErrorMessage() {
        if (isDisplayed(errorMessage)) {
            return getText(errorMessage, "Error Message");
        }
        return null; // Or throw exception if preferred
    }

    public void validateLogin() {
        logger.info("Validating login success...");
        wait.until(ExpectedConditions.urlContains("https://myaccount.esbnetworks.ie/"));
        logger.info("Login validated: URL contains 'https://myaccount.esbnetworks.ie/'");
    }
}
