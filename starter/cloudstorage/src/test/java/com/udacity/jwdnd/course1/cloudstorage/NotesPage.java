package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

  public void editNote(int id, String noteTitle, String noteDescription, WebDriver webDriver){
    waitUntilElementIsAvailableById(webDriver, "edit-note-"+id);
    WebElement editButton =  webDriver.findElement(By.id("edit-note-"+id));
    WebElement titleLabel =  webDriver.findElement(By.id("note-title-"+id));
    WebElement descriptionLabel =  webDriver.findElement(By.id("note-description-"+id));
    editButton.click();
    titleInput.sendKeys(noteTitle);
    descriptionInput.sendKeys(noteDescription);
    submitButton.click();
    waitUntilElementIsAvailableById(webDriver, "edit-note-"+id);

  }

  public void deleteNote(String noteTitle, String noteDescription){
    addNoteButton.click();
    titleInput.sendKeys(noteTitle);
    descriptionInput.sendKeys(noteDescription);
    submitButton.click();
  }


  public void waitUntilHomePageRenders(WebDriver webDriver){
    WebElement successDiv = waitUntilElementIsAvailableById(webDriver, "add-note-button");
  }

}
