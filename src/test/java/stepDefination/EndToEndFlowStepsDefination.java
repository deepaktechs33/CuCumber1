package stepDefination;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.CartPage;
import pageObject.CheckoutCompletePage;
import pageObject.CheckoutInfoPage;
import pageObject.CheckoutOverviewPage;
import pageObject.HomePage;

public class EndToEndFlowStepsDefination {

    WebDriver driver = BaseClass.getDriver();
    HomePage homePage = new HomePage(driver);
    CartPage cartPage = new CartPage(driver);
    CheckoutInfoPage checkoutInfoPage = new CheckoutInfoPage(driver);
    CheckoutOverviewPage checkoutOverviewPage = new CheckoutOverviewPage(driver);
    CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);

    @When("the user adds {string} to the cart")
    public void the_user_adds_to_the_cart(String productName) {
        homePage.addProductToCart(productName);
    }

    @When("the user goes to the cart")
    public void the_user_goes_to_the_cart() {
        homePage.goToCart();
    }

    @Then("the product {string} should be present in the cart")
    public void the_product_should_be_present_in_the_cart(String productName) {
        assertTrue(
                "Product '" + productName + "' was not found in the cart",
                cartPage.isProductPresent(productName)
        );
    }

    @When("the user clicks on the checkout button")
    public void the_user_clicks_on_the_checkout_button() {
        cartPage.clickCheckout();
    }

    @When("the user enters {string} {string} {string} as checkout details")
    public void the_user_enters_checkout_details(String firstName, String lastName, String zip) {
        checkoutInfoPage.enterDetails(firstName, lastName, zip);
    }

    @And("the user clicks the continue button")
    public void the_user_clicks_the_continue_button() {
        checkoutInfoPage.clickContinue();
    }

    @Then("the user should be navigated to the checkout overview page")
    public void the_user_should_be_navigated_to_the_checkout_overview_page() {
        assertEquals(
                "Checkout overview page did not load as expected",
                "Checkout: Overview",
                checkoutOverviewPage.getPageTitle()
        );
    }

    @When("the user clicks the finish button")
    public void the_user_clicks_the_finish_button() {
        checkoutOverviewPage.clickFinish();
    }

    @Then("the order confirmation should be displayed")
    public void the_order_confirmation_should_be_displayed() {
        assertEquals(
                "Order confirmation header did not match the expected value",
                "Thank you for your order!",
                checkoutCompletePage.getConfirmationHeader()
        );
    }

    @When("the user clicks on back to home button")
    public void the_user_clicks_on_back_to_home_button() {
        checkoutCompletePage.clickBackHome();
    }

    @Then("the user should be back on the products page")
    public void the_user_should_be_back_on_the_products_page() {
        assertEquals(
                "User was not navigated back to the products page",
                "Products",
                homePage.getPageTitle()
        );
    }
}