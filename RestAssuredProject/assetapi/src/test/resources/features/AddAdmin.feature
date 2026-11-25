Feature: Add Admin
  Verify that a new Admin can be successfully added.

  Background:
    Given the Add Admin API endpoint is available
    And I have the Add Admin Endpoint
    And I have a valid authentication token for Add Admin
    And the request content type is "application/json"

  @smoke @addAdmin
  Scenario: Verify that Admin can be added with valid details
    Given I have valid Admin details
    When I send a POST request to Add Admin API
    Then the response status code for Add Admin should be 201
    And the response should contain Admin ID
    And the response content type should be "application/json"

  @smoke @addAmin @negative
  Scenario: Verify that Admin cannot be added with an existing employee ID
    Given I have valid Admin details with an existing employee ID
    When I send a POST request to Add Admin API
    Then the response status code for Add Admin should be 400

  @addAmin @negative
  Scenario:  Verify that Admin cannot be added with an existing mail
    Given I have valid Admin details with an existing email
    When I send a POST request to Add Admin API
    Then the response status code for Add Admin should be 400

  @addAmin @negative
  Scenario Outline: Verify that Admin cannot be added with null or empty fields
    Given I have Admin details with "<field>" and "<value>" fields
    When I send a POST request to Add Admin API
    Then the response status code for Add Admin should be 400

    Examples:
      | field     | value |
      | firstName | null  |
      | firstName | empty |
      | lastName  | null  |
      | lastName  | empty |
      | empId     | null  |
      | empId     | empty |
      | mail      | null  |
      | mail      | empty |
      | status    | null  |
      | status    | empty |
      | role      | null  |
      | role      | empty |
      | location  | null  |
      | location  | empty |
      | password  | null  |
      | password  | empty |

  Scenario Outline: Verify that Admin cannot be added with special characters
    Given I have Admin details with special characters for the fields "<field>" and "<value>"
    When I send a POST request to Add Admin API
    Then the response should be "<message>"
    And the response status code for Add Admin should be 400

    Examples:
      | field     | value                               | message                                                  |
      | firstName | Hema@                               | First Name must contain only letters, spaces, or hyphens |
      | lastName  | chandran@                           | Last Name must contain only letters, spaces, or hyphens  |
      | mail      | hemacnddan198679#$%^&*@verinite.com | Invalid email address                                    |
      | status    | A#$#@                               | status can be Active or Inactive                         |
      | role      | Employee@                           | Role can be Admin or Employee                            |
      | location  | Bangalore@                          | Location must contain only letters, spaces, or hyphens   |
      | otp       | 123@4567                            | Only number format will ne accepted                      |

  Scenario: Verify the Admin cannot be added with invalid Token
    Given I have Admin details but with invalid token
    And I have an invalid authentication token for the Add Admin
    When I send a POST request to Add Admin API
    And the response status code for Add Admin should be 400

  Scenario Outline: Verify that Admin cannot be add with different methods
    Given I have Admin details but with Invalid methods
    And I have a valid authentication token for Add Admin
    When I send "<request>" to the Add Admin API
    Then the response status code for Add Admin should be 405
    And the response should contain "<message>"

    Examples:
      | request | message            |
      | GET     | Method Not Allowed |
      | PUT     | Method Not Allowed |
      | DELETE  | Method Not Allowed |

# Verify that Admin can be added with valid details
# Verify that Admin cannot be added with an existing employee ID
# Verify that Admin cannot be added with an existing mail
# Verify that Admin cannot be added with null or empty fields
# Verify that Admin cannot be added with special characters












