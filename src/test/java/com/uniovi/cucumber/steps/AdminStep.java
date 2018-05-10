package com.uniovi.cucumber.steps;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uniovi.cucumber.pageobjects.PO_View;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AdminStep {
	
	private static String PathFirefox = "C:\\Firefox46.win\\FirefoxPortable.exe";
	private static String gecko = "C:\\Firefox46.win\\geckodriver.exe";
	private static WebDriver driver;
	private static String baseUrl = "http://localhost:8082";

	public static WebDriver getDriver() {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.firefox.marionette", gecko);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	
	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}
	
	@BeforeClass
    public static void setUp() throws IOException {
        String travisCiFlag = System.getenv().get("TRAVIS");
        FirefoxBinary firefoxBinary = "true".equals(travisCiFlag)
                ? getFirefoxBinaryForTravisCi()
                : new FirefoxBinary();
 
        driver = new FirefoxDriver();
    }
 
    private static FirefoxBinary getFirefoxBinaryForTravisCi() throws IOException {
        String firefoxPath = getFirefoxPath(); 
        return new FirefoxBinary(new File(firefoxPath));
    }
 
    private static String getFirefoxPath() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("which", "firefox");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try (InputStreamReader isr = new InputStreamReader(process.getInputStream(), "UTF-8");
             BufferedReader br = new BufferedReader(isr)) {
            return br.readLine();
        }
    }
	
	@Given("^a logged in admin$")
	public void aLoggedInAdmin(){
		driver = getDriver();		
		driver.navigate().to(baseUrl);
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys("fireman1@gmail.com");
		WebElement pass = driver.findElement(By.id("pass"));
		pass.sendKeys("123456");
		login = driver.findElement(By.id("login"));
		login.click();
	}

	@When("^he is in the main page$")
	public void heIsInTheMainPage() {
		//We find the welcome message
		WebElement assigned = driver.findElement(By.name("tableIncidents"));
		assertTrue(assigned !=null);
	}
	
	@When("^he is in the admin page$")
	public void heIsInTheAdminPage() {
		//We find the welcome message
		WebElement but = driver.findElement(By.id("operatorPermissions"));
		but.click();
	}
	
	@Then("^incidents are shown")
	public void incidentsAreShown() {
		List<WebElement> list = PO_View.checkElement(driver, "free", "//table[@name='tableIncidents']/tbody/tr");
		// Comprobamos que hay 1
		assertTrue(list.size() >= 1);
	}
	
	@Then("^he can change some permissions")
	public void heCanChangeSomePermissions() {
		List<WebElement> list = PO_View.checkElement(driver, "free", "//table[@name='tableIncidents']/tbody/tr");
		// Comprobamos que hay 1
		assertTrue(list.size() >= 1);
	}
	
	

}
