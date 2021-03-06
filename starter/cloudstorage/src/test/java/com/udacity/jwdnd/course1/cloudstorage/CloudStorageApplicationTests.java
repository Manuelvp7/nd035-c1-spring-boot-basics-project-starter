package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignUpPage signUpPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();
		signUpPage = new SignUpPage(driver);
		loginPage = new LoginPage(driver);

	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignUpPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void signUpUser(){
		WebDriverWait wait;
		wait = new WebDriverWait(driver, 15);

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser("Juan Manuel", "Vilchis Pineda", "manolo2", "manolo2");

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("inputUsername"))));
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signUpPage.getSuccessAlertText(driver));
	}

	@Test
	public void logoutUser(){
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String noteTitle = "NOTE TITLE";
		String noteDescription = "DESCRIPTION";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickLogoutBtn();

		driver.get("http://localhost:" + this.port + "/home");
		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Login']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	public void loginSucces() throws InterruptedException {
		WebDriverWait wait;
		wait = new WebDriverWait(driver, 15);

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser("Juan Manuel", "Vilchis Pineda", "manolo3", "manolo3");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("inputUsername"))));

		loginPage.logInUser("manolo3", "manolo3");

		Assertions.assertEquals("Home", driver.getTitle() );
	}

	@Test
	void itShouldCreateNote() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String noteTitle = "NOTE TITLE";
		String noteDescription = "DESCRIPTION";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnNotesTab();

		NotesPage notesPage = new NotesPage(driver);
		notesPage.waitUntilHomePageRenders( driver);
		notesPage.createNote( driver,noteTitle, noteDescription);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldEditNote() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String noteTitle = "EDIT TITLE";
		String noteDescription = "EDIT DESCRIPTION";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnNotesTab();

		NotesPage notesPage = new NotesPage(driver);

		notesPage.waitUntilHomePageRenders( driver);
		notesPage.createNote(driver, noteTitle, noteDescription);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHome();
		this.homePage.clickOnNotesTab();
		notesPage.editNote( " UPDATED", " UPDATED", driver);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldDeleteNote() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String noteTitle = "DELETE TITLE";
		String noteDescription = "DELETE DESCRIPTION";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnNotesTab();

		NotesPage notesPage = new NotesPage(driver);

		notesPage.waitUntilHomePageRenders( driver);
		notesPage.createNote( driver,noteTitle, noteDescription);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHome();
		this.homePage.clickOnNotesTab();
		notesPage.deleteNote( 1, driver);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldCreateCredential() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String credentialUrl = "http://www.test.com";
		String credentialUsername = "test@user.com";
		String credentialPassword = "P4ssw0rd";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnCredentialsTab();

		CredentialsPage credentialsPage = new CredentialsPage(driver);
		credentialsPage.waitUntilHomePageRenders(driver);
		credentialsPage.createCredential(driver, credentialUrl, credentialUsername, credentialPassword);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldEditCredential() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String credentialUrl = "http://www.test.edit.com";
		String credentialUsername = "test.edit@user.com";
		String credentialPassword = "P4ssw0rd";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnCredentialsTab();

		CredentialsPage credentialsPage = new CredentialsPage(driver);
		credentialsPage.waitUntilHomePageRenders(driver);
		credentialsPage.createCredential(driver, credentialUrl, credentialUsername, credentialPassword);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHome();
		this.homePage.clickOnCredentialsTab();
		credentialsPage.editCredential( "updated", "updated", "updated", driver);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldDeleteCredential() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String credentialUrl = "http://www.test.com";
		String credentialUsername = "test@user.com";
		String credentialPassword = "P4ssw0rd";

		//When
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(firstName, lastName, userName, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser(userName, password);

		this.homePage = new HomePage(driver);
		this.homePage.clickOnCredentialsTab();

		CredentialsPage credentialsPage = new CredentialsPage(driver);
		credentialsPage.waitUntilHomePageRenders(driver);
		credentialsPage.createCredential(driver, credentialUrl, credentialUsername, credentialPassword);
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHome();
		this.homePage.clickOnCredentialsTab();
		credentialsPage.deleteCredential( 1, driver);

		//Then
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='Success']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}
}
