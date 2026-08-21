package stepDefination;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import factory.BaseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.SortPage;



public class SortStepsDefination {

    WebDriver driver = BaseClass.getDriver();
    SortPage sortPage = new SortPage(driver);

    @When("the user opens the sort filter")
    public void the_user_opens_the_sort_filter() {
        sortPage.clickSortDropdown();
    }

    @When("the user selects the {string} sort option")
    public void the_user_selects_the_sort_option(String sortOption) {
        sortPage.selectSortOption(sortOption);
    }

    @Then("the products should be sorted by {string}")
    public void the_products_should_be_sorted_by(String sortOption) {
        Assert.assertEquals(
                sortPage.getSelectedSortOption(),
                sortOption,
                "Dropdown did not show the selected sort option"
        );
        Assert.assertTrue(
                sortPage.isSortWorkingCorrectly(sortOption),
                "Products are not actually sorted by " + sortOption
        );
    }
}