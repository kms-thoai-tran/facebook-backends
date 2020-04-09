package com.example.facebookbackend.util;

import lombok.experimental.UtilityClass;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class BuilderUtils {

    public static Map<String, Object> generateQueryInClause(String field, List<String> attributeValues) {
        Map<String, Object> result = new HashMap<>();
        String expression = field + " IN (%s)";
        String params = "";
        Map<String, AttributeValue> values = new HashMap<>();
        for (int i = 0; i < attributeValues.size(); i++) {
            params += String.format(":Id%s, ", i);
            values.put(String.format(":Id%s", i), AttributeValue.builder().s(attributeValues.get(i)).build());
        }

        result.put("query", String.format(expression, params.substring(0, params.length() - 2))); // remove the last characters space, comma
        result.put("attributeValues", values);
        return result;
    }
}
