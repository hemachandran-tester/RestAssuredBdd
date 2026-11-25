
package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class SendOTPToMail {

    // Request and Response
    private RequestSpecification request;
    private Response response;

    // Base URL and Endpoint
    private final String baseURL =
            ConfigReader.getProperty("base.url");

    private final String endPoint =
            ConfigReader.getProperty("send.opt.to.mail");

    private final String empID =
            CredentialsReader.getCredential("send.opt.to.mail.empID");

    // Authentication Tokens
    private final String token =
            CredentialsReader.getCredential("valid.AuthenticationToken");

    private final String invalidToken =
            CredentialsReader.getCredential("invalid.AuthenticationToken");


    // Base URL
    @Given("I have a valid base URL for the send OPT to mail")
    public void iHaveAValidBaseURLForTheSendOTPToMail() {

        request = given()
                .baseUri(baseURL)
                .header("Accept", "*/*");

    }


    // Endpoint
    @And("I have a valid endpoint for the send OPT to mail")
    public void iHaveAValidEndPointForTheSendOTPToMail() {

        request.basePath(endPoint);

    }


    // Valid Employee ID
    @And("I have a valid employee ID for the send OPT to mail")
    public void iHaveAValidEmployeeIDForTheSendOTPToMail() {

        request.queryParam("empId", empID);

    }


    // Valid Authentication Token
    @And("I have a valid authentication token for send OPT to mail")
    public void iHaveAValidAuthenticationTokenForSendOTPToMail() {

        request.header("Authorization", "Bearer " + token);

    }


    // Send POST Request
    @Then("I send a POST request to the send OTP to mail")
    public void iSendAPOSTRequestToTheSendOTPToMail() {

        response = request
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();

    }


    // Validate Status Code
    @Then("The response status code for send OTP to mail should be {int}")
    public void theResponseStatusCodeForSendOTPToMailShouldBe(
            int expectedStatusCode) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Unexpected response status code"
        );

    }


    // Validate Empty Response
    @And("The response for send OPT to mail should be empty")
    public void theResponseForSendOTPToMailShouldBeEmpty() {

        String responseBody = response.getBody().asString();

        Assert.assertTrue(
                responseBody == null || responseBody.isEmpty(),
                "Response body is not empty"
        );

    }


    // Validate Response Contains
    @And("The response should contains {string}")
    public void theResponseShouldContains(String errorMessage) {

        String responseBody = response.getBody().asString();

        Assert.assertTrue(
                responseBody.contains(errorMessage),
                "Expected error message not found: " + errorMessage
        );

    }


    // Invalid Employee ID
    @And("I have a invalid emp ID for send OPT to mail")
    public void iHaveAInvalidEmpIDForSendOTPToMail() {

        String invalidEmpID = "V00DFRTYUILOIUYTRE";

        request = given()
                .baseUri(baseURL)
                .basePath(endPoint)
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .queryParam("empId", invalidEmpID);

    }


    // Validate Error Message
    @And("The error message should be {string} for send OTP to mail")
    public void theErrorMessageShouldBeForSendOTPToMail(
            String errorMessage) {

        String responseBody = response.getBody().asString();

        Assert.assertTrue(
                responseBody.contains(errorMessage),
                "Expected error message not found: " + errorMessage
        );

    }


    // Invalid Authentication Token
    @And("I have a invalid authentication token for send OTP to mail")
    public void iHaveAInvalidAuthenticationTokenForSendOTPToMail() {

        request = given()
                .baseUri(baseURL)
                .basePath(endPoint)
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + invalidToken)
                .queryParam("empId", empID);

    }

    @And("I dont have a authentication toke for the send OTP to mail")
    public void iDontHaveAAuthenticationTokenForTheSendOTPToMail(){

        request.given()
                .baseUri(baseURL)
                .basePath(endPoint)
                .header("Accept","*/*")
                .queryParam("empId",empID);


    }

}
