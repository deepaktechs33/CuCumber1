package stepDefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import factory.BaseClass;
import pageObject.FooterPage;
import pageObject.HomePage;
import pageObject.LoginPage;

public class FooterStepsDefination {

    WebDriver driver = BaseClass.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    HomePage homePage = new HomePage(driver);
    FooterPage footerPage = new FooterPage(driver);


    @When("the user scrolls to the footer")
    public void the_user_scrolls_to_the_footer() {
        footerPage.scrollToFooter();
    }

    @Then("the footer should show the copyright and policy text")
    public void the_footer_should_show_the_copyright_and_policy_text() {
        Assert.assertTrue(footerPage.isCopyrightTextDisplayed(), "Copyright text not displayed in footer");
        Assert.assertTrue(footerPage.isPolicyTextDisplayed(), "Policy text (Terms of Service / Privacy Policy) not displayed in footer");
    }

    @Then("the social media links should be displayed")
    public void the_social_media_links_should_be_displayed() {
        Assert.assertTrue(footerPage.areSocialMediaLinksDisplayed(), "Social media links are not displayed in footer");
    }
}
