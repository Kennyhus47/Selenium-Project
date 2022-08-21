package com.app.pages;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.app.utils.BaseClass;

public class HomePage extends BaseClass {

// ==================Home page locators=======================	

	@FindBy(xpath = "//div[@id='weather-wrapper']/strong")
	private WebElement currectDateTime;

	@FindBy(xpath = "//div[contains(@class,'page-header')]/child::ul//a[contains(text(),'Sport')]")
	private WebElement sportsMenu;
	
	@FindBy(xpath = "//div[contains(@class,'sport nav-secondary-container')]/child::div/ul//a[contains(text(),'Football')]")
	private WebElement footballSubMenu;

	@FindBy(xpath = "//div[contains(@class,'nav-secondary') and contains(@data-track-module,'nav-secondary')]")
	private WebElement subMenu;
	
	@FindBy(xpath = "(//div[contains(@itemprop,'mainEntity')]/child::div//h2/a)[1]")
	private WebElement firstArticle;
	
	@FindBy(xpath = "(//div[@itemprop='articleBody']//div[contains(@class,'artSplitter mol-img-group')]//img)[1]")
	private WebElement firstGallery;
	
//	@FindBy(xpath = "//div[contains(@class,'paginationButtons')]//button[@aria-label='Next']")
//	private WebElement NextButton;
	
	@FindBy(xpath = "//div/button[contains(@class,'nextButton')]")
	private WebElement NextButton;
	
	
	@FindBy(xpath = "//div/button[contains(@class,'previousButton')]")
	private WebElement PreviousButton;
	
	@FindBy(xpath = "//div[contains(@class,'counter')]")
	private WebElement GalarryNumberCount;
	
	@FindBy(xpath = "//button[@aria-label='Close']")
	private WebElement CloseIcon;
	
//	@FindBy(xpath = "(//video[contains(@class,'vjs-tech')]/following-sibling::video/following-sibling::div/div[contains(@class,'fullscreen-control')]/div/span)[3]")
//	private WebElement ZoomInIconInVideo;
	
	@FindBy(xpath = "//div[(contains(@class,'videoWrapper'))]//div[@class='vjs-title']/following-sibling::div[@class='vjs-control-bar']/div[contains(@class,'fullscreen-control')]")
	private WebElement ZoomInIconInVideo;
	
	
	@FindBy(xpath = "//div[contains(text(),'Premier League')]/parent::div[contains(@class,'sideRailsHeading')]/following-sibling::div/table/tbody/tr")
	private WebElement PremierLeagueTableTR;
	
	@FindBy(xpath = "//div[(contains(@class,'videoWrapper'))]//div[@class='vjs-title']/following-sibling::div[@class='vjs-control-bar']/div[contains(@class,'fullscreen-control')]//following-sibling::div[contains(@class,'social-controls')]//div[contains(@class,'facebook-button')]/div")
	private WebElement facebookIcon;
	
	
	@FindBy(xpath = "//div[contains(text(),'Premier League')]/parent::div[contains(@class,'sideRailsHeading')]/following-sibling::div/table/tbody/tr")
	private WebElement teamPosition;
	
	@FindBy(xpath = "//div[contains(text(),'Premier League')]/parent::div[contains(@class,'sideRailsHeading')]/following-sibling::div/table/tbody/tr")
	private WebElement teamPoints;
	

	
	

//=========================================================================	

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	public String getCurrectDateAndTime() throws InterruptedException {
		return currectDateTime.getText();
	}
	
	public void clickOnSportsMenu() throws InterruptedException, IOException {
		waitForPageLoad();
		waitAndClickElement(sportsMenu);
		//sportsMenu.click();
	}

	public void clickOnFootballSunMenu() throws InterruptedException, IOException {
		waitForPageLoad();
		waitAndClickElement(footballSubMenu);
		//footballSubMenu.click();

		
	}
	
	public String getSportsMenuColor() throws InterruptedException {
		String color=  sportsMenu.getCssValue("background-color");
		String hexColor= Color.fromString(color).asHex();
		return hexColor;
	}
	
	public String getFootballSunMenuColor() {
		String color=  subMenu.getCssValue("background-color");
		String hexColor= Color.fromString(color).asHex();
		return hexColor;
	}
	
	public void clickOnFirstArticle() throws InterruptedException, IOException {
		waitForPageLoad();
		waitAndClickElement(firstArticle);
		//firstArticle.click();
	}

	
	public void clickOnFirstGallery() throws InterruptedException, IOException {
		waitForPageLoad();
		waitAndClickElement(firstGallery);
		Thread.sleep(2000);
		//firstGallery.click();
	}
	
	public void clickOnNextButton() throws InterruptedException, IOException {
		waitAndClickElement(NextButton);
		//NextButton.click();
	}
	
	public void clickOnPreviousButton() throws InterruptedException, IOException {
		waitAndClickElement(PreviousButton);
		//PreviousButton.click();
	}
	
	public boolean isNextButtonDisplayed() throws InterruptedException {
		
		return NextButton.isDisplayed();
	}

	public boolean isPreviousButtonDisplayed() throws InterruptedException {
		return PreviousButton.isDisplayed();
	}
	
	public void clickOnFacbookIcon() throws InterruptedException, IOException {
		String baseWindow = driver.getWindowHandle();
		System.out.println("My base widnow ID is : " + baseWindow);
		isElementClickable(facebookIcon);
		facebookIcon.click();
		Set<String> set = new HashSet<String>();
		set.addAll(driver.getWindowHandles());

		for (String str : set) {
			System.out.println("All my window ID's " + str);
			driver.switchTo().window(str);
			Thread.sleep(3000);
			String title = driver.getTitle();
			if (title.equalsIgnoreCase("Post to Facebook")) {
				assertTrue(driver.getTitle().contains("Post to Facebook"));
				Thread.sleep(2000);
				driver.close();
			}
		}

		Thread.sleep(3000);
		driver.switchTo().window(baseWindow);
	}
	
	
	public void navigateToEmbeddedVideoScreen() throws InterruptedException
	{
		while(!(isElementDisplayed(ZoomInIconInVideo)))
		{
			jsClick(NextButton);
			Thread.sleep(1000);
		}
	}
	
	public void clickOnFullScreenIcon() throws InterruptedException
	{
		ZoomInIconInVideo.click();
		Thread.sleep(2000);
	}

	
	public String getTeamPoints(String teamName)
	{
		WebElement element= teamPoints.findElement(By.xpath("//td[contains(text(),'"+teamName+"')]//following-sibling::td[2]"));
		return element.getText();
		
	}
	
	public String getTeamPosition(String teamName)
	{
		WebElement element= teamPosition.findElement(By.xpath("//td[contains(text(),'"+teamName+"')]//preceding-sibling::td[3]"));
		return element.getText();
		
	}
	public void clickOnCloseIcon() throws InterruptedException
	{
		CloseIcon.click();
		Thread.sleep(2000);
		driver.navigate().back();
		waitForPageLoad();
		
	}
}
