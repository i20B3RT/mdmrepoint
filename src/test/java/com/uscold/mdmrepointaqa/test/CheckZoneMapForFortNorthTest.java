package com.uscold.mdmrepointaqa.test;

import com.uscold.mdmrepointaqa.test.utility.AssistPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.log4testng.Logger.getLogger;


public class CheckZoneMapForFortNorthTest extends Abstract {

    public static org.testng.log4testng.Logger logger = getLogger(CheckZoneMapForFortNorthTest.class);

    @Test
    public void goToZoneMap() throws IOException, InterruptedException {

        AssistPage.chooseModule(driver, "Zone Map");

        AssistPage.chooseWarehouse(driver, 160);

        WebElement divContainer = driver.findElement(By.xpath("//div[@id='search_in_chosen']"));
        WebElement a = divContainer.findElement(By.tagName("a"));

        click(a);
        List<WebElement> rooms = divContainer.findElement(By.tagName("ul")).findElements(By.tagName("li"));
        rooms.stream().filter(el -> el.getText().contains("A")).findFirst().get().click();

        WebElement btn = driver.findElement(By.xpath("//button[@id='SrchZoneMap']"));
        click(btn);
/*
        logger.warn("making a screenshot");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/home/uscold/share/screen.jpg"));

        String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        logger.warn("screen as base64" + base64);*/

    }


}
