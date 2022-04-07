package com.fastretailing.marketingpf.domain.entities;

import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutputPlatformOutboundSettingIntermediate extends BaseEntity {

    private Long outputPlatformOutboundSettingIntermediateSequence;
    private Long outboundSettingSequence;
    private Long outputPlatformSequence;
    private String outputPlatformType;
    private Long adAccountSequence;
    private Long adsPlatformSequence;
    private Long crmPlatformSequence;
    private String audienceId;
    private String userListName;
    private String extractionTargetId;

    /**
     * Get OutputPlatformType As Enum
     *
     * @return OUTPUT_PLATFORM_TYPE
     */
    public OUTPUT_PLATFORM_TYPE getOutputPlatformTypeAsEnum() {
        return OUTPUT_PLATFORM_TYPE.createFromValue(this.outputPlatformType);
    }

    /**
     * Get extractionTargetId as enum
     *
     * @return EXTRACTION_TARGET_ID
     */
    public EXTRACTION_TARGET_ID getExtractionTargetIdAsEnum() {
        return EXTRACTION_TARGET_ID.createFromValue(this.extractionTargetId);
    }
}
