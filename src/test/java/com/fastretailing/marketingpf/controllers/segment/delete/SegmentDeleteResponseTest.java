package com.fastretailing.marketingpf.controllers.segment.delete;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.tests.JsonAssert;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class SegmentDeleteResponseTest {

    @Test
    public void expectingGetResponseSuccess_givingAvailableSegment() {
        Segment segment = new Segment();
        segment.setSegmentSequence(1001L);
        segment.setBusinessType("business_01");
        segment.setBrandId("brand_01");
        segment.setCountryId("country_01");
        segment.setDeliveryScheduledMonth("schedule_month_01");
        segment.setEventTargetPeriodType("periodType_01");
        segment.setDeliveryScheduledMonth("period_type_01");
        segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 11, 30));
        segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 12, 30));
        segment.setEventTargetPeriodRelativeNumberValue(2001L);
        segment.setEventTargetPeriodRelativePeriodUnit("unit_01");
        segment.setTargetItemCategory("item_category_1");
        segment.setSegmentName("segment_name_1");
        segment.setDescription("desc_1");
        segment.setSqlEditFlag("flg_1");
        segment.setEditedSql("sql_1");
        segment.setStatus("stt_1");
        segment.setEventTypeList("{}");
        segment.setAdditionalConditionLogicalOperatorType("1");
        segment.setDeletionFlagForAudit("t");
        segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
        segment.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
        segment.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
        segment.setCreateUserIdForAudit("user_01");
        segment.setUpdateUserIdForAudit("user_01");
        segment.setDeletionUserIdForAudit("user_01");

        SegmentCondition condition_01 = new SegmentCondition();
        condition_01.setSegmentSequence(1001L);
        condition_01.setSegmentConditionSequence(2001L);
        condition_01.setSegmentItemForScreenSequence(3001L);
        condition_01.setSegmentItemForSqlSequence(4001L);
        condition_01.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
        condition_01.setComparisonValue("val_01");
        condition_01.setSegmentConditionBlockOrder(1);
        condition_01.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
        condition_01.setDeletionFlagForAudit("t");
        condition_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
        condition_01.setCreateUserIdForAudit("user_01");
        condition_01.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
        condition_01.setDeletionUserIdForAudit("user_01");
        condition_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
        condition_01.setUpdateUserIdForAudit("user_01");
        SegmentCondition condition_02 = new SegmentCondition();
        condition_02.setSegmentSequence(1001L);
        condition_02.setSegmentConditionSequence(2002L);
        condition_02.setSegmentItemForScreenSequence(3002L);
        condition_02.setSegmentItemForSqlSequence(4002L);
        condition_02.setOperatorType(OPERATOR_TYPE.NOT_EQUAL.getValueAsString());
        condition_02.setComparisonValue("val_02");
        condition_02.setSegmentConditionBlockOrder(1);
        condition_02.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
        condition_02.setDeletionFlagForAudit("t");
        condition_02.setCreateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 1));
        condition_02.setCreateUserIdForAudit("user_02");
        condition_02.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 2));
        condition_02.setDeletionUserIdForAudit("user_02");
        condition_02.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 3));
        condition_02.setUpdateUserIdForAudit("user_02");

        SegmentDeleteResponse response = new SegmentDeleteResponse(segment, Arrays.asList(condition_01, condition_02));
        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{"
                        + "    \"targetSegment\": {"
                        + "        \"segmentSequence\": 1001,"
                        + "        \"businessType\": \"business_01\","
                        + "        \"brandId\": \"brand_01\","
                        + "        \"countryId\": \"country_01\","
                        + "        \"deliveryScheduledMonth\": \"period_type_01\","
                        + "        \"eventTargetPeriodType\": \"periodType_01\","
                        + "        \"eventTargetPeriodStartDate\": \"2021-11-30\","
                        + "        \"eventTargetPeriodEndDate\": \"2021-12-30\","
                        + "        \"eventTargetPeriodRelativeNumberValue\": 2001,"
                        + "        \"eventTargetPeriodRelativePeriodUnit\": \"unit_01\","
                        + "        \"targetItemCategory\": \"item_category_1\","
                        + "        \"segmentName\": \"segment_name_1\","
                        + "        \"description\": \"desc_1\","
                        + "        \"sqlEditFlag\": \"flg_1\","
                        + "        \"editedSql\": \"sql_1\","
                        + "        \"status\": \"stt_1\","
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
                        + "                \"deletionFlagForAudit\": \"t\","
                        + "                \"createDateTimeForAudit\": \"2021-01-01 00:00:01\","
                        + "                \"createUserIdForAudit\": \"user_01\","
                        + "                \"deletionDateTimeForAudit\": \"2021-01-01 00:00:02\","
                        + "                \"deletionUserIdForAudit\": \"user_01\","
                        + "                \"updateDateTimeForAudit\": \"2021-01-01 00:00:03\","
                        + "                \"updateUserIdForAudit\": \"user_01\""
                        + "            }, {"
                        + "                \"segmentConditionSequence\": 2002,"
                        + "                \"segmentItemForScreenSequence\": 3002,"
                        + "                \"segmentItemForSqlSequence\": 4002,"
                        + "                \"operatorType\": \"2\","
                        + "                \"comparisonValue\": \"val_02\","
                        + "                \"segmentConditionBlockOrder\": 1,"
                        + "                \"logicalOperatorType\": \"1\","
                        + "                \"deletionFlagForAudit\": \"t\","
                        + "                \"createDateTimeForAudit\": \"2021-02-02 00:00:01\","
                        + "                \"createUserIdForAudit\": \"user_02\","
                        + "                \"deletionDateTimeForAudit\": \"2021-02-02 00:00:02\","
                        + "                \"deletionUserIdForAudit\": \"user_02\","
                        + "                \"updateDateTimeForAudit\": \"2021-02-02 00:00:03\","
                        + "                \"updateUserIdForAudit\": \"user_02\""
                        + "            }"
                        + "        ],"
                        + "        \"deletionFlagForAudit\": \"t\","
                        + "        \"createDateTimeForAudit\": \"2021-01-01 00:00:01\","
                        + "        \"createUserIdForAudit\": \"user_01\","
                        + "        \"deletionDateTimeForAudit\": \"2021-01-01 00:00:02\","
                        + "        \"deletionUserIdForAudit\": \"user_01\","
                        + "        \"updateDateTimeForAudit\": \"2021-01-01 00:00:03\","
                        + "        \"updateUserIdForAudit\": \"user_01\""
                        + "    }"
                        + "}");
    }
}
