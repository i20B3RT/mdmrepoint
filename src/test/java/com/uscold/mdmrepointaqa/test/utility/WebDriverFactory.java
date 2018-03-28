package com.uscold.mdmrepointaqa.test.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    private static final Logger LOGGER = Logger.getLogger(WebDriverFactory.class);


    /*public static synchronized WebDriver getDriver() {
        return getDriver(new ChromeOptions());
    }*/

    public static WebDriver getDriver(ChromeOptions chromeOptions) {
        WebDriver driver;
        driver = new ChromeDriver(chromeOptions);
        configureCommonParams(driver);
        return driver;
    }


    private static void configureCommonParams(WebDriver driver) {
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        //PageHelper.maximizeWindow(driver);
    }
}
