package com.fastretailing.marketingpf.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutboundSettingSegmentIntermediate extends BaseEntity {

    private Long outboundSettingSegmentIntermediateSequence;
    private Long outboundSettingSequence;
    private Long segmentSequence;
}
