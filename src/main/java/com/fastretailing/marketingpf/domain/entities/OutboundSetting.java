package com.fastretailing.marketingpf.domain.entities;

import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutboundSetting extends BaseEntity {

    private Long outboundSettingSequence;
    private String businessType;
    private String brandId;
    private String countryId;
    private String submissionTimingType;
    private LocalDateTime reserveSubmissionDateTime;
    private LocalDateTime regularlySubmissionFrequencyDateTimeBasis;
    private Integer regularlySubmissionFrequencyPeriodNumberValue;
    private Integer regularlySubmissionFrequencyPeriodUnit;
    private String submissionCompletionContact;
    private String outboundSettingName;

    /**
     * Get BusinessType As Enum
     *
     * @return BUSINESS_TYPE
     */
    public BUSINESS_TYPE getBusinessTypeAsEnum() {
        return BUSINESS_TYPE.createFromValue(this.businessType);
    }

    /**
     * Get SubmissionTimingType As Enum
     *
     * @return SUBMISSION_TIMING_TYPE
     */
    public SUBMISSION_TIMING_TYPE getSubmissionTimingTypeAsEnum() {
        return SUBMISSION_TIMING_TYPE.createFromValue(this.submissionTimingType);
    }
}
