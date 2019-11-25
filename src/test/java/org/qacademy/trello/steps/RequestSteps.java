package org.qacademy.trello.steps;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import org.qacademy.core.ScenarioContext;
import org.qacademy.trello.RequestSpec;

public class RequestSteps {

    private ScenarioContext context;

    public RequestSteps(final ScenarioContext context) {
        this.context = context;
    }

    @Given("I use the {string} account")
    public void iUseTheService(final String accountName) {
        RequestSpecification requestSpecification = RequestSpec.getRequestSpec(accountName);
        context.set(org.qacademy.core.cucumber.steps.RequestSteps.KEY_REQUEST_SPEC, requestSpecification);
    }
}
