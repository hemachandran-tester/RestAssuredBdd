Feature: Login to Asset

    Scenario: Login to Asset with valid credentials
        Given the login API is available
        When i login with valid credentials
        Then the response status should be 200
        And the response should contain an authentication token

    Scenario: Login to Asset with invalid credentials
        Given the login API is available
        When i login with invalid credentials
        Then the response status should be 401

    Scenario Outline: Login to Asset with invalid employeeID
        Given the login API is available
        When i login with employeeID "<employeeID>" and valid password
        Then the response status should be <status>
        And the response field "<errorField>" should equal "<errorMessage>"

        Examples:
            | employeeID | status | errorField | errorMessage                   |
            | V000       | 400    | empId      | Must be V followed by 5 digits |
            | Vuyf       | 400    | empId      | Must be V followed by 5 digits |
            | V0000123   | 400    | empId      | Must be V followed by 5 digits |

    Scenario Outline: Login to Asset with invalid password
        Given the login API is available
        When i login with valid employeeID and password "<password>"
        Then the response status should be <status>
        And the response field "<errorField>" should equal "<errorMessage>"

        Examples:
            | password | status | errorField | errorMessage                                 |
            | abc      | 400    | password   | Password must be between 8 and 20 characters |
            | He       | 400    | password   | Password must be between 8 and 20 characters |
            | H@@@234  | 400    | password   | Password must be between 8 and 20 characters |
