package pageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SortPage extends BasePage {

    public SortPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "product_sort_container")
    WebElement sortDropdown;

    @FindBy(className = "inventory_item_name")
    List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    List<WebElement> productPrices;

    public void clickSortDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)).click();
    }

    public void selectSortOption(String visibleText) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        select.selectByVisibleText(visibleText);
    }

    public String getSelectedSortOption() {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOf(sortDropdown)));
        return select.getFirstSelectedOption().getText();
    }

    // Verifies the products currently on screen are actually in the order
    // this sort option claims, not just that the dropdown shows it selected.
//    public boolean isSortWorkingCorrectly(String sortOption) {
//        List<String> names = productNames.stream()
//                .map(WebElement::getText)
//                .collect(Collectors.toList());
//        List<Double> prices = productPrices.stream()
//                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
//                .collect(Collectors.toList());

        public boolean isSortWorkingCorrectly(String sortOption) {
            List<String> names = new ArrayList<String>();
            for (WebElement element : productNames) {
                names.add(element.getText());
            }

            List<Double> prices = new ArrayList<Double>();
            for (WebElement element : productPrices) {
                String priceText = element.getText().replace("$", "");
                prices.add(Double.parseDouble(priceText));
            }
        switch (sortOption) {
            case "Name (A to Z)":
                return isAscending(names);
            case "Name (Z to A)":
                return isDescending(names);
            case "Price (low to high)":
                return isAscendingNum(prices);
            case "Price (high to low)":
                return isDescendingNum(prices);
            default:
                throw new IllegalArgumentException("Unknown sort option: " + sortOption);
        }
    }

    private boolean isAscending(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareToIgnoreCase(list.get(i + 1)) > 0) return false;
        }
        return true;
    }

    private boolean isDescending(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareToIgnoreCase(list.get(i + 1)) < 0) return false;
        }
        return true;
    }

    private boolean isAscendingNum(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) return false;
        }
        return true;
    }

    private boolean isDescendingNum(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i + 1)) return false;
        }
        return true;
    }
}