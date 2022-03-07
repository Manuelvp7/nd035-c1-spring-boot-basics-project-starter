package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends WebPageImpl {

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "buttonLogIn")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver){
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void logInUser(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public String getSuccessAlertText(WebDriver webDriver){
        WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "succesMessage");
        return successDiv.getText();
    }



}
