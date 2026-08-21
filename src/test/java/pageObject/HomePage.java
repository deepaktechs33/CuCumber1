package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "title")
    WebElement lblPageTitle;

    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;
    @FindBy(className = "shopping_cart_badge")
    WebElement cartBadge;

    public boolean isCartIconDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(lblPageTitle)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOf(lblPageTitle)
        ).getText();
    }

    private By addToCartButton(String productName) {
        String id = "add-to-cart-" +
                productName.toLowerCase()
                        .replace(" ", "-")
                        .replace("(", "")
                        .replace(")", "");
        return By.id(id);
    }

    

    public void addProductToCart(String productName) {
        By btnLocator = addToCartButton(productName);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        btn.click();

        try {
            // Confirms the click actually took effect before moving on.
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
        } catch (org.openqa.selenium.TimeoutException firstAttemptFailed) {
            // Same click-registers-but-doesn't-fire issue as goToCart().
            // Re-locate the button (page state may have shifted) and retry via JS.
            WebElement retryBtn = wait.until(ExpectedConditions.presenceOfElementLocated(btnLocator));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", retryBtn);
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
        }
    }
 // Adds each product in the list one at a time, reusing the same
 // click-and-verify logic (with JS-click fallback) as addProductToCart.
 public void addProductsToCart(List<String> productNames) {
     for (String productName : productNames) {
         addProductToCart(productName);
     }
 }

 // Convenience overload for call sites that prefer literals over a List,
 // e.g. addProductsToCart("Sauce Labs Backpack", "Sauce Labs Bike Light").
 public void addProductsToCart(String... productNames) {
     for (String productName : productNames) {
         addProductToCart(productName);
     }
 }
 

 // Reads the cart badge's numeric count. Returns 0 if the badge isn't
 // present (i.e. the cart is empty) rather than throwing.
 public int getCartItemCount() {
     try {
         String countText = wait.until(ExpectedConditions.visibilityOf(cartBadge)).getText();
         return Integer.parseInt(countText.trim());
     } catch (Exception e) {
         return 0;
     }
 }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();

        try {
            wait.until(ExpectedConditions.urlContains("cart.html"));
        } catch (org.openqa.selenium.TimeoutException firstAttemptFailed) {
            // The click registered but navigation didn't happen (seen with
            // Chrome/Selenium version drift). Retry once with a JS-dispatched
            // click, which bypasses native mouse-event quirks.
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", cartIcon);
            wait.until(ExpectedConditions.urlContains("cart.html"));
        }
    }
}
