package com.demo.homePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.init.AbstractPage;

public class HomeVerificationPage extends AbstractPage {

	public HomeVerificationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	WebDriverWait wait = new WebDriverWait(driver, 55);
	
	@FindBy(xpath="//a[text()='About Us']")
	WebElement btnAboutUs;
	@FindBy(xpath="//a[text()='Services']")
	WebElement btnServices;
	@FindBy(xpath="//a[text()='Digital Assurance']")
	WebElement btnDigitalAssurance;
	@FindBy(xpath="//ul[@id='menu-my_menu']//a[text()='Blog']")
	WebElement btnBlog;
	@FindBy(xpath="//a[text()='Resources']")
	WebElement btnResources;
	@FindBy(xpath="//ul[@id='menu-my_menu']//a[text()='Career']")
	WebElement btnCareer;
	@FindBy(xpath="//ul[@id='menu-my_menu']//a[text()='Contact Us']")
	WebElement btnContactUs;
	
	public boolean verifyKiwiQAHomepage(){
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='About Us']")));
		
		return  driver.findElement(By.xpath("//a[text()='About Us']")).isDisplayed() && 
				driver.findElement(By.xpath("//a[text()='Services']")).isDisplayed() &&
				driver.findElement(By.xpath("//a[text()='Digital Assurance']")).isDisplayed() &&
				driver.findElement(By.xpath("//ul[@id='menu-my_menu']//a[text()='Blog']")).isDisplayed() &&
				driver.findElement(By.xpath("//a[text()='Resources']")).isDisplayed() &&
				driver.findElement(By.xpath("//ul[@id='menu-my_menu']//a[text()='Career']")).isDisplayed() &&
				driver.findElement(By.xpath("//ul[@id='menu-my_menu']//a[text()='Contact Us']")).isDisplayed();
		}
	
	public boolean verifyThankyouPage() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Thank You!']")));

		return  driver.findElement(By.xpath("//div[text()='Thank You!']")).isDisplayed();
	}
}
