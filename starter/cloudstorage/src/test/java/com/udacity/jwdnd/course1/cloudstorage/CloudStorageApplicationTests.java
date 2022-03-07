package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser("Juan Manuel", "Vilchis Pineda", "manolo", "M4nolito_130");
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signUpPage.getSuccessAlertText(driver));
	}

	@Test
	public void loginSucces(){
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.logInUser("manolo", "M4nolito_130");
		this.homePage = new HomePage(driver);
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
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='NOTE TITLE']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}

	@Test
	void itShouldCreateCredential() {
		//Given
		String firstName = "manolo";
		String lastName = "manolo";
		String userName = "manolo";
		String password = "manolo";

		String credentialUrl = "www.test.com";
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
		WebElement noteElement = driver.findElement(By.xpath("//*[text()='test@user.com']"));
		Assertions.assertTrue(noteElement.isEnabled());
	}
}
