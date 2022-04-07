package com.fastretailing.marketingpf.controllers.batchJob.search;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.BatchJobList;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList.BatchJobListDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.services.BatchJobService;
import com.fastretailing.marketingpf.services.OutboundSettingService;
import com.fastretailing.marketingpf.services.OutputPlatformOutboundSettingIntermediateService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class BatchJobSearchControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public SegmentService segmentService;

    @MockBean
    public BatchJobService batchJobService;

    @MockBean
    public OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

    @MockBean
    public OutboundSettingService outboundSettingService;

    @MockBean
    public UserMasterService userMasterService;

    @Test
    public void expectingFindSuccess() throws Exception {
        Segment s_1 = new Segment();
        s_1.setSegmentSequence(1001L);
        s_1.setSegmentName("name_01");
        Segment s_2 = new Segment();
        s_2.setSegmentSequence(1002L);
        s_2.setSegmentName("name_02");
        Segment s_3 = new Segment();
        s_3.setSegmentSequence(1003L);
        s_3.setSegmentName("name_03");
        List<Segment> segmentList = Arrays.asList(s_1, s_2, s_3);
        Mockito.doReturn(segmentList).when(segmentService).findListBySegmentName(Mockito.any());
        Mockito.doReturn(s_1).doReturn(s_2).doReturn(s_3).doReturn(s_1).doReturn(s_2).doReturn(s_3).when(segmentService).findById(Mockito.any());

        BatchJobListDto job_1001 = new BatchJobListDto();
        job_1001.setBatchJobSequence(1001L);
        job_1001.setBatchJobType("batchJobType_01");
        job_1001.setSegmentSequenceList("[1001,1002,1003]");
        job_1001.setOutboundSettingSequence(3001L);
        job_1001.setStatus("status_01");
        job_1001.setDeletionFlagForAudit("f");
        job_1001.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        job_1001.setCreateUserIdForAudit("user_01");
        job_1001.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        job_1001.setUpdateUserIdForAudit("user_01");
        BatchJobListDto job_1002 = new BatchJobListDto();
        job_1002.setBatchJobSequence(1002L);
        job_1002.setBatchJobType("batchJobType_02");
        job_1002.setSegmentSequenceList("[1001,1002,1003]");
        job_1002.setOutboundSettingSequence(3002L);
        job_1002.setStatus("status_02");
        job_1002.setDeletionFlagForAudit("f");
        job_1002.setCreateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        job_1002.setCreateUserIdForAudit("user_02");
        job_1002.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        job_1002.setUpdateUserIdForAudit("user_02");
        BatchJobList batchJobList = new BatchJobList();
        batchJobList.setJobList(Arrays.asList(job_1001, job_1002));
        Mockito.doReturn(batchJobList).when(batchJobService).search(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSetting = new OutputPlatformOutboundSettingIntermediate();
        outputPlatformOutboundSetting.setOutputPlatformOutboundSettingIntermediateSequence(1001L);
        outputPlatformOutboundSetting.setOutboundSettingSequence(2001L);
        outputPlatformOutboundSetting.setOutputPlatformSequence(3001L);
        outputPlatformOutboundSetting.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
        outputPlatformOutboundSetting.setAdAccountSequence(4001L);
        outputPlatformOutboundSetting.setAdsPlatformSequence(5001L);
        outputPlatformOutboundSetting.setCrmPlatformSequence(6001L);
        outputPlatformOutboundSetting.setAudienceId("456");
        outputPlatformOutboundSetting.setUserListName("name_01");
        outputPlatformOutboundSetting.setExtractionTargetId(EXTRACTION_TARGET_ID.MAIL_ADDRESS.getValueAsString());

        OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSetting2 = new OutputPlatformOutboundSettingIntermediate();
        outputPlatformOutboundSetting2.setOutputPlatformOutboundSettingIntermediateSequence(1002L);
        outputPlatformOutboundSetting2.setOutboundSettingSequence(2001L);
        outputPlatformOutboundSetting2.setOutputPlatformSequence(3001L);
        outputPlatformOutboundSetting2.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
        outputPlatformOutboundSetting2.setAdAccountSequence(4001L);
        outputPlatformOutboundSetting2.setAdsPlatformSequence(5001L);
        outputPlatformOutboundSetting2.setCrmPlatformSequence(6001L);
        outputPlatformOutboundSetting2.setAudienceId("789");
        outputPlatformOutboundSetting2.setUserListName("name_789");
        outputPlatformOutboundSetting2.setExtractionTargetId(EXTRACTION_TARGET_ID.ADID.getValueAsString());
        Mockito.doReturn(Arrays.asList(outputPlatformOutboundSetting, outputPlatformOutboundSetting2)).when(outputPlatformOutboundSettingIntermediateService).findListByOutboundSettingSequence(Mockito.any());

        OutboundSetting outboundSetting = new OutboundSetting();
        outboundSetting.setOutboundSettingSequence(1001L);
        outboundSetting.setBusinessType(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER.getValueAsString());
        outboundSetting.setBrandId(BRAND.UQ.getCode());
        outboundSetting.setCountryId(COUNTRY.JP.getCode());
        outboundSetting.setSubmissionTimingType(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION.getValueAsString());
        outboundSetting.setReserveSubmissionDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        outboundSetting.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        outboundSetting.setRegularlySubmissionFrequencyPeriodNumberValue(1000);
        outboundSetting.setRegularlySubmissionFrequencyPeriodUnit(1);
        outboundSetting.setSubmissionCompletionContact("{}");
        outboundSetting.setOutboundSettingName("name_01");
        Mockito.doReturn(outboundSetting).when(outboundSettingService).findById(Mockito.any());

        Map<String, String> userMasterMap = new HashMap<String, String>();
        userMasterMap.put("user_01", "user_fullname_01");
        userMasterMap.put("user_02", "user_fullname_02");
        Mockito.doReturn(userMasterMap).when(userMasterService).getUserIdToUserNameMap();

        mvc.perform(get("/api/marketingpf/v1/fr/jp/request_get_jobs/").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"jobList\": [{"
                                + "            \"audienceIdList\": [\"name_01(456)\", \"name_789(789)\"],"
                                + "            \"businessType\": \"1\","
                                + "            \"submissionTimingType\": \"1\","
                                + "            \"reserveSubmissionDateTime\": \"2021-01-01 00:00:00\","
                                + "            \"regularlySubmissionFrequencyDateTimeBasis\": \"2021-01-01 00:00:00\","
                                + "            \"regularlySubmissionFrequencyPeriodNumberValue\": 1000,"
                                + "            \"regularlySubmissionFrequencyPeriodUnit\": 1,"
                                + "            \"batchJobSequence\": 1001,"
                                + "            \"batchJobType\": \"batchJobType_01\","
                                + "            \"outboundSettingSequence\": 3001,"
                                + "            \"status\": \"status_01\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createUserIdForAudit\": \"user_fullname_01\","
                                + "            \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_01\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"segmentInfoList\": [{"
                                + "                    \"segmentSequence\": 1001,"
                                + "                    \"segmentName\": \"name_01\""
                                + "                }, {"
                                + "                    \"segmentSequence\": 1002,"
                                + "                    \"segmentName\": \"name_02\""
                                + "                }, {"
                                + "                    \"segmentSequence\": 1003,"
                                + "                    \"segmentName\": \"name_03\""
                                + "                }"
                                + "            ]"
                                + "        }, {"
                                + "            \"audienceIdList\": [\"name_01(456)\", \"name_789(789)\"],"
                                + "            \"businessType\": \"1\","
                                + "            \"submissionTimingType\": \"1\","
                                + "            \"reserveSubmissionDateTime\": \"2021-01-01 00:00:00\","
                                + "            \"regularlySubmissionFrequencyDateTimeBasis\": \"2021-01-01 00:00:00\","
                                + "            \"regularlySubmissionFrequencyPeriodNumberValue\": 1000,"
                                + "            \"regularlySubmissionFrequencyPeriodUnit\": 1,"
                                + "            \"batchJobSequence\": 1002,"
                                + "            \"batchJobType\": \"batchJobType_02\","
                                + "            \"outboundSettingSequence\": 3002,"
                                + "            \"status\": \"status_02\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createUserIdForAudit\": \"user_fullname_02\","
                                + "            \"updateDateTimeForAudit\": \"2021-02-02 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_02\","
                                + "            \"createDateTimeForAudit\": \"2021-02-02 00:00:00\","
                                + "            \"segmentInfoList\": [{"
                                + "                    \"segmentSequence\": 1001,"
                                + "                    \"segmentName\": \"name_01\""
                                + "                }, {"
                                + "                    \"segmentSequence\": 1002,"
                                + "                    \"segmentName\": \"name_02\""
                                + "                }, {"
                                + "                    \"segmentSequence\": 1003,"
                                + "                    \"segmentName\": \"name_03\""
                                + "                }"
                                + "            ]"
                                + "        }"
                                + "    ]"
                                + "}"));
    }
}
