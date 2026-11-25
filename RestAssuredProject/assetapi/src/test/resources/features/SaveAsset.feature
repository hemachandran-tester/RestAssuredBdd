Feature: Save Asset
  To save a asset there should be AssetName, SerialNumber, EmpID, Status ,
  Type, PurchaseDate , WarrantyDate, Location, ModelName, OperatingSystem,
  ReturnDate, AddedBy, AssignedDate, AssignedBy, AssetSourcedBy

  Background:
    Given The SaveAsset API endpoint is available
    And I have a valid authentication token for saveAsset
    And The request content type is Json

  @smoke @positive
  Scenario: Successfully add an asset with valid details
    Given I add asset with valid details
    When I send POST request to save the asset
    Then The response status code should be 200
    And Created asset details should be displayed

  @positive
  Scenario: Successfully add an asset with only mandatory details
    Given I add asset with valid details
    When I send POST request to save the asset
    Then The response status code should be 200
    And Created asset details should be displayed

    # Validate with Existing Serial NUmber
  @negative
  Scenario: Try to create a asset with a Serial Number that already exists
    Given I add asset with a existing serialNumber
    When I send POST request to save the asset
    Then The response status code should be 409
    And The error message for existing serialNumber should be "Already Serial Number exists"

      # Validate with Empty and Null Values
  @negative
  Scenario Outline: Validate Save Asset fields with empty

    Given I add an asset with "<field>" set to "<valueType>"
    When I send POST request to save the asset
    Then The response status code should be 400
    And The error message should be "<expectedMessage>"

    Examples:
      | field           | valueType | expectedMessage                                    |
      | assetName       | empty     | Must be alphanumeric, underscores, and spaces only |
      | serialNumber    | empty     | must not be blank                                  |
      | status          | empty     | must not be blank                                  |
      | purchaseDate    | empty     | Purchase Date is required                          |
      | operatingSystem | empty     | must not be blank                                  |
      | addedBy         | empty     | must not be blank                                  |
      | assetSourcedBy  | empty     | must not be blank                                  |
      | type            | empty     | must not be blank                                  |
      | warrantyDate    | empty     | Warranty Date is required                          |

      # Invalid inputs fields
  @negative
  Scenario Outline: Validate the save asset fields with invalid inputs

    Given I add asset with invalid details "<field>" set to "<valueType>"
    When I send POST request to save the asset
    Then The response status code should be <statusCode>
    And The error message for "<field>" should be "<expectedMessage>"

    Examples:
      | field           | valueType | statusCode | expectedMessage                                        |
      | assetName       | #         | 400        | Must be alphanumeric, underscores, and spaces only     |
      | serialNumber    | #         | 400        | Must be a Alphanumeric & _                             |
      | empId           | #         | 400        | Must be V followed by 5 digits or empty                |
      | purchaseDate    | @         | 400        | Bad Request                                            |
      | warrantyDate    | @         | 400        | Bad Request                                            |
      | location        | @         | 400        | Location must contain only letters, spaces, or hyphens |
      | modelName       | @         | 400        | ModelName must be alphanumeric & _                     |
      | operatingSystem | @         | 400        | operatingSystem must be alphanumeric & _               |
      | returnDate      | @         | 400        | Bad Request                                            |
      | addedBy         | @         | 400        | Must be V followed by 5 digits or empty                |
      | assignedDate    | @         | 400        | Bad Request                                            |
      | assignedBy      | @         | 400        | Must be V followed by 5 digits or empty                |
      | assetSourcedBy  | 😊😊      | 400        | Asset sourced by must be  Alphanumeric & _             |
      | type            | 😊😊      | 400          | type must be  Alphanumeric & _                         |

  @negative @authentication
  Scenario: Validate the save asset with invalid Authorization token for SaveAsset
    Given I add asset with valid details
    And I have an invalid authentication token for saveAsset
    When I send POST request to save the asset
    Then The response status code should be 401 Invalid or Expired Token

  @negative
  Scenario Outline: Validate Save Asset with missing mandatory fields
    Given I add an asset request without the "<field>" field
    When I send POST request to save the asset
    Then the response status code should be 400
    And the error message for "<field>" should be "<expectedMessage>"
    Examples:
      | field        | expectedMessage           |
      | assetName    | Asset name is required    |
      | serialNumber | Serial number is required |
      | empId        | Employee ID is required   |
      | purchaseDate | Purchase Date is required |
      | warrantyDate | Warranty Date is required |

  @negative
  Scenario Outline: Validate save asset date fields with invalid formates
    Given I add asset with invalid data formats for "<field>" with "<invalid date>"
    When I send POST request to save the asset
    Then The response status code should be 400

    Examples:
      | field        | invalid date |
      | purchaseDate | 15-07-2026   |
      | warrantyDate | 2029/07/15   |
      | returnDate   | 2026-40-13   |
      | assignedDate | 2026-13-40   |

      # Warranty date before the purchase date
  @negative
  Scenario Outline: Validate Warranty date before the purchase date
    Given I add an asset with Purchase Date "<PurchaseDate>" and Warranty Date "<WarrantyDate>"
    When I send POST request to save the asset
    Then The response status code should be 409
    And The error message should be "Warranty Date must not be before Purchase Date"

    Examples:
      | PurchaseDate | WarrantyDate |
      | 2026-10-15   | 2026-10-10   |

    @negative
    Scenario Outline: Validate with different methods
      Given I add asset with valid details
      When I send "<methods>" request to save the asset
      Then The response status code should be 405
      And The error message should be "<expectedMessage>"
      Examples:
        | methods | expectedMessage    |
        | GET     | Method Not Allowed |
        | PUT     | Method Not Allowed |
        | PATCH   | Method Not Allowed |
        | DELETE  | Method Not Allowed |


















