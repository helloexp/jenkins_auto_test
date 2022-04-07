package com.fastretailing.marketingpf.controllers.segment.get;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SegmentGetResponseTest {

    @Test
    public void expectingGetSuccess() {
        Segment segment = new Segment();
        segment.setSegmentSequence(1001L);
        segment.setBusinessType("business_01");
        segment.setBrandId("brand_01");
        segment.setCountryId("country_01");
        segment.setDeliveryScheduledMonth("2021-01-01");
        segment.setEventTargetPeriodType("type_01");
        segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 2, 2));
        segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 3, 3));
        segment.setEventTargetPeriodRelativeNumberValue(2001L);
        segment.setEventTargetPeriodRelativePeriodUnit("3");
        segment.setTargetItemCategory("category_01");
        segment.setSegmentName("segment_01");
        segment.setDescription("desc_01");
        segment.setSqlEditFlag("flag_01");
        segment.setEditedSql("sql_01");
        segment.setStatus("stt_01");
        segment.setEventTypeList("{}");
        segment.setAdditionalConditionLogicalOperatorType("1");
        segment.setDeletionFlagForAudit("false");
        segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        segment.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        segment.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        segment.setCreateUserIdForAudit("user_01");
        segment.setDeletionUserIdForAudit("user_02");
        segment.setUpdateUserIdForAudit("user_03");

        SegmentCondition condition_01 = new SegmentCondition();
        condition_01.setSegmentConditionSequence(2001L);
        condition_01.setSegmentSequence(1001L);
        condition_01.setSegmentItemForScreenSequence(4001L);
        condition_01.setSegmentItemForSqlSequence(4001L);
        condition_01.setOperatorType("operator_type_01");
        condition_01.setComparisonValue("value_01");
        condition_01.setLogicalOperatorType("log_operator_type_01");
        condition_01.setSegmentConditionBlockOrder(1);
        condition_01.setDeletionFlagForAudit("f");
        condition_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        condition_01.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        condition_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        condition_01.setCreateUserIdForAudit("user_01");
        condition_01.setDeletionUserIdForAudit("user_01");
        condition_01.setUpdateUserIdForAudit("user_01");
        
        SegmentCondition condition_02 = new SegmentCondition();
        condition_02.setSegmentConditionSequence(2002L);
        condition_02.setSegmentSequence(1002L);
        condition_02.setSegmentItemForScreenSequence(4002L);
        condition_02.setSegmentItemForSqlSequence(4002L);
        condition_02.setOperatorType("operator_type_02");
        condition_02.setComparisonValue("value_02");
        condition_02.setLogicalOperatorType("log_operator_type_02");
        condition_02.setSegmentConditionBlockOrder(2);
        condition_02.setDeletionFlagForAudit("f");
        condition_02.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        condition_02.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        condition_02.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        condition_02.setCreateUserIdForAudit("user_02");
        condition_02.setDeletionUserIdForAudit("user_02");
        condition_02.setUpdateUserIdForAudit("user_02");

        List<SegmentCondition> conditionList = Arrays.asList(condition_01, condition_02);

        SegmentGetResponse response = new SegmentGetResponse(segment, conditionList);

        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{"
                        + "    \"targetSegment\": {"
                        + "        \"segmentSequence\": 1001,"
                        + "        \"businessType\": \"business_01\","
                        + "        \"brandId\": \"brand_01\","
                        + "        \"countryId\": \"country_01\","
                        + "        \"deliveryScheduledMonth\": \"2021-01-01\","
                        + "        \"eventTargetPeriodType\": \"type_01\","
                        + "        \"eventTargetPeriodStartDate\": \"2021-02-02\","
                        + "        \"eventTargetPeriodEndDate\": \"2021-03-03\","
                        + "        \"eventTargetPeriodRelativeNumberValue\": 2001,"
                        + "        \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                        + "        \"targetItemCategory\": \"category_01\","
                        + "        \"segmentName\": \"segment_01\","
                        + "        \"description\": \"desc_01\","
                        + "        \"sqlEditFlag\": \"flag_01\","
                        + "        \"editedSql\": \"sql_01\","
                        + "        \"status\": \"stt_01\","
                        + "        \"eventTypeList\": \"{}\","
                        + "        \"additionalConditionLogicalOperatorType\": \"1\","
                        + "        \"segmentConditionList\": [{"
                        + "                \"segmentConditionSequence\": 2001,"
                        + "                \"segmentItemForScreenSequence\": 4001,"
                        + "                \"segmentItemForSqlSequence\": 4001,"
                        + "                \"operatorType\": \"operator_type_01\","
                        + "                \"comparisonValue\": \"value_01\","
                        + "                \"segmentConditionBlockOrder\": 1,"
                        + "                \"logicalOperatorType\": \"log_operator_type_01\","
                        + "                \"deletionFlagForAudit\": \"f\","
                        + "                \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "                \"createUserIdForAudit\": \"user_01\","
                        + "                \"updateDateTimeForAudit\": \"2021-03-03 00:00:00\","
                        + "                \"updateUserIdForAudit\": \"user_01\""
                        + "            }, {"
                        + "                \"segmentConditionSequence\": 2002,"
                        + "                \"segmentItemForScreenSequence\": 4002,"
                        + "                \"segmentItemForSqlSequence\": 4002,"
                        + "                \"operatorType\": \"operator_type_02\","
                        + "                \"comparisonValue\": \"value_02\","
                        + "                \"segmentConditionBlockOrder\": 2,"
                        + "                \"logicalOperatorType\": \"log_operator_type_02\","
                        + "                \"deletionFlagForAudit\": \"f\","
                        + "                \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "                \"createUserIdForAudit\": \"user_02\","
                        + "                \"updateDateTimeForAudit\": \"2021-03-03 00:00:00\","
                        + "                \"updateUserIdForAudit\": \"user_02\""
                        + "            }"
                        + "        ],"
                        + "        \"deletionFlagForAudit\": \"false\","
                        + "        \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "        \"createUserIdForAudit\": \"user_01\","
                        + "        \"updateDateTimeForAudit\": \"2021-03-03 00:00:00\","
                        + "        \"updateUserIdForAudit\": \"user_03\""
                        + "    }"
                        + "}");
    }
}
