package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;


public class SearchResultsPage {
    private By HOTEL_BUTTONS = By.xpath("//div[@data-testid='title']");

    public By getHotelButtons() {
        return HOTEL_BUTTONS;
    }

    public String getHotelRating(String hotelName) {
        String hotelRating = String.format("//div[contains(text(), '%s')]/ancestor::div[@data-testid='property-card']//div[contains(@aria-label, 'Scored')]", hotelName);
        return $(By.xpath(hotelRating)).getText();
    }
}
