package com.fastretailing.marketingpf.domain.entities;

import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Segment extends BaseEntity {

    private Long segmentSequence;
    private String businessType;
    private String brandId;
    private String countryId;
    private String deliveryScheduledMonth;
    private String eventTargetPeriodType;
    private LocalDate eventTargetPeriodStartDate;
    private LocalDate eventTargetPeriodEndDate;
    private Long eventTargetPeriodRelativeNumberValue;
    private String eventTargetPeriodRelativePeriodUnit;
    private String targetItemCategory;
    private String segmentName;
    private String description;
    private String sqlEditFlag;
    private String editedSql;
    private String status;
    private String eventTypeList;
    private String additionalConditionLogicalOperatorType;

    /**
     * Get additionalConditionLogicalOperatorType as enum
     *
     * @return LOGICAL_OPERATOR_TYPE
     */
    public LOGICAL_OPERATOR_TYPE getAddLogicalOperatorTypeAsEnum() {
        return LOGICAL_OPERATOR_TYPE.createFromValue(this.additionalConditionLogicalOperatorType);
    }

    /**
     * Get SqlEditFlag as enum
     *
     * @return SQL_EDIT_FLAG
     */
    public SQL_EDIT_FLAG getSqlEditFlagAsEnum() {
        return SQL_EDIT_FLAG.createFromValue(this.sqlEditFlag);
    }

    /**
     * Get eventTargetPeriodRelativePeriodUnit as enum
     *
     * @return EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT
     */
    public EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT getEventTargetPeriodRelativePeriodUnitAsEnum() {
        return EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.createFromValue(this.eventTargetPeriodRelativePeriodUnit);
    }

    /**
     * Get eventTargetPeriodType as enum
     *
     * @return EVENT_TARGET_PERIOD_TYPE
     */
    public EVENT_TARGET_PERIOD_TYPE getEventTargetPeriodTypeAsEnum() {
        return EVENT_TARGET_PERIOD_TYPE.createFromValue(this.eventTargetPeriodType);
    }
}
