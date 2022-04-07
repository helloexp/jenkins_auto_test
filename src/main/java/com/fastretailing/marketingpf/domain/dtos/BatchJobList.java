package com.fastretailing.marketingpf.domain.dtos;

import com.fastretailing.marketingpf.domain.entities.BaseEntity;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to map data from MKDB.
 * This table is actually being managed in MKDB
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BatchJobList {

    private List<BatchJobListDto> jobList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class BatchJobListDto extends BaseEntity {

        private Long batchJobSequence;
        private String batchJobType;
        private String segmentSequenceList;
        private Long outboundSettingSequence;
        private String status;
    }
}
