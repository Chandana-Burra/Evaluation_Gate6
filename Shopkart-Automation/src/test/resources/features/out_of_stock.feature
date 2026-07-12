@api @negative
Feature: Out of stock

  Scenario: Quantity exceeds stock
    Given "alice" has a cart
    When she adds 100 x "SKU-BAG"
    Then the response status should be 409