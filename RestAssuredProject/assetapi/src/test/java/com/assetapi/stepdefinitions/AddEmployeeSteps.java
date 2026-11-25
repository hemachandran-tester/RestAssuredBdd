package com.assetapi.stepdefinitions;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

import com.assetapi.utils.CredentialsReader;
import org.testng.Assert;
import com.assetapi.utils.ConfigReader;
import io.restassured.response.Response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class AddEmployeeSteps {

    private Map<String, Object> employee;
    private Response response;
    private String baseUrl = ConfigReader.getProperty("base.url");
    private String addemployeeEndpoint = ConfigReader.getProperty("add.employee.endPoint");
    private String token = CredentialsReader.getCredential("valid.AuthenticationToken");
    private String employeeId = CredentialsReader.getCredential("employee.id");
    private String firstname = CredentialsReader.getCredential("employee.firstName");
    private String lastname = CredentialsReader.getCredential("employee.lastName");
    private String role = CredentialsReader.getCredential("employee.role");
    private String mail = CredentialsReader.getCredential("employee.mail");
    private String mobile = CredentialsReader.getCredential("employee.mobile");
    private String location = CredentialsReader.getCredential("employee.location");
    private String status = CredentialsReader.getCredential("employee.status");
    private String department = CredentialsReader.getCredential("employee.department");
    private String designaiton = CredentialsReader.getCredential("employee.designation");

    @Given("the Add Employee API endpoint is available")
    public void addEmployeeEndpointIsAvailable() {
        if (baseUrl == null || addemployeeEndpoint == null) {
            throw new IllegalStateException("Base URL or Add employee endpoint is missing");
        }
    }

    @Given("the request content type is Json")
    public void requestContentTypeJson() {

    }

    @Given("I have a valid authentication token")
    public void validAuthenticationToken() {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Authentication token is missing");
        }
    }

    private Map<String, Object> createEmployeeRequest() {
        employee = new HashMap<>();

        employee.put("empId", employeeId);
        employee.put("firstName", firstname);
        employee.put("lastName", lastname);
        employee.put("role", role);
        employee.put("mail", mail);
        employee.put("mobile", mobile);
        employee.put("location", location);
        employee.put("status", status);
        employee.put("department", department);
        employee.put("designation", designaiton);

        return employee;
    }

    @Given("i add employee with valid details")
    public void addEmployeeWithValidDetails() {
        employee = createEmployeeRequest();
    }

    @When("i send POST request to add the employee")
    public void sendPostRequestToAddEmployee() {
        response = io.restassured.RestAssured
                .given()
                .baseUri(baseUrl)
                .basePath(addemployeeEndpoint)
                .contentType(io.restassured.http.ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(employee)
                .when()
                .post();

        System.out.println("Request URL: " +
                baseUrl + addemployeeEndpoint);
        System.out.println("Response Status: " +
                response.getStatusCode());
        System.out.println("Response Body: " +
                response.asPrettyString());

        response.then().log().all();
    }

    // Generic status code verification method
    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Unexpected HTTP status code. Response: "
                        + response.asPrettyString());
    }

    @Then("created employee details should be displayed")
    public void employeeDetailsShouldBeDisplayed() {
        System.out.println(response.asPrettyString());
    }

    @Then("the employee should be saved successfully")
    public void employeeSavedSuccessfully() {
        Assert.assertEquals(response.getStatusCode(), 201,
                "Employee was not saved successfully. Response: " + response.asString());
    }

    // Successfully add an employee with only mandatory details

    @Given("i add employee with only mandatory details")
    public void addEmployeeWithOnlyMandatoryDetails() {
        employee = new java.util.HashMap<>();

        employee.put("empId", employeeId);
        employee.put("firstName", firstname);
        employee.put("lastName", lastname);
        employee.put("mail", mail);
        employee.put("mobile", mobile);
        employee.put("location", location);
        employee.put("department", department);
        employee.put("designation", designaiton);
        employee.put("role", "");
        employee.put("status", "");
    }

    // Helper Methods

    private String normalizeField(String field) {
        return switch (field.trim().toLowerCase()) {
            case "empid", "employee id" -> "empId";
            case "firstname", "first name" -> "firstName";
            case "lastname", "last name" -> "lastName";
            case "role" -> "role";
            case "mail", "email" -> "mail";
            case "mobile", "mobile number" -> "mobile";
            case "location" -> "location";
            case "status" -> "status";
            case "department" -> "department";
            case "designation" -> "designation";
            default -> throw new IllegalArgumentException(
                    "Unknown employee field: " + field);
        };
    }

    // Negative
    // Add an employee with "field" missing

    @Then("the error message {string} should be {string}")
    public void errorMessageForMissingField(String field, String message) {

        String actualResponse = response.getBody().asString();

        Assert.assertTrue(
                actualResponse.contains(message),
                "Expected error message: " + message
                        + field
                        + "response: " + actualResponse);

    }

    // Add an employee with mandatory fields are empty
    @Given("i add an employee with mandatory {string} is empty")
    public void addEmployeeWithMandatoryFieldsMissing(String field) {

        employee = createEmployeeRequest();

        String jsonField = normalizeField(field);

        employee.put(jsonField, "");

    }

    // Add an employee with fields with NULL values
    @Given("i add an employee with {string} as null")
    public void addEmployeeWithFieldsAsNull(String field) {

        employee = createEmployeeRequest();

        String jsonfield = normalizeField(field);

        employee.put(jsonfield, null);

    }

    // Add an employee with duplicate employee ID
    @Given("i add an employee with duplicate employee ID")
    public void addEmployeeWithDuplicateId() {

        employee = createEmployeeRequest();

        employee.put("empId", employeeId);

    }

    // Add an employee with duplicate Email
    @Given("i add an employee with a duplicate Email")
    public void addEmployeeWithDuplicateEmail() {

        employee = createEmployeeRequest();

        employee.put("mail", mail);

    }

    // Add an employee with duplicate mobile number
    @Given("i add an employee with a duplicate mobile number")
    public void addEmployeeWithDuplicateMobile() {

        employee = createEmployeeRequest();

        employee.put("mobile", mobile);

    }

    // Add an employee with invalid Authentication Token
    @Given("I have an invalid authentication token")
    public void addEmployeeWithInvalidAuthenticationToken() {

        token = CredentialsReader.getCredential("invalid.AuthenticationToken");

    }

    @Then("the error message should be {string}")
    public void verifyErrorMessage(String expectedMessage) {

        String actualMessage = response.jsonPath().getString("message");

        org.testng.Assert.assertEquals(actualMessage, expectedMessage, "Unexpected error message");
    }

    @Given("i add an employee with invalid HTTP method")
    public void addEmployeeWithInvalidHttpMethod() {
        employee = createEmployeeRequest();
    }

    @When("i send a GET request to add an employee")
    public void sendGetRequestToAddEmployee() {

        response = given()
                .baseUri(baseUrl)
                .basePath(addemployeeEndpoint)
                .header("Authorization", "Bearer " + token)
                .when()
                .get();

        response.then().log().all();

    }

    @Then("the error {string} should be {string}")
    public void verifyErrorField(String field, String expectedMessage) {
        String actualMessage = response.jsonPath().getString(field);

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Given("i add an employee with invalid role")
    public void addEmployeeWithInvalidRole() {
        employee = createEmployeeRequest();
        employee.put("role", "InvalidRole");
    }

    @Given("i add an employee with {string} missing")
    public void addEmployeeWithMissingField(String field) {
        employee = createEmployeeRequest();
        employee.remove(normalizeField(field));
    }

    @Given("i add an employee with invalid status")
    public void addEmployeeWithInvalidStatus() {
        employee = createEmployeeRequest();
        employee.put("status", "InvalidStatus");
    }
}