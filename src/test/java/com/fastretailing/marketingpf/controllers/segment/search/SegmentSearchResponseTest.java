package com.fastretailing.marketingpf.controllers.segment.search;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import com.fastretailing.marketingpf.controllers.segment.search.SegmentSearchResponse.SegmentResponse;
import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.tests.JsonAssert;

class SegmentSearchResponseTest  {

    @Test
    public void expectingSuccess() {
        Segment segment = new Segment();
        segment.setSegmentSequence(1001L);
        segment.setBusinessType("2");
        segment.setBrandId("010");
        segment.setCountryId("1");
        segment.setDeliveryScheduledMonth("2021-01-01");
        segment.setEventTargetPeriodType("1");
        segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 2, 2));
        segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 3, 3));
        segment.setEventTargetPeriodRelativeNumberValue(2001L);
        segment.setEventTargetPeriodRelativePeriodUnit("3");
        segment.setTargetItemCategory("category_01");
        segment.setSegmentName("segment_01");
        segment.setDescription("desc_01");
        segment.setSqlEditFlag("1");
        segment.setStatus("1");
        segment.setEventTypeList("{}");
        segment.setAuditInfoForCreation("user_01", LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
        segmentBreakdown.setTargetNumberOfPeopleBySegment(10005L);
        segmentBreakdown.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 2, 1, 2, 3));

        Map<String, String> userMasterMap = new HashMap<>();
        userMasterMap.put("user_01", "user_fullname_01");
        userMasterMap.put("user_02", "user_fullname_02");

        SegmentResponse response = new SegmentResponse(segment, segmentBreakdown, userMasterMap);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{"
                        + "    \"segmentSequence\": 1001,"
                        + "    \"businessType\": \"2\","
                        + "    \"brandId\": \"010\","
                        + "    \"countryId\": \"1\","
                        + "    \"deliveryScheduledMonth\": \"2021-01-01\","
                        + "    \"eventTargetPeriodType\": \"1\","
                        + "    \"eventTargetPeriodStartDate\": \"2021-02-02\","
                        + "    \"eventTargetPeriodEndDate\": \"2021-03-03\","
                        + "    \"eventTargetPeriodRelativeNumberValue\": 2001,"
                        + "    \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                        + "    \"targetItemCategory\": \"category_01\","
                        + "    \"segmentName\": \"segment_01\","
                        + "    \"description\": \"desc_01\","
                        + "    \"sqlEditFlag\": \"1\","
                        + "    \"status\": \"1\","
                        + "    \"eventTypeList\": \"{}\","
                        + "    \"targetNumberOfPeopleBySegment\": 10005,"
                        + "    \"targetNumberOfPeopleBySegmentDateTime\": \"2021-01-02 01:02:03\","
                        + "    \"deletionFlagForAudit\": \"f\","
                        + "    \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "    \"createUserIdForAudit\": \"user_fullname_01\","
                        + "    \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\","
                        + "    \"updateUserIdForAudit\": \"user_fullname_01\""
                        + "}");
    }
}
