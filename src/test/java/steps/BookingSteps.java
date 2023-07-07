package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

import java.util.ArrayList;
import java.util.List;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BookingSteps {

    private String city;
    @Before
    public void init() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(driver);
        getWebDriver().manage().window().maximize();
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
    public void userDoesSearch() throws InterruptedException {
        open("https://www.booking.com/");
        $(By.xpath("//input[@type='search' or @name='ss']")).shouldBe(Condition.visible, Duration.ofSeconds(15)).sendKeys(city);
        Thread.sleep(2000);
        $(By.xpath("//button[@type='submit']")).click();
    }

    @Then("Hotel {string} should be on the first page")
    public void hotelShouldBeOnTheFirstPage(String hotel) {
        List<String> hotelsNames = new ArrayList<>();
        for (SelenideElement element: $$(By.xpath("//button[@type='submit']"))) {
            hotelsNames.add(element.getText());
        }
        System.out.println(hotelsNames);
        Assert.assertTrue(hotelsNames.contains(hotel));
    }

}
