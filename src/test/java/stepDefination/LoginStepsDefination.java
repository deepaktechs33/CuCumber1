package stepDefination;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.HomePage;
import pageObject.LoginPage;




public class LoginStepsDefination {

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;

	@Given("user is on the Swag Labs login page")
	public void user_is_on_the_swag_labs_login_page() {
		driver = BaseClass.getDriver();
		loginPage = new LoginPage(driver);
		assertTrue("App logo is not displayed on the login page", loginPage.isLogoDisplayed());
	}

	@When("user logs in with username {string} and password {string}")
	public void user_logs_in_with_username_and_password(String username, String password) {
		loginPage.login(username, password);
	}

	@Then("user should be navigated to the products page")
	public void user_should_be_navigated_to_the_products_page() {
		homePage = new HomePage(driver);
		assertTrue("Cart icon not visible — login likely failed", homePage.isCartIconDisplayed());
	}

	@Then("the page title should be {string}")
	public void the_page_title_should_be(String expectedTitle) {
		homePage = new HomePage(driver);
		assertEquals(expectedTitle, homePage.getPageTitle());
	}

	@Then("user should see an error message {string}")
	public void user_should_see_an_error_message(String expectedError) {
		assertTrue("Error message was not displayed", loginPage.isErrorDisplayed());
		assertTrue(
				"Error text mismatch. Actual: " + loginPage.getErrorMessage(),
				loginPage.getErrorMessage().contains(expectedError));
	}
	
	


}

