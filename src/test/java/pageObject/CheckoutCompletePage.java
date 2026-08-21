package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {

	public CheckoutCompletePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(className = "complete-header")
	WebElement lblConfirmationHeader;

	@FindBy(id = "back-to-products")
	WebElement btnBackHome;

	public String getConfirmationHeader() {
		return wait.until(ExpectedConditions.visibilityOf(lblConfirmationHeader)).getText();
	}

	public void clickBackHome() {

	    wait.until(ExpectedConditions.elementToBeClickable(btnBackHome)).click();

	    wait.until(ExpectedConditions.urlContains("inventory.html"));
	}
}
