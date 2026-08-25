package stepDefination;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import io.cucumber.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setup() throws IOException {
		WebDriver driver = BaseClass.initilizeBrowser();
		Properties p = BaseClass.getProperties();
		driver.get(p.getProperty("appURL"));
	}

	// runs SECOND (higher order runs first for @After) — driver is already quit by now
	@After(order = 0)
	public void tearDown() {
		BaseClass.quitDriver();
	}

	// runs FIRST — driver is still alive here, so the screenshot works
	@After(order = 1)
	public void addScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			WebDriver driver = BaseClass.getDriver();
			if (driver == null) {
				return;
			}
			TakesScreenshot ts = (TakesScreenshot) driver;

			// Embed directly into the Cucumber report — shows inline next to the failing step
			byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshotBytes, "image/png", scenario.getName());

			// Keep the existing file copy too, for anyone who wants the raw PNG
			File source = ts.getScreenshotAs(OutputType.FILE);
			File destination = new File(
					System.getProperty("user.dir")
					+ "/target/screenshots/"
					+ scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_")
					+ "_" + Thread.currentThread().getId()
					+ ".png"
			);
			try {
				Files.createDirectories(destination.getParentFile().toPath());
				Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}