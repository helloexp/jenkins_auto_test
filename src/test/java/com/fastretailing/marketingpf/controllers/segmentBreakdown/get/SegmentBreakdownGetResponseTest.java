package com.fastretailing.marketingpf.controllers.segmentBreakdown.get;

import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SegmentBreakdownGetResponseTest extends BaseSpringBootTest {

    @Test
    public void expectingGetResponseSuccess_givingAvailableParams() {
        SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
        segmentBreakdown.setSegmentBreakdownSequence(1001L);
        segmentBreakdown.setSegmentSequence(2001L);
        segmentBreakdown.setTargetNumberOfPeopleBySegment(3001L);
        segmentBreakdown.setSegmentBreakdown("{\"name\": \"hoge\"}");
        segmentBreakdown.setDeletionFlagForAudit("f");
        segmentBreakdown.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        segmentBreakdown.setCreateUserIdForAudit("user_01");
        segmentBreakdown.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        segmentBreakdown.setDeletionUserIdForAudit("user_02");
        segmentBreakdown.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        segmentBreakdown.setUpdateUserIdForAudit("user_03");
        SegmentBreakdownGetResponse response = new SegmentBreakdownGetResponse(segmentBreakdown);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{"
                + "    \"segmentBreakdownSequence\": 1001,"
                + "    \"segmentSequence\": 2001,"
                + "    \"targetNumberOfPeopleBySegment\": 3001,"
                + "    \"segmentBreakdown\": \"{\\\"name\\\": \\\"hoge\\\"}\","
                + "    \"deletionFlagForAudit\": \"f\","
                + "    \"createDateTimeForAudit\": \"2021-01-01 01:01:01\","
                + "    \"createUserIdForAudit\": \"user_01\","
                + "    \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                + "    \"updateUserIdForAudit\": \"user_03\""
                + "}");
    }
}
