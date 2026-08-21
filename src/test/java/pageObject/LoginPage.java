package pageObject;

import org.openqa.selenium.WebDriver;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "user-name")
	WebElement txtUsername;

	@FindBy(id = "password")
	WebElement txtPassword;

	@FindBy(id = "login-button")
	WebElement btnLogin;

	@FindBy(css = "h3[data-test='error']")
	WebElement lblErrorMessage;

	@FindBy(className = "login_logo")
	WebElement lblAppLogo;

	public void enterUsername(String username) {
		txtUsername.clear();
		txtUsername.sendKeys(username);
	}

	public void enterPassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}

	public void clickLogin() {
		btnLogin.click();
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLogin();
	}

	public String getErrorMessage() {
		return lblErrorMessage.getText();
	}

	public boolean isErrorDisplayed() {
		try {
			return lblErrorMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isLogoDisplayed() {
		try {
			return lblAppLogo.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}