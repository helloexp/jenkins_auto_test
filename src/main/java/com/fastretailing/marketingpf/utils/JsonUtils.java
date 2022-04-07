package com.fastretailing.marketingpf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonUtils {

    /**
     * Create default ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper createMapper() {
        return new Jackson2ObjectMapperBuilder().serializers(
                new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE),
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).build();
    }

    /**
     * Map JSON string to object by class
     *
     * @param <T>
     * @param json
     * @param clazz
     * @return object T
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return createMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Map JSON string to object by TypeReference
     *
     * @param <T>
     * @param json
     * @return object T
     */
    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        if (Objects.isNull(json)) {
            return  null;
        }
        try {
            return createMapper().readValue(json, typeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize object to JSON string
     *
     * @param object
     * @return JSON string
     */
    public static String toJson(Object object) {
        try {
            return createMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize object to pretty JSON string
     *
     * @param object
     * @return pretty JSON string
     */
    public static String toPrettyJson(Object object) {
        try {
            return createMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test if 2 json string have same content, ignore order
     *
     * @param content1
     * @param content2
     * @return TRUE if content is same, FALSE if content is not match
     */
    public static boolean isSameJsonContent(String content1, String content2) {
        ObjectMapper objectMapper = createMapper();
        try {
            return objectMapper.readTree(content1).equals(objectMapper.readTree(content2));
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * Test if string is valid JSON or not
     *
     * @param str
     * @return TRUE if valid JSON, FALSE if invalid JSON
     */
    public static boolean isValidJson(String str) {
        try {
            createMapper().readTree(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Test if string is JSON and can be deserialized to Object by Class
     *
     * @param str
     * @return boolean
     */
    public static boolean isDeserializableJson(String str, Class<?> clazz) {
        try {
            fromJson(str, clazz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Test if string is JSON and can be deserialized to Object by TypeReference
     *
     * @param str
     * @return boolean
     */
    public static boolean isDeserializableJson(String str, TypeReference<?> typeRef) {
        try {
            fromJson(str, typeRef);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
