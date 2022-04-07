package com.fastretailing.marketingpf.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SegmentItemForSqlMaster extends BaseEntity {
    private Long segmentItemForSqlSequence;
    private Long segmentItemForScreenSequence;
    private String brandId;
    private String countryId;
    private String eventType;
    private String segmentItemName;
    private String queryForSegment;
}
