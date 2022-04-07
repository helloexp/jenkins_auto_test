package com.fastretailing.marketingpf.controllers.segment.update;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentConditionDto;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentDto;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.ChoicesListDto;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.ChoicesListDto.ChoicesDetail;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForScreenMasterMapper;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.utils.AuthUtils;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegmentUpdateValidator {

    @Autowired
    public SegmentMapper segmentMapper;

    @Autowired
    public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

    /**
     * Validate for update Segment request
     *
     * @param segmentSequence
     * @param segmentDto
     */
    public void validate(Long segmentSequence, SegmentDto segmentDto) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ValidationFailException(new ValidationErrors("segmentSequence", "Can not found Segment"));
        }
        if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT || segmentDto.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            AuthUtils.preAuthorizeForSqlSegmentRole();
        }
        if (segmentDto.getSqlEditFlagAsEnum() != null) {
            if (segmentDto.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.BASIC_SEGMENT && segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
                throw new ValidationFailException(new ValidationErrors("SqlEditFlag", "Can not convert Sql Segment to Basic Segment"));
            }
            if (segmentDto.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.BASIC_SEGMENT) {
                this.validateBasicSegment(segmentDto);
            }
            if (segmentDto.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
                this.validateSqlSegment(segmentDto);
            }
        } else {
            if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.BASIC_SEGMENT) {
                this.validateBasicSegment(segmentDto);
            }
            if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
                this.validateSqlSegment(segmentDto);
            }
        }
    }

    /**
     * Validate for basic Segment
     *
     * @param segmentDto
     */
    public void validateBasicSegment(SegmentDto segmentDto) {
        if (segmentDto.getEventTargetPeriodTypeAsEnum() != null) {
            if (segmentDto.getEventTargetPeriodTypeAsEnum() == EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE) {
                if (segmentDto.getEventTargetPeriodStartDate() == null) {
                    throw new ValidationFailException(new ValidationErrors("eventTargetPeriodStartDate", "EventTargetPeriodStartDate is null"));
                }
                if (segmentDto.getEventTargetPeriodEndDate() == null) {
                    throw new ValidationFailException(new ValidationErrors("eventTargetPeriodEndDate", "EventTargetPeriodEndDate is null"));
                }
            }
            if (segmentDto.getEventTargetPeriodTypeAsEnum() == EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE) {
                if (segmentDto.getEventTargetPeriodRelativeNumberValue() == null) {
                    throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativeNumberValue", "EventTargetPeriodRelativeNumberValue is null"));
                }
                if (segmentDto.getEventTargetPeriodRelativePeriodUnit() == null) {
                    throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativePeriodUnit", "EventTargetPeriodRelativePeriodUnit is null"));
                }
                if (EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.createFromValue(segmentDto.getEventTargetPeriodRelativePeriodUnit()) == null) {
                    throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativePeriodUnit", "EventTargetPeriodRelativePeriodUnit is invalid"));
                }
            }
        }

        // Validate segment condition
        if (CollectionUtils.isNotEmpty(segmentDto.getSegmentConditionList())) {
            if (StringUtils.isAllBlank(segmentDto.getAdditionalConditionLogicalOperatorType())) {
                throw new ValidationFailException(new ValidationErrors("eventTargetPeriodType", "AdditionalConditionLogicalOperatorType is null or empty"));
            }
            for (SegmentConditionDto condition : segmentDto.getSegmentConditionList()) {
                this.validateSegmentCondition(condition);
            }
        }
    }

    /**
     * Validate segment condition
     *
     * @param segmentCondition
     */
    public void validateSegmentCondition(SegmentConditionDto segmentCondition) {
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

    /**
     * Validate for basic Segment
     *
     * @param segmentDto
     */
    public void validateSqlSegment(SegmentDto segmentDto) {
        if (StringUtils.isAllBlank(segmentDto.getEditedSql())) {
            throw new ValidationFailException(new ValidationErrors("editedSql", "EditedSql is null or empty"));
        }
        if (segmentDto.getEditedSql().contains(";")) {
            throw new ValidationFailException(new ValidationErrors("editedSql", "EditedSql has invalid character"));
        }
    }
}
