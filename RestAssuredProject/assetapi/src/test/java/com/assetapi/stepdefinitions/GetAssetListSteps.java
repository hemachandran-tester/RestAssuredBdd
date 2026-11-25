package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;


public class GetAssetListSteps {

    private static final Logger log = LoggerFactory.getLogger(GetAssetListSteps.class);
    // API Request and Response Objects
    private RequestSpecification request;
    private Response response;

    // Base URL and Endpoint Configuration
    private final String baseURL = ConfigReader.getProperty("base.url");
    private final String endPoint = ConfigReader.getProperty("get.assert.list");

    // Authentication Credentials Configuration

    private final String token = CredentialsReader.getCredential("valid.AuthenticationToken");
    private final String invalidToken = CredentialsReader.getCredential("invalid.AuthenticationToken");

    // Background
    @Given("I have the base URL for assetList")
    public void iHaveTheBaseURLForAssetList() {

        request = given().baseUri(baseURL);

    }

    @And("the asset list endpoint is available")
    public void theAssetListEndPointIsAvailable() {

        request = request.basePath(endPoint);

    }

    // Token
    @And("I have a valid authentication token for assetList API")
    public void iHaveAValidAuthenticationTokenForAssetListAPI() {

        request = request.header("Authorization", "Bearer " + token);

    }

    @And("I have required headers for the assertList")
    public void iHaveRequiredHeadersForTheAsserList() {

        request = request
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    @And("the request content type is for assertList {string}")
    public void theRequestContentTypeIsFOrAssertList(String expectedContentType) {

        request = request
                .contentType(expectedContentType);
    }

    @When("I send a GET request to the assetList API")
    public void iSendAGETRequestToTheAssetListAPI() {

        response = request
                .given()
                .when()
                .get();

    }

    @Then("The response status code for assetList API should be {int}")
    public void theResponseStatusCodeForAssetListAPIShouldBe(int expectedStatusCode) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode
        );

    }

    @And("The response content of assetList type should be {string}")
    public void theResponseContentOfAssetListTypeShouldBe(String expectedType) {

        Assert.assertEquals(response.getContentType(), "application/json");

    }

    @And("I have a invalid authentication token for assetList API")
    public void iHaveAInvalidAuthenticationTokenForAssetListAPI() {

        request = request
                .given()
                .header("Authorization", "Bearer " + invalidToken);

    }

    @And("The response content for assetList should contain all the {string}")
    public void theResponseContentForAssetListShouldContainAllThe(String field) {

        Object actualField = response.jsonPath().get(field);

        Assert.assertNotNull(actualField, "Field '" + field + "' is missing");

    }

    @When("I send a POST request to assetList API")
    public void iSendAPOSTRequestToAssetListAPI() {

        response = request
                .given()
                .post();

    }

    @And("The error message for invalid Method should be {string}")
    public void theErrorMessageForInvalidMethodShouldBe(String message) {

        response = request.when().post();
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Content Type: " + response.getContentType());
        System.out.println("Response Body:");
        response.prettyPrint();

    }

    @And("All the asset IDs should be unique in assetList")
    public void allTheAssetIDsShouldBeUniqueInAssetList() {

        List<Object> assetIds = response.jsonPath().getList("assetId");
        Assert.assertNotNull(assetIds, "Asset IDs are missing from the response");
        Assert.assertFalse(assetIds.isEmpty(), "Asset ID list is empty");
        Assert.assertFalse(assetIds.contains(null),
                "Response contains null asset ID's");

        Set<Object> uniqueAssetIds = new HashSet<>(assetIds);
        Assert.assertEquals(
                uniqueAssetIds.size(),
                assetIds.size(),
                "Duplicate asset IDs found in the response"
        );

    }


}
