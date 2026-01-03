package com.esbnetworks.automation.base;

import com.esbnetworks.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("----------Initializing Selenium WebDriver------------");
        ChromeOptions options = new ChromeOptions();

        String headless = ConfigReader.getProperty("headless");
        if (Boolean.parseBoolean(headless)) {
            options.addArguments("--headless");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // We will navigate in the test, or here if it's always the same
        String url = ConfigReader.getProperty("login.url");
        logger.info("Navigating to login page: " + url);
        driver.get(url);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            logger.info("----------Closing WebDriver----------");
            driver.quit();
        }
    }
}
