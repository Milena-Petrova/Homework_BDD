package ui;

import cucumber.api.java.en_old.Ac;
import org.apache.commons.lang3.CharSet;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.List;

public class AccountProfilePage {

    private static PageAction action;

    @FindBy(how = How.CSS, using = "div.settingsHeader")
    private WebElement setting;


    @FindBy(how = How.CSS, using = "a.settingsMenuEntry[href*='settings/stores']")
    private WebElement warehouse;

    @FindBy(how = How.CSS, using = "form.selenium-form-add-stores div.title")
    private WebElement addNewWarehouseTitle;

    @FindBy(how = How.CSS, using = "form.selenium-form-add-stores input[type='text']")
    private WebElement warehouseArea;

    @FindBy(how = How.CSS, using = "form.selenium-form-add-stores input.button")
    private WebElement addNewWarehouse;

    @FindBy(how = How.ID, using = "okmsg")
    WebElement confirmNewAddedWareHouseMessage;

    @FindBy(how = How.CSS, using = "th.check")
    private WebElement checkbox;

    @FindBy(how = How.CSS, using = "a.stores-del-btn")
    private WebElement delete;

    @FindBy(how = How.CSS,using = "a.selenium-delete-store")
    private WebElement deleteWarehouse;


    public AccountProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        action = new PageAction(driver);
    }

    /**
     * This method returns visible text of the setting button "Настройки"
     *
     * @return visible text of setting button
     */
    public String getUserSettingText() {
        return action.getText(setting);

    }

    /**
     * This method returns page title
     *
     * @return page title text
     */
    public String getPageTitle() {
        return action.getTitle();
    }

    /**
     * This method navigates to the warehouse action menu
     */
    public void pressWarehouseButton() {
        action.clickButton(warehouse);
    }

    /**
     * This method returns title of the warehouse adding blank
     *
     * @return title of blank space
     */
    public String getWebElementText() {
        return action.getText(addNewWarehouseTitle);
    }

    /**
     * Thi method adds a new warehouse
     *
     * @param warehouseName the name of the new warehouse
     */
    public void addWarehouse(String warehouseName) {
        action.typeText(warehouseArea, warehouseName);
        action.clickButton(addNewWarehouse);
    }

    /**
     * This method confirms adding the warehouse
     *
     * @return successful message
     */
    public String confirmationMessage() {
        return action.getText(confirmNewAddedWareHouseMessage);
    }

    /**
     * This method removes all warehouses
     *
     * @param
     */
    public void deleteAllWarehouses() {
          action.clickButton(deleteWarehouse);
          action.acceptAlert();
    }

    public void acceptAlert() {
        action.acceptAlert();
    }
}


