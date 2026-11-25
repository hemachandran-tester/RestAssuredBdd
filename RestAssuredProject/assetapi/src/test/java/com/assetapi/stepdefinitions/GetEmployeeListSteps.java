package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import io.restassured.common.mapper.TypeRef;
import java.util.*;

import static io.restassured.RestAssured.given;

public class GetEmployeeListSteps {

    // API Request and Response Objects
    private RequestSpecification request;
    private Response response;

    // Base URL and Endpoint Configuration
    private String baseURL = ConfigReader.getProperty("base.url");
    private String endPoint = ConfigReader.getProperty("get.employee.list");

    // Authentication Credentials Configuration
    private String token = CredentialsReader.getCredential("valid.AuthenticationToken");
    private String invalidToken = CredentialsReader.getCredential("invalid.AuthenticationToken");

    // Background
    @Given("I have the base URL")
    public void iHaveTheBaseURL() {

        request = given()
                .baseUri(baseURL);

    }

    @And("the employee list API endpoint is available")
    public void theEmployeeListAPIEndpointIsAvailable() {

        request = request
                .basePath(endPoint);

    }

    @And("I have a valid authentication token for employeeList API")
    public void iHaveAValidAuthenticationTokenForEmployeeAPI() {

        request = request
                .header("Authorization", "Bearer " + token);

    }

    @And("I have required headers for the employeeList")
    public void iHaveRequiredHeadersForTheEmployeeList() {

        request = request
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

    }

    @And("the request content type is for employeeList {string}")
    public void theRequestContentTypeIs(String expectedContentType) {

        request = request
                .contentType(expectedContentType);

    }

    @When("I send a GET request to the employeeList API")
    public void iSendAGETRequestToTheEmployeeListAPI() {
        response = request
                .when()
                .get();

    }

    @Then("the response status code for employeeList API should be {int}")
    public void theResponseStatusCodeForEmployeeListAPIShouldBe(int expectedStatusCode) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Status code validation failed"
        );

    }

    @And("the response content of employeeList type should be {string}")
    public void theResponseContentTypeShouldBe(String expectedType) {

        Assert.assertEquals(response.getContentType(), "application/json");

    }

    @Then("The response for employeeList should contains {string}")
    public void theResponseForEmployeeListShouldContains(String expectedField) {

        Object actualField = response.jsonPath().get(expectedField);

        Assert.assertNotNull(actualField, "Field '" + expectedField + "' is missing");

    }

    @Given("I send a request with invalid authentication token")
    public void iSendAGETRequestWithoutAuthenticationToken() {

        request = request
                .header("Authorization", "Bearer " + invalidToken);
    }

    @Then("The response status code for employeeList should be {int}")
    public void theResponseStatusCodeForEmployeeListShouldBe(int expectedStatusCode) {

        Assert.assertEquals(expectedStatusCode, 401);

    }

    @Given("I send a request without an authentication token")
    public void iSendAGETRequestWithoutAnAuthenticationToken() {

        request = request.
                given().header("Authorization", "Bearer ");
    }

    public void validateField(Map<String, Object> employee, String field) {

        Assert.assertNotNull(employee.get(field),
                field + " should not be null");

        Assert.assertFalse(employee.get(field).toString().trim().isEmpty(),
                field + " should not be empty");
    }

    @And("the employeeList response body should not be empty")
    public void theEmployeeListResponseBodyShouldNotBeEmpty() {

        List<Map<String, Object>> employees =
                response.as(new TypeRef<List<Map<String, Object>>>() {
                });

        Assert.assertNotNull(employees, "Employee list should not be null");
        Assert.assertFalse(employees.isEmpty(), "Employee list should not be empty");

        for (Map<String, Object> employee : employees) {

            validateField(employee, "empId");
            validateField(employee, "firstName");
            validateField(employee, "lastName");
            validateField(employee, "role");
            validateField(employee, "mail");
            validateField(employee, "mobile");
            validateField(employee, "location");
            validateField(employee, "status");
            validateField(employee, "department");
            validateField(employee, "designation");
        }
    }

    @When("I send a POST request to the employeeList API")
    public void iSendAPOSTRequestToTheEmployeeListAPI() {

        response = request.
                when().post(baseURL + endPoint);

    }

    public void validateEmployeeIDsAreUnique(List<Map<String, Object>> employees) {
        List<String> employeeIds = new ArrayList<>();

        for (Map<String, Object> employee : employees) {
            employeeIds.add(employee.get("empId").toString());
        }

        Set<String> uniqueEmployeesIds = new HashSet<>(employeeIds);

        Assert.assertEquals(uniqueEmployeesIds.size(), employeeIds.size(),
                "Duplicate employee IDs found");
    }

    @Then("The employee IDs in response should be unique")
    public void theEmployeeIDsInResponseShouldBeUnique() {

        List<Map<String, Object>> employees =
                response.as(new TypeRef<List<Map<String, Object>>>() {
                });

        validateEmployeeIDsAreUnique(employees);

    }

}
