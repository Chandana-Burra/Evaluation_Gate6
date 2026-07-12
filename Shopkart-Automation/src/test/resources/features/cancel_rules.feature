@api @negative
Feature: Cancel order rules

  Scenario: Cancel a placed order
    Given "alice" has a PLACED order
    When she cancels the order
    Then the order status becomes "CANCELLED"
    And cancelling the same order again returns 409