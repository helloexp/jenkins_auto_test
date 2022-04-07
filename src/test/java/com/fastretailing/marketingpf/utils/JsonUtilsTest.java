package com.fastretailing.marketingpf.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


public class JsonUtilsTest {

    @Test
    public void testCreateMapper() {
        assertThat(JsonUtils.createMapper()).isNotNull();
    }

    @Test
    public void testToJson() {
        assertThat(JsonUtils.toJson(null)).isEqualTo("null");
        assertThat(JsonUtils.toJson("hoge")).isEqualTo("\"hoge\"");
        assertThat(JsonUtils.toJson(10)).isEqualTo("10");
        assertThat(JsonUtils.toJson(10L)).isEqualTo("10");
        assertThat(JsonUtils.toJson(10.0)).isEqualTo("10.0");
        assertThat(JsonUtils.toJson(LocalDate.of(2020, 1, 2))).isEqualTo("\"2020-01-02\"");
        assertThat(JsonUtils.toJson(LocalDateTime.of(2020, 1, 2, 3, 4, 5))).isEqualTo("\"2020-01-02T03:04:05\"");
        assertThat(JsonUtils.toJson(10.0)).isEqualTo("10.0");
        assertThat(JsonUtils.toJson(true)).isEqualTo("true");
        assertThat(JsonUtils.toJson(false)).isEqualTo("false");
        assertThat(JsonUtils.toJson(Arrays.asList("a", "b"))).isEqualTo("[\"a\",\"b\"]");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("string", "string");
        map.put("long", "string");
        map.put("boolean", "string");
        map.put("list1", Arrays.asList("a", "b"));
        map.put("list2", Arrays.asList(10, 20));
        JsonAssert.assertJsonOutput(map).isSameContentAs("{"
                + "    \"boolean\": \"string\","
                + "    \"list1\": [\"a\", \"b\"],"
                + "    \"list2\": [10, 20],"
                + "    \"long\": \"string\","
                + "    \"string\": \"string\""
                + "}");
    }

    @Test
    public void testToPrettyJson() {
        assertThat(JsonUtils.toJson(null)).isEqualTo("null");
        assertThat(JsonUtils.toJson("hoge")).isEqualTo("\"hoge\"");
        assertThat(JsonUtils.toJson(10)).isEqualTo("10");
        assertThat(JsonUtils.toJson(10L)).isEqualTo("10");
        assertThat(JsonUtils.toJson(10.0)).isEqualTo("10.0");
        assertThat(JsonUtils.toJson(true)).isEqualTo("true");
        assertThat(JsonUtils.toJson(false)).isEqualTo("false");
        assertThat(JsonUtils.toJson(Arrays.asList("a", "b"))).isEqualTo("[\"a\",\"b\"]");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("string", "string");
        map.put("long", "string");
        map.put("boolean", "string");
        map.put("list1", Arrays.asList("a", "b"));
        map.put("list2", Arrays.asList(10, 20));
        JsonAssert.assertJsonString(JsonUtils.toPrettyJson(map))
                .isSameContentAs("{"
                        + "    \"boolean\": \"string\","
                        + "    \"list1\": [\"a\", \"b\"],"
                        + "    \"list2\": [10, 20],"
                        + "    \"long\": \"string\","
                        + "    \"string\": \"string\""
                        + "}");
    }

    @Test
    public void testIsSameJsonContent() {
        assertThat(JsonUtils.isSameJsonContent("", "")).isTrue();
        assertThat(JsonUtils.isSameJsonContent("{\"hoge\":\"fuga\", \"piyo\": 1}", "{\"piyo\": 1, \"hoge\":\"fuga\"}")).isTrue();
        assertThat(JsonUtils.isSameJsonContent("{\"hoge\":\"fuga\", \"piyo\": 1}", "{\"hoge\":\"fuga\", \"piyo\": 1}")).isTrue();
        assertThat(JsonUtils.isSameJsonContent("{}", "{}")).isTrue();
        assertThat(JsonUtils.isSameJsonContent("[]", "[]")).isTrue();
        assertThat(JsonUtils.isSameJsonContent("[]", "{}")).isFalse();
        assertThat(JsonUtils.isSameJsonContent("{\"hoge\":\"fuga\", \"piyo\": 1}", "{\"hoge\":\"fuga\", \"piyo\": 2}")).isFalse();
        assertThat(JsonUtils.isSameJsonContent("{\"hoge\":\"fuga\", \"piyo\": 1}", "{\"hoge\":\"fuga\"}")).isFalse();
    }

    @Test
    public void testIsValidJson() {
        assertThat(JsonUtils.isValidJson("{\"hoge\":\"fuga\"}")).isTrue();
        assertThat(JsonUtils.isValidJson("{}")).isTrue();
        assertThat(JsonUtils.isValidJson("[]")).isTrue();
        assertThat(JsonUtils.isValidJson("[1,3]")).isTrue();
        assertThat(JsonUtils.isValidJson("[1,\"hoge\"]")).isTrue();
        assertThat(JsonUtils.isValidJson(null)).isFalse();
        assertThat(JsonUtils.isValidJson("{[}")).isFalse();
        assertThat(JsonUtils.isValidJson("[}")).isFalse();
        assertThat(JsonUtils.isValidJson("{\"hoge\":\"}")).isFalse();
    }

}
