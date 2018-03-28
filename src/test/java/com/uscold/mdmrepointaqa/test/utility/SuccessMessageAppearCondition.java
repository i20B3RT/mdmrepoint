package com.uscold.mdmrepointaqa.test.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import javax.annotation.Nullable;

public class SuccessMessageAppearCondition implements ExpectedCondition<Boolean> {

    private final String substringOfMessageToBePresented;
    private final String elementId;

    public SuccessMessageAppearCondition(String elementId, String msgSubstring) {
        this.substringOfMessageToBePresented = msgSubstring;
        this.elementId = elementId;
    }


    @Nullable
    @Override
    public Boolean apply(@Nullable WebDriver driver) {
        WebElement statusMsg = driver.findElement(By.id(elementId));
        AssistPage.scrollTo(driver, statusMsg);
        return statusMsg.isDisplayed() && statusMsg.getText().toLowerCase().contains(substringOfMessageToBePresented);
    }
}
