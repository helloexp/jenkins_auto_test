package com.fastretailing.marketingpf.domain.entities;

import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutputPlatformMaster extends BaseEntity {

    private Long outputPlatformSequence;
    private String outputPlatformType;
    private String outputPlatformName;

    public OUTPUT_PLATFORM_TYPE getOutputPlatformTypeAsEnum() {
        return OUTPUT_PLATFORM_TYPE.createFromValue(this.outputPlatformType);
    }
}
