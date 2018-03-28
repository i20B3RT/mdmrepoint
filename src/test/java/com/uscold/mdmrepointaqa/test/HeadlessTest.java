package com.uscold.mdmrepointaqa.test;

import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HeadlessTest {
    private static final String EWM_URL = "http://uatwas.uscold.com:9113/ewm/home.htm";//System.getProperty("ewm.url");

    WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(HeadlessTest.class);
    @Test
    public void test() throws InterruptedException, IOException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        /*DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--test-type");

        chromeOptions.addArguments("--dump-dom");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments(" --disable-translate");
        chromeOptions.addArguments("--no-default-browser-check");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-renderer-backgrounding");
        chromeOptions.addArguments("--enable-logging=v=1");
        //useless
        //chromeOptions.addArguments("window-size=1920x1080");
       // chromeOptions.addArguments("--window-size=1080,1920");
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);*/

        driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        //AssistPage.maximizeWindow(driver);
        driver.get(EWM_URL);
        assert false;
        driver.findElement(By.tagName("body"));

        WebElement loginElement = driver.findElement(By.name("j_username"));
        WebElement passwordElement = driver.findElement(By.name("j_password"));
        loginElement.sendKeys(System.getProperty("user.username"));
        passwordElement.sendKeys(System.getProperty("user.password"));

        driver.findElement(By.name("btn_login")).click();
        AssistPage.chooseModule(driver,"Locations & Racks Setup");

        makeScreen();
        AssistPage.chooseWarehouse(driver, 160);
    }

    int i = 1;

    private void makeScreen() throws IOException {
        String name = "screen" + i++ + ".jpg";
        LOGGER.warn("making a screenshot");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //new File("/home/uscold/share/screen.jpg")
        FileUtils.copyFile(scrFile, new File("screens/" + name));
        //FileUtils.copyFile(scrFile, new File("target/surefire-reports/screens/"+name));

        String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        LOGGER.warn("screen as base64" + base64);
    }
    @AfterClass(alwaysRun = true)
    public void afterTest() {
        driver.quit();
    }
}
