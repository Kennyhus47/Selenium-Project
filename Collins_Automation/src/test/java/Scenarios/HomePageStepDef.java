package Scenarios;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import com.app.pages.HomePage;
import com.app.utils.BaseClass;
import com.app.utils.Log;
import com.app.utils.PropertyReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomePageStepDef extends BaseClass {

	HomePage homePage = new HomePage(driver);

	@Given("User is on Application home page")
	public void user_is_on_application_home_page() {
		Log.info("User is on Home page of application");
	}

	@Then("I Verify the webpage displays current date and time")
	public void i_verify_the_webpage_displays_current_date_and_time() throws InterruptedException {
		String systemCurrentDateAndTime= getCurrentDayOfWeek() + ", "+ getCurrentMonth() + " "+ getCurrentDay()+ " " + getCurrentYear();
		String currentDateAndTime = homePage.getCurrectDateAndTime();
		assertEquals(currentDateAndTime, systemCurrentDateAndTime, "Current data should be displayed and matched");
		Log.info("Verify the current date and time");
	}

	@When("I Navigate to the Sport menu")
	public void i_navigate_to_the_sport_menu() throws InterruptedException, IOException {
		homePage.clickOnSportsMenu();
	}

	@Then("I Verify primary navigation colour Sport and secondary navigation Football are same")
	public void i_verify_primary_navigation_colour_sport_and_secondary_navigation_football_are_same() throws InterruptedException {
		String sportMenuColor= homePage.getSportsMenuColor();
		String FootballSubMenuColor= homePage.getFootballSunMenuColor();
		assertEquals(sportMenuColor, FootballSubMenuColor, "Sports menu and sub menu color should be same");
		
	}

	@When("I Click on the Football sub navigation item")
	public void i_click_on_the_football_sub_navigation_item() throws InterruptedException, IOException {
		homePage.clickOnFootballSunMenu();
		
	}

	@When("I Click on the first article displayed and traverse to the gallery image")
	public void i_click_on_the_first_article_displayed_and_traverse_to_the_gallery_image() throws InterruptedException, IOException {
		homePage.clickOnFirstArticle();
	}

	@When("I Click on gallery icon appearing on the right-hand side corner")
	public void i_click_on_gallery_icon_appearing_on_the_right_hand_side_corner() throws InterruptedException, IOException {
		homePage.clickOnFirstGallery();

	}

	@Then("I Verify previous and next arrows buttons")
	public void i_verify_previous_and_next_arrows_buttons() throws InterruptedException {
		homePage.isNextButtonDisplayed();
		homePage.isPreviousButtonDisplayed();
		assertTrue(homePage.isNextButtonDisplayed(), "Next button should be displayed");
		assertTrue(homePage.isPreviousButtonDisplayed(), "Previous button should be displayed");
		
	}

	@Then("I Navigate to a video embedded within the article")
	public void i_navigate_to_a_video_embedded_within_the_article() throws InterruptedException {
		homePage.navigateToEmbeddedVideoScreen();
	}

	
	@When("I Click the full screen button on the right-hand corner of the video")
	public void i_click_the_full_screen_button_on_the_right_hand_corner_of_the_video() throws InterruptedException {
		homePage.clickOnFullScreenIcon();
	}
	
	
	@When("I Click on the Facebook share icon and verify it opens Facebook modal dialog")
	public void i_click_on_the_facebook_share_icon_and_verify_it_opens_facebook_modal_dialog() throws InterruptedException, IOException {
			homePage.clickOnFacbookIcon();
	}
	

	@When("I Click on collapse icon on video")
	public void i_click_on_collapse_icon_on_video() throws InterruptedException {
		homePage.clickOnFullScreenIcon();
	}

	@When("I Get {string} team points")
	public void i_get_team_points(String teamName) {
		System.out.println("Points: "+ homePage.getTeamPoints(teamName));
		
	}

	@When("I Get {string} team position")
	public void i_get_team_position(String teamName) {
		System.out.println("Points: "+ homePage.getTeamPosition(teamName));
		
	}
	
	
	@When("I Click on close icon")
	public void i_click_on_close_icon() throws InterruptedException {
		homePage.clickOnCloseIcon();
	}

}