@test
Feature: Warehouse test in https://inv.bg/

  Background:
    Given user is logged in the system
    Then user panel should contain text "milipt.mp@gmail.com"

# DM18-TC-27 ************************************
  Scenario: Can create a new warehouse.
    When I click Настойки button in the top links panel
    And I wait 3 second for the students to see what is going on
    Then Page title should contain text "Фирмен профил"
    Then Page should contain text "Настройки"
    When I click on "Складове" from settings menu - По склада
    And I wait 3 second for the students to see what is going on
    Then PageSource should contain text "Добавете нов склад:"
    When I type the name "Тeхника" and press - Добави
    Then Confirmation message "Складът беше добавен успешно." is displayed
    And I delete all warehouses
    And user go to home page

#DM-18-TC-28, I don't know how to describe the precondition that there should be existing : a warehouse, an article and a client
#Този тест ми фейлва, защото не ми се получава избора на името на склада в аутокомплийт полето.
  Scenario Outline: Add a new product in the warehouse
    Given New warehouse "Едра домакинска техника" is created into "Складове"
    When I click "Създай движение в склада" button in the short links panel
    And I wait 3 second for the students to see what is going on
    Then Page title should contain text "Ново движение"
    And WarehousePage should contain text "Добавяне на движение в склада"
    And Radio button "Изкарване" is selected
    When I click on radio button "Вкарване"
    And I wait 5 second for the students to see what is going on
    Then Warehouse form should contain text "В склад:"
    When I enter "<warehouseName>" "<itemName>" "<quantity>"
    And click Вкарай стоката
#    ----------------the test stops here---------------
    Then Page title should contain text "Преглед на движение"
    Then The confirmed message is displayed "Движението е потвърдено успешно."
#    ---------------------delete the new warehouse--------------------
    And I click Настойки button in the top links panel
    And I click on "Складове" from settings menu - По склада
    And I delete all warehouses
    And user go to home page
    Examples:
      | warehouseName           | itemName  | quantity |
      | Едра домакинска техника | Телевизор | 1        |

#      DM-18-TC-29************************************************************************
  Scenario: Can't add a warehouse with existing name.
    Given New warehouse "Техника" is created into "Складове"
    When I click Настойки button in the top links panel
    And I wait 3 second for the students to see what is going on
    Then Page title should contain text "Фирмен профил"
    Then Page should contain text "Настройки"
    When I click on "Складове" from settings menu - По склада
    And I wait 3 second for the students to see what is going on
    Then PageSource should contain text "Добавете нов склад:"
    When I type the name "Техника" and press - Добави
    Then Error message "Неуспешно добавяне. Вече има склад с такова име." should be displayed
    And I delete all warehouses
    And user go to home page


#  @Copied from examples
  Scenario: Can navigate to clients page
    When I navigate to Clients page
    Then Add New Client button should contain text "Нов клиент"
    When I clean all clients on API level
    And I delete all clients

#DM-18-TC-20****************************************** съжалявам, че е дълго, но късно включих
  Scenario Outline: : Add a new company with all fields by using the short path.
    When I select "Добавяне на нов клиент" button in the short links panel
    And I wait 3 second for the students to see what is going on
    Then Page title should contain text "Добавяне на клиент"
    When I create new company with "<Firm name:>" "<Bulstat>" "<Address by registry>" "<PostCode>" "<City>" "<Country>" "<PersonName>" "<ClientCode>" "<Telefone>" "<Business name:>" "<Address:>" "<issuer>"
    Then client message with text should be displayed "Клиентът е добавен успешно"
    And Page title should contain text "Преглед на клиент"
    And I delete all clients

    Examples:
      | Firm name:                | Bulstat    | Address by registry | PostCode | City     | Country  | PersonName | ClientCode | Telefone   | Business name:       | Address:        | issuer        |
      | Двама мъже и половина ООД | 9999999999 | ул. Незнайна 99     | 8890     | Твърдица | България | Чарли Шийн | 0          | 1234567890 | Two and half man Ltd | Unknown str. 99 | Charlie Sheen |

#  DN-18-TC-21****************************************
#  @I also copied and modified
  Scenario Outline: Try weather the system permits duplicating the VAT numbers.
   When I navigate to Clients page
    When I create new client with name "<name>" and vat "<vat>" and address "<address>" and town "<town>"
    Then client message with text should be displayed "<successful>"
    And I press "обратно в клиенти"
    When I create new client with name "<name>" and vat "<vat>" and address "<address>" and town "<town>"
    Then an error message should be displayed "<unsuccessful>"
    And I clean all clients on API level
    Examples:
      | name   | vat        | address | town  | successful                 | name | vat        | address | town  | unsuccessful                            |
      | NoName | 9999999999 | Bla 20  | Sofia | Клиентът е добавен успешно. | name | 9999999999 | bla 34  | Sofia | Вече съществува фирма тест с този булстат |

