package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList.BatchJobListDto;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class BatchJobServiceTest extends BaseSpringBootTest {

    @Nested
    public class CreateTest {

        @MockBean
        private BatchJobService batchJobService;

        @Test
        public void expectingCreateSuccess() {
            BatchJob batchJob = new BatchJob();
            batchJob.setJob(new BatchJobDto());
            batchJob.getJob().setBatchJobSequence(1001L);
            batchJob.getJob().setBatchJobType("batchJobType_01");
            batchJob.getJob().setSegmentSequenceList("[1001,1002]");
            batchJob.getJob().setOutboundSettingSequence(3001L);
            batchJob.getJob().setStatus("status_01");
            batchJob.getJob().setDeletionFlagForAudit("false");
            batchJob.getJob().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1));
            batchJob.getJob().setCreateUserIdForAudit("user_01");
            batchJob.getJob().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            batchJob.getJob().setDeletionUserIdForAudit("user_02");
            batchJob.getJob().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            batchJob.getJob().setUpdateUserIdForAudit("user_03");
            Mockito.doReturn(batchJob).when(batchJobService).create(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString());

            BatchJob result = batchJobService.create(2001L, "hoge", "user_01");
            assertThat(result.getJob().getBatchJobSequence()).isEqualTo(1001L);
            assertThat(result.getJob().getBatchJobType()).isEqualTo("batchJobType_01");
            assertThat(result.getJob().getSegmentSequenceList()).isEqualTo("[1001,1002]");
            assertThat(result.getJob().getOutboundSettingSequence()).isEqualTo(3001L);
            assertThat(result.getJob().getStatus()).isEqualTo("status_01");
            assertThat(result.getJob().getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getJob().getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1));
            assertThat(result.getJob().getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getJob().getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            assertThat(result.getJob().getDeletionUserIdForAudit()).isEqualTo("user_02");
            assertThat(result.getJob().getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            assertThat(result.getJob().getUpdateUserIdForAudit()).isEqualTo("user_03");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(batchJobService).create(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString());

            try {
                batchJobService.create(2002L, "hoge", "user_01");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class SearchTest {

        @MockBean
        private BatchJobService batchJobService;

        @Autowired
        private Config config;

        @Test
        public void expectingSearchSuccess() {
            batchJobService.config = config;
            BatchJobListDto job_1001 = new BatchJobListDto();
            job_1001.setBatchJobSequence(1001L);
            job_1001.setBatchJobType("batchJobType_01");
            job_1001.setSegmentSequenceList("{[2001,2002]}");
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
            job_1002.setSegmentSequenceList("{[2003,2004]}");
            job_1002.setOutboundSettingSequence(3002L);
            job_1002.setStatus("status_02");
            job_1002.setDeletionFlagForAudit("f");
            job_1002.setCreateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            job_1002.setCreateUserIdForAudit("user_02");
            job_1002.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            job_1002.setUpdateUserIdForAudit("user_02");
            BatchJobList batchJobList = new BatchJobList();
            batchJobList.setJobList(Arrays.asList(job_1001, job_1002));

            ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
            doReturn(batchJobList).when(batchJobService).getAsMono(uriCaptor.capture(), any());
            doCallRealMethod().when(batchJobService).search(any(), any(), any(), any());
            BatchJobList result = batchJobService.search(BATCH_JOB_TYPE.UPLOAD, Arrays.asList(1001L, 1002L), BATCH_JOB_STATUS.STOP, "user");
            assertThat(uriCaptor.getValue().toString()).isEqualTo("/api/marketingdb/v1/fr/jp/jobs/?segmentSequenceList=1001&segmentSequenceList=1002&batchJobType=1&status=4&updateUserIdForAudit=user");
            assertThat(result.getJobList().size()).isEqualTo(2);
            assertThat(result.getJobList().get(0).getBatchJobSequence()).isEqualTo(1001L);
            assertThat(result.getJobList().get(0).getBatchJobType()).isEqualTo("batchJobType_01");
            assertThat(result.getJobList().get(0).getSegmentSequenceList()).isEqualTo("{[2001,2002]}");
            assertThat(result.getJobList().get(0).getOutboundSettingSequence()).isEqualTo(3001L);
            assertThat(result.getJobList().get(0).getStatus()).isEqualTo("status_01");
            assertThat(result.getJobList().get(1).getBatchJobSequence()).isEqualTo(1002L);
            assertThat(result.getJobList().get(1).getBatchJobType()).isEqualTo("batchJobType_02");
            assertThat(result.getJobList().get(1).getSegmentSequenceList()).isEqualTo("{[2003,2004]}");
            assertThat(result.getJobList().get(1).getOutboundSettingSequence()).isEqualTo(3002L);
            assertThat(result.getJobList().get(1).getStatus()).isEqualTo("status_02");

            result = batchJobService.search(null, Arrays.asList(1001L, 1002L), null, "user");
            assertThat(uriCaptor.getValue().toString()).isEqualTo("/api/marketingdb/v1/fr/jp/jobs/?segmentSequenceList=1001&segmentSequenceList=1002&updateUserIdForAudit=user");
        }
    }

    @Nested
    public class StopTest {

        @MockBean
        private BatchJobService batchJobService;

        @Autowired
        private Config config;

        @Test
        public void expectingSuccess() {
            batchJobService.config = config;
            ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
            doReturn(new BatchJob()).when(batchJobService).putForMono(uriCaptor.capture(), any());
            doCallRealMethod().when(batchJobService).stop(any());
            batchJobService.stop(1001L);
            assertThat(uriCaptor.getValue().toString()).isEqualTo("/api/marketingdb/v1/fr/jp/job/stop/1001");
        }
    }
}
