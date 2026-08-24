package pageObject;



import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HamburgerPage extends BasePage {

    public HamburgerPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "react-burger-menu-btn")
    WebElement hamburgerIcon;

    @FindBy(id = "inventory_sidebar_link")
    WebElement lnkAllItems;

    @FindBy(id = "about_sidebar_link")
    WebElement lnkAbout;

    @FindBy(id = "logout_sidebar_link")
    WebElement lnkLogout;

    @FindBy(id = "reset_sidebar_link")
    WebElement lnkResetAppState;

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(hamburgerIcon)).click();

        try {
            wait.until(ExpectedConditions.visibilityOf(lnkAllItems));
        } catch (org.openqa.selenium.TimeoutException firstAttemptFailed) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", hamburgerIcon);
            wait.until(ExpectedConditions.visibilityOf(lnkAllItems));
        }
    }
    private WebElement getMenuElement(String optionName) {
        switch (optionName) {
            case "All Items":
                return lnkAllItems;
            case "About":
                return lnkAbout;
            case "Logout":
                return lnkLogout;
            case "Reset App State":
                return lnkResetAppState;
            default:
                throw new IllegalArgumentException("Unknown menu option: " + optionName);
        }
    }

    public boolean isMenuOptionDisplayed(String optionName) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(getMenuElement(optionName))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickMenuOption(String optionName) {
        WebElement menuOption = wait.until(ExpectedConditions.elementToBeClickable(getMenuElement(optionName)));
        ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0]; setTimeout(function(){ el.click(); }, 0);", menuOption);
    }
}