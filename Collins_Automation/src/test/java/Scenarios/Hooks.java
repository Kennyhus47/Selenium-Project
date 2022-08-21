package Scenarios;

import java.util.Random;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.app.utils.BaseClass;
import com.app.utils.PropertyReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;

public class Hooks extends BaseClass {

	public PropertyReader propertyReader;
	public static String ApplicationURL;
	public static String browserType;
	public static String url;
	public static String server;

	enum BrowserType {
		Firefox, Chrome, Edge, Safari
	}

	@Before
	public void openBrowser() throws InterruptedException {

		propertyReader = new PropertyReader();
		browserType = propertyReader.readApplicationFile("BROWSER");
		server = propertyReader.readApplicationFile("SERVER");

		if (BrowserType.Firefox.toString().equals(browserType)) {
			driver = initDriver(browserType);

		} else if (BrowserType.Edge.toString().equals(browserType)) {
			driver = initDriver(browserType);

		} else if (BrowserType.Chrome.toString().equals(browserType)) {
			driver = initDriver(browserType);
		} else if (BrowserType.Safari.toString().equals(browserType)) {
			driver = initDriver(browserType);
		} else {
			driver = initDriver("Firefox");
		}
		switch (server) {
		case "qa":
			String QA_URL = propertyReader.readApplicationFile("QA_URL");
			url = QA_URL;
			break;
		case "dev":
			String Dev_URL = propertyReader.readApplicationFile("Dev_URL");
			url = Dev_URL;
			break;
		case "prod":
			String Prod_URL = propertyReader.readApplicationFile("Prod_URL");
			url = Prod_URL;
			break;
		}
		driver.get(url);
	}

	/*
	 * @AfterStep public void afterSteps(Scenario scenario) {
	 * 
	 * final byte[] screenshot = ((TakesScreenshot)
	 * driver).getScreenshotAs(OutputType.BYTES); scenario.attach(screenshot,
	 * "image/png", scenario.getName()); }
	 * 
	 */

	@After
	public void tearDown(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", scenario.getName());
			}
		} catch (Exception e) {
			System.out.println(e + "failed to take screenshot");
		}

		//driver.close();
		driver.quit();

	}

	public String randomString(int len) {

		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	
	

}
