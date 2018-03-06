package akamai.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SearchJobPage extends BasePage<SearchJobPage> {

    public static final String SEARCH_PAGE_ADDRESS = "http://akamaijobs.referrals.selectminds.com/";

    @FindBy(id = "keyword")
    private WebElement jobKeywordInput;

    @FindBy(css = "div#jLocInputHldr")
    private WebElement locationInput;

    @FindBy(css = "div.chzn-drop li")
    private List<WebElement> locationOptions;

    @FindBy(className = "search_btn")
    private WebElement searchButton;

    @FindBy(css = ".jResultsActive .job_list_row")
    private List<WebElement> searchResultItems;

    @FindBy(css = "#job_no_results_list_hldr")
    private WebElement noResultsPanel;

    public SearchJobPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getJobKeywordInput() {
        return jobKeywordInput;
    }

    public WebElement getLocationInput() {
        return locationInput;
    }

    public List<WebElement> getLocationOptions() {
        return locationOptions;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getNoResultsPanel() {
        waitUntilSearchIsFinished();
        return noResultsPanel;
    }

    public List<SearchResultItem> getSearchResultItems() {
        waitUntilSearchIsFinished();
        return searchResultItems.stream()
                .map(SearchResultItem::new)
                .collect(Collectors.toList());
    }

    public SearchJobPage fillFindJobByKeywordField(String keyword) {
        getJobKeywordInput().sendKeys(keyword);
        return this;
    }

    public SearchJobPage selectLocation(String location) {
        getLocationInput().click();
        getLocationOptions().stream()
                .filter(e -> e.getText().contains(location))
                .findFirst()
                .ifPresent(WebElement::click);
        return this;
    }

    public SearchJobPage clickSearchButton() {
        getSearchButton().click();
        return this;
    }

    private SearchJobPage waitUntilSearchIsFinished() {
        wait.until(visibilityOfElementLocated(By.cssSelector(".jResultsActive[style='display: block;']")));
        return this;
    }

    public class SearchResultItem {
        WebElement webElement;

        SearchResultItem(WebElement webElement) {
            this.webElement = webElement;
        }

        public String getJobTitle() {
            return webElement.findElement(By.cssSelector(".job_link")).getText();
        }

        public String getLocation() {
            return webElement.findElement(By.cssSelector(".location")).getText();
        }
    }
}
