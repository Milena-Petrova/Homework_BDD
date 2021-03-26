package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    private PageAction action;

    @FindBy(how = How.ID, using = "loginusername")
    private WebElement usernameField;

    @FindBy(how = How.XPATH, using = "//div[@class='userpanel-header']")
    private WebElement userPanel;

    @FindBy(how = How.CSS, using = "a.selenium-settings-menu ")
    private WebElement settings;

    @FindBy(how = How.ID,using = "tabs_home")
    private WebElement start;

    @FindBy(how = How.XPATH,using = "//div[@class='startmenu']//a[contains(@href,'clients')]")
    private WebElement shortLinkToClents;

    public DashboardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        action = new PageAction(driver);
    }

    public String getUserPanelText() {
        return action.getText(userPanel);
    }

    /**
     * This method navigates to the user profile account
     */
    public void goToUserAccountSettings() {
        action.clickButton(settings);
        System.out.println(settings.getText());
    }

    /**
     * This method navigates to the warehouse's action page
     */
    public void goToWarehouseActionPage(String link) {
     //   action.clickButton(createWarehouseAction,link);
      action.clickLinkByText(link);
    }

    /**
     * This method returns to the Home page
     */
    public void goHome() {
        action.clickButton(start);
    }

    public void gotToClients(String text) {
        action.clickButton(shortLinkToClents,text);
    }
}
