@api @negative @security
Feature: Orders are visible only to their owner

  Scenario: Another customer cannot read someone else's order
    Given "alice" has a PLACED order
    When "bob" requests the order
    Then the response status should be 403