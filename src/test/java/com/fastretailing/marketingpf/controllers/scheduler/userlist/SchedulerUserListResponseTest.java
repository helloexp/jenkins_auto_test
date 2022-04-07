package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import com.fastretailing.marketingpf.controllers.scheduler.userlist.SchedulerUserListResponse.SchedulerUserListResponseDto;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SchedulerUserListResponseTest{

    @Test
    public void testSchedulerUserListResponseDto() {
        BatchJobDto batchJobDto = new BatchJobDto();
        batchJobDto.setBatchJobSequence(1001L);
        batchJobDto.setBatchJobType(BATCH_JOB_TYPE.UPLOAD.getValueAsString());
        batchJobDto.setSegmentSequenceList("[1001,1002]");
        batchJobDto.setOutboundSettingSequence(3001L);
        batchJobDto.setStatus(BATCH_JOB_STATUS.REQUESTED.getValueAsString());
        batchJobDto.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 1, 1, 1, 1));
        BatchJob batchJob = new BatchJob();
        batchJob.setJob(batchJobDto);

        SchedulerUserListResponseDto response = new SchedulerUserListResponseDto(batchJob);
        JsonAssert.assertJsonOutput(response).isSameContentAs("{" +
                "    \"batchJobSequence\": 1001," +
                "    \"outboundSettingSequence\": 3001" +
                "}");
    }
}
