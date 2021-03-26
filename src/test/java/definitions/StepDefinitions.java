package definitions;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.Asserts;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.api.ClientAPI;
import rest.api.InvoiceAPI;
import rest.api.ItemAPI;
import ui.*;
import util.Constants;

import java.io.File;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    //Page objects
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private InvoicePage invoicePage;
    private ItemPage itemPage;
    private ClientPage clientPage;
    private CashBoxPage cashBoxPage;
    private ForgottenPasswordPage forgottenPasswordPage;
    private AccountProfilePage accountProfilePage;
    WebDriver driver;
    private WarehousePage warehousePage;


    private void startBrowser(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            configureBrowser(browser);
        }
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            configureBrowser(browser);
        }


    }


    private void configureBrowser(String browser) {
        LOGGER.info("==================== TEST START ====================");
        LOGGER.info("Starting browser:" + browser);
        driver.manage().deleteAllCookies(); //delete cookies
        driver.manage().window().maximize(); //To maximize browser
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   //Implicit wait
    }


    @Before
    public void before() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        startBrowser("chrome");
    }

    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot screenshotTakingDriver = (TakesScreenshot) driver;
                try {
                    File localScreenshots = new File(new File("target"), "screenshots");
                    if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
                        localScreenshots.mkdirs();
                    }
                    String fileName = scenario.getName().replace(" ", "_") + "_" + LocalTime.now().getMinute() + ".png";
                    File screenshot = new File(localScreenshots, fileName);
                    FileUtils.copyFile(screenshotTakingDriver.getScreenshotAs(OutputType.FILE), screenshot);
                    LOGGER.info("Screenshot saved with name:" + fileName);
                } catch (Exception e1) {
                    LOGGER.error("Unable to take screenshot", e1);
                }
            } else {
                LOGGER.info("Driver '{}' can't take screenshots so skipping it.", driver.getClass());
            }
        }
        driver.quit(); //close browser instance
    }


    @Given("^user is at the Login page$")
    public void user_is_at_Home_Page() {
        loginPage = new LoginPage(driver);
        loginPage.gotoPage();
    }

    @When("^I navigate to Items page$")
    public void gotoItemsPage() {
        itemPage = new ItemPage(driver);
        itemPage.gotoPage();
    }

    @When("^I navigate to CashBox page$")
    public void gotoCashBoxPage() {
        cashBoxPage = new CashBoxPage(driver);
        cashBoxPage.gotoPage();
    }

    @When("^I navigate to Clients page$")
    public void gotoClientsPage() {
        clientPage = new ClientPage(driver);
        clientPage.gotoPage();
    }

    @When("^I navigate to Invoices page$")
    public void gotoInvoicesPage() {
        invoicePage = new InvoicePage(driver);
        invoicePage.gotoPage();
    }

    @Given("^user is logged in the system$")
    public void userLoggedIn() {
        loginPage = new LoginPage(driver);
        loginPage.gotoPage();
        loginPage.enterUsername(Constants.USERNAME);
        loginPage.enterPassword(Constants.PASSWORD);
        loginPage.pressLoginButton();
    }

    @When("^I clean all items on API level$")
    public void deleteAllItemsAPILevel() {
        ItemAPI itemAPI = new ItemAPI();
        itemAPI.deleteAllExistingItems();
    }

    @When("^I clean all invoices on API level$")
    public void deleteAllInvoicesAPILevel() {
        InvoiceAPI invoiceAPI = new InvoiceAPI();
        invoiceAPI.deleteAllExistingInvoices();
    }

    @When("^I clean all clients on API level$")
    public void deleteAllClientsAPILevel() {
        ClientAPI clientAPI = new ClientAPI();
        clientAPI.deleteAllExistingClients();
    }

    @When("^I enter username \"(.*)\"$")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @And("^I enter password \"(.*)\"$")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("^I enter item name \"(.*)\"$")
    public void enterItemName(String name) {
        itemPage.enterName(name);
    }

    @When("^I create new item with name \"(.*)\" and nameENG \"(.*)\" and price \"(.*)\" and priceQuantity \"(.*)\"$")
    public void createNewItem(String name, String nameENG, String price, String priceQuantity) {
        itemPage.createItem(name, nameENG, price, priceQuantity);
    }

    @When("^I create new client with name \"(.*)\" and vat \"(.*)\" and address \"(.*)\" and town \"(.*)\"$")
    public void createNewClient(String name, String vat, String address, String town) {
        clientPage.createClient(name, vat, address, town);
    }

    @When("^I create new expense with value \"(.*)\" and title \"(.*)\" and firmName \"(.*)\" and category \"(.*)\" and notes \"(.*)\"$")
    public void createNewExpense(String value, String title, String firmName, String category, String notes) {
        cashBoxPage.createExpense(value, title, firmName, category, notes);
    }

    @When("^I create new income with value \"(.*)\" and title \"(.*)\" and firmName \"(.*)\" and category \"(.*)\" and notes \"(.*)\"$")
    public void createNewIncome(String value, String title, String firmName, String category, String notes) {
        cashBoxPage.createIncome(value, title, firmName, category, notes);
    }

    @And("^I press Login button$")
    public void iPressLoginButton() {
        loginPage.pressLoginButton();
    }

    @When("^I delete all items")
    public void deleteAllItems() {
        itemPage.deleteAllItems();
    }

    @When("^I delete all expenses")
    public void deleteAllExpenses() {
        cashBoxPage.deleteAllItems();
    }

    @When("^I delete all clients")
    public void deleteAllClients() {
        clientPage.deleteAllClients();
    }

    @Then("^login error message with text should be displayed \"(.*)\"$")
    public void loginErrorMessage(String errorMessage) {
        loginPage.verifyMessage(errorMessage);
    }

    @Then("^item message with text should be displayed \"(.*)\"$")
    public void itemSuccessMessage(String successMessage) {
        Assertions.assertThat(itemPage.getSuccessAddMessage()).as("Item Added").contains(successMessage);
    }


    @Then("^client message with text should be displayed \"(.*)\"$")
    public void clientSuccessMessage(String successMessage) {
        Assertions.assertThat(clientPage.getSuccessAddMessage()).as("Item Added").contains(successMessage);
    }


    @Then("^user panel should contain text \"(.*)\"$") //**********************************************
    public void userPanelShouldContain(String text) {
        dashboardPage = new DashboardPage(driver);
        Assertions.assertThat(dashboardPage.getUserPanelText()).as("User Panel").contains(text);
    }

    @Then("^Add New Item button should contain text \"(.*)\"$")
    public void addNewItemVisible(String text) {
        Assertions.assertThat(itemPage.getNewItemLinkText()).as("Add Item Link").contains(text);
    }

    @Then("^Add New Expense button should contain text \"(.*)\"$")
    public void addNewExpenseVisible(String text) {
        Assertions.assertThat(cashBoxPage.getNewExpenseButtonText()).as("Add Expense Link").contains(text);
    }

    @Then("^Add New Income button should contain text \"(.*)\"$")
    public void addNewIncomeVisible(String text) {
        Assertions.assertThat(cashBoxPage.getNewIncomeButtonText()).as("Add Income Link").contains(text);
    }

    @Then("^Add New Client button should contain text \"(.*)\"$")
    public void addNewClientVisible(String text) {
        Assertions.assertThat(clientPage.getNewClientLinkText()).as("Add Item Link").contains(text);
    }

    @Then("^Add New Invoice button should contain text \"(.*)\"$")
    public void addNewInvoiceButtonVisible(String text) {
        Assertions.assertThat(invoicePage.getNewInvoiceLinkText()).as("Add Invoice Link").contains(text);
    }

    @When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\" and click Login button$")
    public void iEnterUsernameAndPasswordAndClickLoginButton(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.pressLoginButton();

    }

    @And("^I wait (\\d+) second for the students to see what is going on$")
    public void iWaitSecondForTheStudentsToSeeWhatIsGoingOn(int arg0) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^I enter email \"([^\"]*)\"$")
    public void iEnterEmail(String email) {
        loginPage.enterUsername(email);
    }

    @Given("^I am at the Login page$")
    public void iAmAtTheLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.gotoPage();
    }

    @When("^I click reset password link$")
    public void iClickResetPasswordLink() {
        loginPage.pressResetPasswordLink();
    }

    @Then("^Forgotten password page should be loaded$")
    public void forgottenPasswordPageShouldBeLoaded() {
        forgottenPasswordPage = new ForgottenPasswordPage(driver);
        Assertions.assertThat(forgottenPasswordPage.getPageTitle())
                .as("Page Title")
                .isEqualToIgnoringCase("Възстановяване на парола");
    }

    @Then("^Forgotten password page title is \"([^\"]*)\"$")
    public void forgottenPasswordPageTitleIs(String title) {
        forgottenPasswordPage = new ForgottenPasswordPage(driver);
        Assertions.assertThat(forgottenPasswordPage.getPageTitle())
                .as("Page Title")
                .isEqualToIgnoringCase(title);
    }

    @And("^I click send button$")
    public void iClickSendButton() {
        forgottenPasswordPage.clickSendButton();
    }

    @When("^I enter email \"([^\"]*)\" for forgotten password restore$")
    public void iEnterEmailForForgottenPasswordRestore(String email) {
        forgottenPasswordPage.enterEmail(email);
    }

    @Then("^error message with text \"([^\"]*)\" is displayed$")
    public void errorMessageWithTextIsDisplayed(String error) {
        Assertions
                .assertThat(forgottenPasswordPage.getErrorMessage())
                .as("Error message")
                .isEqualToIgnoringCase(error);
    }

    @When("^I click Настойки button in the top links panel$")
    public void iClickSetButtonInTheTopLinksPanel() {
        // Write code here that turns the phrase above into concrete actions
        dashboardPage = new DashboardPage(driver);
        dashboardPage.goToUserAccountSettings();
    }

    @When("^I click \"([^\"]*)\" button in the short links panel$")
    public void iClickButtonInTheShortLinksPanel(String link) {
        // Write code here that turns the phrase above into concrete actions
        dashboardPage = new DashboardPage(driver);
        dashboardPage.goToWarehouseActionPage(link);
    }


    @And("^Radio button \"([^\"]*)\" is selected$")
    public void radioButtonIsSelected(String radioButton) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        Asserts.check(warehousePage.selectRadioButtonOut(radioButton).isSelected(), "The button is not selected");
    }

    @When("^I click on \"([^\"]*)\" from settings menu - По склада$")
    public void iClickOnFromSettingsMenuToWarеhouse(String warehouses) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.pressWarehouseButton();
    }

    @Then("^Page title should contain text \"([^\"]*)\"$")
    public void pageTitleShouldContainText(String title) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        Asserts.check(accountProfilePage.getPageTitle().contains(title), "Wronge page");
    }

    @Then("^Page should contain text \"([^\"]*)\"$")
    public void pageShouldContainText(String textSettings) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        Asserts.check(accountProfilePage.getUserSettingText().contains(textSettings), "The button is not 'Настройки'");
    }

    @Then("^PageSource should contain text \"([^\"]*)\"$")
    public void pageSourceShouldContainText(String addNewWarehouse) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        Asserts.check(accountProfilePage.getWebElementText().contains(addNewWarehouse), "Добави нов склад липсва");
    }


    @When("^I type the name \"([^\"]*)\" and press - Добави$")
    public void iEnterAName(String warehouseName) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.addWarehouse(warehouseName);
    }

    @Then("^Confirmation message \"([^\"]*)\" is displayed$")
    public void confirmationMessageIsDisplayed(String confirmNewWarehouse) {
        // Write code here that turns the phrase above into concrete actions
        accountProfilePage = new AccountProfilePage(driver);
        Asserts.check(accountProfilePage.confirmationMessage().contains(confirmNewWarehouse), "Складът не е добавен");
    }

    @When("^user go to home page$")
    public void userGoToHomePage() {
        dashboardPage = new DashboardPage(driver);
        dashboardPage.goHome();
    }

    @When("^I delete all warehouses$")
    public void iDeleteAllWarehouses() {
        accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.deleteAllWarehouses();
        accountProfilePage.acceptAlert();
    }


    @And("^WarehousePage should contain text \"([^\"]*)\"$")
    public void warehousepageShouldContainText(String text) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        Asserts.check(warehousePage.getText().contains(text), "Missing or wrong message in the warehouse page.");
    }

    @Then("^Warehouse form should contain text \"([^\"]*)\"$")
    public void warehouseFormShouldContainText(String text) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        Asserts.check(warehousePage.getActionFormText().contains(text), "This is not option 'в склад'");
    }

    @When("^I click on radio button \"([^\"]*)\"$")
    public void iClickOnRadioButton(String radioButton) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        warehousePage.selectRadioButtonIn(radioButton);
    }


    @Given("^New warehouse \"([^\"]*)\" is created into \"([^\"]*)\"$")
    public void newWarehouseIsCreatedInto(String warehouseName, String warehouses) {
        // Write code here that turns the phrase above into concrete actions
        dashboardPage = new DashboardPage(driver);
        dashboardPage.goToUserAccountSettings();
        accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.pressWarehouseButton();
        accountProfilePage.addWarehouse(warehouseName);
        dashboardPage = new DashboardPage(driver);
        dashboardPage.goHome();
    }

    @Then("^The confirmed message is displayed \"([^\"]*)\"$")
    public void theConfirmedMessageIsDisplayed(String message) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        warehousePage.assertMessage(message);
    }

    @When("^I enter \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void iEnter(String warehouse, String item, String quantity) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        warehousePage.sendText(warehouse, item, quantity);
    }

    @And("^click Вкарай стоката$")
    public void clickInsertStock() {
        warehousePage = new WarehousePage(driver);
        warehousePage.clickOnInsert();
    }

    @Then("^Error message \"([^\"]*)\" should be displayed$")
    public void errorMessageShouldBeDisplayed(String message) {
        // Write code here that turns the phrase above into concrete actions
        warehousePage = new WarehousePage(driver);
        Assert.assertTrue(warehousePage.errorMessage().contains(message));
    }

    @When("^I select \"([^\"]*)\" button in the short links panel$")
    public void iSelectButtonInTheShortLinksPanel(String text) {
        // Write code here that turns the phrase above into concrete actions
     dashboardPage=new DashboardPage(driver);
     dashboardPage.gotToClients(text);
    }

    @When("^I create new company with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void iCreateNewCompanyWith(String firm, String vat, String address, String post, String city, String country, String person,
                                      String code, String phone, String bussinessName, String engAddress, String issuer){
        // Write code here that turns the phrase above into concrete actions
        clientPage = new ClientPage(driver);
        clientPage.createCompany(firm,vat,address,post,city,country,person,code,phone,bussinessName,engAddress,issuer);
    }

    @And("^I press \"([^\"]*)\"$")
    public void iPress(String arg0){
        // Write code here that turns
        clientPage.goBack();
    }

    @Then("^an error message should be displayed \"([^\"]*)\"$")
    public void anErrorMessageShouldBeDisplayed(String message){
        // Write code here that turns the phrase above into concrete actions
        clientPage = new ClientPage(driver);
        Assert.assertFalse(clientPage.errorMessage().contains(message));
    }
}
