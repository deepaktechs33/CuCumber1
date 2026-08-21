package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "title")
    WebElement lblPageTitle;

    @FindBy(id = "checkout")
    WebElement btnCheckout;

    @FindBy(className = "inventory_item_name")
    List<WebElement> cartItemNames;

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOf(lblPageTitle)).getText();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCheckout)).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
    }

    // Checks whether a product with this exact name is listed on the cart page
    public boolean isProductPresent(String productName) {
        List<WebElement> items;
        try {
          
            items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.className("inventory_item_name")
            ));
        } catch (Exception e) {
            return false; 
        }

        return items.stream().anyMatch(e -> e.getText().equalsIgnoreCase(productName));
    }
 // Same slug logic as HomePage.addToCartButton, but SauceDemo's cart-page
 // remove buttons use a "remove-" id prefix instead of "add-to-cart-".
 private By removeButton(String productName) {
     String id = "remove-" +
             productName.toLowerCase()
                     .replace(" ", "-")
                     .replace("(", "")
                     .replace(")", "");
     return By.id(id);
 }

 public void removeProductFromCart(String productName) {
     By btnLocator = removeButton(productName);
     WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
     btn.click();

     try {
         // Confirms the item's row (and its Remove button) actually left the DOM.
         wait.until(ExpectedConditions.invisibilityOfElementLocated(btnLocator));
     } catch (org.openqa.selenium.TimeoutException firstAttemptFailed) {
         WebElement retryBtn = wait.until(ExpectedConditions.presenceOfElementLocated(btnLocator));
         ((org.openqa.selenium.JavascriptExecutor) driver)
                 .executeScript("arguments[0].click();", retryBtn);
         wait.until(ExpectedConditions.invisibilityOfElementLocated(btnLocator));
     }
 }

 // Number of product rows currently listed on the cart page.
 public int getCartItemCount() {
     try {
         return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                 By.className("inventory_item_name")
         )).size();
     } catch (Exception e) {
         return 0;
     }
 }
}