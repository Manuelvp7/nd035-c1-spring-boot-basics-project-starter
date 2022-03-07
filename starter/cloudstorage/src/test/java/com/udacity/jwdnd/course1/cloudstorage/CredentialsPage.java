package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage extends WebPageImpl {

  @FindBy(id = "add-credential-button")
  private WebElement addCredentialButton;

  @FindBy(id = "footer-credential-submit")
  private WebElement submitButton;

  @FindBy(id = "credential-url")
  private WebElement urlInput;

  @FindBy(id = "credential-username")
  private WebElement usernameInput;

  @FindBy(id = "credential-password")
  private WebElement passwordInput;

  public CredentialsPage(WebDriver webDriver) {
    super(webDriver);
    waitUntilElementIsAvailableById(webDriver, "add-credential-button");
    PageFactory.initElements(webDriver, this);
  }

  public void createCredential(
      WebDriver driver,
      String credentialUrl,
      String credentialUsername,
      String credentialPassword){

    addCredentialButton.click();
    waitUntilElementIsAvailableById(driver,"footer-credential-submit");
    urlInput.sendKeys(credentialUrl);
    usernameInput.sendKeys(credentialUsername);
    passwordInput.sendKeys(credentialPassword);
    submitButton.click();
  }

  public void waitUntilHomePageRenders(WebDriver webDriver){
    WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "add-credential-button");
  }

}
