package ui;

import org.junit.Assert;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import java.util.List;

public class WarehousePage {
    private static WebDriver driver;
    private static PageAction action;

    @FindBy(how = How.ID, using = "headline2")
    private WebElement warehouseAction;
    @FindBy(how = How.ID, using = "act_type_out")
    private WebElement radioButtonOut;
    @FindBy(how = How.ID, using = "act_type_in")
    private WebElement radioButtonIn;
    @FindBy(how = How.CSS, using = "table.invoice-data-table")
    private WebElement actionTable;
    @FindBy(how = How.ID, using = "to")
    private WebElement warehouse;
    @FindBy(how = How.XPATH, using = "//tr[@id='obj_0']//td//input[@class='selenium-selector-obj-name objects__name objects-name__input-long']")
    private WebElement item;
    @FindBy(how = How.XPATH, using = "//tr[@id='obj_0']//input[@class='selenium-selector-obj-quantity objects__quantity']")
    private WebElement quantifier;
    @FindBy(how = How.CSS, using = "div.submitinvoice input[value='Вкарай стоката']")
    private WebElement insertItems;
    @FindBy(how = How.CSS, using = "div.selenium-autocomplete-box.selected")
    private WebElement autocompleteWarehouse;
    @FindBy(how = How.ID,using = "okmsg")
    private WebElement successfulMessage;
    @FindBy (how=How.CSS,using = "div.warnmsg.selenium-warning-block")
    private WebElement errorMessage;


    public WarehousePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        action = new PageAction(driver);
    }

    /**
     * This method returns the text of a web element
     *
     * @return visible text of the specific web element
     */
    public String getText() {

        return action.getText(warehouseAction);
    }

    /**
     * This method returns selected WebElement radiobutton Изкарване
     *
     * @param buttonName the name of the button
     * @return
     */
    public WebElement selectRadioButtonOut(String buttonName) {
        if (!radioButtonOut.isSelected() && radioButtonOut.getText().equals(buttonName)) {
            radioButtonOut.click();
        }
        return radioButtonOut;
    }

    /**
     * This method returns
     *
     * @return
     */
    public String getActionFormText() {
        System.out.println(actionTable.getText());
        return action.getText(actionTable);
    }

    /**
     * This method returns selected WebElement radiobutton Вкарване
     *
     * @param radioButton the name of the button
     * @return
     */
    public void selectRadioButtonIn(String radioButton) {
        if (!radioButtonIn.isSelected()) {
            radioButtonIn.click();
        }
    }

    /**
     * Enter string name in the field warehouse
     * @param name
     */
    public void sendText(String name) {
        action.typeText(warehouse, name);
    }

    public void submitName(String name) {
        action.selectByVisibleText(autocompleteWarehouse, name);
    }

    public void sendText(String name,String articile, String quantity) {
        action.typeText(warehouse,name);
        action.typeText(item, articile);
        action.typeText(quantifier, quantity);
    }

    public void clickOnInsert() {
        action.clickButton(insertItems);
    }


    public boolean assertMessage(String message) {
       return action.getText(successfulMessage).contains(message);
    }

    public boolean isSelectText(String name) {
        return action.waitUntilVisible(By.cssSelector("div.selenium-autocomplete-box.selected"), name);
    }

    public String errorMessage() {
            return action.getText(errorMessage);
        }
}

