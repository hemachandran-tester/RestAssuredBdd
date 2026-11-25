Feature: Get Asset List

  Verify that the asset list return asset records successfully.

  Background:
    Given I have the base URL for assetList
    And the asset list endpoint is available
    And I have required headers for the assertList
    And the request content type is for assertList "application/json"

  @smoke @assetList @positive
  Scenario: Verify that asset list can get successfully
    And I have a valid authentication token for assetList API
    When I send a GET request to the assetList API
    Then The response status code for assetList API should be 200
    And The response content of assetList type should be "application/json"

  @smoke @assetList @positive
  Scenario Outline: Verify that assetList response contains all the fields
    And I have a valid authentication token for assetList API
    When I send a GET request to the assetList API
    Then The response status code for assetList API should be 200
    And The response content for assetList should contain all the "<field>"
    And The response content of assetList type should be "application/json"

    Examples:
      | field           |
      | assetId         |
      | assetName       |
      | serialNumber    |
      | empId           |
      | status          |
      | type            |
      | purchaseDate    |
      | warrantyDate    |
      | location        |
      | modelName       |
      | operatingSystem |
      | returnDate      |
      | addedBy         |
      | assignedDate    |
      | assignedBy      |
      | assetSourcedBy  |

  @smoke @assetList @negative
  Scenario: Verify that asset list with invalid token
    And I have a invalid authentication token for assetList API
    When I send a GET request to the assetList API
    Then The response status code for assetList API should be 401
    And The response content of assetList type should be "application/json"

  @smoke @assetList @negative
  Scenario: Verify that asset list with different method
    And I have a valid authentication token for assetList API
    When I send a POST request to assetList API
    Then The response status code for assetList API should be 405
    And The error message for invalid Method should be "message"

  @smoke @assetList @negative
  Scenario: Verify that asset list without authentication token
    When I send a GET request to the assetList API
    Then The response status code for assetList API should be 401

  @smoke @assetList @positive
  Scenario: Verify that all the Asset IDs are unique
    And I have a valid authentication token for assetList API
    When I send a GET request to the assetList API
    Then The response status code for assetList API should be 200
    And The response content of assetList type should be "application/json"
    And All the asset IDs should be unique in assetList












