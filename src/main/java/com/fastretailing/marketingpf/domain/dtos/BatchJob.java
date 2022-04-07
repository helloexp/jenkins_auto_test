package com.fastretailing.marketingpf.domain.dtos;

import com.fastretailing.marketingpf.domain.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to map data from MKDB.<br>
 * This table is actually being managed in MKDB
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BatchJob {

    private BatchJobDto job;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class BatchJobDto extends BaseEntity {

        private Long batchJobSequence;
        private String batchJobType;
        private String segmentSequenceList;
        private Long outboundSettingSequence;
        private String status;
    }
}
