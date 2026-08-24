package stepDefination;

import java.util.List;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import factory.BaseClass;
import pageObject.HamburgerPage;
import pageObject.LoginPage;

public class HamburgerStepsDefination {

    WebDriver driver = BaseClass.getDriver();
    HamburgerPage hamburgerPage = new HamburgerPage(driver);

    @When("the user opens the hamburger menu")
    public void the_user_opens_the_hamburger_menu() {
        hamburgerPage.openMenu();
    }

    @Then("the menu should display the following options:")
    public void the_menu_should_display_the_following_options(DataTable dataTable) {
        List<String> options = dataTable.asList(String.class);
        for (String option : options) {
            Assert.assertTrue(
                    hamburgerPage.isMenuOptionDisplayed(option),
                    "'" + option + "' menu option not displayed"
            );
        }
    }

    @When("the user clicks on the {string} menu option")
    public void the_user_clicks_on_the_menu_option(String optionName) {
        hamburgerPage.clickMenuOption(optionName);
    }

    @When("the user navigates back to the previous page")
    public void the_user_navigates_back_to_the_previous_page() {
        // Wait for the "About" click's navigation to actually land on saucelabs.com
        // before issuing our own navigation — prevents the two navigations from racing
        // in the same tab, which was leaving the login form blank/unsubmitted.
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("saucelabs.com"));

        driver.navigate().to("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        new WebDriverWait(driver, Duration.ofSeconds(25))
                .until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
    }

    @Then("the user should be redirected to the login page")
    public void the_user_should_be_redirected_to_the_login_page() {
        BaseClass.getWait().until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://www.saucedemo.com/",
                "User was not redirected to the login page after logout"
        );
    }
}