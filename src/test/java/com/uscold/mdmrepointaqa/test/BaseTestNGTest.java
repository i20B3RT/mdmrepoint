package com.uscold.mdmrepointaqa.test;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;


public abstract class BaseTestNGTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTestNGTest.class);

//    private String screenshotPath = "";

//    ExtentReports extent;
//    ExtentTest test;
//    ExtentTest logger;
//    WebDriver driver;


    @BeforeClass
    public void beforeTestConfig(final ITestContext testContext) {
        LOGGER.info(String.format("starting test %s", this.getClass().getSimpleName()));

    }


    @AfterClass
    public void afterTestconfig(final ITestContext testContext) {
        LOGGER.info(String.format("Stopping test %s", this.getClass().getSimpleName()));
    }

}
