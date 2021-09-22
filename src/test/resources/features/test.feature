Feature: Coffee Maker
  A user can save recipe up to 3 recipe and can make coffee of their choice.

  Scenario: buy new coffee maker
    Given I buy new coffee maker
    Then the ingredient in inventory is all 15

  Scenario: add new recipe to the coffee maker
    Given I buy new coffee maker
    When I add new recipe
    Then there is recipes present in coffee maker

  Scenario: pay for coffee more than the cost and receive change correctly
    Given I buy new coffee maker
    When I add new recipe
     And I pay 100
     And make a cup of coffee
    Then the change is 50

  Scenario: pay for coffee equal to the cost of the coffee
    Given I buy new coffee maker
    When I add new recipe
    Then I pay 50
    And make a cup of coffee
    Then the change is 0

  Scenario: when make a coffee use correct amount of ingredient
    Given I buy new coffee maker
    When I add new recipe
    And I pay 50
    And make a cup of coffee
    Then use correct amount of ingredient

  Scenario: try to make a coffee with no recipe
    Given I buy new coffee maker
    And I pay 50
    Then make coffee with no recipe

  Scenario: pay for coffee less to the cost of the coffee
    Given I buy new coffee maker
    When I add new recipe
    And I pay 25
    And make a cup of coffee
    Then the change is 25
    