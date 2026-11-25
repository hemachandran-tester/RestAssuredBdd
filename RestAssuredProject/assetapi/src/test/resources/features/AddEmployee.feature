Feature: Add Employee
    Each Employee should have a empId, firstName, lastName,
    role, mail, mobile, location, status, department, designation

    Background:
        Given the Add Employee API endpoint is available
        And the request content type is Json
        And I have a valid authentication token

    @smoke @positive
    Scenario: Successfully add an employee with valid details
        Given i add employee with valid details
        When i send POST request to add the employee
        Then the response status code should be 201
        And created employee details should be displayed
        And the employee should be saved successfully

    @positive
    Scenario: Successfully add an employee with only mandatory details
        Given i add employee with only mandatory details
        When i send POST request to add the employee
        Then the response status code should be 201
        And created employee details should be displayed
        And the employee should be saved successfully

    @negative
    Scenario Outline: Validate when  employee fields is missing
        Given i add an employee with "<field>" missing
        When i send POST request to add the employee
        Then the response status code should be 400
        And the error message "<field>" should be "<message>"

        Examples:

          | field       | message          |
          | empID       | must not be null |
          | firstName   | must not be null |
          | lastName    | must not be null |
          | role        | must not be null |
          | mail        | must not be null |
          | mobile      | must not be null |
          | location    | must not be null |
          | status      | must not be null |
          | department  | must not be null |
          | designation | must not be null |

  @negative
    Scenario Outline: Validate when mandatory fields are empty
        Given i add an employee with mandatory "<field>" is empty
        When i send POST request to add the employee
        Then the response status code should be 400
        And the error message "<field>" should be "<message>"

        Examples:

          | field       | message                                                   |
          | empID       | must not be blank                                         |
          | lastName    | Last Name must contain only letters, spaces, or hyphens   |
          | mail        | Invalid email address                                     |
          | mobile      | Valid Mobile Number Needed                                |
          | location    | Location must contain only letters, spaces, or hyphens    |
          | department  | Department must contain only letters, spaces, or hyphens  |
          | designation | Designation must contain only letters, spaces, or hyphens |

  @negative
  Scenario Outline: Validate employee fields with null values
    Given i add an employee with "<field>" as null
        When i send POST request to add the employee
        Then the response status code should be 400
        And the error message "<field>" should be "<message>"

        Examples:

          | field       | message          |
          | firstName   | must not be null |
          | lastName    | must not be null |
          | mail        | must not be null |
          | mobile      | must not be null |
          | location    | must not be null |
          | department  | must not be null |
          | designation | must not be null |

  @negative
    Scenario: Reject an employee with duplicated employee ID
        Given i add an employee with duplicate employee ID
        When i send POST request to add the employee
        Then the response status code should be 409

    @negative
    Scenario: Reject an employee with duplicate Email
        Given i add an employee with a duplicate Email
        When i send POST request to add the employee
        Then the response status code should be 409

    @negative
    Scenario: Reject an employee with a duplicate mobile number
        Given i add an employee with a duplicate mobile number
        When i send POST request to add the employee
        Then the response status code should be 409


    @negative @authentication
    Scenario: Reject an employee with an invalid authentication token
        Given I have an invalid authentication token
        And i add employee with valid details
        When i send POST request to add the employee
        Then the response status code should be 401

    @negative
    Scenario: Reject an employee with a invalid HTTP method
        Given i add an employee with invalid HTTP method
        When i send a GET request to add an employee
        Then the response status code should be 405
        And the error "error" should be "Method Not Allowed"

    @negative
    Scenario: Reject an employee with a invalid role
        Given i add an employee with invalid role
        When i send POST request to add the employee
        Then the response status code should be 400
        And the error message "role" should be "Role can be Admin or Employee"

    @negative
    Scenario: Reject an employee with a invalid status
        Given i add an employee with invalid status
        When i send POST request to add the employee
        Then the response status code should be 400
        And the error message "status" should be "status can be Active or Inactive"








