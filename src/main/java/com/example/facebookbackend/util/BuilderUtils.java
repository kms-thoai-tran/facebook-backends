package com.example.facebookbackend.util;

import lombok.experimental.UtilityClass;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class BuilderUtils {

    public static Map<String, Object> generateQueryInClause(String field, List<AttributeValue> attributeValues) {
        Map<String, Object> result = new HashMap<>();
        String expression = field + " IN (%s)";
        String params = "";
        Map<String, AttributeValue> values = new HashMap<>();
        for (int i = 0; i < attributeValues.size(); i++) {
            params += String.format(":Id%s", i);
            values.put(String.format(":Id%s", i), attributeValues.get(0));
        }

        result.put("filterExpression", String.format(expression, params));
        result.put("expressionAttributeValues", values);
        return result;
    }
}
