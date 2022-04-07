package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import lombok.Data;

public class SchedulerUserListResponse extends BaseResponse {

    public SchedulerUserListResponseDto batchJob;

    public SchedulerUserListResponse(BatchJob batchJob) {
        this.batchJob = new SchedulerUserListResponseDto(batchJob);
    }

    @Data
    @JsonPropertyOrder({"batchJobSequence", "outboundSettingSequence"})
    public static class SchedulerUserListResponseDto {
        private Long batchJobSequence;
        private Long outboundSettingSequence;

        public SchedulerUserListResponseDto(BatchJob batchJob) {
            this.batchJobSequence = batchJob.getJob().getBatchJobSequence();
            this.outboundSettingSequence = batchJob.getJob().getOutboundSettingSequence();
        }
    }
}
