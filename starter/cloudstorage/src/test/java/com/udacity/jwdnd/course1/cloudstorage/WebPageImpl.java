package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebPageImpl implements  WebPage{

    private WebDriver driver;
    private WebDriverWait wait;

    public WebPageImpl(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    @Override
    public WebElement waitUntilElementIsAvailableById(WebDriver driver, String elementId) {
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(elementId))));
    }




}
