Feature: Verify OTP

  Verify the password can be changed with the OTP received from the registered
  employee mail.

  Background:
    Given I have a base URL for the verify OTP
    And I have a valid endpoint for verify OTP
    And I have required headers for verify OTP
    And The request content type for verify OTP is "application/json"

  @positive @smoke @verifyOTP
  Scenario: Verify that a password can be change with a valid OTP
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I have a valid new password
    And I hava valid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response staus code for verify OTP should be 200
    And The response for verify OTP should contains "Password updated successfully"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be change with invalid OTP
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I have a valid new password
    And I hava invalid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Invalid OTP"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be changed without OTP
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I have a valid new password
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be changed without mail
    And I have a valid authentication token for verify OTP
    And I have a valid old password
    And I have a valid new password
    And I hava valid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be changed without old password
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid new password
    And I hava valid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be changed without new password
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I hava valid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be changed with OTP in invalid length
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I hava invvalid length OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

  Scenario Outline: Verify that password cannot be changed with invalid mail
    And I have a valid authentication token for verify OTP
    And I have a "<value>" mail for verify OTP
    And I have a valid old password
    And I hava valid length OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

    Examples:
      | value                                      |
      | test@gmail.com                             |
      | john.doe@gmail.com                         |
      | user123@yahoo.com                          |
      | wehede3172@anonmart.com                    |
      | first.last@company.co.in                   |
      | test_user@example.org                      |
      | user+test@gmail.com                        |
      | plainaddress                               |
      | test@                                      |
      | @gmail.com                                 |
      | test@gmail                                 |
      | test gmail@gmail.com                       |
      | test@@gmail.com                            |
      | test@gmail..com                            |
      | .test@gmail.com                            |
      | test@gmail.com.                            |
      | a@gmail.com                                |
      | 1234567890@gmail.com                       |
      | user.name+123@gmail.com                    |
      | user-name@gmail.com                        |
      | user_name@gmail.com                        |
      | USER@GMAIL.COM                             |
      | test@sub.example.com                       |
      | test@localhost                             |
      | test@123.com                               |
      | test@gmail.com                             |
      | test@gmail.c                               |
      | <script>alert(1)</script>@gmail.com        |
      | test'; DROP TABLE users;--@gmail.com       |
      | test"@gmail.com                            |
      | test\@gmail.com                            |
      | test😀@gmail.com                           |
      | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com |

  Scenario Outline: Verify that password cannot be changed with invalid old password
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a "<value>" valid old password
    And I hava valid length OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

    Examples:
      | value                            |
      | hema123A@                        |
      | Hema123A@                        |
      | hema123a@                        |
      | HEMA123A@                        |
      | hema123                          |
      | hema123@@                        |
      | hema123A@@                       |
      | 12345678                         |
      | abcdefgh                         |
      | ABCDEFGH                         |
      | 12345678A@                       |
      | Hema@123                         |
      | @#$%^&*()                        |
      | hema 123A@                       |
      | hema123A@123                     |
      | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa |
      | 12345678901234567890             |
      | password                         |
      | Password123                      |
      | P@ssw0rd!                        |
      | oldPassword123                   |
      | <script>alert(1)</script>        |
      | '; DROP TABLE users;--           |
      | hema😀123A@                      |

  Scenario Outline: Verify that password cannot be changed with invalid new password
    And I have a valid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I have a "<value>" new password
    And I hava valid length OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

    Examples:
      | value                            |
      | hema123A@                        |
      | Hema123A@                        |
      | hema123a@                        |
      | HEMA123A@                        |
      | hema123                          |
      | hema123@@                        |
      | hema123A@@                       |
      | 12345678                         |
      | abcdefgh                         |
      | ABCDEFGH                         |
      | 12345678A@                       |
      | Hema@123                         |
      | @#$%^&*()                        |
      | hema 123A@                       |
      | hema123A@123                     |
      | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa |
      | 12345678901234567890             |
      | password                         |
      | Password123                      |
      | P@ssw0rd!                        |
      | oldPassword123                   |
      | <script>alert(1)</script>        |
      | '; DROP TABLE users;--           |
      | hema😀123A@                      |


  Scenario Outline: Verify that password cannot be changed with invalid OTP
    And I have a valid authentication token for verify OTP
    And I have a "<value>" mail for verify OTP
    And I have a valid old password
    And I hava valid length OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Bad Request"

    Examples:
      | value        |
      | 12345        |
      | 1234567      |
      | abcdef       |
      | abc123       |
      | 12 728       |
      | 17@728       |
      | 17#728       |
      | -17272       |
      | 1727.8       |
      | 0000000      |
      | 99999999     |
      | 123456789012 |
      | 17272@       |
      | +172728      |
      | 172728.      |
      | １７２７２８       |
      | 😀72728      |

  @negative @smoke @verifyOTP
  Scenario: Verify that password cannot be change with invalid authentication token
    And I have a invalid authentication token for verify OTP
    And I have a valid mail for verify OTP
    And I have a valid old password
    And I have a valid new password
    And I hava invalid OTP for verify OTP
    When I send a POST request to verify OTP
    Then The response status code for verify OTP should be 400
    And The response for verify OTP should contains "Invalid OTP"

