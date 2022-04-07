package com.fastretailing.marketingpf.controllers.segmentBreakdown.get;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.services.SegmentBreakdownService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentBreakdownGetControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public SegmentBreakdownService segmentBreakdownService;

    @Test
    public void expectingGetSuccess_givingAvailableParams() throws Exception {
        SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
        segmentBreakdown.setSegmentBreakdownSequence(1001L);
        segmentBreakdown.setSegmentSequence(2001L);
        segmentBreakdown.setTargetNumberOfPeopleBySegment(3001L);
        segmentBreakdown.setSegmentBreakdown("{\"name\": \"hoge\"}");
        segmentBreakdown.setDeletionFlagForAudit("f");
        segmentBreakdown.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        segmentBreakdown.setCreateUserIdForAudit("user_01");
        segmentBreakdown.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        segmentBreakdown.setUpdateUserIdForAudit("user_03");
        Mockito.doReturn(segmentBreakdown).when(segmentBreakdownService).findBySegmentSequence(Mockito.anyLong());

        mvc.perform(get("/api/marketingpf/v1/fr/jp/segment_breakdown/1001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
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
                        + "}"));
    }
}
