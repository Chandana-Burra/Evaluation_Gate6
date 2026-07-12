@api @negative
Feature: Out Of Stock

  Scenario: Add quantity greater than available stock

    Given "alice" is logged in
    When she adds 100 x "SKU-CAP"
    Then the response status should be 409