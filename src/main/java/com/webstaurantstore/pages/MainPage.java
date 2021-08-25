package com.webstaurantstore.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.webstaurantstore.utils.ConfigurationReader;

public class MainPage {

	
	private SoftAssert softAssert;
	private WebDriver driver;
	
	public MainPage(WebDriver driver, SoftAssert softAssert) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		this.softAssert = softAssert;
	}
	
	
	@FindBy(id = "searchval")
	private WebElement searchInput;
	
	@FindBy(xpath = "//button[contains(text(),'Search')]")
	private WebElement searchButton;
	
	@FindBy(xpath = "//div[@id='paging']//ul/li[contains(@class,'rc-pagination-next')]")
	private WebElement nextPageBtn;
	
	@FindBy(xpath = "//div[@id='paging']//ul/li[contains(@class, 'rc-pagination-item-9')]")
	private WebElement lastPage_9;
	
	@FindBy(xpath = "//div[@class='ReactModalPortal']//header/h4")
	private WebElement prdAccessoriesPopup;
	
	@FindBy(xpath = "//div[@class='ReactModalPortal']//button[text()='Add To Cart']")
	private WebElement addToCartBtn;
	
	@FindBy(xpath = "//div[@data-role='notification']")
	private WebElement addedToCartNotif;
	
	@FindBy(xpath = "//div[@class='notification__action']/a[text()='View Cart']")
	private WebElement viewCartLink;
	
	public void navigateToMainPage() {
		this.driver.get(ConfigurationReader.getProperty("url"));
		String actual = this.driver.getTitle();
		this.softAssert.assertEquals(actual, "WebstaurantStore", "Title must be WebstaurantStore but was "+actual);
	}
	
	public void searchItem(String item) throws InterruptedException {
		searchInput.sendKeys(item);
		Thread.sleep(1000);
		searchButton.click();
	}
	
	
	public void verifySearchedItems(String itemToVerify) throws InterruptedException {
		
		while(true) {
			Thread.sleep(2000);
			scrollToElement(nextPageBtn);
			
			//print all 60 items per page and verify each has title 'Table'
			List<WebElement> itemDescriptions = driver.findElements(
					By.xpath("//div[@id='product_listing']//div[@id='details']//a[@data-testid='itemDescription']"));
			
			for (int i = 0; i < itemDescriptions.size(); i++) {
				String title = itemDescriptions.get(i).getText().toLowerCase();
				boolean containsKeyWord = title.contains(itemToVerify.toLowerCase());
				softAssert.assertTrue(containsKeyWord, 
						title + " does not contain " + itemToVerify);
			}
			
			//sleep 2 seconds before clicking next page
			Thread.sleep(2000);
			String disabled = nextPageBtn.getAttribute("aria-disabled");
			if (disabled.equals("false")) {
				nextPageBtn.click();
			}else {
				break;
			}
		}
	}
	
	private void scrollToElement(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500); 
	}

	public void goToLastPage() throws InterruptedException {
		scrollToElement(nextPageBtn);
		lastPage_9.click();
		scrollToElement(nextPageBtn);
		
		
	}
	
	public void addItemToCart() throws InterruptedException {
		List<WebElement> items = driver
				.findElements(By.xpath("//div[@id='product_listing']//div[@class='add-to-cart']"));

		WebElement lastItemAddToCart = items.get(items.size() - 1);
		lastItemAddToCart.click();
		Thread.sleep(1000);

		softAssert.assertTrue(prdAccessoriesPopup.isDisplayed(), "Product Accessories Pop up did not appear");

		addToCartBtn.click();
		Thread.sleep(300);
		
		softAssert.assertTrue(addedToCartNotif.isDisplayed(), "Added to Cart Notification did not appear");

		viewCartLink.click();
		Thread.sleep(1000);
	}
	
}
