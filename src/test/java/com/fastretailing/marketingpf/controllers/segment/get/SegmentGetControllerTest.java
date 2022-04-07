package com.fastretailing.marketingpf.controllers.segment.get;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentGetControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @Test
    @Sql("/controllers/SegmentGetControllerTest/data.sql")
    public void expectingGetSuccess_givingManyCases() throws Exception {
        mvc.perform(get("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/1001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{\n"
                                + "\"targetSegment\": {"
                                + "    \"segmentSequence\": 1001,\n"
                                + "    \"businessType\": \"1\",\n"
                                + "    \"brandId\": \"090\",\n"
                                + "    \"countryId\": \"1\",\n"
                                + "    \"deliveryScheduledMonth\": \"202101\",\n"
                                + "    \"eventTargetPeriodType\": \"1\",\n"
                                + "    \"eventTargetPeriodStartDate\": \"2021-11-30\",\n"
                                + "    \"eventTargetPeriodEndDate\": \"2021-12-08\",\n"
                                + "    \"eventTargetPeriodRelativeNumberValue\": 2001,\n"
                                + "    \"eventTargetPeriodRelativePeriodUnit\": \"1\",\n"
                                + "    \"targetItemCategory\": \"item_category_1\",\n"
                                + "    \"segmentName\": \"segment_name_1\",\n"
                                + "    \"description\": \"desc_1\",\n"
                                + "    \"sqlEditFlag\": \"\",\n"
                                + "    \"editedSql\": \"0\",\n"
                                + "    \"status\": \"1\",\n"
                                + "    \"eventTypeList\": \"{}\",\n"
                                + "    \"additionalConditionLogicalOperatorType\": \"1\",\n"
                                + "    \"segmentConditionList\": [{\n"
                                + "            \"segmentConditionSequence\": 1001,\n"
                                + "            \"segmentItemForScreenSequence\": 7,\n"
                                + "            \"segmentItemForSqlSequence\": 4001,\n"
                                + "            \"operatorType\": \"3\",\n"
                                + "            \"comparisonValue\": \"[3, 4]\",\n"
                                + "            \"segmentConditionBlockOrder\": 1,\n"
                                + "            \"logicalOperatorType\": \"1\",\n"
                                + "            \"deletionFlagForAudit\": \"f\",\n"
                                + "            \"createDateTimeForAudit\": \"2021-01-01 00:00:01\",\n"
                                + "            \"createUserIdForAudit\": \"user_01\",\n"
                                + "            \"updateDateTimeForAudit\": \"2021-01-01 00:00:02\",\n"
                                + "            \"updateUserIdForAudit\": \"user_01\"\n"
                                + "        }, {\n"
                                + "            \"segmentConditionSequence\": 1002,\n"
                                + "            \"segmentItemForScreenSequence\": 8,\n"
                                + "            \"segmentItemForSqlSequence\": 4002,\n"
                                + "            \"operatorType\": \"3\",\n"
                                + "            \"comparisonValue\": \"[3, 4]\",\n"
                                + "            \"segmentConditionBlockOrder\": 1,\n"
                                + "            \"logicalOperatorType\": \"2\",\n"
                                + "            \"deletionFlagForAudit\": \"f\",\n"
                                + "            \"createDateTimeForAudit\": \"2021-02-02 00:00:01\",\n"
                                + "            \"createUserIdForAudit\": \"user_02\",\n"
                                + "            \"updateDateTimeForAudit\": \"2021-02-02 00:00:02\",\n"
                                + "            \"updateUserIdForAudit\": \"user_02\"\n"
                                + "        }\n"
                                + "    ],\n"
                                + "    \"deletionFlagForAudit\": \"f\",\n"
                                + "    \"createDateTimeForAudit\": \"1970-01-01 00:00:00\",\n"
                                + "    \"createUserIdForAudit\": \"user_id_1\",\n"
                                + "    \"updateDateTimeForAudit\": \"1970-01-01 00:00:00\",\n"
                                + "    \"updateUserIdForAudit\": \"user_id_1\"\n"
                                + "  }"
                                + "}"));

        // segmentConditionList is empty
        mvc.perform(get("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/1003").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json().contentEquals(
                "{"
                        + "\"targetSegment\": {"
                        + "\"segmentSequence\":1003,"
                        + "\"businessType\":\"1\","
                        + "\"brandId\":\"090\","
                        + "\"countryId\":\"1\","
                        + "\"deliveryScheduledMonth\":\"202101\","
                        + "\"eventTargetPeriodType\":\"1\","
                        + "\"eventTargetPeriodStartDate\":\"2021-11-30\","
                        + "\"eventTargetPeriodEndDate\":\"2021-12-08\","
                        + "\"eventTargetPeriodRelativeNumberValue\":2003,"
                        + "\"eventTargetPeriodRelativePeriodUnit\":\"3\","
                        + "\"targetItemCategory\":\"item_category_3\","
                        + "\"segmentName\":\"segment_name_3\","
                        + "\"description\":\"desc_3\","
                        + "\"sqlEditFlag\":\"\","
                        + "\"editedSql\":\"0\","
                        + "\"status\":\"1\","
                        + "\"eventTypeList\":\"{}\","
                        + "\"additionalConditionLogicalOperatorType\":\"1\","
                        + "\"segmentConditionList\":[],"
                        + "\"deletionFlagForAudit\":\"f\","
                        + "\"createDateTimeForAudit\":\"1970-01-01 00:00:00\","
                        + "\"createUserIdForAudit\":\"user_id_3\","
                        + "\"updateDateTimeForAudit\":\"1970-01-01 00:00:00\","
                        + "\"updateUserIdForAudit\":\"user_id_3\""
                        + "}"
                        + "}"));
    }
}
