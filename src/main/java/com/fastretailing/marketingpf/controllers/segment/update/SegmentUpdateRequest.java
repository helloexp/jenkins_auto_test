package com.fastretailing.marketingpf.controllers.segment.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.utils.JsonUtils;
import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import com.fastretailing.marketingpf.validators.DeliveryScheduledMonthValid;
import com.fastretailing.marketingpf.validators.EventTargetPeriodTypeValid;
import com.fastretailing.marketingpf.validators.LogicalOperatorTypeValid;
import com.fastretailing.marketingpf.validators.OperatorTypeValid;
import com.fastretailing.marketingpf.validators.Required;
import com.fastretailing.marketingpf.validators.SegmentStatusValid;
import com.fastretailing.marketingpf.validators.SequenceValid;
import com.fastretailing.marketingpf.validators.SqlEditFlagValid;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.Data;

@Data
public class SegmentUpdateRequest {

    @Valid
    private SegmentDto targetSegment;

    @Data
    public static class SegmentDto {

        @Required
        private String businessType;

        @Required
        @BrandValid
        private String brandId;

        @Required
        @CountryValid
        private String countryId;

        @Required
        @DeliveryScheduledMonthValid
        private String deliveryScheduledMonth;

        @EventTargetPeriodTypeValid
        private String eventTargetPeriodType;

        private LocalDate eventTargetPeriodStartDate;

        private LocalDate eventTargetPeriodEndDate;

        private Long eventTargetPeriodRelativeNumberValue;

        private String eventTargetPeriodRelativePeriodUnit;

        private String targetItemCategory;

        @Required
        private String segmentName;

        private String description;

        @SqlEditFlagValid
        private String sqlEditFlag;

        private String editedSql;

        @SegmentStatusValid
        private String status;

        private List<String> eventTypeList;

        @LogicalOperatorTypeValid
        private String additionalConditionLogicalOperatorType;

        @Valid
        private List<SegmentConditionDto> segmentConditionList;

        /**
         * Get additionalConditionLogicalOperatorType as enum
         *
         * @return LOGICAL_OPERATOR_TYPE
         */
        @JsonIgnore
        public LOGICAL_OPERATOR_TYPE getAdditionalConditionLogicalOperatorTypeAsEnum() {
            return LOGICAL_OPERATOR_TYPE.createFromValue(this.additionalConditionLogicalOperatorType);
        }

        /**
         * Get SqlEditFlag as enum
         *
         * @return SQL_EDIT_FLAG
         */
        @JsonIgnore
        public SQL_EDIT_FLAG getSqlEditFlagAsEnum() {
            return SQL_EDIT_FLAG.createFromValue(this.sqlEditFlag);
        }

        /**
         * Get eventTargetPeriodType as enum
         *
         * @return EVENT_TARGET_PERIOD_TYPE
         */
        @JsonIgnore
        public EVENT_TARGET_PERIOD_TYPE getEventTargetPeriodTypeAsEnum() {
            return EVENT_TARGET_PERIOD_TYPE.createFromValue(this.eventTargetPeriodType);
        }

        /**
         * Get eventTypeList as String
         *
         * @return String
         */
        @JsonIgnore
        public String getEventTypeListAsString() {
            if (this.eventTypeList == null) {
                return null;
            }
            return JsonUtils.toJson(this.eventTypeList);
        }

        /**
         * Get basic segment condition list (block order == 0)
         *
         * @return List<SegmentConditionRequestDto>
         */
        @Hidden
        public List<SegmentConditionDto> getBasicSegmentConditionList() {
            return this.segmentConditionList.stream().filter(condition -> condition.getSegmentConditionBlockOrder() == 0).collect(Collectors.toList());
        }

        /**
         * Get extra segment condition list (block order > 0)
         *
         * @return List<SegmentConditionRequestDto>
         */
        @Hidden
        public List<SegmentConditionDto> getExtraSegmentConditionList() {
            return this.segmentConditionList.stream().filter(condition -> condition.getSegmentConditionBlockOrder() > 0).collect(Collectors.toList());
        }
    }

    @Data
    public static class SegmentConditionDto {

        @SequenceValid
        private Long segmentConditionSequence;

        @SequenceValid
        private Long segmentItemForScreenSequence;

        @OperatorTypeValid
        private String operatorType;

        private List<String> comparisonValue;

        private Integer segmentConditionBlockOrder;

        @LogicalOperatorTypeValid
        private String logicalOperatorType;

        /**
         * Get operatorType as enum
         *
         * @return OPERATOR_TYPE
         */
        @JsonIgnore
        public OPERATOR_TYPE getOperatorTypeAsEnum() {
            return OPERATOR_TYPE.createFromValue(this.operatorType);
        }

        /**
         * Get logicalOperatorType as enum
         *
         * @return LOGICAL_OPERATOR_TYPE
         */
        @JsonIgnore
        public LOGICAL_OPERATOR_TYPE getLogicalOperatorTypeAsEnum() {
            return LOGICAL_OPERATOR_TYPE.createFromValue(this.logicalOperatorType);
        }

        /**
         * Get comparisonValue as String
         *
         * @return String
         */
        @JsonIgnore
        public String getComparisonValueAsString() {
            if (this.comparisonValue == null) {
                return null;
            }
            return JsonUtils.toJson(this.comparisonValue);
        }
    }
}
