package com.webstaurantstore.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class ViewCartPage {


	private SoftAssert softAssert;
	private WebDriver driver;
	
	public ViewCartPage(WebDriver driver, SoftAssert softAssert) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		this.softAssert = softAssert;
	}
	
	@FindBy(xpath = "//div[@class='cartItems']//a[text()='Empty Cart']")
	private WebElement emptyCartBtn;
	
	@FindBy(xpath = "//h1[contains(text(),'Cart')]")
	private WebElement cartHeader;
	
	@FindBy(xpath = "//div[text()='Are you sure you want to empty your cart?']")
	private WebElement deleteCartModal;
	
	@FindBy(xpath = "//div[@class='modal-footer']/button[text()='Empty Cart']")
	private WebElement emptyCartModalBtn;
	
	@FindBy(xpath = "//div[@class='empty-cart__text']/p[text()='Your cart is empty.']")
	private WebElement cartEmptyTxt;
	
	public void emptyCart() throws InterruptedException {
		
		softAssert.assertTrue(cartHeader.getText().trim().equals("Cart"));
		
		emptyCartBtn.click();
		
		Thread.sleep(2000);
	
		softAssert.assertTrue(deleteCartModal.isDisplayed(), "Delete pop up did not appear");
		
		emptyCartModalBtn.click();
		softAssert.assertEquals(cartEmptyTxt.getText().trim(), "Your cart is empty.", "Your Cart is empty message is wrong or did not appear");
		
	}
	
	
	
}
