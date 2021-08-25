
package com.webstaurantstore;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.webstaurantstore.pages.MainPage;
import com.webstaurantstore.pages.ViewCartPage;
import com.webstaurantstore.utils.BaseSetUp;

public class EndToEndTest extends BaseSetUp {

	
	@Test
	public void searchItemTest() throws InterruptedException {
		
		driver = new ChromeDriver();
		SoftAssert softAssert = new SoftAssert();
		MainPage page = new MainPage(driver,softAssert);
		page.navigateToMainPage();
		page.searchItem("stainless work table");
		page.verifySearchedItems("Table");
		softAssert.assertAll();
	
	}
	
	@Test
	public void addToCardLastItemTest() throws InterruptedException {
		
		driver = new ChromeDriver();
		SoftAssert softAssert = new SoftAssert();
		MainPage page = new MainPage(driver,softAssert);
		ViewCartPage viewCartPage = new ViewCartPage(driver, softAssert);
		
		page.navigateToMainPage();
		
		page.searchItem("stainless work table");
		page.goToLastPage();
		
		page.addItemToCart();
		
		viewCartPage.emptyCart();
	
	}
	
	
	
}
