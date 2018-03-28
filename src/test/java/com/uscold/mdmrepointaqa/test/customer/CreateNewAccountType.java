package com.uscold.mdmrepointaqa.test.customer;

import com.uscold.mdmrepointaqa.test.Abstract;
import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import com.uscold.mdmrepointaqa.test.utility.WriteDataToExcel;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.base.Ascii.toUpperCase;

/**
 * ********************************************
 * * Created by ${"Robert Morales"} on 1/23/2018.
 * ********************************************
 **/

public class CreateNewAccountType extends Abstract {

    final static String whselevelcn = "800 - BETHLEHEM";
    final static String whse = "800";
    final static String whselevelpush = "820 - BETHLEHEM";

    StringBuffer newline= new StringBuffer("");
    private String customerNumber = "";
    private String atwhse = "";
    private String bdat = "";

    public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/test-input/testdata.xlsx";
    public static String TEST_OUTPUT_PATH = System.getProperty("user.dir") + "/test-input/";
    public static String TEST_OUTPUT_FILE_NAME = "test_data_out";
    public static String TEST_OUTPUT_SHEET_NAME = "TestResults";
    public static String TESTDATA_SHEET_PATH_OUT = System.getProperty("user.dir") + "/test-output/test_data_out.xlsx";

    Calendar dateTwo = Calendar.getInstance();
    Date dateOne = dateTwo.getTime();
    DateFormat dateForm = new SimpleDateFormat("YYMMddhhmmss");
    String tDay = dateForm.format(dateOne);


    @DataProvider()
    public Object[][] getTestData() {
        Object data[][] = AssistPage.getTestData("CustomerAccountTypes", TESTDATA_SHEET_PATH);
        return data;
    }

    @Test(priority = 1, dataProvider = "getTestData")
    public void CreateNewAccountType(String createinvoiceto, String testcase, String module, String atwarehouse, String towarehouse, String bdaccounttype, String bdcorporateName, String bdattention, String bdstrandardphone, String bdaddressLine1, String bdaddressLine2, String bdaddressLine3, String bdcity, String bdstate, String bdzipcode, String bdglnid, String adnationalcustomer, String adedi, String admanufacturerId, String adsiccode, String adstatus, String adattention, String adcustomergroup, String adcustomertype, String adcustomerclass, String adwebaccess, String adtmscustomer, String adcustomerhold, String adinventory, String adpalletexchange, String adalphasearchfield, String addock, String adtarrifcode, String cdcontactname, String cdtitle, String cdstandardphone, String cdphoneext, String cdcontacttype, String cdcontactpriority, String cdfaxnumber, String cdmail, String ranamenumber, String raaddress, String raaccounttype
    ) throws Exception {

//        if (atwarehouse.contentEquals(towarehouse)) {
//            extentTest = extent.startTest("1. Create new " + bdaccounttype + " customer at the whse level at the " + atwarehouse + ".");
//        }
        extentTest = extent.startTest("Create New "+bdaccounttype +" at " + atwarehouse + ". "+newline+"TC: "+testcase);

        if (!atwarehouse.contentEquals(towarehouse)) {
            AssistPage.chooseWarehouse(driver, atwarehouse);
        }
        String atwhse = atwarehouse;
        AssistPage.chooseModule(driver, module);

        click(driver.findElement(By.id("createNewBtn")));

        //basicDetails(driver,bdaccounttype);
        AssistPage.click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "type_chosen", bdaccounttype));
        String bdat = bdaccounttype;

        //String StringbdcorporateName = new String(bdcorporateName);
        AssistPage.sendInput(driver, "id", "corporateName", bdcorporateName.toUpperCase() + tDay.substring(0, 6));
        AssistPage.sendInput(driver, "id", "txt_ConsigneeName", bdcorporateName.toUpperCase() + tDay.substring(0, 6));
        AssistPage.sendInput(driver, "xp", "//input[@id='attention']", bdattention.toUpperCase());
        AssistPage.sendInput(driver, "xp", "//input[@id='standardPhone']", bdstrandardphone.substring(2, 12));
        AssistPage.sendInput(driver, "id", "addressLine1", bdaddressLine1.toUpperCase());

        //Check if address line 2 and 3 have non-null values
        if (!bdaddressLine2.contains("null")) {
            AssistPage.sendInput(driver, "id", "addressLine2", bdaddressLine2.toUpperCase());
        } else if (!bdaddressLine3.contains("null")) {
            AssistPage.sendInput(driver, "id", "addressLine3", bdaddressLine3.toUpperCase());
        }

        AssistPage.sendInput(driver, "id", "city", bdcity.toUpperCase());
        AssistPage.click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "stateList_chosen", bdstate.toUpperCase()));
        AssistPage.sendInput(driver, "id", "zip", bdzipcode.substring(2, 7));


        //Check if bdglnid has non-null values
        if (!bdglnid.contains("null")) {
            AssistPage.sendInput(driver, "id", "glnId", bdglnid.toUpperCase().substring(2,5));
        }


        customerNumber = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");
        System.out.print("[AssistPage] [INFO] this record was cached: " + customerNumber + " for searching on the test below" + System.lineSeparator());

        click(driver.findElement(By.id("basicDtlNext")));

        WebElement nextPageThrobber = driver.findElement(By.xpath("//div[contains(@class, 'pageLoadingThrobber')]"));
        wait.until(ExpectedConditions.invisibilityOf(nextPageThrobber));

        //This means the uscs popup was triggered and the current page is still the basic details page
        if (!driver.getCurrentUrl().toLowerCase().contains("customer/basicdetails/2/Next*//*.do")) {
            AssistPage.uspsWindow(driver, "eWM test");
            click(driver.findElement(By.id("basicDtlNext")));
        }

        WebElement nextPageThrobberafteruscspopup = driver.findElement(By.xpath("//div[contains(@class, 'pageLoadingThrobber')]"));
        wait.until(ExpectedConditions.invisibilityOf(nextPageThrobberafteruscspopup));

        //Skipp other data if account is not a customer customer account
        if (bdaccounttype.equals("Bill To")) {
            click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "customerClassification_chosen", adcustomerclass));

            if (adinventory.contains("N")) {
                click(driver.findElement(By.id("inventNo")));
            }
        } else if (bdaccounttype.equals("Customer")) {


            if (!admanufacturerId.contains("null")) {
                AssistPage.sendInput(driver, "id", "manufacturerId", admanufacturerId.substring(2, 3));
            }

            if (!adattention.contains("null")) {
                AssistPage.sendInput(driver, "id", "attentionId", adattention.substring(2, 12));
            }

            if (!adcustomergroup.contains("null")) {
                AssistPage.sendInput(driver, "id", "customerGroup", adcustomergroup);
            }

//        click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "a.chosen-single", "Active"));

            click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "customerClassification_chosen", adcustomerclass));

            if (adtmscustomer.contains("Y")) {
                click(driver.findElement(By.id("tmsCustYes")));
            }

//        if (!adcustomerhold.contains("null")) {
//            click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "customerHold_chosen", adcustomerhold));
//        }


            if (!adpalletexchange.contains("N")) {
                click(driver.findElement(By.id("palletExchangeYes")));
            }

            if (!adalphasearchfield.contains("null")) {
                AssistPage.sendInput(driver, "id", "alphaSearchField", adalphasearchfield);
            }

            if (!addock.contains("null")) {
                AssistPage.sendInput(driver, "id", "noOfDockDoors", addock);
            }

            if (!adtarrifcode.contains("null")) {
                AssistPage.sendInput(driver, "id", "tariffCode", adtarrifcode);
            }
        }


        //Scrolling with Javascript was needed because the browser hides the page.
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("window.scrollBy(0,450)", "");

        click(driver.findElement(By.id("additionalDtlSubmit")));

        //WebElement statusMsg = driver.findElement(By.xpath("//span[@id='message']"));
        WebElement statusMsg = driver.findElement(By.xpath("//div[@id='reportSuccessMsg']"));

        System.out.println(statusMsg);
        if (!statusMsg.isDisplayed() && !statusMsg.getText().contains(customerNumber)) {
            Assert.assertTrue(false,"Failed to create "+bdaccounttype);

            WriteDataToExcel one = new WriteDataToExcel(TESTDATA_SHEET_PATH_OUT);
            one.setCellData(TEST_OUTPUT_SHEET_NAME, "customernumber",customerNumber,bdaccounttype, adinventory,adtmscustomer,"Failed");

            throw new RuntimeException("Failed to create customer");
        }
        WriteDataToExcel two = new WriteDataToExcel(TESTDATA_SHEET_PATH_OUT);
        two.setCellData(TEST_OUTPUT_SHEET_NAME, "customernumber",customerNumber,bdaccounttype, adinventory, adtmscustomer,"Passed");

    }


    @Test(dependsOnMethods = "CreateNewAccountType",priority = 2 )
    public void CustomerViewTMS () {

        extentTest = extent.startTest("View TMS customer testa.Push record from the "+whselevelpush+" to the level.");

        if (!atwhse.contains("Enterprise")) {
            throw new SkipException("No whse push "+bdat+" created at warehouse "+bdat+".");
        }
        driver.navigate().refresh();

        try {

            Select mySelect = new Select(driver.findElement(By.xpath(".//*[@id='hidebox']")));
            mySelect.selectByVisibleText(whselevelpush);
            click(driver.findElement(By.xpath("//input[@id='moveToRight']")));
            click(driver.findElement(By.xpath("//button[@id='done']")));

            //check if error isDisplayed if not it will check for success msg and click on the cancel to return to the landing page
            WebElement erroriPresence = driver.findElement(By.id("openErrorDialog"));
            if (erroriPresence.isDisplayed()) {
//            System.out.println(erroriPresence);
                throw new RuntimeException("Error, The whse push failed" + erroriPresence);
            } else {
//        super.isElementFound("openErrorDialog");
                super.isElementFound("message");
                click(driver.findElement(By.id("cancelWhse")));
            }
        }catch (NoSuchElementException nse){
            nse.printStackTrace();
            Assert.assertTrue(false);
            click(driver.findElement(By.id("cancelWhse")));
        }

    }



//    @Test(dependsOnMethods = "CreateNewAccountType",priority = 2 )
//    public void pushCustToWarehouseLevel () {
//
//        extentTest = extent.startTest("a.Push record from the "+whselevelpush+" to the level.");
//
//        driver.navigate().refresh();
//
//        try {
//            Select mySelect = new Select(driver.findElement(By.xpath(".//*[@id='hidebox']")));
//            mySelect.selectByVisibleText(whselevelpush);
//            click(driver.findElement(By.xpath("//input[@id='moveToRight']")));
//            click(driver.findElement(By.xpath("//button[@id='done']")));
//
//            //check if error isDisplayed if not it will check for success msg and click on the cancel to return to the landing page
//            WebElement erroriPresence = driver.findElement(By.id("openErrorDialog"));
//            if (erroriPresence.isDisplayed()) {
////            System.out.println(erroriPresence);
//                throw new RuntimeException("Error, The whse push failed" + erroriPresence);
//            } else {
////        super.isElementFound("openErrorDialog");
//                super.isElementFound("message");
//                click(driver.findElement(By.id("cancelWhse")));
//            }
//        }catch (NoSuchElementException nse){
//            nse.printStackTrace();
//            Assert.assertTrue(false);
//            click(driver.findElement(By.id("cancelWhse")));
//        }
//
//    }


//    public static void basicDetails(WebDriver driver, String acctType) throws InterruptedException {
//        Calendar dateTwo = Calendar.getInstance();
//        Date dateOne = dateTwo.getTime();
//        DateFormat dateForm = new SimpleDateFormat("YYMMddhhmmss");
//        String tDay = dateForm.format(dateOne);
//
//        AssistPage.click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "type_chosen", acctType));
//        AssistPage.sendInput(driver,"id","corporateName",bdcorporateName+tDay.substring(0,6));
//        AssistPage.sendInput(driver,"id","txt_ConsigneeName",bdcorporateName+tDay.substring(0,6));
//        AssistPage.sendInput(driver,"xp","//input[@id='attention']",bdattention);
//        AssistPage.sendInput(driver,"xp","//input[@id='standardPhone']",bdstrandardphone.substring(2,10));
//        AssistPage.sendInput(driver,"id","addressLine1",bdaddressLine1);
//        if (!bdaddressLine2.contains("null")) {
//            AssistPage.sendInput(driver, "id", "addressLine1", bdaddressLine2);
//        }
//        AssistPage.sendInput(driver,"id","city",bdcity.substring(2,5));
//        AssistPage.click(AssistPage.chooseValueFromStandardDropDownByTextMatch(driver, "stateList_chosen", bdstate));
//        AssistPage.sendInput(driver,"id","zip",bdzip);
//    }

}
