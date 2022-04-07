package com.fastretailing.marketingpf.domain.dtos;

import com.fastretailing.marketingpf.domain.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to map data from MKDB
 * this table is actually being managed in MKDB
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SegmentBreakdown extends BaseEntity {

    private Long segmentBreakdownSequence;
    private Long segmentSequence;
    private Long targetNumberOfPeopleBySegment;
    private String segmentBreakdown;
}
