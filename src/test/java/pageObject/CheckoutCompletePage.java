package pageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import factory.BaseClass;
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
	    WebElement backHomeBtn = BaseClass.getWait().until(
	        ExpectedConditions.elementToBeClickable(By.id("back-to-products"))
	    );
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backHomeBtn);
	    BaseClass.getWait().until(ExpectedConditions.urlContains("inventory.html"));
	}
}