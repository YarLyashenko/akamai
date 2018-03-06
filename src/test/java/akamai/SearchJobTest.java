package akamai;

import akamai.pages.SearchJobPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static akamai.pages.SearchJobPage.SEARCH_PAGE_ADDRESS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchJobTest extends BaseTest {

    @DisplayName("Feature: Unlogged customer is able to search for a job")
    @Test
    void findJobInLocationPositive() {
        String location = "Krakow, Poland";
        String jobKeyword = "Test";

        driver.get(SEARCH_PAGE_ADDRESS);
        SearchJobPage searchJobPage = new SearchJobPage(driver);

        searchJobPage.fillFindJobByKeywordField(jobKeyword)
                .selectLocation(location)
                .clickSearchButton();

        List<SearchJobPage.SearchResultItem> searchResultItems = searchJobPage.getSearchResultItems();

        assertFalse(searchResultItems.isEmpty(), "Search result list is empty.");
        assertTrue(searchResultItems.stream().anyMatch(
                item -> item.getJobTitle().contains(jobKeyword) && item.getLocation().contains(location)),
                String.format("No items have been found for requested keyword='%1$s' and location='%2$s", jobKeyword, location));
    }

    @DisplayName("Feature: Customer is notified when no offers match given criteria")
    @Test
    void findJobInLocationNegative() {
        String jobKeyword = "XXX";

        driver.get(SEARCH_PAGE_ADDRESS);
        SearchJobPage searchJobPage = new SearchJobPage(driver);

        searchJobPage.fillFindJobByKeywordField(jobKeyword)
                .clickSearchButton();

        assertTrue(searchJobPage.getSearchResultItems().isEmpty(), "Search result list is not empty.");

        WebElement noResultsPanel = searchJobPage.getNoResultsPanel();
        assertTrue(noResultsPanel.isDisplayed(), "No results panel is not displayed");
        assertTrue(noResultsPanel.getText()
                        .contains(String.format("Your search matching keyword(s) %1$s did not return any job results.", jobKeyword)),
                "No job returns message is not displayed or displayed incorrectly.");
    }
}
