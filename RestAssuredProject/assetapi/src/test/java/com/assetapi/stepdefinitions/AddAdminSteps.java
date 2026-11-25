
package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import com.assetapi.readers.JsonReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class AddAdminSteps {

    private Map<String, Object> adminRequest;
    private RequestSpecification request;
    private Response response;

    private String existingEmpID = "V33335";
    private String existingEmail = "employee5@gmail.co";

    private String baseurl =
            ConfigReader.getProperty("base.url");

    private String addAdminEndpoint =
            ConfigReader.getProperty("add.admin.endpoint");

    private String token =
            CredentialsReader.getCredential("valid.AuthenticationToken");

    private String invalidToken = CredentialsReader.getCredential("invalid.AuthenticationToken");

    // Background

    @Given("the Add Admin API endpoint is available")
    public void theAddAdminAPIEndpointIsAvailable() {

        request = given()
                .baseUri(baseurl);
    }

    @And("I have the Add Admin Endpoint")
    public void iHaveTheAddAdminEndpoint() {

        request.basePath(addAdminEndpoint);
    }

    @And("I have a valid authentication token for Add Admin")
    public void iHaveAValidAuthenticationTokenForAddAdmin() {

        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                    "Token is invalid or missing"
            );
        }

        request.header("Authorization", "Bearer " + token);
    }

    @And("the request content type is {string}")
    public void theRequestContentTypeIs(String contentType) {

        request.contentType(contentType)
                .accept(ContentType.JSON);
    }
    // Request preparation

    @Given("I have valid Admin details")
    public void iHaveValidAdminDetails() {

        adminRequest = JsonReader.readJsonFile(
                "testdata/AddAdmin.json"
        );
    }
    // API execution

    @When("I send a POST request to Add Admin API")
    public void iSendAPOSTRequestToAddAdminAPI() {

        response = request
                .body(adminRequest)
                .when()
                .post();
    }
    // Response validations

    @Then("the response status code for Add Admin should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Response status code mismatch"
        );
    }

    @And("the response should contain Admin ID")
    public void theResponseShouldContainAdminID() {

        String actualResponse = response.asString();

        Assert.assertNotNull(actualResponse);

        Assert.assertEquals(actualResponse,
                "Admin registered successfully.",
                "Admin already exists.");
    }

    @And("the response content type should be {string}")
    public void theResponseContentTypeShouldBe(String expectedContentType) {

        String actualContentType = response.getContentType();

        Assert.assertEquals(
                actualContentType,
                expectedContentType,
                "Response content type does not match"
        );
    }

    @Given("I have valid Admin details with an existing employee ID")
    public void iHaveValidAdminDetailsWithAnExistingEmployeeID() {
        adminRequest = JsonReader.readJsonFile(
                "testdata/AddAdmin.json"
        );
        adminRequest.put("employeeID", existingEmpID);
    }

    @Given("I have valid Admin details with an existing email")
    public void iHaveValidAdminDetailsWithAnExistingEmail() {
        adminRequest = JsonReader.readJsonFile("testdata/AddAdmin.json");

        adminRequest.put("mail", existingEmail);
    }

    @Given("I have Admin details with {string} and {string} fields")
    public void iHaveAdminDetailsWithAndFields(String field, String value) {

        adminRequest = JsonReader.readJsonFile("testdata/AddAdmin.json");

        if (value.equalsIgnoreCase("null")) {
            adminRequest.put(field, null);
        } else if (value.equalsIgnoreCase("empty")) {
            adminRequest.put(field, "");
        }

    }

    @Given("I have Admin details with special characters for the fields {string} and {string}")
    public void iHaveAdminDetailsWithSpecialCharactersForTheFieldsAndValue(
            String field, String value) {

        adminRequest = JsonReader.readJsonFile("testdata/AddAdmin.json");

        adminRequest.put(field, value);

    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String expectedMessage) {

        String actualMessage;

        String responseBody = response.getBody().asString();

        try {
            Map<String, String> responseMap =
                    new com.fasterxml.jackson.databind.ObjectMapper()
                            .readValue(responseBody, Map.class);

            actualMessage = responseMap.values()
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(responseBody);

        } catch (Exception e) {
            actualMessage = responseBody;
        }

        Assert.assertEquals(
                actualMessage.trim(),
                expectedMessage.trim(),
                "Response message mismatch"
        );
    }

    @Given("I have Admin details but with invalid token")
    public void iHaveAdminDetailsButWIthInvalidToken() {

        adminRequest = JsonReader.readJsonFile("testdata/AddAdmin.json");

    }

    @And("I have an invalid authentication token for the Add Admin")
    public void iHaveAnInvalidAuthenticationTokenForTheAddAdmin() {

        request.header("Authorization", "Bearer " + invalidToken);

    }

    @Given("I have Admin details but with Invalid methods")
    public void iHaveAdminDetailsButWithInvalidMethods() {

        adminRequest = JsonReader.readJsonFile("testdata/AddAdmin.json");

    }

    @When("I send {string} to the Add Admin API")
    public void iSendMethodToTheAddAdminAPI(String method) {

        request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(adminRequest);

        switch (method.toUpperCase()) {

            case "POST":
                response = request
                        .when()
                        .post(baseurl + addAdminEndpoint);
                break;

            case "PUT":
                response = request
                        .when()
                        .put(baseurl + addAdminEndpoint);
                break;

            case "GET":
                response = request
                        .when()
                        .get(baseurl + addAdminEndpoint);
                break;

            case "DELETE":
                response = request
                        .when()
                        .delete(baseurl + addAdminEndpoint);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported HTTP method: " + method);
        }

        response.prettyPrint();

    }

    @And("the response should contain {string}")
    public void theResponseShouldContain(String expectedMessage) {
        String actualResponse = response.getBody().asString();

        Assert.assertTrue(
                actualResponse.contains(expectedMessage),
                "Expected response to contain: " + expectedMessage
                        + " but actual response was: " + actualResponse
        );
    }

}



