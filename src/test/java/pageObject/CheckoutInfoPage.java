package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutInfoPage extends BasePage {

	public CheckoutInfoPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(className = "title")
	WebElement lblPageTitle;

	@FindBy(id = "first-name")
	WebElement txtFirstName;

	@FindBy(id = "last-name")
	WebElement txtLastName;

	@FindBy(id = "postal-code")
	WebElement txtZip;

	@FindBy(id = "continue")
	WebElement btnContinue;

	public String getPageTitle() {
		return wait.until(ExpectedConditions.visibilityOf(lblPageTitle)).getText();
	}

	public void enterDetails(String firstName, String lastName, String zip) {
		wait.until(ExpectedConditions.visibilityOf(txtFirstName));

		txtFirstName.clear();
		txtFirstName.sendKeys(firstName);
		txtLastName.clear();
		txtLastName.sendKeys(lastName);
		txtZip.clear();
		txtZip.sendKeys(zip);
	}

	public void clickContinue() {
		wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();

		try {
			wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
		} catch (TimeoutException firstAttemptFailed) {
		
			WebElement retryBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
					org.openqa.selenium.By.id("continue")
			));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryBtn);
			wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
		}
	}
}
