package steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import pages.MainPage;
import pages.SearchResultsPage;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BookingSteps {

    private String city;
    private MainPage mainPage;
    private SearchResultsPage searchResultsPage;

    @Before
    public void init() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOptions.addArguments("--incognito");
        //chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(driver);
        getWebDriver().manage().window().maximize();

        mainPage = new MainPage();
        searchResultsPage = new SearchResultsPage();
    }

    @After
    public void close() {
        getWebDriver().quit();
    }

    @Given("User is looking for hotel in {string} city")
    public void userIsLookingForHotel(String city) {
        this.city = city;
    }

    @When("User does search")
    public void userDoesSearch() {
        mainPage
                .openPage()
                .fillInCity(city)
                .enterSearchButton();
    }

    @Then("Hotel {string} should be on the first page")
    public void hotelShouldBeOnTheFirstPage(String hotel) {
        List<String> hotelsNames = new ArrayList<>();
        for (SelenideElement element : $$(searchResultsPage.getHotelButtons())) {
            hotelsNames.add(element.getText());
        }
        Assert.assertTrue(hotelsNames.contains(hotel));
    }

    @And("Hotel {string} rating should be {string}")
    public void hotelRatingShouldBe(String hotelName, String expectedHotelRating) {
        String actualHotelRating = searchResultsPage.getHotelRating(hotelName);
        Assert.assertEquals(actualHotelRating, expectedHotelRating);
    }
}
