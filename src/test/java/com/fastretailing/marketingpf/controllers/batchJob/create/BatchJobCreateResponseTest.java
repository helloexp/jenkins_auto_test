package com.fastretailing.marketingpf.controllers.batchJob.create;

import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BatchJobCreateResponseTest {

    @Test
    public void expectingFindSuccess() {
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

        BatchJobCreateResponse response = new BatchJobCreateResponse(batchJob);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{"
                        + "    \"batchJobSequence\": 1001,"
                        + "    \"batchJobType\": \"batchJobType_01\","
                        + "    \"segmentSequenceList\": \"[1001,1002]\","
                        + "    \"outboundSettingSequence\": 3001,"
                        + "    \"status\": \"status_01\","
                        + "    \"deletionFlagForAudit\": \"false\","
                        + "    \"createDateTimeForAudit\": \"2021-01-01 01:01:00\","
                        + "    \"createUserIdForAudit\": \"user_01\","
                        + "    \"deletionDateTimeForAudit\": \"2021-02-02 02:02:02\","
                        + "    \"deletionUserIdForAudit\": \"user_02\","
                        + "    \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                        + "    \"updateUserIdForAudit\": \"user_03\""
                        + "}");
    }
}
