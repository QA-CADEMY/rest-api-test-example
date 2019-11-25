Feature: Boards

  Background:
    Given I use the "owner" account
    And I send a "POST" request to "/boards" with json body
    """
    {
    "name": "Board created by cucumber"
    }
    """
    And I save the response as "P"
    And I save the request endpoint for deleting

  @cleanData
  Scenario: PUT Board
    When I send a "PUT" request to "/boards/(P.id)" with json body
    """
    {
    "name": "Board updated by cucumber"
    }
    """
    Then I validate the response has status code 200
    And I validate the response contains "name" equals "Board updated by cucumber"
