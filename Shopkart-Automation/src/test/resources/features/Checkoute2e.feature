@e2e @smoke
Feature: Checkout places an order and the backend agrees

  Scenario: A logged-in customer checks out a two-item cart
    Given "alice" is logged in
    And she adds 2 x "SKU-BAG" to her cart
    When she checks out with a valid address
    Then the order confirmation shows status "PLACED"
    And the API returns status "PLACED" and totalPaise 99800
    And the orders table has exactly one "PLACED" row for "alice"