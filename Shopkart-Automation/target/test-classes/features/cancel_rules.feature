@api @negative
Feature: Cancel Order

  Scenario: Cancel a placed order

    Given "alice" has a PLACED order
    When she cancels the order
    Then the order status should become "CANCELLED"
    And cancelling the order again returns 409