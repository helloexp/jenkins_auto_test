package com.fastretailing.marketingpf.controllers.segment.update;

import static com.fastretailing.marketingpf.tests.JsonAssert.assertJsonOutput;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SegmentUpdateResponseTest {

    @Test
    public void testSegmentUpdateResponse() {
        SegmentCondition condition_01 = new SegmentCondition();
        condition_01.setSegmentSequence(1001L);
        condition_01.setSegmentConditionSequence(2001L);
        condition_01.setSegmentItemForScreenSequence(3001L);
        condition_01.setSegmentItemForSqlSequence(4001L);
        condition_01.setOperatorType("1");
        condition_01.setComparisonValue("val_01");
        condition_01.setSegmentConditionBlockOrder(1);
        condition_01.setLogicalOperatorType("1");
        condition_01.setDeletionFlagForAudit("f");
        condition_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
        condition_01.setCreateUserIdForAudit("user_01");
        condition_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
        condition_01.setUpdateUserIdForAudit("user_01");

        SegmentCondition condition_02 = new SegmentCondition();
        condition_02.setSegmentSequence(1001L);
        condition_02.setSegmentConditionSequence(2002L);
        condition_02.setSegmentItemForScreenSequence(3002L);
        condition_02.setSegmentItemForSqlSequence(4002L);
        condition_02.setOperatorType("2");
        condition_02.setComparisonValue("val_02");
        condition_02.setSegmentConditionBlockOrder(1);
        condition_02.setLogicalOperatorType("1");
        condition_02.setDeletionFlagForAudit("f");
        condition_02.setCreateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 1));
        condition_02.setCreateUserIdForAudit("user_02");
        condition_02.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 2));
        condition_02.setUpdateUserIdForAudit("user_02");

        List<SegmentCondition> conditionList = Arrays.asList(condition_01, condition_02);

        Segment segment = new Segment();
        segment.setSegmentSequence(1001L);
        segment.setBusinessType("1");
        segment.setBrandId("1");
        segment.setCountryId("1");
        segment.setDeliveryScheduledMonth("202101");
        segment.setEventTargetPeriodType("1");
        segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 1, 1));
        segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 1, 1));
        segment.setEventTargetPeriodRelativeNumberValue(6000L);
        segment.setEventTargetPeriodRelativePeriodUnit("1");
        segment.setTargetItemCategory("item_01");
        segment.setSegmentName("segment_01");
        segment.setDescription("desc_01");
        segment.setSqlEditFlag("1");
        segment.setEditedSql("sql_01");
        segment.setStatus("status_01");
        segment.setEventTypeList("{}");
        segment.setAdditionalConditionLogicalOperatorType("1");
        segment.setDeletionFlagForAudit("f");
        segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        segment.setCreateUserIdForAudit("user_01");
        segment.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        segment.setUpdateUserIdForAudit("user_01");

        SegmentUpdateResponse response = new SegmentUpdateResponse(segment, conditionList);

        assertJsonOutput(response).isSameContentAs(
                "{"
                        + "    \"targetSegment\": {"
                        + "        \"segmentSequence\": 1001,"
                        + "        \"businessType\": \"1\","
                        + "        \"brandId\": \"1\","
                        + "        \"countryId\": \"1\","
                        + "        \"deliveryScheduledMonth\": \"202101\","
                        + "        \"eventTargetPeriodType\": \"1\","
                        + "        \"eventTargetPeriodStartDate\": \"2021-01-01\","
                        + "        \"eventTargetPeriodEndDate\": \"2021-01-01\","
                        + "        \"eventTargetPeriodRelativeNumberValue\": 6000,"
                        + "        \"eventTargetPeriodRelativePeriodUnit\": \"1\","
                        + "        \"targetItemCategory\": \"item_01\","
                        + "        \"segmentName\": \"segment_01\","
                        + "        \"description\": \"desc_01\","
                        + "        \"sqlEditFlag\": \"1\","
                        + "        \"editedSql\": \"sql_01\","
                        + "        \"status\": \"status_01\","
                        + "        \"eventTypeList\": \"{}\","
                        + "        \"additionalConditionLogicalOperatorType\": \"1\","
                        + "        \"segmentConditionList\": [{"
                        + "                \"segmentConditionSequence\": 2001,"
                        + "                \"segmentItemForScreenSequence\": 3001,"
                        + "                \"segmentItemForSqlSequence\": 4001,"
                        + "                \"operatorType\": \"1\","
                        + "                \"comparisonValue\": \"val_01\","
                        + "                \"segmentConditionBlockOrder\": 1,"
                        + "                \"logicalOperatorType\": \"1\","
                        + "                \"deletionFlagForAudit\": \"f\","
                        + "                \"createDateTimeForAudit\": \"2021-01-01 00:00:01\","
                        + "                \"createUserIdForAudit\": \"user_01\","
                        + "                \"updateDateTimeForAudit\": \"2021-01-01 00:00:02\","
                        + "                \"updateUserIdForAudit\": \"user_01\""
                        + "            }, {"
                        + "                \"segmentConditionSequence\": 2002,"
                        + "                \"segmentItemForScreenSequence\": 3002,"
                        + "                \"segmentItemForSqlSequence\": 4002,"
                        + "                \"operatorType\": \"2\","
                        + "                \"comparisonValue\": \"val_02\","
                        + "                \"segmentConditionBlockOrder\": 1,"
                        + "                \"logicalOperatorType\": \"1\","
                        + "                \"deletionFlagForAudit\": \"f\","
                        + "                \"createDateTimeForAudit\": \"2021-02-02 00:00:01\","
                        + "                \"createUserIdForAudit\": \"user_02\","
                        + "                \"updateDateTimeForAudit\": \"2021-02-02 00:00:02\","
                        + "                \"updateUserIdForAudit\": \"user_02\""
                        + "            }"
                        + "        ],"
                        + "        \"deletionFlagForAudit\": \"f\","
                        + "        \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "        \"createUserIdForAudit\": \"user_01\","
                        + "        \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "        \"updateUserIdForAudit\": \"user_01\""
                        + "    }"
                        + "}");
    }
}
