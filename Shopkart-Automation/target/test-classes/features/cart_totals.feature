@api @db
Feature: Cart Totals

  Scenario: Cart total equals quantity multiplied by price

    Given alice has a cart
    When she adds 2 x "SKU-BAG"
    Then the cart total should be 99800 paise
    And the database stores total 99800