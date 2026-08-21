Feature: Product sort filter verification

  Background:
    Given user is on the Swag Labs login page
    When user logs in with username "standard_user" and password "secret_sauce"
    Then user should be navigated to the products page

  Scenario Outline: Verify each sort option correctly reorders the products
    When the user opens the sort filter
    And the user selects the "<sortOption>" sort option
    Then the products should be sorted by "<sortOption>"

    Examples:
      | sortOption           |
      | Name (A to Z)        |
      | Name (Z to A)        |
      | Price (low to high)  |
      | Price (high to low)  |