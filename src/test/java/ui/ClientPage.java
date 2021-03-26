package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import util.Constants;
import util.enums.Pages;

import java.util.Collection;
import java.util.Date;

public class ClientPage {
    private PageAction action;

    @FindBy(how = How.ID, using = "searchbtn")
    private WebElement searchButton;

    @FindBy(how = How.ID, using = "okmsg")
    private WebElement addSuccessMessage;

    @FindBy(how = How.NAME, using = "firm_name")
    private WebElement firmNameField;

    @FindBy(how = How.NAME, using = "firm_bulstat")
    private WebElement firmVatField;

    @FindBy(how = How.NAME, using = "firm_addr")
    private WebElement firmAddressField;

    @FindBy(how = How.NAME, using = "firm_town")
    private WebElement firmTownField;

    @FindBy(how = How.NAME, using = "price_quantity")
    private WebElement priceQuantityField;

    @FindBy(how = How.NAME, using = "do_submit")
    private WebElement addItemButton;

    @FindBy(how = How.ID, using = "cl_delbtn")
    private WebElement deleteClientButton;

    @FindBy(how = How.ID, using = "handle_all")
    private WebElement selectAllItemsCheckbox;

    @FindBy(how = How.XPATH, using = "//a[@class='newbtn selenium-add-client-button']")
    private WebElement addNewClientLink;
    @FindBy(how=How.CSS,using = "input.client__postal-code")
    private WebElement postCode;
    @FindBy(how = How.CSS,using = "input[name='firm_mol']")
    private WebElement name;
    @FindBy(how = How.CSS,using = "input[name='clcode']")
    private WebElement clientCode;
    @FindBy(how=How.CSS,using = "input[name='phone_numbers[0][number]']")
    private WebElement telefone;
    @FindBy(how = How.CSS,using = "input[name='country']")
    private WebElement country;
    @FindBy(how = How.CSS,using = "input[name='firm_name_en']")
    private WebElement bussiness;
    @FindBy(how=How.CSS,using = "textarea[name='firm_addr_en']")
    private WebElement engAdres;
    @FindBy(how = How.CSS,using = "input[name='firm_mol_en']")
    private WebElement issueName;
    @FindBy(how = How.ID,using = "goback")
    private WebElement goback;
    @FindBy(how =How.ID,using = "error")
    private WebElement errorMessage;


    public ClientPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        action = new PageAction(driver);
    }

    public void enterFirmName(String name) {
        action.typeText(firmNameField, name);
    }

    public void enterFirmVat(String vat) {
        action.typeText(firmVatField, vat);
    }

    public void enterFirmTown(String town) {
        action.typeText(firmTownField, town);
    }

    public void enterFirmAddress(String address) {
        action.typeText(firmAddressField, address);
    }

    public String getSuccessAddMessage() {
        return action.getText(addSuccessMessage);
    }


    public void checkAllItems() {
        action.clickButton(selectAllItemsCheckbox);
    }

    public void pressDeleteClientButton() {
        action.clickButton(deleteClientButton);
    }

    public void deleteAllClients() {
        gotoPage();
        checkAllItems();
        pressDeleteClientButton();
        action.acceptAlert();
    }


    public void pressAddItemButton() {
        action.clickButton(addItemButton);
    }

    public void clickAddNewClientLink() {
        action.clickButton(addNewClientLink);
    }

    public void createClient(String name, String vat, String address, String town) {
        gotoPage();
        clickAddNewClientLink();
        enterFirmName(name);
        enterFirmVat(vat);
//        enterFirmName(name + "-" + new Date().toString());
//        enterFirmVat(vat + "-" + new Date().toString());
        enterFirmAddress(address);
        enterFirmTown(town);
        pressAddItemButton();
    }


    public void gotoPage() {
        action.gotoPage(Pages.INV_CLIENT_PAGE.getPath());
    }

    public String getNewClientLinkText() {
        return action.getText(addNewClientLink);
    }

    /**
     * Create company with all fields
     * @param firm
     * @param vat
     * @param address
     * @param post
     * @param city
     * @param area
     * @param person
     * @param code
     * @param phone
     * @param bussinessName
     * @param engAddress
     * @param issuer
     */
    public void createCompany(String firm, String vat, String address, String post, String city,String area, String person,
                              String code, String phone,String bussinessName, String engAddress, String issuer) {
        action.typeText(firmNameField,firm);
        action.typeText(firmVatField,vat);
        action.typeText(firmAddressField,address);
        action.typeText(postCode,post);
        action.typeText(firmTownField,city);
        action.typeText(country,area);
        action.typeText(name,person);
        action.typeText(clientCode,code);
        action.typeText(telefone,phone);
        action.typeText(bussiness,bussinessName);
        action.typeText(engAdres,engAddress);
        action.typeText(issueName,issuer);
        pressAddItemButton();
    }

    public void goBack() {
        action.clickButton(goback);
    }

    public String errorMessage(){
    return action.getText(errorMessage);
    }
}
