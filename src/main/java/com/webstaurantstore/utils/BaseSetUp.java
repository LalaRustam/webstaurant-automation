package com.webstaurantstore.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseSetUp {

	protected WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
	}
	
	@AfterMethod
	public void closeDriver() {
		driver.quit();
	}
}
