package stepDefination;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.CartPage;
import pageObject.HomePage;

public class AddCartRemoveStepDefination {

    WebDriver driver = BaseClass.getDriver();
    HomePage homePage = new HomePage(driver);
    CartPage cartPage = new CartPage(driver);

    @Given("the user has added {string} to the cart")
    public void the_user_has_added_to_the_cart(String productName) {
        homePage.addProductToCart(productName);
    }

    @When("the user navigates to the cart page")
    public void the_user_navigates_to_the_cart_page() {
        homePage.goToCart();
    }

    @When("the user removes {string} from the cart")
    public void the_user_removes_from_the_cart(String productName) {
        cartPage.removeProductFromCart(productName);
    }

    @Then("the cart should contain {string}")
    public void the_cart_should_contain(String productName) {
        Assert.assertTrue(
                productName + " was not found in the cart",
                cartPage.isProductPresent(productName)
        );
    }

    @Then("the cart should not contain {string}")
    public void the_cart_should_not_contain(String productName) {
        Assert.assertFalse(
                productName + " was still found in the cart after removal",
                cartPage.isProductPresent(productName)
        );
    }

    @Then("the cart item count should be {int}")
    public void the_cart_item_count_should_be(int expectedCount) {
        Assert.assertEquals(
                "Cart page item count did not match expected value",
                expectedCount,
                cartPage.getCartItemCount()
        );
    }

    @When("the user adds the following products to the cart:")
   public void the_user_adds_the_following_products_to_the_cart(DataTable dataTable) {
       List<String> products = dataTable.asList(String.class);
      homePage.addProductsToCart(products);
     Assert.assertEquals(
           "Cart badge count does not match the number of products added",
              products.size(),   
              homePage.getCartItemCount()
                                          
      );
//     Assert.assertEquals(
//    	        "Cart badge count does not match the number of products added",
//    	        999,                          // ← changed from products.size() to a wrong number
//    	        homePage.getCartItemCount()
//    	);
    }
}