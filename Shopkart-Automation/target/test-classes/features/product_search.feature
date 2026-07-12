@smoke @ui @api
Feature: Product Search

  Scenario: Search returns matching products

    Given "alice" is logged in
    When she searches for "Training Tee"
    Then matching products are displayed
    And the Product API returns "Training Tee"