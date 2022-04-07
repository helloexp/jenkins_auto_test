package com.fastretailing.marketingpf.tests;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationError;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.util.ObjectUtils;

/**
 * Using to assert json response<br>
 * <code>
 * mvc.perform(get("/segment/id/1001").contentType(MediaType.APPLICATION_JSON))
 *     .andExpect(status().isOk())
 *     .andExpect(json().contentEquals("{}"));
 * </code>
 */
public class JsonContentMatchers extends ContentResultMatchers {

    public ResultMatcher contentEquals(String expectedContent) {
        return result -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                assertEquals("Response content", objectMapper.readTree(expectedContent), objectMapper.readTree(result.getResponse().getContentAsString()));
            } catch (JsonProcessingException e) {
                fail("Not valid json", ObjectUtils.nullSafeToString(expectedContent), ObjectUtils.nullSafeToString(result.getResponse().getContentAsString()));
            }
        };
    }

    public ResultMatcher errorContentEquals(String expectedContent) {
        return result -> {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
            // Due to disable filter for testing , requestId and timestamp will be null
            // assertThat(errorResponse.requestId).isNotNull();
            // assertThat(errorResponse.timestamp).isNotNull();
            // Clear requestId, timestamp
            errorResponse.requestId = null;
            errorResponse.timestamp = null;
            errorResponse.validationErrors = errorResponse.validationErrors.stream()
                    .sorted(Comparator.comparing(ValidationError::getField).thenComparing(ValidationError::getMessage))
                    .collect(Collectors.toList());
            try {
                assertEquals("Response content", objectMapper.readTree(expectedContent), objectMapper.readTree(objectMapper.writeValueAsString(errorResponse)));
            } catch (JsonProcessingException e) {
                fail("Not valid json", ObjectUtils.nullSafeToString(expectedContent), ObjectUtils.nullSafeToString(result.getResponse().getContentAsString()));
            }
        };

    }

    /**
     * For testing json result with MockMvc
     *
     * @return JsonContentMatchers
     */
    public static JsonContentMatchers json() {
        return new JsonContentMatchers();
    }
}
