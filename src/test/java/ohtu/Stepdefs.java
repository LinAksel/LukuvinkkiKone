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
    
    @When("title {string}, link {string}, description {string} and tags {string} are given")
    public void correctTitleAndLinkAreGiven(String title, String link, String description, String tags) {
        lisaa(title, link,description, tags);
    }

    @Then("system will respond with {string}")
    public void userIsLoggedIn(String content) {
        pageHasContent(content);
    }

    @Then ("http:// is added in the beginning of link")
    public void httpAddedToTheLink(){
        pageHasContent("http://www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html");
    }

    @Given("list is selected")
    public void listIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Listaa vinkit"));
        element.click();
    }

    @Then ("list page opens")
    public void listIsShown(){
        pageHasContent("Seitsemän veljestä");
        pageHasContent("Paroni von Münchhausen");

    }

   @Given("app is started")
   public void mainPageIsStarted(){
        driver.get(baseUrl);
   }

   @Then ("main page opens")
   public void mainPageIsShown(){
        pageHasContent("Lukuvinkkikone");
        pageHasContent("Listaa vinkit");
        pageHasContent("Lisää uusi vinkki!");

   }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void lisaa(String title, String link, String description, String tags) {
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("link"));
        element.sendKeys(link);
        element = driver.findElement(By.name("description"));
        element.sendKeys(description);
        element = driver.findElement(By.name("tags"));
        element.sendKeys(tags);
        element = driver.findElement(By.name("add"));
        element.submit();
    }
}
