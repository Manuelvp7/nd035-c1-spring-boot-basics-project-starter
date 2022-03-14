package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage extends WebPageImpl{

    @FindBy(id = "home")
    private WebElement homeTag;

    public ResultPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsAvailableById(driver, "home");
        PageFactory.initElements(driver, this);
    }

    public void returnToHome(){
        homeTag.click();
    }
}
