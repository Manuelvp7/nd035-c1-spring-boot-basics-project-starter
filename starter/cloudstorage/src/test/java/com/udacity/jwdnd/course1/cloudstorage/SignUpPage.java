package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage extends WebPageImpl{

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "buttonSignup")
    private  WebElement signupButton;

    public SignUpPage(WebDriver webDriver){
        super(webDriver);
        PageFactory.initElements(webDriver,this);
    }

    public void signUpUser(String firstName, String lastName, String username, String password){
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        signupButton.click();
    }


    public String getSuccessAlertText(WebDriver webDriver){
        WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "succesMessage");
        return successDiv.getText();
    }

}
