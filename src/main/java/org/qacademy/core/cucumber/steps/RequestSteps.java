package org.qacademy.core.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.qacademy.core.ScenarioContext;
import org.qacademy.core.api.DynamicIdHelper;
import org.qacademy.core.api.RequestManager;
import org.testng.Assert;

public class RequestSteps {

    public static final String KEY_REQUEST_SPEC = "REQUEST_SPEC";
    private static final String KEY_LAST_ENDPOINT = "LAST_ENDPOINT";
    private static final String KEY_LAST_RESPONSE = "LAST_RESPONSE";
    private Response response;
    private ScenarioContext context;

    public RequestSteps(final ScenarioContext context) {
        this.context = context;
    }

    @Given("I send a {string} request to {string} with json body")
    public void iSendARequestToWithJsonBody(final String httpMethod, final String endpoint,
                                            final String jsonBody) {
        RequestSpecification requestSpecification = (RequestSpecification) context.get(KEY_REQUEST_SPEC);
        String builtEndpoint = DynamicIdHelper.replaceIds(context, endpoint);
        response = RequestManager.doRequest(httpMethod, requestSpecification, builtEndpoint,
                DynamicIdHelper.replaceIds(context, jsonBody));
        context.set(KEY_LAST_ENDPOINT, builtEndpoint);
        context.set(KEY_LAST_RESPONSE, response);
    }

    @Then("I validate the response has status code {int}")
    public void iValidateTheResponseHasStatusCode(int expectedStatusCode) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, expectedStatusCode);
    }

    @And("I validate the response contains {string} equals {string}")
    public void iValidateTheResponseContainsEquals(final String attribute, final String expectedValue) {
        String actualProjectName = response.jsonPath().getString(attribute);
        Assert.assertEquals(actualProjectName, expectedValue);
    }

    @And("I save the response as {string}")
    public void iSaveTheResponseAs(final String key) {
        context.set(key, response);
    }

    @And("I save the request endpoint for deleting")
    public void iSaveTheRequestEndpointForDeleting() {
        String lastEndpoint = (String) context.get(KEY_LAST_ENDPOINT);
        String lastResponseId = ((Response) context.get(KEY_LAST_RESPONSE)).jsonPath().getString("id");
        String finalEndpoint = String.format("%s/%s", lastEndpoint, lastResponseId);
        context.addEndpoint(finalEndpoint);
    }
}
