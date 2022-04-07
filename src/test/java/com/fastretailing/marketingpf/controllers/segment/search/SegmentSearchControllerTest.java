package com.fastretailing.marketingpf.controllers.segment.search;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.services.SegmentBreakdownService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentSearchControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public SegmentService segmentService;

    @MockBean
    public SegmentBreakdownService segmentBreakdownService;

    @MockBean
    public UserMasterService userMasterService;

    @Test
    public void expectingSuccess() throws Exception {
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
        segment.setEditedSql("1");
        segment.setStatus("1");
        segment.setEventTypeList("{}");
        segment.setAuditInfoForCreation("user_01", LocalDateTime.of(2021, 1, 1, 0, 0, 0));

        Segment segment2 = new Segment();
        segment2.setSegmentSequence(1002L);
        segment2.setBusinessType("1");
        segment2.setBrandId("090");
        segment2.setCountryId("1");
        segment2.setDeliveryScheduledMonth("2021-01-01");
        segment2.setEventTargetPeriodType("2");
        segment2.setEventTargetPeriodStartDate(LocalDate.of(2021, 2, 2));
        segment2.setEventTargetPeriodEndDate(LocalDate.of(2021, 3, 3));
        segment2.setEventTargetPeriodRelativeNumberValue(2001L);
        segment2.setEventTargetPeriodRelativePeriodUnit("3");
        segment2.setTargetItemCategory("category_01");
        segment2.setSegmentName("segment_01");
        segment2.setDescription("desc_01");
        segment2.setSqlEditFlag("1");
        segment2.setEditedSql("1");
        segment2.setStatus("1");
        segment2.setEventTypeList("{}");
        segment2.setAuditInfoForCreation("user_02", LocalDateTime.of(2021, 1, 1, 0, 0, 2));

        SegmentBreakdown segmentBreakdown = new SegmentBreakdown();
        segmentBreakdown.setSegmentBreakdownSequence(2001L);
        segmentBreakdown.setSegmentSequence(1001L);
        segmentBreakdown.setTargetNumberOfPeopleBySegment(3001L);
        segmentBreakdown.setSegmentBreakdown("{\"name\": \"hoge\"}");
        segmentBreakdown.setAuditInfoForCreation("user_02", LocalDateTime.of(2021, 1, 1, 0, 0, 2));

        SegmentBreakdown segmentBreakdown2 = new SegmentBreakdown();
        segmentBreakdown2.setSegmentBreakdownSequence(2002L);
        segmentBreakdown2.setSegmentSequence(1002L);
        segmentBreakdown2.setTargetNumberOfPeopleBySegment(3005L);
        segmentBreakdown2.setSegmentBreakdown("{\"name\": \"hoge\"}");
        segmentBreakdown2.setAuditInfoForCreation("user_03", LocalDateTime.of(2021, 1, 1, 0, 0, 3));

        Mockito.doReturn(Arrays.asList(segment, segment2)).when(segmentService)
                .search(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        Mockito.doReturn(Arrays.asList(segmentBreakdown, segmentBreakdown2)).when(segmentBreakdownService).search(Mockito.any(), Mockito.any(), Mockito.any());

        Map<String, String> userMasterMap = new HashMap<String, String>();
        userMasterMap.put("user_01", "user_fullname_01");
        userMasterMap.put("user_02", "user_fullname_02");
        Mockito.doReturn(userMasterMap).when(userMasterService).getUserIdToUserNameMap();

        mvc.perform(get("/api/marketingpf/v1/fr/jp/segments/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"segmentList\": [{"
                                + "            \"segmentSequence\": 1001,"
                                + "            \"businessType\": \"2\","
                                + "            \"brandId\": \"010\","
                                + "            \"countryId\": \"1\","
                                + "            \"deliveryScheduledMonth\": \"2021-01-01\","
                                + "            \"eventTargetPeriodType\": \"1\","
                                + "            \"eventTargetPeriodStartDate\": \"2021-02-02\","
                                + "            \"eventTargetPeriodEndDate\": \"2021-03-03\","
                                + "            \"eventTargetPeriodRelativeNumberValue\": 2001,"
                                + "            \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                                + "            \"targetItemCategory\": \"category_01\","
                                + "            \"segmentName\": \"segment_01\","
                                + "            \"description\": \"desc_01\","
                                + "            \"sqlEditFlag\": \"1\","
                                + "            \"status\": \"1\","
                                + "            \"eventTypeList\": \"{}\","
                                + "            \"targetNumberOfPeopleBySegment\": 3001,"
                                + "            \"targetNumberOfPeopleBySegmentDateTime\": \"2021-01-01 00:00:02\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"createUserIdForAudit\": \"user_fullname_01\","
                                + "            \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_01\""
                                + "        }, {"
                                + "            \"segmentSequence\": 1002,"
                                + "            \"businessType\": \"1\","
                                + "            \"brandId\": \"090\","
                                + "            \"countryId\": \"1\","
                                + "            \"deliveryScheduledMonth\": \"2021-01-01\","
                                + "            \"eventTargetPeriodType\": \"2\","
                                + "            \"eventTargetPeriodStartDate\": \"2021-02-02\","
                                + "            \"eventTargetPeriodEndDate\": \"2021-03-03\","
                                + "            \"eventTargetPeriodRelativeNumberValue\": 2001,"
                                + "            \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                                + "            \"targetItemCategory\": \"category_01\","
                                + "            \"segmentName\": \"segment_01\","
                                + "            \"description\": \"desc_01\","
                                + "            \"sqlEditFlag\": \"1\","
                                + "            \"status\": \"1\","
                                + "            \"eventTypeList\": \"{}\","
                                + "            \"targetNumberOfPeopleBySegment\": 3005,"
                                + "            \"targetNumberOfPeopleBySegmentDateTime\": \"2021-01-01 00:00:03\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 00:00:02\","
                                + "            \"createUserIdForAudit\": \"user_fullname_02\","
                                + "            \"updateDateTimeForAudit\": \"2021-01-01 00:00:02\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_02\""
                                + "        }"
                                + "    ]"
                                + "}"));

        // Check validate request
        mvc.perform(get("/api/marketingpf/v1/fr/jp/segments/").contentType(MediaType.APPLICATION_JSON)
                        .queryParam("segmentSequence","-1")
                        .queryParam("businessType","9")
                        .queryParam("deliveryScheduledMonth","202113")
                        .queryParam("brandId","9")
                        .queryParam("countryId","9")
                        .queryParam("status","9")
                        .queryParam("eventTargetPeriodType","9")
                        .queryParam("eventTargetPeriodRelativePeriodUnit","9")
                        .queryParam("numberOfPeopleConditions","9"))
                .andExpect(status().is4xxClientError())
                .andExpect(json().errorContentEquals(
                        "{"
                                + "    \"errorCode\": \"E00400\","
                                + "    \"errorMessage\": \"Bad request\","
                                + "    \"errorDetail\": \"Validation failed\","
                                + "    \"validationErrors\": [{"
                                + "            \"field\": \"brandId\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"businessType\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"countryId\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"deliveryScheduledMonth\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"eventTargetPeriodRelativePeriodUnit\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"eventTargetPeriodType\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"numberOfPeopleConditions\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"segmentSequence\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }, {"
                                + "            \"field\": \"status\","
                                + "            \"message\": \"errors.invalid\""
                                + "        }"
                                + "    ]"
                                + "}"));

        mvc.perform(get("/api/marketingpf/v1/fr/jp/segments/").contentType(MediaType.APPLICATION_JSON)
                        .queryParam("eventTargetPeriodRelativeNumberValue","-1"))
                .andExpect(status().is4xxClientError())
                .andExpect(json().errorContentEquals(
                        "{"
                                + "    \"errorCode\": \"E00400\","
                                + "    \"errorMessage\": \"Bad request\","
                                + "    \"errorDetail\": \"Validation failed\","
                                + "    \"validationErrors\": [{"
                                + "            \"field\": \"eventTargetPeriodRelativeNumberValue\","
                                + "            \"message\": \"EventTargetPeriodRelativeNumberValue must be positive number.\""
                                + "        }"
                                + "    ]"
                                + "}"));
    }
}
