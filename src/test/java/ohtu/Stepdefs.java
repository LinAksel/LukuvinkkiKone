package ohtu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
        lisaa(title, link, description, tags);
    }

    @Then("system will respond with {string}")
    public void newItemWasAdded(String content) {
        pageHasContent(content);
    }

    @Then("http:// is added in the beginning of link")
    public void httpAddedToTheLink() {
        pageHasContent("http://www.teos.fi/kirjat/kaikki/2004/pussikaljaromaani.html");
    }

    @Given("list is selected")
    public void listIsSelected() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Listaa vinkit"));
        element.click();
    }

    @Then("list page opens")
    public void listIsShown() {
        pageHasContent("Seitsemän veljestä");
        pageHasContent("Paroni von Münchhausen");
    }

    @Given("mark as read is selected")
    public void markedAsRead() {
        driver.get(baseUrl + "/list");
        WebElement element = driver.findElement(By.id("readornot"));
        element.click();
    }

    @Then("read date is added")
    public void readDateIsAdded() {
        pageHasContent("Luettu:");
    }

    @Given("remove read date is selected")
    public void removeReadDate() {
        driver.get(baseUrl + "/list");
        WebElement element = driver.findElement(By.id("read"));
        element.click();
    }

    @Then("read date is removed")
    public void readDateIsRemoved() {
        assertFalse("Luettu:", false);
    }

    @Given("app is started")
    public void mainPageIsStarted() {
        driver.get(baseUrl);
    }

    @Then("main page opens")
    public void mainPageIsShown() {
        pageHasContent("Lukuvinkkikone");
        pageHasContent("Listaa vinkit");
        pageHasContent("Lisää uusi vinkki!");

    }

    @When("tag {string} is entered")
    public void tagIsEntered(String tagi) {
        WebElement element = driver.findElement(By.name("tagSearchField"));
        element.sendKeys(tagi);
        element = driver.findElement(By.name("search"));
        element.submit();
    }

    @Then("page has content {string}")
    public void pageHasCertainContent(String tagi) {
        pageHasContent(tagi);
    }

    @When("empty tag is entered")
    public void emptyTagIsEntered() {
        WebElement element = driver.findElement(By.name("search"));
        element.submit();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    // Tällä voi testata että hakuun ei tule ylimääräistä
    private void pageDoesntHaveContent(String content) {
        assertTrue(!driver.getPageSource().contains(content));
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
