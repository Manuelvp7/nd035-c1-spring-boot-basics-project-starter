package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends WebPageImpl {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        waitUntilElementIsAvailableById(webDriver, "logout-div");
        PageFactory.initElements(webDriver, this);
    }

    public void clickOnCredentialsTab(){
        navCredentialsTab.click();
    }

    public void clickOnNotesTab(){
        navNotesTab.click();
    }

    public void waitUntilHomePageRenders(WebDriver webDriver){
        WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "logout-div");
    }
}
