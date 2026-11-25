package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import  org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GetAssetBySNOStep {

    // Request and Response
    private RequestSpecification request;
    private Response response;

    // Base URL and Endpoint
    private final String baseURl = ConfigReader.getProperty("base.url");
    private final String endpoint = ConfigReader.getProperty("get.assert.by.SNO");
    private final String assetSNO = CredentialsReader.getCredential("asset.SNO");

    // Token
    private final String token = CredentialsReader.getCredential("valid.AuthenticationToken");
    private final String invalidToken = CredentialsReader.getCredential("invalid.AuthenticationToken");

    @Given("I have the base URL for get asset by SNO")
    public void iHaveTheBaseURLFOrGetAssetBySNO() {

        request = given()
                .baseUri(baseURl);

    }

    @And("I have a valid endpoint for the get asset by SNO")
    public void iHaveAValidEndpointForTheGetAssetBySNO() {

        request = request
                .given()
                .basePath(endpoint);

    }

    @And("I have a valid Asset SNO")
    public void iHaveAValidAssetSNO() {

        request = request
                .pathParam("sno", assetSNO);

    }

    @And("I have required headers for get asset by SNO")
    public void iHaveRequiredHeadersForGetAssetBySNO() {

        request = request
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

    }

    @And("The request content type is for get asset by SNO is {string}")
    public void theRequestContentTypeIsForGetAssetBySNOIs(String expectedContentType) {

        request = request
                .contentType(expectedContentType);

    }

    @And("I have a valid authentication token for asset by SNO")
    public void iHaveAValidAuthenticationTokenForAssetBySNO() {

        request = request
                .header("Authorization", "Bearer " + token);

    }

    @When("I send a GET request to get asset by SNO")
    public void iSendAGETRequestToGetAssetBySNO() {

        response = request
                .when()
                .get();

    }

    @Then("The response status code for get asset by SNO {int}")
    public void theResponseStatusCodeForGetAssetBySNO(int expectedStatusCode) {

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code is not matched");

    }

    @And("The response content for get asset by SNO is {string}")
    public void theResponseContentForGetAssetBySNOIs(String expectedType) {

        Assert.assertEquals(response.getContentType(), expectedType);

    }

    @And("The response should contains all the {string}")
    public void theResponseShouldContainsAllThe(String field) {

        Assert.assertTrue(
                response.jsonPath().getMap("").containsKey(field),
                "Missing field: " + field
        );

    }

    @And("the asset serialNumber is same as the given one {string}")
    public void theAssetSerialNumberIsSameAsTheGivenOneSerialNumber(String serialNumber) {

        Assert.assertEquals(serialNumber, assetSNO);

    }

    @And("The status {string} should be either {string}")
    public void theStausShouldBeEither(String field, String value) {

        String actualStatus = response.jsonPath().getString(field);

        Assert.assertTrue(
                actualStatus.equalsIgnoreCase("UnAssigned")
                        || actualStatus.equalsIgnoreCase("Assigned"),
                "Invalid status received: " + actualStatus
        );

    }

    @And("I have a invalid authentication token for asset by SNO")
    public void iHaveAInvalidAuthenticationTokenForAssetBySNO() {

        request = request
                .header("Authorization", "Bearer " + invalidToken);

    }

    @And("I have a invalid SNO for get asset by SNO")
    public void iHaveAInvalidSNOForGetAssetBySNO() {

        // Invalid SNO
        String invalidSNO = "SN10005ASDFGHJKL";

        request = request
                .pathParam("sno", invalidSNO);

    }

    @When("I have a POST request to get asset by SNO")
    public void iHaveAPOSTRequestToGetAssetBySNO() {

        response = request
                .when()
                .post();

    }

    @And("The response for invalid method for get asset by SNO should be {string}")
    public void theResponseForInvalidMethodForGetAssetBySNOShouldBe(String message) {

        String responseBody = response.getBody().asString();

        Assert.assertTrue(responseBody.contains(message), "Expected error message was not found: " + message);

    }

}
