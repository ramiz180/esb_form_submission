package com.esbnetworks.automation.pages;

import com.esbnetworks.automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RenewableConnectionPage extends BasePage {

    // --- Navigation Links ---
    private final By microGenLink = By.xpath("//a[contains(@href, '/MicroGenerationApplication/Index')]");
    private final By continueLink = By.linkText("Continue");

    // --- Form 001 Fields ---
    private final By customerName = By.id("ContactDetails_CustomerContact_Name");
    private final By customerEmail = By.id("ContactDetails_CustomerContact_Email");
    private final By customerPhone = By.id("ContactDetails_CustomerContact_ContactNumber");

    private final By installerPosition = By.id("ContactDetails_InstallerConsultantContact_PositionHeld");
    private final By installerCompanyName = By.id("ContactDetails_InstallerConsultantContact_CompanyName");
    private final By installerName = By.id("ContactDetails_InstallerConsultantContact_Name");
    private final By installerPhone = By.id("ContactDetails_InstallerConsultantContact_ContactNumber");
    private final By installerPhone2 = By.id("ContactDetails_InstallerConsultantContact_SecondaryContactNumber");
    private final By installerEmail = By.id("ContactDetails_InstallerConsultantContact_Email");

    private final By addrHouse = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_HouseNumber");
    private final By addrStreet = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_Street");
    private final By addrTown = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_TownlandCity");
    private final By addrCounty = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_County");
    private final By addrCountry = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_SelectedCountry");
    private final By addrPostCode = By.id("ContactDetails_InstallerConsultantContact_PostalAddress_PostalCode");

    // --- Save & Continue Button ---
    private final By saveAndContinueBtn = By.xpath("//span[.='Save & Continue']");

    // --- Form 002 Fields (Site Details) ---
    private final By firstInstallYes = By.id("SiteDetails_FirstMicoGenInstall_Yes");
    private final By firstInstallNo = By.id("SiteDetails_FirstMicoGenInstall_No");

    // --- Form 002 (Enhanced) ---
    private final By mprnInput = By.xpath("//label[contains(., 'MRPN Number')]/following-sibling::input");

    // Site Address
    private final By siteHouse = By.xpath("( //label[contains(., 'House Name')]/following::input[1])[2]");
    private final By siteStreet = By.xpath("(//input[contains(@placeholder, 'street name')])[2]");
    private final By siteTownArea = By.xpath("(//input[contains(@placeholder, 'townland or area')])[2]");
    private final By siteCity = By.xpath("(//input[contains(@placeholder, 'townland or city')])[2]");
    private final By siteCounty = By.xpath(
            "//*[contains(text(), 'Site Address')]//following::label[contains(., 'County')]/following::select[1]");
    private final By siteCountry = By.xpath(
            "//*[contains(text(), 'Site Address')]//following::label[contains(., 'Country')]/following::select[1]");
    private final By siteEircode = By.xpath("(//input[contains(@placeholder, 'Eircode')])[2]");

    // Microgen Details
    private final By installDateBefore = By
            .xpath("//span[contains(., 'before January 28th, 2022')]/preceding-sibling::input[@type='radio']");
    private final By installDateAfter = By
            .xpath("//span[contains(., 'after January 28th, 2022')]/preceding-sibling::input[@type='radio']");

    private final By connectionExisting = By
            .xpath("//span[contains(., 'Existing Connection')]/preceding-sibling::input[@type='radio']");
    private final By connectionNew = By
            .xpath("//span[contains(., 'New Connection')]/preceding-sibling::input[@type='radio']");

    private final By phase1 = By.xpath("//span[contains(., '1 Phase')]/preceding-sibling::input[@type='radio']");
    private final By phase3 = By.xpath("//span[contains(., '3 Phase')]/preceding-sibling::input[@type='radio']");

    private final By manufacturer = By.xpath("//input[contains(@placeholder, 'Manufacturer')]");
    private final By model = By.xpath("//input[contains(@placeholder, 'Model')]");
    private final By inverterCapacity = By.xpath("//label[contains(., 'Inverter Capacity')]/following::input[1]");

    public RenewableConnectionPage(WebDriver driver) {
        super(driver);
    }

    private final By ratedCurrent = By.xpath("//label[contains(., 'Rated Current')]/following::input[1]");
    private final By generatorInstalled = By.xpath("//label[contains(., 'Generator')]/following::input[1]");
    private final By storageInstalled = By.xpath("//label[contains(., 'Storage')]/following::input[1]");

    // Checkboxes - using specific text to differentiate
    private final By confirmCert = By
            .xpath("//label[contains(., 'type test certification')]/following::input[@type='checkbox'][1]");
    private final By confirmSettings = By
            .xpath("//label[contains(., 'settings installed')]/following::input[@type='checkbox'][1]");

    private final By saveBtn = By.xpath("//button[.= 'Save']");

    public void enterRatedCurrent(String text) {
        sendKeys(ratedCurrent, text, "Rated Current");
    }

    public void enterGeneratorInstalled(String text) {
        sendKeys(generatorInstalled, text, "Generator Installed");
    }

    public void enterStorageInstalled(String text) {
        sendKeys(storageInstalled, text, "Storage Installed");
    }

    public void clickConfirmCert() {
        // Checkboxes often need JS click or specific handling if hidden/styled
        try {
            if (!driver.findElement(confirmCert).isSelected()) {
                clickRadioButton(confirmCert, "Confirm Certification"); // Reusing clickRadioButton as it handles clicks
                                                                        // well
            }
        } catch (Exception e) {
            // Try JS click if standard interaction fails check
            WebElement el = driver.findElement(confirmCert);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            logger.info("Clicked 'Confirm Certification' via JS");
        }
    }

    public void clickConfirmSettings() {
        try {
            if (!driver.findElement(confirmSettings).isSelected()) {
                clickRadioButton(confirmSettings, "Confirm Settings");
            }
        } catch (Exception e) {
            WebElement el = driver.findElement(confirmSettings);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            logger.info("Clicked 'Confirm Settings' via JS");
        }
    }

    public void clickSave() {
        scrollToElement(saveBtn);
        WebElement btn = driver.findElement(saveBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        logger.info("Clicked 'Save'");
    }

    public void uploadFile(String filePath) {
        // 1. Click "Upload File 1" to open modal
        // Using "Upload File 1" text or similar unique identifier
        By uploadBtn = By.xpath("(//button[contains(., 'Upload File')])[1]");
        clickRadioButton(uploadBtn, "Upload File 1"); // Click to open modal

        // 2. Wait for modal and handle file input
        // The modal usually puts the file input in the DOM.
        // We target the input inside the modal (or the visible generic one)
        By fileInput = By.xpath("//input[@type='file']");
        By attachBtn = By.xpath("//button[contains(., 'Attach')]");

        try {
            Thread.sleep(2000); // Wait for modal animation

            // Upload file
            WebElement uploadElement = driver.findElement(fileInput);
            uploadElement.sendKeys(filePath);
            logger.info("Sent keys to file input: " + filePath);

            Thread.sleep(1000);

            // Click Attach
            WebElement attach = driver.findElement(attachBtn);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", attach);
            logger.info("Clicked 'Attach' in modal");

            Thread.sleep(2000); // Wait for upload/attach to process

        } catch (Exception e) {
            logger.error("Failed to upload file via modal: " + e.getMessage());
            // Fallback: try global input if logic fails?
            // Better to throw so we know it failed
            throw new RuntimeException("Upload failed", e);
        }
    }

    public void navigateToApplication() {
        // Step 2 from python: Click link 1 then link 2
        try {
            // handleCookies(); // Check for cookies before starting navigation

            int maxRetries = 3;
            for (int i = 0; i < maxRetries; i++) {
                try {
                    // Use JS click for 'microGenLink' in case of overlay/interception
                    WebElement microGenBtn = driver.findElement(microGenLink);
                    org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", microGenBtn);

                    Thread.sleep(2000); // Short wait to allow page load

                    // Check for 500 Error
                    if (driver.getPageSource().contains("HTTP ERROR 500") || driver.getTitle().contains("500")
                            || driver.getPageSource().contains("page isn’t working")) {
                        logger.warn("Encountered 500 Error. Retrying attempt " + (i + 1) + " of " + maxRetries);
                        driver.navigate().back();
                        Thread.sleep(2000);
                        continue; // Retry
                    }

                    // If we get here, likely success, break loop (or add verification check)
                    break;
                } catch (Exception e) {
                    logger.error("Exception during navigation attempt " + (i + 1) + ": " + e.getMessage());
                }
            }

            Thread.sleep(2000); // Allow time for next page to settle

            // Use JS click for 'Continue' in case of overlay/interception
            WebElement continueBtn = driver.findElement(continueLink);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fillCustomerDetails(String name, String email, String phone) {
        sendKeys(customerName, name, "Customer Name");
        sendKeys(customerEmail, email, "Customer Email");
        if (phone != null)
            sendKeys(customerPhone, phone, "Customer Phone");
    }

    public void fillInstallerDetails(String positionValue, String company, String name, String phone, String phone2,
            String email) {
        selectByValue(installerPosition, positionValue, "Installer Position"); // Value "1" for Installer
        sendKeys(installerCompanyName, company, "Installer Company Name");
        sendKeys(installerName, name, "Installer Name");
        if (phone != null)
            sendKeys(installerPhone, phone, "Installer Phone");
        if (phone2 != null)
            sendKeys(installerPhone2, phone2, "Installer Phone 2");
        sendKeys(installerEmail, email, "Installer Email");
    }

    public void fillAddress(String house, String street, String town, String county, String countryValue,
            String postCode) {
        sendKeys(addrHouse, house, "House Number");
        sendKeys(addrStreet, street, "Street Address");
        sendKeys(addrTown, town, "Town/City");
        sendKeys(addrCounty, county, "County");
        selectByValue(addrCountry, countryValue, "Country"); // "0" for Ireland
        sendKeys(addrPostCode, postCode, "Postal Code");
    }

    public void selectFirstInstall(boolean isYes) {
        if (isYes) {
            clickRadioButton(firstInstallYes, "First Install Yes");
        } else {
            clickRadioButton(firstInstallNo, "First Install No");
        }
    }

    public void clickSaveAndContinue() {
        // Scroll to element to ensure it's in view if needed
        scrollToElement(saveAndContinueBtn);
        // Use JS click if standard click is flaky with spans/overlays
        WebElement btn = driver.findElement(saveAndContinueBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        logger.info("Clicked 'Save & Continue'");
    }

    // --- Form 002 Methods ---

    public void enterMPRN(String mprn) {
        scrollToElement(mprnInput);
        sendKeys(mprnInput, mprn, "MRPN Number");
    }

    public void enterSiteHouse(String house) {
        sendKeys(siteHouse, house, "Site House");
    }

    public void enterSiteStreet(String street) {
        sendKeys(siteStreet, street, "Site Street");
    }

    public void enterSiteTownArea(String town) {
        sendKeys(siteTownArea, town, "Site Town/Area");
    }

    public void enterSiteCity(String city) {
        sendKeys(siteCity, city, "Site City");
    }

    public void selectSiteCounty(String county) {
        selectByVisibleText(siteCounty, county, "Site County");
    }

    public void selectSiteCountry(String country) {
        selectByVisibleText(siteCountry, country, "Site Country");
    }

    public void enterSiteEircode(String eircode) {
        sendKeys(siteEircode, eircode, "Site Eircode");
    }

    public void selectInstallDate(String date) {
        if (date.toLowerCase().contains("before")) {
            clickRadioButton(installDateBefore, "Installed Before Jan 2022");
        } else {
            clickRadioButton(installDateAfter, "Installed After Jan 2022");
        }
    }

    public void ConnectionType(String type) {
        if (type.toLowerCase().contains("new")) {
            clickRadioButton(connectionNew, "New Connection");
        } else {
            clickRadioButton(connectionExisting, "Existing Connection");
        }
    }

    public void selectPhase(int phase) {
        if (phase == 3) {
            clickRadioButton(phase3, "3 Phase");
        } else {
            clickRadioButton(phase1, "1 Phase");
        }
    }

    public void selectEnergySource(String source) {
        String xpath = "//input[contains(@class, 'unit-details-energy-source') and @value='" + source + "']";
        By locator = By.xpath(xpath);
        // We use JS click or standard click depending on stability.
        // The user's trace showed standard click failing often, so utilizing the
        // clickRadioButton helper
        // or a direct approach might be best.
        // clickRadioButton helper wraps the wait and logger.
        clickRadioButton(locator, "Energy Source: " + source);
    }

    public void enterManufacturer(String text) {
        sendKeys(manufacturer, text, "Manufacturer");
    }

    public void enterModel(String text) {
        sendKeys(model, text, "Model");
    }

    public void enterInverterCapacity(String text) {
        sendKeys(inverterCapacity, text, "Inverter Capacity");
    }
}
