package org.qacademy.core.api;

import io.restassured.response.Response;
import org.qacademy.core.ScenarioContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DynamicIdHelper {

    private DynamicIdHelper() {
    }

    public static String replaceIds(final ScenarioContext context, final String body) {
        if (!body.contains("(")) {
            return body;
        }
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("(?<=\\()(.*?)(?=\\))");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String element = matcher.group();
            String[] elementSplit = element.split("\\.");
            Response response = (Response) context.get(elementSplit[0]);
            matcher.appendReplacement(result, response.jsonPath().getString(element.substring(element.indexOf(".") + 1)));
        }
        matcher.appendTail(result);
        return result.toString().replaceAll("[\\(\\)]", "");
    }

}
