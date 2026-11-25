Feature: Get Asset by there Serial Number.

  Verify that asset can be get by there SNO successfully.

  Background:
    Given I have the base URL for get asset by SNO
    And I have a valid endpoint for the get asset by SNO
    And I have a valid Asset SNO
    And I have required headers for get asset by SNO
    And The request content type is for get asset by SNO is "application/json"

  @positive @smoke @getAssetBySNO
  Scenario: Verify that asset can be get successfully with valid SNO
    And I have a valid authentication token for asset by SNO
    When I send a GET request to get asset by SNO
    Then The response status code for get asset by SNO 200
    And The response content for get asset by SNO is "application/json"

  @positive @smoke @getAssetBySNO
  Scenario Outline: Verify that the response asset details have all the fields
    And I have a valid authentication token for asset by SNO
    When I send a GET request to get asset by SNO
    Then The response status code for get asset by SNO 200
    And The response content for get asset by SNO is "application/json"
    And The response should contains all the "<field>"

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

  @positive @smoke @getAssetBySNO
  Scenario: Verify that the asset SNO in response is same as the one given
    And I have a valid authentication token for asset by SNO
    When I send a GET request to get asset by SNO
    Then The response content for get asset by SNO is "application/json"
    And The response status code for get asset by SNO 200
    And the asset serialNumber is same as the given one "SN10005"

  @positive @smoke @getAssetBySNO
  Scenario Outline: Verify that the asset status can be either UnAssigned or Assigned
    And I have a valid authentication token for asset by SNO
    When I send a GET request to get asset by SNO
    Then The response content for get asset by SNO is "application/json"
    And The response status code for get asset by SNO 200
    And The status "<field>" should be either "<value>"

    Examples:
      | field  | value      |
      | status | UnAssigned |
      | status | Assigned   |

  @negative @authentication @getAssetBySNO
  Scenario: Verify that asset cannot be get with invalid authentication token
    And I have a invalid authentication token for asset by SNO
    When I send a GET request to get asset by SNO
    And The response status code for get asset by SNO 401

  @negative @authentication @getAssetBySNO
  Scenario: Verify that asset cannot be get using invalid SNO
    And I have a valid authentication token for asset by SNO
    And I have a invalid SNO for get asset by SNO
    When I send a GET request to get asset by SNO
    Then The response status code for get asset by SNO 400

  @negative @getAssetBySNO
  Scenario: Verify that asset cannot be get with invalid method
    And I have a valid authentication token for asset by SNO
    When I have a POST request to get asset by SNO
    And The response status code for get asset by SNO 405
    And The response for invalid method for get asset by SNO should be "Method Not Allowed"









    






