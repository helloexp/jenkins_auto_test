package com.fastretailing.marketingpf.controllers.segment.create;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentConditionRequestDto;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.ChoicesListDto;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.ChoicesListDto.ChoicesDetail;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.EVENT_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForScreenMasterMapper;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegmentBasicCreateValidator {

    @Autowired
    public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

    /**
     * Validate for Segment basic request
     *
     * @param targetSegment
     */
    public void validate(SegmentCreateRequestDto targetSegment) {
        if (StringUtils.isAllBlank(targetSegment.getTargetItemCategory())) {
            throw new ValidationFailException(new ValidationErrors("targetItemCategory", "TargetItemCategory is null or empty"));
        }

        if (StringUtils.isAllBlank(targetSegment.getEventTypeListAsString())) {
            throw new ValidationFailException(new ValidationErrors("eventTypeList", "EventTypeList is null or empty"));
        }

        if (!targetSegment.getEventTypeList().isEmpty()) {
            for (String eventTypeAsString : targetSegment.getEventTypeList()) {
                if (EVENT_TYPE.createFromValue(eventTypeAsString) == null) {
                    throw new ValidationFailException(new ValidationErrors("eventType", "eventType is invalid"));
                }
            }
        }

        if (StringUtils.isAllBlank(targetSegment.getEventTargetPeriodType())) {
            throw new ValidationFailException(new ValidationErrors("eventTargetPeriodType", "EventTargetPeriodType is null or empty"));
        }

        if (targetSegment.getEventTargetPeriodTypeAsEnum() == EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE) {
            if (targetSegment.getEventTargetPeriodStartDate() == null) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodStartDate", "EventTargetPeriodStartDate is null"));
            }
            if (targetSegment.getEventTargetPeriodEndDate() == null) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodEndDate", "EventTargetPeriodEndDate is null"));
            }
        } else if (targetSegment.getEventTargetPeriodTypeAsEnum() == EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE) {
            if (targetSegment.getEventTargetPeriodRelativeNumberValue() == null) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativeNumberValue", "EventTargetPeriodRelativeNumberValue is null"));
            }
            if (targetSegment.getEventTargetPeriodRelativePeriodUnit() == null) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativePeriodUnit", "EventTargetPeriodRelativePeriodUnit is null"));
            }
            if (EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.createFromValue(targetSegment.getEventTargetPeriodRelativePeriodUnit()) == null) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativePeriodUnit", "EventTargetPeriodRelativePeriodUnit is invalid"));
            }
        } else {
            throw new ValidationFailException(new ValidationErrors("eventTargetPeriodType", "EventTargetPeriodType is invalid"));
        }

        if (CollectionUtils.isNotEmpty(targetSegment.getSegmentConditionList())) {
            if (StringUtils.isAllBlank(targetSegment.getAdditionalConditionLogicalOperatorType())) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodType", "AdditionalConditionLogicalOperatorType is null or empty"));
            }
            for (SegmentConditionRequestDto conditionRequestDto : targetSegment.getSegmentConditionList()) {
                this.validateSegmentCondition(conditionRequestDto);
            }
        }
    }

    /**
     * Validate Segment Condition
     */
    public void validateSegmentCondition(SegmentConditionRequestDto segmentCondition) {
        if (segmentCondition.getSegmentItemForScreenSequence() == null) {
            throw new ValidationFailException(new ValidationErrors("SegmentItemForScreenSequence", "SegmentItemForScreenSequence is null"));
        }

        if (segmentCondition.getOperatorTypeAsEnum() == null) {
            throw new ValidationFailException(new ValidationErrors("OperatorType", "OperatorType is null or empty"));
        }

        if (!(segmentCondition.getOperatorTypeAsEnum() == OPERATOR_TYPE.IN || segmentCondition.getOperatorTypeAsEnum() == OPERATOR_TYPE.NOT_IN)) {
            if (segmentCondition.getComparisonValue().size() > 1) {
                throw new ValidationFailException(new ValidationErrors("ComparisonValue", "ComparisonValue must be single value. segmentItemForScreenSequence = " + segmentCondition.getSegmentItemForScreenSequence()));
            }
        }

        if (StringUtils.isAllBlank(segmentCondition.getComparisonValueAsString())) {
            throw new ValidationFailException(new ValidationErrors("ComparisonValue", "ComparisonValue is null or empty"));
        }

        if (segmentCondition.getSegmentConditionBlockOrder() == null) {
            throw new ValidationFailException(new ValidationErrors("SegmentConditionBlockOrder", "SegmentConditionBlockOrder is null"));
        }

        if (segmentCondition.getLogicalOperatorTypeAsEnum() == null) {
            throw new ValidationFailException(new ValidationErrors("LogicalOperatorType", "LogicalOperatorType is null or empty"));
        }

        // Validate operatorType
        SegmentItemForScreenMaster segmentItemForScreenMaster = segmentItemForScreenMasterMapper.findById(segmentCondition.getSegmentItemForScreenSequence());
        if (segmentItemForScreenMaster == null) {
            throw new ValidationFailException(new ValidationErrors("SegmentItemForScreenSequence",
                    "SegmentItemForScreenSequence is invalid. SegmentItemForScreenSequence = " + segmentCondition.getSegmentItemForScreenSequence()));
        }
        if (!segmentItemForScreenMaster.getOperatorListAsEnumList().contains(segmentCondition.getOperatorTypeAsEnum())) {
            throw new ValidationFailException(new ValidationErrors("OperatorType",
                    "OperatorType = " + segmentCondition.getOperatorType() + " is not in OperatorList of segmentItemForScreenMaster with SegmentItemForScreenSequence = "
                            + segmentCondition.getSegmentItemForScreenSequence()));
        }
        INPUT_TYPE inputType = segmentItemForScreenMaster.getInputTypeAsEnum();
        if (inputType.isNumericalValue()) {
            if (!segmentCondition.getComparisonValue().stream().allMatch(e -> NumberUtils.isCreatable(e))) {
                throw new ValidationFailException(new ValidationErrors("ComparisonValue", "ComparisonValue must be number value"));
            }
        }
        if (inputType.hasChoiceslistAvailable()) {
            ChoicesListDto choicesListDto = segmentItemForScreenMaster.getChoicesListAsDto();
            if (!segmentCondition.getComparisonValue().stream().allMatch(choicesListDto.getChoicesList().stream().map(ChoicesDetail::getValue).collect(Collectors.toList())::contains)) {
                throw new ValidationFailException(new ValidationErrors("ComparisonValue",
                        "There is element in comparisonValue " + segmentCondition.getComparisonValue() + " is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = "
                                + segmentCondition.getSegmentItemForScreenSequence()));
            }
        }
    }
}
