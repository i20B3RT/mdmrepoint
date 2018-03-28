package com.uscold.mdmrepointaqa.test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import com.uscold.mdmrepointaqa.test.utility.TestConstants;
import com.uscold.mdmrepointaqa.test.utility.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.uscold.mdmrepointaqa.test.utility.TestConstants.GET_ELEMENT_TIMEOUT;

public abstract class Abstract extends BaseTestNGTest {
    private static final Logger LOGGER = Logger.getLogger(Abstract.class);
    private static final boolean IS_HEADLESS = BooleanUtils.toBoolean(System.getProperty("ui.headless"));
    public static final String EWM_URL = System.getProperty("ewm.url");

    protected WebDriver driver;
    protected WebDriverWait wait;

    public ExtentReports extent;
    public ExtentTest extentTest;
    public static ExtentTest logger;

    @BeforeSuite
    public void beforeSuite() {
        LOGGER.info("EWM_URL:" + EWM_URL);
        LOGGER.info("HEADLESS:" + IS_HEADLESS);
    }


    @BeforeClass
    public void beforeClassInit() {
        driver = initDriver();
        wait = new WebDriverWait(driver, GET_ELEMENT_TIMEOUT);
        driver.manage().timeouts().implicitlyWait(TestConstants.GET_ELEMENT_TIMEOUT, TimeUnit.SECONDS);
        AssistPage.loginWithCookies(driver, EWM_URL);
    }

    @BeforeTest
    public void setExtent(){
        extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
        extent.addSystemInfo("Host Name", "PC");
        extent.addSystemInfo("User Name", "Robert Morales");
        extent.addSystemInfo("Environment", "eWM: 9109");
//        extentTest = extent.startTest(testName.getName(), "");

    }

//    @BeforeMethod
//    public static void createParentNode(ITestContext testName)
//    {
//        extentTest = extent.startTest(testName.getName(), "");
//    }

    private WebDriver initDriver() {
        driver = WebDriverFactory.getDriver(getOptions(IS_HEADLESS));
        return driver;
    }

    private ChromeOptions getOptions(boolean isHeadless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-first-run");
            chromeOptions.addArguments("--no-default-browser-check");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--start-maximized");
        }

        return chromeOptions;
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        driver.quit();
        LOGGER.info("Driver quit");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException{

        if(result.getStatus()==ITestResult.FAILURE){
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); //to add name in extent report
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); //to add error/exception in extent report

            String screenshotPath = getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report
            //extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screencast/video in extent report
        }
        else if(result.getStatus()==ITestResult.SKIP){
            extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
            //logger.log(LogStatus.SKIP, this.getClass().getSimpleName() + " Test Case Skipped");
        }
        else if(result.getStatus()==ITestResult.SUCCESS){
            extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
            //logger.log(LogStatus.PASS, this.getClass().getSimpleName() + " Test Case Success and Title Verified");
        }


        extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report
       // driver.quit();
    }

    @AfterTest(alwaysRun = true)
    public void endReport(){
        extent.flush();
        extent.close();
    }

    protected void click(WebElement el, int maxWaitTimeMillis) {
        AssistPage.click(el, maxWaitTimeMillis);
    }

    protected void click(WebElement el) {
        AssistPage.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    protected void click(By by) {
        WebElement el = driver.findElement(by);
        AssistPage.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    long recordTimeForOperation(Runnable runnable) {
        long startedAt = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startedAt;
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    public boolean isElementFound( String text) {
        long startedAt = System.currentTimeMillis();
        RuntimeException caughtEx = null;
        try{
            WebElement webElement = driver.findElement(By.id(text));
            System.out.println("isElementFound : true :"+text + "true");
            LOGGER.warn("was waiting for " + (System.currentTimeMillis() - startedAt) + " to click on " + text);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("isElementFound : false :"+text);
            return false;
        }
        return true;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }
}