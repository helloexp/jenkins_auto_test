package com.fastretailing.marketingpf.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.marketingpf.utils.JsonUtils;
import org.assertj.core.api.AbstractAssert;

/**
 * Use to assert content of two JSON strings ignore field order
 */
public class JsonAssert extends AbstractAssert<JsonAssert, String> {

    public JsonAssert(String actual) {
        super(actual, JsonAssert.class);
    }

    public JsonAssert isSameContentAs(String expected) {
        isNotNull();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (!objectMapper.readTree(expected).equals(objectMapper.readTree(actual))) {
                failWithMessage("Expected: %s\nbut was: %s", expected, actual);
            }
        } catch (JsonProcessingException e) {
            failWithMessage("Not valid json. Expected: %s\nbut was: %s", expected, actual);
        }
        return this;
    }

    public static JsonAssert assertJsonString(String actual) {
        return new JsonAssert(actual);
    }

    public static JsonAssert assertJsonOutput(Object actual) {
        if(actual instanceof String) {
            throw new RuntimeException("This method does not support string. Please use assertJsonString");
        }
        return new JsonAssert(JsonUtils.toJson(actual));
    }
}
