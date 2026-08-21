package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutOverviewPage extends BasePage {

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(className = "title")
	WebElement lblPageTitle;

	@FindBy(id = "finish")
	WebElement btnFinish;

	public String getPageTitle() {
		return wait.until(ExpectedConditions.visibilityOf(lblPageTitle)).getText();
	}

	public void clickFinish() {
		wait.until(ExpectedConditions.elementToBeClickable(btnFinish)).click();

		try {
			wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
		} catch (TimeoutException firstAttemptFailed) {
			// Same click-registers-but-doesn't-navigate issue seen with the
			// cart icon, add-to-cart button, and continue button - retry via JS.
			WebElement retryBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryBtn);
			wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
		}
	}
}
