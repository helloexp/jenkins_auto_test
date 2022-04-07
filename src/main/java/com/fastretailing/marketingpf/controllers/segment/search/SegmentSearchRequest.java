package com.fastretailing.marketingpf.controllers.segment.search;

import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.NUMBER_OF_PEOPLE_CONDITIONS;
import com.fastretailing.marketingpf.domain.enums.SEGMENT_STATUS;
import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.BusinessTypeValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import com.fastretailing.marketingpf.validators.DeliveryScheduledMonthValid;
import com.fastretailing.marketingpf.validators.EventTargetPeriodRelativePeriodUnitValid;
import com.fastretailing.marketingpf.validators.EventTargetPeriodTypeValid;
import com.fastretailing.marketingpf.validators.SegmentStatusValid;
import com.fastretailing.marketingpf.validators.SequenceValid;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SegmentSearchRequest {

    @SequenceValid
    private Long segmentSequence;

    @BusinessTypeValid
    private String businessType;

    @DeliveryScheduledMonthValid
    private String deliveryScheduledMonth;

    @BrandValid
    private String brandId;

    @CountryValid
    private String countryId;

    @SegmentStatusValid
    private String status;

    private List<Long> eventTypeList;

    @EventTargetPeriodTypeValid
    private String eventTargetPeriodType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventTargetPeriodStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventTargetPeriodEndDate;

    private Integer eventTargetPeriodRelativeNumberValue;

    @EventTargetPeriodRelativePeriodUnitValid
    private String eventTargetPeriodRelativePeriodUnit;

    private String segmentName;

    private Long numberOfPeopleValue;

    @Range(message="errors.invalid", min=1, max=2)
    private Integer numberOfPeopleConditions;

    /**
     * Convert to ENUM
     *
     * @return BUSINESS_TYPE
     */
    @Hidden
    public BUSINESS_TYPE getBusinessTypeAsEnum() {
        return BUSINESS_TYPE.createFromValue(businessType);
    }

    /**
     * Convert to ENUM
     *
     * @return BRAND
     */
    @Hidden
    public BRAND getBrandIdAsEnum() {
        return BRAND.createFromCode(brandId);
    }

    /**
     * Convert to ENUM
     *
     * @return COUNTRY
     */
    @Hidden
    public COUNTRY getCountryIdAsEnum() {
        return COUNTRY.createFromCode(countryId);
    }

    /**
     * Convert to ENUM
     *
     * @return SEGMENT_STATUS
     */
    @Hidden
    public SEGMENT_STATUS getStatusAsEnum() {
        return SEGMENT_STATUS.createFromValue(status);
    }

    /**
     * Convert to ENUM
     *
     * @return EVENT_TARGET_PERIOD_TYPE
     */
    @Hidden
    public EVENT_TARGET_PERIOD_TYPE getEventTargetPeriodTypeAsEnum() {
        return EVENT_TARGET_PERIOD_TYPE.createFromValue(eventTargetPeriodType);
    }

    /**
     * Convert to ENUM
     *
     * @return EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT
     */
    @Hidden
    public EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT getEventTargetPeriodRelativePeriodUnitAsEnum() {
        return EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.createFromValue(eventTargetPeriodRelativePeriodUnit);
    }

    /**
     * Get numberOfPeopleConditions as enum
     *
     * @return NUMBER_OF_PEOPLE_CONDITIONS
     */
    @Hidden
    public NUMBER_OF_PEOPLE_CONDITIONS getNumberOfPeopleConditionsAsEnum() {
        return NUMBER_OF_PEOPLE_CONDITIONS.createFromValue(this.numberOfPeopleConditions);
    }
}
