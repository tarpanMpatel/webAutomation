package com.demo.homePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.init.AbstractPage;
import com.init.Common;

public class HomeIndexPage extends AbstractPage{

	public HomeIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	WebDriverWait wait = new WebDriverWait(driver, 20);

	@FindBy(xpath="//a[text()='Accept']")
	WebElement btnAcceptCookies;
	@FindBy(xpath="//a[text()='Request A Call']")
	WebElement btnRequestCall;
	
	public HomeVerificationPage callback(String name, String email, String subject, String message) {
		
		if(btnAcceptCookies.isDisplayed()) {
			btnAcceptCookies.click();
		}
		Common.clickOn(driver, btnRequestCall);
		log("Clicked on 'Request A Call' button.");
		Common.pause(2);
		enterCallbackDetails(name, email, subject, message);

		return new HomeVerificationPage(driver);
	}
	
	@FindBy(xpath="//div[@id='wpcf7-f7-o1']//input[@placeholder='Name*']")
	WebElement txtName;
	@FindBy(xpath="//div[@id='wpcf7-f7-o1']//input[@placeholder='Email*']")
	WebElement txtEmail;
	@FindBy(xpath="//div[@id='wpcf7-f7-o1']//input[@placeholder='Subject*']")
	WebElement txtSubject;
	@FindBy(xpath="//div[@id='wpcf7-f7-o1']//textarea[@placeholder=\"Message*\"]")
	WebElement txtMessage;
	@FindBy(xpath="//div[@id='wpcf7-f7-o1']//input[@type='submit']")
	WebElement btnSubmit;
	
	public HomeVerificationPage enterCallbackDetails(String name, String email, String subject, String message) {
		txtName.sendKeys(name);
		txtEmail.sendKeys(email);
		txtSubject.sendKeys(subject);
		txtMessage.sendKeys(message);
		//btnSubmit.click();
		
		return new HomeVerificationPage(driver);	
	}	
}