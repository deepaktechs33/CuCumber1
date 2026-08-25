package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	// one WebDriver per thread — required once scenarios run in parallel
	private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
	// one WebDriverWait per thread, tied to that thread's own driver
	private static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();

	public static WebDriver initilizeBrowser() throws IOException {
		Properties p = getProperties();
		String executionEnv = p.getProperty("execution_env");
		String browser = p.getProperty("browser").toLowerCase();
		String os = p.getProperty("os").toLowerCase();
		boolean isHeadless = Boolean.parseBoolean(p.getProperty("headless", "false"));

		WebDriver driver;

		if (executionEnv.equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			switch (os) {
			case "windows":
				capabilities.setPlatform(Platform.WINDOWS);
				break;
			case "mac":
				capabilities.setPlatform(Platform.MAC);
				break;
			case "linux":
				capabilities.setPlatform(Platform.LINUX);
				break;
			default:
				throw new IllegalArgumentException("No matching OS configured: " + os);
			}
			switch (browser) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				throw new IllegalArgumentException("No matching browser configured: " + browser);
			}
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:0000/"), capabilities);
			} catch (Exception e) {
				throw new IOException("Failed to start RemoteWebDriver", e);
			}
		} else if (executionEnv.equalsIgnoreCase("local")) {
			switch (browser) {
			case "chrome":
				ChromeOptions options = new ChromeOptions();
				if (isHeadless) {
					options.addArguments("--headless=new");
				}
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				driver = new ChromeDriver(options);
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				throw new IllegalArgumentException("No matching browser configured: " + browser);
			}
		} else {
			throw new IllegalArgumentException("No matching execution_env configured: " + executionEnv);
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		DRIVER.set(driver);
		return driver;
	}

	public static WebDriver getDriver() {
		return DRIVER.get();
	}

	public static WebDriverWait getWait() {
		WebDriver driver = DRIVER.get();
		if (driver == null) {
			throw new IllegalStateException("Driver is not initialized. Call initilizeBrowser() first.");
		}
		if (WAIT.get() == null) {
			WAIT.set(new WebDriverWait(driver, Duration.ofSeconds(10)));
		}
		return WAIT.get();
	}

	public static Properties getProperties() throws IOException {
		try (FileReader file = new FileReader(
				System.getProperty("user.dir") + "/src/test/resources/config.properties")) {
			Properties p = new Properties();
			p.load(file);
			return p;
		}
	}

	public static void quitDriver() {
		WebDriver driver = DRIVER.get();
		if (driver != null) {
			driver.quit();
		}
		DRIVER.remove();
		WAIT.remove();
	}

	// kept so any existing calls to reset() still compile — now also quits the browser
	public static void reset() {
		quitDriver();
	}
}