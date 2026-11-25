Feature: Get Employee List

  Verify that the employee list API returns employee records successfully.

  Background:
    Given I have the base URL
    And the employee list API endpoint is available
    And I have required headers for the employeeList
    And the request content type is for employeeList "application/json"

  @smoke @employeeList @positive
  Scenario: Verify that employees list can get successfully
    And I have a valid authentication token for employeeList API
    When I send a GET request to the employeeList API
    Then the response status code for employeeList API should be 200
    And the response content of employeeList type should be "application/json"

  @smoke @employeeList @positive
  Scenario Outline: Verify that the response contains all the fields
    And I have a valid authentication token for employeeList API
    When I send a GET request to the employeeList API
    Then The response for employeeList should contains "<field>"
    And the response content of employeeList type should be "application/json"

    Examples:
      | field       |
      | empId       |
      | firstName   |
      | lastName    |
      | role        |
      | mail        |
      | mobile      |
      | location    |
      | status      |
      | department  |
      | designation |

  @employeeList @negative @authentication
  Scenario: Verify that employeeList cannot be get with invalid authentication token
    Given I send a request with invalid authentication token
    When I send a GET request to the employeeList API
    Then the response status code for employeeList API should be 401

  @employeeList @negative @authentication
  Scenario: Verify that employeeList cannot be get without an authentication token
    Given I send a request without an authentication token
    When I send a GET request to the employeeList API
    Then the response status code for employeeList API should be 401

  @employeeList @positive
  Scenario: Verify that employeeList response body is not empty
    And I have a valid authentication token for employeeList API
    When I send a GET request to the employeeList API
    Then the response status code for employeeList API should be 200
    And the employeeList response body should not be empty

  @employeeList @negative
  Scenario: Verify that employeeLIst cannot be get with invalid method
    And I have a valid authentication token for employeeList API
    When I send a POST request to the employeeList API
    Then the response status code for employeeList API should be 405

  @employeeList @positive
  Scenario: Verify that employee ID's are unique
    And I have a valid authentication token for employeeList API
    When I send a GET request to the employeeList API
    Then the response status code for employeeList API should be 200
    And The employee IDs in response should be unique














