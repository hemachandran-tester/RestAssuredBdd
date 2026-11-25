package com.assetapi.stepdefinitions;

import com.assetapi.readers.JsonReader;
import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class SaveAssetSteps {

    private Response response;

    // URL and endpoint
    private final String baseurl =
            ConfigReader.getProperty("base.url");

    private final String saveAssetEndpoint =
            ConfigReader.getProperty("save.asset.endpoint");

    // Authentication tokens
    private final String token =
            CredentialsReader.getCredential("valid.AuthenticationToken");

    private final String invalidToken =
            CredentialsReader.getCredential("invalid.AuthenticationToken");

    private ContentType contentType = ContentType.JSON;

    // Request body
    private Map<String, Object> data = new HashMap<>();

    // Common JSON test data path
    private static final String SAVE_ASSET_JSON =
            "testdata/saveAsset.json";
    @Given("The SaveAsset API endpoint is available")
    public void setSaveAssetEndpointIsAvailable() {

        Assert.assertNotNull(baseurl, "Base URL must not be null");

        Assert.assertFalse(
                baseurl.trim().isEmpty(),
                "Base URL must not be empty"
        );

        Assert.assertNotNull(
                saveAssetEndpoint,
                "Save Asset endpoint must not be null"
        );

        Assert.assertFalse(
                saveAssetEndpoint.trim().isEmpty(),
                "Save Asset endpoint must not be empty"
        );

        RestAssured.baseURI = baseurl;
    }

    @Given("I have a valid authentication token for saveAsset")
    public void validAuthenticationToken() {

        Assert.assertNotNull(
                token,
                "Authentication token is missing"
        );

        Assert.assertFalse(
                token.trim().isEmpty(),
                "Authentication token is empty"
        );
    }

    @Given("The request content type is Json")
    public void requestContentTypeIsJson() {

        contentType = ContentType.JSON;
    }


    @Given("I add asset with valid details")
    public void addAssetWithValidDetails() {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );
    }

    @Given("I add asset with a existing serialNumber")
    public void addAssetWithAnExistingSerialNumber() {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        data.put("serialNumber", "SN10002");
    }

    @Given("I add an asset with {string} set to {string}")
    public void addAnAssetWithFieldSetToValueType(
            String field,
            String valueType) {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        switch (valueType.toLowerCase()) {

            case "empty":
                data.put(field, "");
                break;

            case "null":
                data.put(field, null);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported value type: " + valueType
                );
        }

        System.out.println("Testing field: " + field);
        System.out.println("Value type: " + valueType);
        System.out.println("Request body: " + data);
    }

    @Given("I add asset with invalid details {string} set to {string}")
    public void addAssetWithInvalidDetails(
            String field,
            String invalidValue) {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        data.put(field, invalidValue);

        System.out.println("Invalid field: " + field);
        System.out.println("Invalid value: " + invalidValue);
    }

    @Given("I add an asset request without the {string} field")
    public void addAnAssetRequestWithoutTheField(String field) {

        // Read the correct JSON file
        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        // Verify field exists before removing
        Assert.assertTrue(
                data.containsKey(field),
                "Field does not exist in request JSON: " + field
        );

        // Remove mandatory field completely
        data.remove(field);

        System.out.println("Removed mandatory field: " + field);
        System.out.println("Request body: " + data);
    }

    @Given("I add asset with invalid data formats for {string} with {string}")
    public void addAssetWithInvalidDataFormats(
            String field,
            String invalidValue) {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        Assert.assertTrue(
                data.containsKey(field),
                "Field does not exist in request JSON: " + field
        );

        data.put(field, invalidValue);

        System.out.println("Testing invalid field: " + field);
        System.out.println("Invalid value: " + invalidValue);
    }

    @Given("I add an asset with Purchase Date {string} and Warranty Date {string}")
    public void addAnAssetWithPurchaseDateAndWarrantyDate(
            String purchaseDateValue,
            String warrantyDateValue) {

        data = new HashMap<>(
                JsonReader.readJsonFile(SAVE_ASSET_JSON)
        );

        data.put("purchaseDate", purchaseDateValue);
        data.put("warrantyDate", warrantyDateValue);
    }

    @When("I send POST request to save the asset")
    public void sendPutRequestToSaveTheAsset() {

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(contentType)
                .body(data)
                .when()
                .post(saveAssetEndpoint);

        System.out.println("Request body:");
        System.out.println(data);

        System.out.println("API response:");
        response.prettyPrint();
    }

    @When("I send {string} request to save the asset")
    public void sendRequestToSaveTheAsset(String method) {

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(contentType)
                .body(data)
                .when()
                .request(method, saveAssetEndpoint);

        response.prettyPrint();
    }

    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {

        Assert.assertNotNull(
                response,
                "Response is null. Make sure the request step ran first."
        );

        Assert.assertEquals(
                response.statusCode(),
                expectedStatusCode,
                "Status code validation failed"
        );
    }

    @Then("The response status code should be {int} {string}")
    public void theResponseStatusCodeShouldBe(
            int expectedStatusCode,
            String expectedMessage) {

        Assert.assertNotNull(response, "Response is null");

        Assert.assertEquals(
                response.statusCode(),
                expectedStatusCode,
                "Status code validation failed"
        );

        Assert.assertTrue(
                response.statusLine().contains(expectedMessage),
                "Status description validation failed. Actual status line: "
                        + response.statusLine()
        );
    }

    @Then("The response status code should be {int} Invalid or Expired Token")
    public void theResponseStatusCodeShouldBeInvalidOrExpiredToken(
            int expectedStatusCode) {

        Assert.assertNotNull(response, "Response is null");

        Assert.assertEquals(
                response.statusCode(),
                expectedStatusCode,
                "Invalid or Expired Token"
        );
    }

    @And("Created asset details should be displayed")
    public void createdAssetDetailsShouldBeDisplayed() {

        Assert.assertNotNull(response, "Response is null");

        JsonPath jsonPath = response.jsonPath();

        for (String field : data.keySet()) {

            Object expectedValue = data.get(field);
            Object actualValue = jsonPath.get(field);

            Assert.assertEquals(
                    actualValue,
                    expectedValue,
                    "Field value does not match: " + field
            );
        }
    }

    @And("The error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {

        Assert.assertNotNull(response, "Response is null");

        String responseBody = response.asString().trim();

        System.out.println("API Response: " + responseBody);

        String actualMessage = null;

        try {

            JsonPath jsonPath = new JsonPath(responseBody);

            // Check error property
            actualMessage = jsonPath.getString("error");

            // Check message property
            if (actualMessage == null) {
                actualMessage = jsonPath.getString("message");
            }

            // Check single field-level validation response
            if (actualMessage == null) {

                Map<String, Object> errorMap =
                        jsonPath.getMap("$");

                if (errorMap != null && errorMap.size() == 1) {

                    actualMessage = String.valueOf(
                            errorMap.values().iterator().next()
                    );
                }
            }

        } catch (Exception e) {

            actualMessage = responseBody;
        }

        Assert.assertNotNull(
                actualMessage,
                "Could not extract error message from API response: "
                        + responseBody
        );

        Assert.assertEquals(
                actualMessage.trim(),
                expectedMessage.trim(),
                "API error message does not match"
        );
    }

    @And("The error message for existing serialNumber should be {string}")
    public void theErrorMessageForExistingSerialNumberShouldBe(
            String expectedMessage) {

        Assert.assertNotNull(response, "Response is null");

        String actualResponse = response.asString().trim();

        Assert.assertEquals(
                actualResponse,
                expectedMessage,
                "Error message does not match"
        );
    }

    @And("I have an invalid authentication token for saveAsset")
    public void iHaveAnInvalidAuthenticationTokenForSaveAsset() {

        requestSpecification = given()
                .header("Authorization", "Bearer " + invalidToken)
                .contentType(ContentType.JSON);
    }


    @And("(?i)^the error message for \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void theErrorMessageForInvalidInputs(
            String field,
            String expectedMessage) {

        Assert.assertNotNull(
                response,
                "Response is null"
        );

        String responseBody = response.asString().trim();

        System.out.println("API Response: " + responseBody);
        System.out.println("Validating field: " + field);

        String actualMessage = null;

        try {

            JsonPath jsonPath = new JsonPath(responseBody);

            // First check the requested field
            actualMessage = jsonPath.getString(field);

            // Then check generic error property
            if (actualMessage == null) {
                actualMessage = jsonPath.getString("error");
            }

            // Then check generic message property
            if (actualMessage == null) {
                actualMessage = jsonPath.getString("message");
            }

        } catch (Exception e) {

            actualMessage = responseBody;
        }

        Assert.assertNotNull(
                actualMessage,
                "Error message not found in API response for field: "
                        + field
                        + ". Actual response: "
                        + responseBody
        );

        Assert.assertEquals(
                actualMessage.trim(),
                expectedMessage.trim(),
                "Error message mismatch for field: " + field
        );

        System.out.println(
                "Validation passed for field: " + field
        );
    }

}
