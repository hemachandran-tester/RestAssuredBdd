package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class LoginSteps {

    private Response response;

    private String getEmployeeId() { return CredentialsReader.getCredential("valid.EmployeeId").trim(); }

    private String getPassword() { return CredentialsReader.getCredential("valid.password").trim(); }

    private void sendLoginRequest(String employeeId, String password) {
        String requestBody = String.format(
                "{\"empId\":\"%s\",\"password\":\"%s\"}",
                employeeId, password);

        response = given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/login");
    }

    @Given("the login API is available")
    public void loginApiIsAvailable() {
        baseURI = ConfigReader.getProperty("base.url").trim();
    }

    @When("i login with valid credentials")
    public void loginWithValidCred() throws Exception {
        sendLoginRequest(getEmployeeId(), getPassword());
    }

    @When("i login with invalid credentials")
    public void loginWithInvalidCred() throws Exception {
        // Use a syntactically valid but incorrect password.
        sendLoginRequest(getEmployeeId(), "Hema@12345");
    }

    @When("i login with employeeID {string} and valid password")
    public void loginWithEmpIDandValidPass(String employeeID)
            throws Exception {
        sendLoginRequest(employeeID, getPassword());
    }

    @When("i login with valid employeeID and password {string}")
    public void loginWithEmpIDandInValidPass(String password)
            throws Exception {
        sendLoginRequest(getEmployeeId(), password);
    }

    @Then("the response status should be {int}")
    public void responseStatusShouldbe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain an authentication token")
    public void responseShouldHaveAuthToken() {
        response.then().body("token", notNullValue());
    }

    @Then("the response field {string} should equal {string}")
    public void responseFieldShouldEqual(
            String field, String expectedValue) {
        String actualValue = response.jsonPath().getString(field);
        org.testng.Assert.assertEquals(actualValue, expectedValue);
    }

}
