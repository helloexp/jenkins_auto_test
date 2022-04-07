package com.fastretailing.marketingpf.controllers.batchJob.search;

import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import com.fastretailing.marketingpf.validators.BatchJobStatusValid;
import com.fastretailing.marketingpf.validators.BatchJobTypeValid;
import lombok.Data;

@Data
public class BatchJobSearchRequest {

    @BatchJobTypeValid
    public String batchJobType;

    public String segmentName;

    @BatchJobStatusValid
    public String status;

    public String updateUserIdForAudit;

    /**
     * Get batchJobType as enum
     *
     * @return BATCH_JOB_TYPE
     */
    public BATCH_JOB_TYPE getBatchJobTypeAsEnum() {
        return BATCH_JOB_TYPE.createFromValue(this.batchJobType);
    }

    /**
     * Get status as enum
     *
     * @return BATCH_JOB_STATUS
     */
    public BATCH_JOB_STATUS getStatusAsEnum() {
        return BATCH_JOB_STATUS.create(this.status);
    }
}
