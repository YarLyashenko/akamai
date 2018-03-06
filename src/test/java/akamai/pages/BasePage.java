package akamai.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class BasePage<T> {
    public static final int TIMEOUT = 30;

    protected WebDriver driver;
    protected WebDriverWait wait;

    BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        waitForPageIsLoaded();
    }

    private void waitForPageIsLoaded() {
        if (driver instanceof JavascriptExecutor) {
            wait.until((ExpectedCondition<Boolean>) driver ->
                    ((JavascriptExecutor) driver).executeScript(
                            "return document.readyState").equals("complete"));
        }
    }
}
