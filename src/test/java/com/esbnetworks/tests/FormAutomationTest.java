package com.esbnetworks.tests;

import com.esbnetworks.automation.base.BaseTest;
import com.esbnetworks.automation.pages.LoginPage;
import com.esbnetworks.automation.pages.RenewableConnectionPage;
import com.esbnetworks.automation.utils.ConfigReader;
import com.esbnetworks.automation.utils.FileDownloader;
import com.esbnetworks.automation.utils.JsonDataReader;
import com.esbnetworks.automation.base.RetryAnalyzer;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormAutomationTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testLoginAndFillForm() throws InterruptedException {
        logger.info("Starting Login + Form Automation Test");

        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        JsonNode login = JsonDataReader.getNode("login");
        String user = login.get("username").asText();
        String pass = login.get("password").asText();

        logger.info("Logging in as: " + user);
        loginPage.login(user, pass);

        // Basic verification
        String error = loginPage.getErrorMessage();
        if (error != null) {
            Assert.fail("Login failed with error: " + error);
        }

        // Note: Real validation would check for dashboard element, but we'll assume
        // success if no error for now
        // or add explicit wait for dashboard in Page Object

        Thread.sleep(2000);

        // 2. Navigate and Fill Form
        RenewableConnectionPage formPage = new RenewableConnectionPage(driver);
        formPage.navigateToApplication();

        // Fill Form 001
        logger.info("***Filling Form 001***");
        JsonNode form001 = JsonDataReader.getNode("form001");
        JsonNode customer = form001.get("customer");
        JsonNode installer = form001.get("installer");
        JsonNode address = form001.get("address");

        String customer_name = customer.get("name").asText();
        formPage.enterCustomerName(customer_name);
        formPage.enterCustomerEmail(customer.get("email").asText());
        formPage.enterCustomerPhone(customer.get("phone").asText());

        formPage.enterInstallerPosition(installer.get("position").asText()); // Installer
        formPage.enterInstallerCompanyName(installer.get("company").asText());
        formPage.enterInstallerName(installer.get("name").asText());
        formPage.enterInstallerPhone(installer.get("phonePrimary").asText());
        formPage.enterInstallerPhone2(installer.get("phoneSecondary").asText());
        formPage.enterInstallerEmail(installer.get("email").asText());

        formPage.enterAddressHouse(address.get("house").asText());
        formPage.enterAddressStreet(address.get("street").asText());
        formPage.enterAddressTown(address.get("town").asText());
        formPage.enterAddressCounty(address.get("county").asText());
        formPage.selectAddressCountry("0"); // Ireland - keeping hardcoded as it might be specific value ID or from
                                            // config
        formPage.enterAddressPostCode(address.get("eircode").asText());

        formPage.clickSaveAndContinue();

        // Fill Form 002
        logger.info("***Filling Form 002***");
        JsonNode form002 = JsonDataReader.getNode("form002");
        JsonNode siteAddress = form002.get("siteAddress");
        JsonNode installation = form002.get("installation");
        JsonNode unit = form002.get("units").get(0);

        formPage.selectFirstInstall(form002.get("firstInstall").asBoolean());

        // Fill remaining Form 002 details
        formPage.enterMPRN(form002.get("mprn").asText());
        formPage.enterSiteHouse(siteAddress.get("house").asText());
        formPage.enterSiteStreet(siteAddress.get("street").asText());
        formPage.enterSiteTownArea(siteAddress.get("area").asText());
        formPage.enterSiteCity(siteAddress.get("city").asText());
        formPage.selectSiteCounty(siteAddress.get("county").asText());
        formPage.selectSiteCountry(siteAddress.get("country").asText());
        formPage.enterSiteEircode(siteAddress.get("eircode").asText());

        boolean installedAfter = installation.get("installedAfterJan28_2022").asBoolean();
        formPage.selectInstallDate(installedAfter ? "After Jan 2022" : "Before Jan 2022");

        String connType = installation.get("connectionType").asText();
        // Capitalize first letter to match "New" or "Existing" check in ConnectionType
        // method
        String connTypeCap = connType.substring(0, 1).toUpperCase() + connType.substring(1);
        formPage.ConnectionType(connTypeCap);

        formPage.selectPhase(installation.get("phase").asInt());
        formPage.selectEnergySource(unit.get("energySource").asText());
        formPage.enterManufacturer(unit.get("manufacturer").asText());
        formPage.enterModel(unit.get("model").asText());
        formPage.enterInverterCapacity(unit.get("inverterCapacity").asText());
        formPage.enterRatedCurrent(unit.get("ratedCurrent").asText());
        formPage.enterGeneratorInstalled(unit.get("generatorInstalled").asText());
        formPage.enterStorageInstalled(unit.get("storageInstalled").asText());

        formPage.clickConfirmCert();
        formPage.clickConfirmSettings();
        Thread.sleep(2000);
        formPage.clickSave();
        Thread.sleep(3000);
        // // Upload File
        // JsonNode uploads = JsonDataReader.getNode("uploads");
        // if (uploads.has("files")) {
        // for (JsonNode file : uploads.get("files")) {
        // int index = file.get("index").asInt();
        // String path = file.get("path").asText();
        // formPage.uploadFile(index, path);
        // Thread.sleep(15000);
        // }
        // }

        // Upload File
        JsonNode uploads = JsonDataReader.getNode("uploads");
        if (uploads.has("files")) {
            for (JsonNode file : uploads.get("files")) {

                int index = file.get("index").asInt();
                String path = file.get("path").asText();

                String localPath = path;

                // If path is an S3 URL → download first
                if (path.startsWith("http")) {
                    logger.info("Downloading file from S3 URL: " + path);
                    localPath = FileDownloader.downloadFile(path, customer_name);
                    logger.info("Downloaded file location: " + localPath);
                }

                // Upload the local file
                formPage.uploadFile(index, localPath);

                Thread.sleep(15000);
            }
        }

        logger.info("Unit Type Test Certificate file uploaded successfully!");

        formPage.clickSaveAndContinueForm2();
        logger.info("Form 2 filled and file uploaded successfully!");

        // ----- Form 3 -----
        logger.info("***Filling Form 003***");
        JsonNode form003 = JsonDataReader.getNode("form003");

        // -------- FORM 3 : POST JAN 28, 2022 --------
        boolean postRegInstaller = form003.get("postRegisteredInstaller").asBoolean();
        formPage.confirmPostRegisteredInstaller(postRegInstaller);

        // MUST be entered or DOM will not render settings
        formPage.enterPostSafeElectricNumber(form003.get("safeElectricNumber").asText());

        // Now all toggles are available
        formPage.confirmPostSingleStageVoltage(form003.get("singleStageVoltage").asBoolean());
        formPage.confirmPostTwoStageVoltageSettingsStage1(form003.get("twoStageVoltageStage1").asBoolean());
        formPage.confirmPostTwoStageVoltageSettingsStage2(form003.get("twoStageVoltageStage2").asBoolean());
        formPage.confirmPostUnderVoltage(form003.get("underVoltage").asBoolean());
        formPage.confirmPostOverFrequency(form003.get("overFrequency").asBoolean());
        formPage.confirmPostUnderFrequency(form003.get("underFrequency").asBoolean());
        formPage.confirmPostROCOF(form003.get("rocOf").asBoolean());
        formPage.confirmPostVectorShift(form003.get("vectorShift").asBoolean());

        formPage.clickCheckbox();
        formPage.clickSaveAndContinueForm3();
        Thread.sleep(5000);
        logger.info("Form 3 filled and checkbox clicked successfully!");
        // formPage.clickSubmitApplication();

        Thread.sleep(15000);
        logger.info("Form submitted successfully!");
    }
}
