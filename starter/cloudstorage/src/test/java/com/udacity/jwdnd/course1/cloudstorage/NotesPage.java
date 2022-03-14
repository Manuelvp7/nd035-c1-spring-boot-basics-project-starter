package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotesPage extends WebPageImpl {

  @FindBy(id = "add-note-button")
  private WebElement addNoteButton;

  @FindBy(id = "footer-note-submit")
  private WebElement submitButton;

  @FindBy(id = "note-title")
  private WebElement titleInput;

  @FindBy(id = "note-description")
  private WebElement descriptionInput;


  public NotesPage(WebDriver webDriver) {
    super(webDriver);
    waitUntilElementIsAvailableById(webDriver, "add-note-button");
    PageFactory.initElements(webDriver, this);
  }

  public void createNote( WebDriver driver,String noteTitle, String noteDescription){
    addNoteButton.click();
    waitUntilElementIsAvailableById(driver,"footer-note-submit");
    titleInput.sendKeys(noteTitle);
    descriptionInput.sendKeys(noteDescription);
    submitButton.click();
  }

  public void editNote(String noteTitle, String noteDescription, WebDriver webDriver){

    WebDriverWait wait = new WebDriverWait(webDriver, 15);
    wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.cssSelector("button[id^='edit-note-']"))));

    WebElement editButton =  webDriver.findElement(By.cssSelector("button[id^='edit-note-']"));

    editButton.click();
    waitUntilElementIsAvailableById(webDriver,"footer-note-submit");
    titleInput.sendKeys(noteTitle);
    descriptionInput.sendKeys(noteDescription);
    submitButton.click();


  }

  public void deleteNote(int id, WebDriver webDriver){

    waitUntilElementIsAvailableById(webDriver, "delete-note-"+id);
    WebElement deleteButton =  webDriver.findElement(By.id("delete-note-"+id));
    deleteButton.click();
  }


  public void waitUntilHomePageRenders(WebDriver webDriver){
    WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "add-note-button");
  }

}
