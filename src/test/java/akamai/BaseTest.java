package akamai;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public abstract class BaseTest {
    protected static WebDriver driver;

    @BeforeAll
    protected static void driverSetup() {

        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", new File("src/test/resources/geckodriver.exe").getAbsolutePath());
                driver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", new File("src/test/resources/chromedriver.exe").getAbsolutePath());
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
    }

    @AfterAll
    protected static void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    protected void tearDown() {
        driver.manage().deleteAllCookies();
    }
}
