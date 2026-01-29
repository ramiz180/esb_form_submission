package com.esbnetworks.tests;

import com.esbnetworks.automation.base.BaseTest;
import com.esbnetworks.automation.pages.LoginPage;
import com.esbnetworks.automation.pages.RenewableConnectionPage;
import com.esbnetworks.automation.utils.ConfigReader;
import com.esbnetworks.automation.base.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormAutomationTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testLoginAndFillForm() throws InterruptedException {
        logger.info("Starting Login + Form Automation Test");

        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        String user = ConfigReader.getProperty("username");
        String pass = ConfigReader.getProperty("password");

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
        formPage.fillCustomerDetails(
                "Donal O'Laoghaire",
                "donieboy65@yahoo.ie",
                "0852838971");

        formPage.fillInstallerDetails(
                "1", // Installer
                "Solar Beo",
                "Ciarán Murphy",
                "0877746021",
                "0669151045",
                "edel@solarbeo.ie");

        formPage.fillAddress(
                "MILLTOWN",
                "DINGLE",
                "Dingle",
                "V92 R159",
                "0", // Ireland
                "V92 R159");

        formPage.clickSaveAndContinue();

        // Fill Form 002
        logger.info("***Filling Form 002***");

        formPage.selectFirstInstall(true);

        // Fill remaining Form 002 details
        formPage.enterMPRN("10303923781"); // Example 11 digit
        formPage.enterSiteHouse("N/A");
        formPage.enterSiteStreet("BRACKAHARAGH");
        formPage.enterSiteTownArea("CAHERDANIEL");
        formPage.enterSiteCity("KILLARNEY");
        formPage.selectSiteCounty("KERRY");
        formPage.selectSiteCountry("Ireland");
        formPage.enterSiteEircode("V93E4F3");

        formPage.selectInstallDate("After Jan 2022");
        formPage.ConnectionType("New");
        formPage.selectPhase(1);
        formPage.selectEnergySource("Solar");
        formPage.enterManufacturer("SAJ");
        formPage.enterModel("HS3-6K-S2-W/G-P-IE");
        formPage.enterInverterCapacity("5.75");
        formPage.enterRatedCurrent("25.00"); // Example value
        formPage.enterGeneratorInstalled("5.30");
        formPage.enterStorageInstalled("5.00");

        formPage.clickConfirmCert();
        formPage.clickConfirmSettings();
        Thread.sleep(2000);
        formPage.clickSave();
        Thread.sleep(3000);
        // Upload File
        formPage.uploadFile(120, "C:\\Users\\ramiz.mondal\\Downloads\\test_report_sheet_67ff74131918a1216d18d7f0.pdf");
        Thread.sleep(15000);
        // formPage.uploadFile(121,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");
        // formPage.uploadFile(122,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");
        // formPage.uploadFile(123,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");

        logger.info("Unit Type Test Certificate file uploaded successfully!");

        formPage.uploadFile(130, "C:\\Users\\ramiz.mondal\\Downloads\\datasheet_67ff74131918a1216d18d7f0.pdf");
        Thread.sleep(15000);
        // formPage.uploadFile(131,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");
        // formPage.uploadFile(132,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");
        // formPage.uploadFile(133,
        // "C:\\Users\\ramiz.mondal\\Downloads\\samplePDF.pdf");

        formPage.clickSaveAndContinueForm2();
        logger.info("Form 2 filled and file uploaded successfully!");

        // ----- Form 3 -----
        logger.info("***Filling Form 003***");

        // -------- FORM 3 : POST JAN 28, 2022 --------
        formPage.confirmPostRegisteredInstaller(true);

        // MUST be entered or DOM will not render settings
        formPage.enterPostSafeElectricNumber("A1170");

        // Now all toggles are available
        formPage.confirmPostSingleStageVoltage(true);
        formPage.confirmPostTwoStageVoltageSettingsStage1(true);
        formPage.confirmPostTwoStageVoltageSettingsStage2(true);
        formPage.confirmPostUnderVoltage(true);
        formPage.confirmPostOverFrequency(true);
        formPage.confirmPostUnderFrequency(true);
        formPage.confirmPostROCOF(true);
        formPage.confirmPostVectorShift(true);

        formPage.clickCheckbox();
        formPage.clickSaveAndContinueForm3();
        Thread.sleep(5000);
        logger.info("Form 3 filled and checkbox clicked successfully!");
        formPage.clickSubmitApplication();

        Thread.sleep(15000);
        logger.info("Form submitted successfully!");
    }
}
