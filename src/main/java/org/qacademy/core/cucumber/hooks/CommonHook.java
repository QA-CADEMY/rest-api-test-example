package org.qacademy.core.cucumber.hooks;

import java.util.List;

import io.cucumber.java.After;
import io.restassured.specification.RequestSpecification;

import org.qacademy.core.api.RequestManager;
import org.qacademy.core.ScenarioContext;
import org.qacademy.core.cucumber.steps.RequestSteps;

public class CommonHook {

    private ScenarioContext context;

    public CommonHook(final ScenarioContext context) {
        this.context = context;
    }

    @After(value = "@cleanData")
    public void afterScenario() {
        RequestSpecification requestSpec = (RequestSpecification) context.get(RequestSteps.KEY_REQUEST_SPEC);
        List<String> endpoints = context.getEndpoints();
        for (String endpoint : endpoints) {
            RequestManager.delete(requestSpec, endpoint);
        }
    }
}
