
package com.fastretailing.marketingpf.controllers.batchJob.create;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;

@JsonPropertyOrder({
        "batchJobSequence",
        "batchJobType",
        "segmentSequenceList",
        "outboundSettingSequence",
        "status",
        "batchJobName",
        "deletionFlagForAudit",
        "createDateTimeForAudit",
        "createUserIdForAudit",
        "deletionDateTimeForAudit",
        "deletionUserIdForAudit",
        "updateDateTimeForAudit",
        "updateUserIdForAudit"})
public class BatchJobCreateResponse extends BaseAuditResponse {

    public Long batchJobSequence;
    public String batchJobType;
    public String segmentSequenceList;
    public Long outboundSettingSequence;
    public String status;

    public BatchJobCreateResponse(BatchJob job) {
        this.batchJobSequence = job.getJob().getBatchJobSequence();
        this.batchJobType = job.getJob().getBatchJobType();
        this.segmentSequenceList = job.getJob().getSegmentSequenceList();
        this.outboundSettingSequence = job.getJob().getOutboundSettingSequence();
        this.status = job.getJob().getStatus();
        this.deletionFlagForAudit = job.getJob().getDeletionFlagForAudit();
        this.createDateTimeForAudit = job.getJob().getCreateDateTimeForAudit();
        this.createUserIdForAudit = job.getJob().getCreateUserIdForAudit();
        this.deletionDateTimeForAudit = job.getJob().getDeletionDateTimeForAudit();
        this.deletionUserIdForAudit = job.getJob().getDeletionUserIdForAudit();
        this.updateDateTimeForAudit = job.getJob().getUpdateDateTimeForAudit();
        this.updateUserIdForAudit = job.getJob().getUpdateUserIdForAudit();
    }
}
