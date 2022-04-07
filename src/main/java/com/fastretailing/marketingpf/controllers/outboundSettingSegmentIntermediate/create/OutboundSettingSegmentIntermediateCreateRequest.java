package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.utils.JsonUtils;
import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.BusinessTypeValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import com.fastretailing.marketingpf.validators.Required;
import com.fastretailing.marketingpf.validators.SequenceValid;
import com.fastretailing.marketingpf.validators.SubmissionTimingTypeValid;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OutboundSettingSegmentIntermediateCreateRequest {

    @Required
    @BrandValid
    private String brandId;

    @Required
    @CountryValid
    private String countryId;

    @Required
    @BusinessTypeValid
    private String businessType;

    @Required
    @SubmissionTimingTypeValid
    private String submissionTimingType;

    private LocalDateTime reserveSubmissionDateTime;

    private LocalDateTime regularlySubmissionFrequencyDateTimeBasis;

    private Integer regularlySubmissionFrequencyPeriodNumberValue;

    private Integer regularlySubmissionFrequencyPeriodUnit;

    @Required
    private List<String> submissionCompletionContactList;

    @Required
    private String outboundSettingName;

    @Required
    private List<Long> segmentSequenceList;

    @Required
    @SequenceValid
    private Long adsPlatformSequence;

    @Required
    private String adsPfLoginUserId;

    @Required
    private Long adAccountSequence;

    @Required
    private List<Long> extractionTargetId;

    /**
     * Get BusinessType as enum
     *
     * @return BUSINESS_TYPE
     */
    @Hidden
    public BUSINESS_TYPE getBusinessTypeAsEnum() {
        return BUSINESS_TYPE.createFromValue(this.businessType.toString());
    }

    /**
     * Get brandId as enum
     *
     * @return BRAND
     */
    @Hidden
    public BRAND getBrandAsEnum() {
        return BRAND.createFromCode(this.brandId);
    }

    /**
     * Get countryId as enum
     *
     * @return COUNTRY
     */
    @Hidden
    public COUNTRY getCountryAsEnum() {
        return COUNTRY.createFromCode(this.countryId);
    }

    /**
     * get submissionTimingType as enum
     *
     * @return SUBMISSION_TIMING_TYPE
     */
    @Hidden
    public SUBMISSION_TIMING_TYPE getSubmissionTimingTypeAsEnum() {
        return SUBMISSION_TIMING_TYPE.createFromValue(this.submissionTimingType.toString());
    }

    /**
     * get extractionTargetId enum list
     *
     * @return List<<code>EXTRACTION_TARGET_ID</code>>
     */
    @Hidden
    public List<EXTRACTION_TARGET_ID> getExtractionTargetIdList() {
        List<EXTRACTION_TARGET_ID> extractionTargetIdList = new ArrayList<>();
        for (Long value : this.extractionTargetId) {
            extractionTargetIdList.add(EXTRACTION_TARGET_ID.createFromValue(value.toString()));
        }
        return extractionTargetIdList;
    }

    @Hidden
    public String getSubmissionCompletionContactListAsString() {
        if (this.submissionCompletionContactList == null) {
            return null;
        }
        return JsonUtils.toJson(this.submissionCompletionContactList);
    }
}
