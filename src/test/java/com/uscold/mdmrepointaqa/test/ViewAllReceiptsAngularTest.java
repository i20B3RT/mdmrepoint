package com.uscold.mdmrepointaqa.test;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class ViewAllReceiptsAngularTest extends Abstract {

    @Test
    public void viewAllReceiptsAngularTest() throws InterruptedException {
        driver.get("http://uatwas.uscold.com:9113/ewm/spa/receipt/receiptsMaintenance/1");
        click(driver.findElement(By.xpath("//div[@class='formelement-select ng-untouched ng-pristine ng-valid']//button[@class='btn dropdown-toggle']")));
        WebElement whseInput = driver.findElement(By.xpath("//div[@class='formelement-menu']/div[@class='input-group']/input"));
        whseInput.sendKeys("160");
        click(driver.findElement(By.xpath("//div[@class='dropdown-menu show']//span[contains(text(),'160')]")));
        //PageHelper.chooseWarehouse(driver,160);
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        WebElement customerInput = driver.findElement(By.xpath("//input[@name='control' and @placeholder='Customer Name']"));
        customerInput.clear();
        customerInput.sendKeys("perd");
        click(driver.findElement(By.xpath("//span[contains(text(),'100950')]")));
        //driver.findElement(By.xpath("//div/formelements/div/formelement/div/formelement-select/div/div/div/div[2]/formelement-window/div/a/span")).click();
        click(driver.findElement(By.xpath("//form/div/button[@class='btn btn-sm btn-primary']")));
        List<WebElement> trs = driver.findElements(By.xpath("//table[@class='gird-template']/tbody/tr"));
        Assert.assertTrue(trs.size() > 0);
    }
}
