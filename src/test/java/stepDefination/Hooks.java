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
	WebDriver driver;
	Properties p;

	@Before
	public void setup() throws IOException {
		driver = BaseClass.initilizeBrowser();
		p = BaseClass.getProperties();
		driver.get(p.getProperty("appURL"));
	}

	@After(order=0)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		BaseClass.reset();
	}


	@After(order =1)
	public void addScreenshot(Scenario scenario) {

		if (scenario.isFailed()) {

			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			File destination = new File(
					System.getProperty("user.dir")
					+ "/target/screenshots/"
					+ scenario.getName()
					.replaceAll("[^a-zA-Z0-9.-]", "_")
					+ ".png"
					);

			try {

				Files.createDirectories(
						destination.getParentFile().toPath()
						);

				Files.copy(
						source.toPath(),
						destination.toPath(),
						StandardCopyOption.REPLACE_EXISTING
						);

				System.out.println(
						"Screenshot saved at: "
								+ destination.getAbsolutePath()
						);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}