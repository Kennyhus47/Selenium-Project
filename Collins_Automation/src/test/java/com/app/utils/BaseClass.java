package com.app.utils;

import java.awt.AWTException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.taskdefs.WaitFor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseClass {

	public static WebDriver driver;
	String driverType;
	public static ExtentReports extent;
	public static ExtentSparkReporter sparkReport;
	WebDriverWait  wait;
	

	enum DriverType {
		Firefox, Chrome, Edge, Safari
	}

	public static WebDriver initDriver(String driverType) {
		if (DriverType.Firefox.toString().equals(driverType)) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (DriverType.Edge.toString().equals(driverType)) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		} else if (DriverType.Chrome.toString().equals(driverType)) {
			
			ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-notifications");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			
		} else if (DriverType.Safari.toString().equals(driverType)) {
			WebDriverManager.safaridriver().setup();
			
			driver = new SafariDriver();
		}
		else {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;

	}

	public WebDriver getWebDriver() {
		return driver;
	}

	
	public int getRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;

		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	public String randomString(int len) {

		String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	/**********************************************************************************
	 **CUSTOM METHODS
	 **********************************************************************************/

	public By ByLocator(WebElement element) {
	    // By format = "[foundFrom] -> locator: term"
	    // see RemoteWebElement toString() implementation
	    String[] data = element.toString().split(" -> ")[1].replace("]", "").split(": ");
	    String locator = data[0];
	    String term = data[1];

	    switch (locator) {
	    case "xpath":
	        return By.xpath(term);
	    case "css selector":
	        return By.cssSelector(term);
	    case "id":
	        return By.id(term);
	    case "tag name":
	        return By.tagName(term);
	    case "name":
	        return By.name(term);
	    case "link text":
	        return By.linkText(term);
	    case "class name":
	        return By.className(term);
	    }
	    return (By) element;
	}
	
	
	public By ByLocator(String locator) {
		By result = null;

		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		} else if (locator.startsWith("#")) {
			result = By.name(locator.replace("#", ""));

		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		}

		else if (locator.startsWith("class=")) {
			result = By.className(locator.replace("class=", ""));
		} else if (locator.startsWith("name=")) {
			result = By.name(locator.replace("name=", ""));
		}

		else if (locator.startsWith("id=")) {
			result = By.id(locator.replace("id=", ""));
		} else if (locator.startsWith("(")) {
			result = By.xpath(locator);
		} else {
			result = By.id(locator);
		}

		return result;
	}
	
	/**********************************************************************************
	 **CLICK METHODS
	 **********************************************************************************/
	public void waitAndClickElement(WebElement element) throws InterruptedException, IOException {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				System.out.println("Unable to wait and click on WebElement, Exception: " + e.getMessage());
				Assert.fail("Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
			}
			attempts++;
		}
	}

	public void waitAndClickElementsUsingByLocator(By by) throws InterruptedException {
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 10) {
			try {
				wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(by)).click();
				System.out.println("Successfully clicked on the element using by locator: " + "<" + by.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				System.out.println("Unable to wait and click on the element using the By locator, Exception: " + e.getMessage());
				Assert.fail("Unable to wait and click on the element using the By locator, element: " + "<"+ by.toString() + ">");
			}
			attempts++;
		}
	}

	public void clickOnTextFromDropdownList(WebElement list, String textToSearchFor) throws Exception {
		Wait<WebDriver> tempWait = new WebDriverWait(driver, 30);
		try {
			
			tempWait.until(ExpectedConditions.elementToBeClickable(list)).click();
			list.sendKeys(textToSearchFor);
			list.sendKeys(Keys.ENTER);
			System.out.println("Successfully sent the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to send the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list.toString() + ">");
			Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
		}
	}


	public void clickOnElementUsingCustomTimeout(WebElement locator, WebDriver driver, int timeout) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
			locator.click();
			System.out.println("Successfully clicked on the WebElement, using locator: " + "<" + locator + ">"+ ", using a custom Timeout of: " + timeout);
		} catch (Exception e) {
			System.out.println("Unable to click on the WebElement, using locator: " + "<" + locator + ">" + ", using a custom Timeout of: " + timeout);
			Assert.fail("Unable to click on the WebElement, Exception: " + e.getMessage());
		}
	}
	

	 /**********************************************************************************
	 **ACTION METHODS
	 **********************************************************************************/

	public void actionMoveAndClick(WebElement element) throws Exception {
		Actions ob = new Actions(driver);
		
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			ob.moveToElement(element).click().build().perform();
			System.out.println("Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).isEnabled();
			if (elementPresent == true) {
				ob.moveToElement(elementToClick).click().build().perform();
				System.out.println("(Stale Exception) - Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
			}
		} catch (Exception e) {
			System.out.println("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void actionMoveAndClickByLocator(By element) throws Exception {
		Actions ob = new Actions(driver);
		try {
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			if (elementPresent == true) {
				WebElement elementToClick = driver.findElement(element);
				ob.moveToElement(elementToClick).click().build().perform();
				System.out.println("Action moved and clicked on the following element, using By locator: " + "<" + element.toString() + ">");
			}
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = driver.findElement(element);
			ob.moveToElement(elementToClick).click().build().perform();
			System.out.println("(Stale Exception) - Action moved and clicked on the following element, using By locator: "+ "<" + element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to Action Move and Click on the WebElement using by locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to Action Move and Click on the WebElement using by locator, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************
	 **SEND KEYS METHODS /
	 **********************************************************************************/
	public void sendKeysToWebElement(WebElement element, String textToSend) throws Exception {
		try {
			this.WaitUntilWebElementIsVisible(element);
			element.clear();
			element.sendKeys(textToSend);
			System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"+ element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: " + textToSend);
			Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}

	/**********************************************************************************
	 **JS METHODS & JS SCROLL
	 **********************************************************************************/
	public void scrollToElementByWebElementLocator(WebElement element) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element)).isEnabled();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -400)");
			System.out.println("Succesfully scrolled to the WebElement, using locator: " + "<" + element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to scroll to the WebElement, using locator: " + "<" + element.toString() + ">");
			Assert.fail("Unable to scroll to the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsPageScroll(int numb1, int numb2) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(" + numb1 + "," + numb2 + ")");
			System.out.println("Succesfully scrolled to the correct position! using locators: " + numb1 + ", " + numb2);
		} catch (Exception e) {
			System.out.println("Unable to scroll to element using locators: " + "<" + numb1 + "> " + " <" + numb2 + ">");
			Assert.fail("Unable to manually scroll to WebElement, Exception: " + e.getMessage());
		}
	}

	public void waitAndclickElementUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].click();", element);
			System.out.println("Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement staleElement = element;
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(staleElement)).isEnabled();
			if (elementPresent == true) {
				js.executeScript("arguments[0].click();", elementPresent);
				System.out.println("(Stale Exception) Successfully JS clicked on the following WebElement: " + "<" + element.toString() + ">");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Unable to JS click on the following WebElement: " + "<" + element.toString() + ">");
			Assert.fail("Unable to JS click on the WebElement, Exception: " + e.getMessage());
		}
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	/**********************************************************************************
	 **WAIT METHODS
	 **********************************************************************************/
	public boolean WaitUntilWebElementIsVisible(WebElement element) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(element));
			System.out.println("WebElement is visible using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean WaitUntilWebElementIsVisibleUsingByLocator(By element) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			System.out.println("Element is visible using By locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using By locator: " + "<" + element.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean isElementDisplayed(WebElement element, int timeout) {
		
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOf(element));
			System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}
	
public boolean isElementDisplayed(WebElement element) {
		boolean result=false;
		try {
			result= element.isDisplayed();
			System.out.println("WebElement is displayed using locator: " + "<" + element.toString() + ">");
			return result;
		} catch (Exception e) {
			System.out.println("WebElement is NOT displayed using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}
	
	public boolean isElementClickable(WebElement element) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
			return false;
		}
	}


	public boolean waitUntilPreLoadElementDissapears(By element) {
		return this.wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
	}


	/**********************************************************************************
	 **PAGE METHODS
	 **********************************************************************************/



	public String getCurrentURL() {
		try {
			String url = driver.getCurrentUrl();
			System.out.println("Found(Got) the following URL: " + url);
			return url;
		} catch (Exception e) {
			System.out.println("Unable to locate (Get) the current URL, Exception: " + e.getMessage());
			return e.getMessage();
		}
	}

	public String waitForSpecificPage(String urlToWaitFor) {
		try {
			String url = driver.getCurrentUrl();
			this.wait.until(ExpectedConditions.urlMatches(urlToWaitFor));
			System.out.println("The current URL was: " + url + ", " + "navigated and waited for the following URL: "+ urlToWaitFor);
			return urlToWaitFor;
		} catch (Exception e) {
			System.out.println("Exception! waiting for the URL: " + urlToWaitFor + ",  Exception: " + e.getMessage());
			return e.getMessage();
		}
	}
	/**********************************************************************************
	 **ALERT & POPUPS METHODS
	 **********************************************************************************/
	public void closePopups(By locator) throws InterruptedException {
		try {
			List<WebElement> elements = this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			for (WebElement element : elements) {
				if (element.isDisplayed()) {
					element.click();
					this.wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
					System.out.println("The popup has been closed Successfully!");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception! - could not close the popup!, Exception: " + e.toString());
			throw (e);
		}
	}

	public boolean checkPopupIsVisible() {
		try {
			@SuppressWarnings("unused")
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			System.out.println("A popup has been found!");
			return true;
		} catch (Exception e) {
			System.err.println("Error came while waiting for the alert popup to appear. " + e.getMessage());
		}
		return false;
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void closeAlertPopupBox() throws AWTException, InterruptedException {
		try {
			Alert alert = this.wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (UnhandledAlertException f) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			System.out.println("Unable to close the popup");
			Assert.fail("Unable to close the popup, Exception: " + e.getMessage());
		}
	}
	
	
	public String getCurrentDayOfWeek() {
		LocalDate currentdate = LocalDate.now();
		DayOfWeek dayOfWeek = currentdate.getDayOfWeek();
		String day = dayOfWeek.toString();
		System.out.println("Current day: " + CamelCase(day.toLowerCase()));
		return CamelCase(day.toLowerCase());
	}
	
	
	public String getCurrentMonth() {
		LocalDate currentdate = LocalDate.now();
		Month currentMonth = currentdate.getMonth();
		String month = currentMonth.toString().substring(0,3);
		return CamelCase(month.toLowerCase());
	}
	
	public String getCurrentDay() {
		LocalDate currentdate = LocalDate.now();
		int currentDay = currentdate.getDayOfMonth();
		String currentDayString = String.valueOf(currentDay);
		return currentDayString+"th";
	}
	
	public String getCurrentYear() {
		LocalDate currentdate = LocalDate.now();
		int currentYear = currentdate.getYear();
		String currentYearString = String.valueOf(currentYear);
		return currentYearString;
	}
	
	public static String CamelCase(String str)
    {
        // Capitalize first letter of string
        str = str.substring(0, 1).toUpperCase()
              + str.substring(1);
  
        // Convert to StringBuilder
        StringBuilder builder
            = new StringBuilder(str);
  
        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {
  
            // Check char is underscore
            if (builder.charAt(i) == '_') {
  
                builder.deleteCharAt(i);
                builder.replace(
                    i, i + 1,
                    String.valueOf(
                        Character.toUpperCase(
                            builder.charAt(i))));
            }
        }
  
        // Return in String type
        return builder.toString();
    }
	
	
	public void waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
		    .executeScript("return document.readyState")));
	}
	
}
