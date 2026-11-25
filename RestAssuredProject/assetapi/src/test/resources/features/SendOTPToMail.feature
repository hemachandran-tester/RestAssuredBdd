Feature: Send OPT to mail.

  Verify that OPT can be sent to the registered employee mail address.

  Background:
    Given I have a valid base URL for the send OPT to mail
    And I have a valid endpoint for the send OPT to mail
    And I have a valid employee ID for the send OPT to mail

  @positive @smoke @sendOTPTomail
  Scenario: Verify that OPT can be sent to the registered employee mail address successfully
    And I have a valid authentication token for send OPT to mail
    When I send a POST request to the send OTP to mail
    Then The response status code for send OTP to mail should be 200
    And The response for send OPT to mail should be empty

  @negative @smoke @sendOTPTomail
  Scenario: Verify OTP cannot be sent without authentication token
    And I dont have a authentication toke for the send OTP to mail
    When I send a POST request to the send OTP to mail
    Then The response status code for send OTP to mail should be 401
    And The response should contains "Invalid or Expired Token"

  @negative @smoke @sendOTPTomail
  Scenario: Verify OTP cannot be sent with an invalid employee ID
    And I have a valid authentication token for send OPT to mail
    And I have a invalid emp ID for send OPT to mail
    When I send a POST request to the send OTP to mail
    And The response status code for send OTP to mail should be 401

  @negative @smoke @sendOTPTomail
  Scenario: Verify OTP cannot be sent with an expired authentication token
    And I have a invalid authentication token for send OTP to mail
    When I send a POST request to the send OTP to mail
    Then The response status code for send OTP to mail should be 401




