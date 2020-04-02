package ohtu;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";

    @Given("add new is selected")
    public void addNewIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Lisää uusi vinkki!"));
        element.click();
    }

    @When("title {string} and link {string} are given")
    public void correctTitleAndLinkAreGiven(String title, String link) {
        lisaa(title, link);
    }

    @Then("system will respond with {string}")
    public void userIsLoggedIn(String content) {
        pageHasContent(content);
    }

    @Given(" list is selected")
    public void listIsSelected() {
        // Lisää testi
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void lisaa(String title, String link) {
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("link"));
        element.sendKeys(link);
        element = driver.findElement(By.name("add"));
        element.submit();
    }
}
