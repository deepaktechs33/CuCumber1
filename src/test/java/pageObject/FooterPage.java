package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FooterPage extends BasePage {

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "footer_copy")
    WebElement footerCopyText;

    @FindBy(className = "social_twitter")
    WebElement twitterLink;

    @FindBy(className = "social_facebook")
    WebElement facebookLink;

    @FindBy(className = "social_linkedin")
    WebElement linkedinLink;

    // Scrolls the footer into view so its contents are actually rendered/visible before assertions run.
    public void scrollToFooter() {
        WebElement footer = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.className("footer"))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footer);
    }

    public String getFooterText() {
        return wait.until(ExpectedConditions.visibilityOf(footerCopyText)).getText();
    }

    // SauceDemo renders the copyright line and the policy links inside the same footer_copy element,
    // e.g. "© 2026 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy"
    public boolean isCopyrightTextDisplayed() {
        try {
            return getFooterText().toLowerCase().contains("all rights reserved");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPolicyTextDisplayed() {
        try {
            String text = getFooterText();
            return text.contains("Terms of Service") && text.contains("Privacy Policy");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areSocialMediaLinksDisplayed() {
        try {
            boolean twitter = wait.until(ExpectedConditions.visibilityOf(twitterLink)).isDisplayed();
            boolean facebook = wait.until(ExpectedConditions.visibilityOf(facebookLink)).isDisplayed();
            boolean linkedin = wait.until(ExpectedConditions.visibilityOf(linkedinLink)).isDisplayed();
            return twitter && facebook && linkedin;
        } catch (Exception e) {
            return false;
        }
    }
}
