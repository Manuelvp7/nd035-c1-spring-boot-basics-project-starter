package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface WebPage {
    WebElement waitUntilElementIsAvailableById(WebDriver driver, String elementId);

}
