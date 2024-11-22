package org.springframework.samples.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OwnersTestE2ETest {

	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
	}

	@Test
	public void testFindOwners() {
		driver.get("http://localhost:8080");
		driver.findElement(By.xpath("//*[@id=\"main-navbar\"]/ul/li[2]/a")).click();
		assertTrue(driver.getPageSource().contains("Owners"));
		driver.findElement(By.id("lastName")).sendKeys("Davis");
		driver.findElement(By.xpath("//*[@id=\"search-owner-form\"]/div[2]/div/button")).click();
		WebElement tbody = driver.findElement(By.xpath("//*[@id=\"owners\"]/tbody"));
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));

		for (WebElement row : rows) {
			assertTrue(row.getText().contains("Davis"));
		}
	}

	@Test
	public void testAddOwners() {
		driver.get("http://localhost:8080");
		driver.findElement(By.xpath("//*[@id=\"main-navbar\"]/ul/li[2]/a")).click();
		assertTrue(driver.getPageSource().contains("Owners"));
		driver.findElement(By.xpath("//*[@id=\"search-owner-form\"]/a")).click();
		driver.findElement(By.id("firstName")).sendKeys("prueba");
		driver.findElement(By.id("lastName")).sendKeys("automatizada");
		driver.findElement(By.id("address")).sendKeys("test");
		driver.findElement(By.id("city")).sendKeys("michigan");
		driver.findElement(By.id("telephone")).sendKeys("1234567899");
		driver.findElement(By.xpath("//*[@id=\"add-owner-form\"]/div[2]/div/button")).click();
		;
		assertTrue(driver.findElement(By.tagName("h2")).getText().contains("Owner Information"));
		assertTrue(driver.findElement(By.id("success-message")).isDisplayed());
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

}
