package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.domain.dtos.CredentialInfoJsonDto;
import com.fastretailing.marketingpf.domain.dtos.SubmissionInfoJsonDto;
import com.fastretailing.marketingpf.domain.dtos.SubmissionInfoJsonDto.SubmissionDetail;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingMapper;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingSegmentIntermediateMapper;
import com.fastretailing.marketingpf.domain.mappers.OutputPlatformOutboundSettingIntermediateMapper;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import com.fastretailing.marketingpf.services.UserListUploadService.UserListUploadRequest;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class UserListUploadServiceTest extends BaseSpringBootTest {

    @Nested
    public class UploadUserListTest {

        @MockBean
        private UserListUploadService userListUploadService;

        @MockBean
        private OutboundSettingMapper outboundSettingMapper;

        @MockBean
        private OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @MockBean
        private OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

        @Captor
        private ArgumentCaptor<URI> uriCaptor;

        @Captor
        private ArgumentCaptor<UserListUploadRequest> bodyCaptor;

        @Autowired
        private Config config;

        @Test
        @Sql("/services/UserListUploadServiceTest/m_ads_pltfrm.sql")
        @Sql("/services/UserListUploadServiceTest/t_api_auth_info.sql")
        public void testUploadUserList() {
            userListUploadService.config = config;
            userListUploadService.outboundSettingMapper = outboundSettingMapper;
            userListUploadService.outputPlatformOutboundSettingIntermediateMapper = outputPlatformOutboundSettingIntermediateMapper;
            userListUploadService.outboundSettingSegmentIntermediateMapper = outboundSettingSegmentIntermediateMapper;

            OutboundSetting outboundSetting = new OutboundSetting();
            outboundSetting.setBusinessType("1");
            outboundSetting.setBrandId("010");
            outboundSetting.setCountryId("1");
            outboundSetting.setSubmissionTimingType("1");
            outboundSetting.setReserveSubmissionDateTime(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outboundSetting.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outboundSetting.setRegularlySubmissionFrequencyPeriodNumberValue(3000);
            outboundSetting.setRegularlySubmissionFrequencyPeriodUnit(3);
            outboundSetting.setSubmissionCompletionContact("[\"mail1\", \"mail2\"]");
            outboundSetting.setOutboundSettingName("outboundSettingName");
            outboundSetting.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 2, 2, 2, 2));

            OutputPlatformOutboundSettingIntermediate outputPlatform = new OutputPlatformOutboundSettingIntermediate();
            outputPlatform.setOutboundSettingSequence(2003L);
            outputPlatform.setOutputPlatformSequence(3003L);
            outputPlatform.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatform.setAdAccountSequence(4003L);
            outputPlatform.setAdsPlatformSequence(1L);
            outputPlatform.setCrmPlatformSequence(6003L);
            outputPlatform.setAudienceId("10001");
            outputPlatform.setUserListName("userListName1");
            outputPlatform.setExtractionTargetId("3");
            outputPlatform.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 2, 2, 2, 2));

            OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate = new OutboundSettingSegmentIntermediate();
            outboundSettingSegmentIntermediate.setOutboundSettingSegmentIntermediateSequence(1001L);
            outboundSettingSegmentIntermediate.setOutboundSettingSequence(2001L);
            outboundSettingSegmentIntermediate.setSegmentSequence(3001L);

            OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate2 = new OutboundSettingSegmentIntermediate();
            outboundSettingSegmentIntermediate2.setOutboundSettingSegmentIntermediateSequence(1002L);
            outboundSettingSegmentIntermediate2.setOutboundSettingSequence(2001L);
            outboundSettingSegmentIntermediate2.setSegmentSequence(3002L);

            BatchJobDto batchJobDto = new BatchJobDto();
            batchJobDto.setBatchJobSequence(1001L);
            batchJobDto.setBatchJobType(BATCH_JOB_TYPE.UPLOAD.getValueAsString());
            batchJobDto.setSegmentSequenceList("[1001,1002]");
            batchJobDto.setOutboundSettingSequence(3001L);
            batchJobDto.setStatus(BATCH_JOB_STATUS.REQUESTED.getValueAsString());
            batchJobDto.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 1, 1, 1, 1));

            BatchJob batchJob = new BatchJob();
            batchJob.setJob(batchJobDto);
            CredentialInfoJsonDto credentialInfoJson = new CredentialInfoJsonDto("clientId", "clientSecret", "developerToken", "refreshToken");
            SubmissionInfoJsonDto submissionInfoJson = new SubmissionInfoJsonDto(new SubmissionDetail("10001", "userListName1"), new SubmissionDetail("10003", "userListName3"), new SubmissionDetail("10005", "userListName5"));
            doReturn(outboundSetting).when(outboundSettingMapper).findById(any());
            doReturn(Arrays.asList(outputPlatform)).when(outputPlatformOutboundSettingIntermediateMapper).findListByOutboundSettingSequence(any());
            doReturn(Arrays.asList(outboundSettingSegmentIntermediate, outboundSettingSegmentIntermediate2)).when(outboundSettingSegmentIntermediateMapper).findByOutboundSettingSequence(any());
            doReturn(credentialInfoJson).when(userListUploadService).createCredentialInfoJson(any(), any());
            doReturn(submissionInfoJson).when(userListUploadService).createSubmissionInfoJson(any());
            doReturn(batchJob).when(userListUploadService).postForMono(uriCaptor.capture(), bodyCaptor.capture(), any());
            doCallRealMethod().when(userListUploadService).uploadUserList(any(), any(), any(), any());

            BatchJob batchJobResult = userListUploadService.uploadUserList(3001L, "select..", 2001L, "user1");
            BatchJobDto batchJobDtoResult = batchJobResult.getJob();
            assertThat(uriCaptor.getValue().toString()).isEqualTo("/api/marketingdb/v1/fr/jp/userlist/");
            assertThat(bodyCaptor.getValue().getSegmentSequenceList().toString()).isEqualTo("[3001, 3002]");
            assertThat(bodyCaptor.getValue().getOutboundSettingSequence()).isEqualTo(3001L);
            assertThat(bodyCaptor.getValue().getSql()).isEqualTo("select..");
            assertThat(bodyCaptor.getValue().getAdAccountSequence()).isEqualTo(4003L);
            assertThat(bodyCaptor.getValue().getNotificationList().toString()).isEqualTo("[mail1, mail2]");
            assertThat(bodyCaptor.getValue().getOutboundSettingName()).isEqualTo("outboundSettingName");
            assertThat(bodyCaptor.getValue().getAdsPlatformSequence()).isEqualTo(1L);
            assertThat(bodyCaptor.getValue().getSubmissionInfoJson()).isEqualTo(submissionInfoJson);
            assertThat(bodyCaptor.getValue().getCredentialInfoJson()).isEqualTo(credentialInfoJson);
            assertThat(batchJobDtoResult.getBatchJobSequence()).isEqualTo(1001L);
            assertThat(batchJobDtoResult.getBatchJobType()).isEqualTo(BATCH_JOB_TYPE.UPLOAD.getValueAsString());
            assertThat(batchJobDtoResult.getSegmentSequenceList()).isEqualTo("[1001,1002]");
            assertThat(batchJobDtoResult.getOutboundSettingSequence()).isEqualTo(3001L);
            assertThat(batchJobDtoResult.getStatus()).isEqualTo(BATCH_JOB_STATUS.REQUESTED.getValueAsString());
            assertThat(batchJobDtoResult.getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(batchJobDtoResult.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1));
            assertThat(batchJobDtoResult.getCreateUserIdForAudit()).isEqualTo("user1");

            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(userListUploadService).uploadUserList(any(), any(), any(), any());

            try {
                userListUploadService.uploadUserList(3001L, "select..", 2001L, "user1");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class CreateCredentialInfoJsonTest {

        @Autowired
        private UserListUploadService userListUploadService;

        @Test
        @Sql("/services/UserListUploadServiceTest/m_ads_pltfrm.sql")
        @Sql("/services/UserListUploadServiceTest/t_api_auth_info.sql")
        public void testCreateCredentialInfoJson() {
            CredentialInfoJsonDto googleCredentialInfoJson = userListUploadService.createCredentialInfoJson(1L, 1001L);
            assertThat(googleCredentialInfoJson.getRefreshToken()).isEqualTo("refresh_token");
            assertThat(googleCredentialInfoJson.getClientId()).isEqualTo("googleusercontent.com");
            assertThat(googleCredentialInfoJson.getClientSecret()).isEqualTo("client_secret");
            assertThat(googleCredentialInfoJson.getDeveloperToken()).isEqualTo("developer_token");

            CredentialInfoJsonDto facebookCredentialInfoJson = userListUploadService.createCredentialInfoJson(2L, 1002L);
            assertThat(facebookCredentialInfoJson.getAccessToken()).isEqualTo("access_token");
            assertThat(facebookCredentialInfoJson.getAppId()).isEqualTo("AppId");
            assertThat(facebookCredentialInfoJson.getAppSecret()).isEqualTo("AppSecret");
        }
    }

    @Nested
    public class CreateSubmissionInfoJsonTest {

        @Autowired
        private UserListUploadService userListUploadService;

        @Test
        public void expectingSuccess() {
            OutputPlatformOutboundSettingIntermediate outputPlatform = new OutputPlatformOutboundSettingIntermediate();
            outputPlatform.setOutboundSettingSequence(2003L);
            outputPlatform.setOutputPlatformSequence(3003L);
            outputPlatform.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatform.setAdAccountSequence(4003L);
            outputPlatform.setAdsPlatformSequence(5003L);
            outputPlatform.setCrmPlatformSequence(6003L);
            outputPlatform.setAudienceId("10001");
            outputPlatform.setUserListName("userListName1");
            outputPlatform.setExtractionTargetId(EXTRACTION_TARGET_ID.MAIL_ADDRESS.getValueAsString());
            outputPlatform.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 1, 1, 1, 1));

            OutputPlatformOutboundSettingIntermediate outputPlatform2 = new OutputPlatformOutboundSettingIntermediate();
            outputPlatform2.setOutboundSettingSequence(2003L);
            outputPlatform2.setOutputPlatformSequence(3003L);
            outputPlatform2.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatform2.setAdAccountSequence(4003L);
            outputPlatform2.setAdsPlatformSequence(5003L);
            outputPlatform2.setCrmPlatformSequence(6003L);
            outputPlatform2.setAudienceId("10002");
            outputPlatform2.setUserListName("userListName2");
            outputPlatform2.setExtractionTargetId(EXTRACTION_TARGET_ID.ADID.getValueAsString());
            outputPlatform2.setAuditInfoForCreation("user2", LocalDateTime.of(2021, 2, 2, 2, 2));

            OutputPlatformOutboundSettingIntermediate outputPlatform3 = new OutputPlatformOutboundSettingIntermediate();
            outputPlatform3.setOutboundSettingSequence(2003L);
            outputPlatform3.setOutputPlatformSequence(3003L);
            outputPlatform3.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatform3.setAdAccountSequence(4003L);
            outputPlatform3.setAdsPlatformSequence(5003L);
            outputPlatform3.setCrmPlatformSequence(6003L);
            outputPlatform3.setAudienceId("10003");
            outputPlatform3.setUserListName("userListName3");
            outputPlatform3.setExtractionTargetId(EXTRACTION_TARGET_ID.IDFA.getValueAsString());
            outputPlatform3.setAuditInfoForCreation("user3", LocalDateTime.of(2021, 3, 3, 3, 3));

            OutputPlatformOutboundSettingIntermediate outputPlatform4 = new OutputPlatformOutboundSettingIntermediate();
            outputPlatform4.setOutboundSettingSequence(2003L);
            outputPlatform4.setOutputPlatformSequence(3003L);
            outputPlatform4.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatform4.setAdAccountSequence(4003L);
            outputPlatform4.setAdsPlatformSequence(5003L);
            outputPlatform4.setCrmPlatformSequence(6003L);
            outputPlatform4.setAudienceId("10004");
            outputPlatform4.setUserListName("userListName4");
            outputPlatform4.setExtractionTargetId("4");
            outputPlatform4.setAuditInfoForCreation("user4", LocalDateTime.of(2021, 3, 3, 3, 3));

            SubmissionInfoJsonDto submissionInfoJsonDto = userListUploadService.createSubmissionInfoJson(Arrays.asList(outputPlatform, outputPlatform2, outputPlatform3));
            assertThat(submissionInfoJsonDto.getMail().userListId).isEqualTo("10001");
            assertThat(submissionInfoJsonDto.getMail().userListName).isEqualTo("userListName1");
            assertThat(submissionInfoJsonDto.getAdid().userListId).isEqualTo("10002");
            assertThat(submissionInfoJsonDto.getAdid().userListName).isEqualTo("userListName2");
            assertThat(submissionInfoJsonDto.getIdfa().userListId).isEqualTo("10003");
            assertThat(submissionInfoJsonDto.getIdfa().userListName).isEqualTo("userListName3");
        }
    }
}
