package org.qacademy.trello;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.qacademy.core.Environment;

public final class RequestSpec {

    private static final Environment ENV = Environment.getInstance();

    private RequestSpec() {
    }

    public static RequestSpecification getRequestSpec(final String account) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ENV.getValue("trello.baseUri"))
                .addQueryParam("key", ENV.getValue(String.format("trello.credentials.%s.key", account)))
                .addQueryParam("token", ENV.getValue(String.format("trello.credentials.%s.token", account)))
                .build();
        return requestSpecification
                .log().method()
                .log().uri()
                .log().params()
                .log().body();
    }
}
