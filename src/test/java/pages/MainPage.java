package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    private static final String URL = "https://www.booking.com/";
    private By CITY_FIELD = By.xpath("//input[@type='search' or @name='ss']");
    private By SEARCH_BUTTON = By.xpath("//button[@type='submit']");

    public MainPage openPage() {
        open("https://www.booking.com/");
        return new MainPage();
    }

    public MainPage fillInCity(String city) {
        $(CITY_FIELD).shouldBe(Condition.visible, Duration.ofSeconds(15)).sendKeys(city);
        return this;
    }

    public SearchResultsPage enterSearchButton() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        $(SEARCH_BUTTON).click();
        return new SearchResultsPage();
    }

}
