package com.fastretailing.marketingpf.controllers.segment.create;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SegmentCreateResponse extends BaseResponse {

    public SegmentCreateResponseDto targetSegment;

    public SegmentCreateResponse(Segment segment, List<SegmentCondition> conditionList) {
        this.targetSegment = new SegmentCreateResponseDto(segment, conditionList);
    }

    @JsonPropertyOrder({
            "segmentSequence",
            "businessType",
            "brandId",
            "countryId",
            "deliveryScheduledMonth",
            "eventTargetPeriodType",
            "eventTargetPeriodStartDate",
            "eventTargetPeriodEndDate",
            "eventTargetPeriodRelativeNumberValue",
            "eventTargetPeriodRelativePeriodUnit",
            "targetItemCategory",
            "segmentName",
            "description",
            "sqlEditFlag",
            "editedSql",
            "status",
            "eventTypeList",
            "additionalConditionLogicalOperatorType",
            "segmentConditionList",
            "deletionFlagForAudit",
            "createDateTimeForAudit",
            "createUserIdForAudit",
            "deletionDateTimeForAudit",
            "deletionUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit"
    })
    public static class SegmentCreateResponseDto extends BaseAuditResponse {

        public Long segmentSequence;
        public String businessType;
        public String brandId;
        public String countryId;
        public String deliveryScheduledMonth;
        public String eventTargetPeriodType;
        public LocalDate eventTargetPeriodStartDate;
        public LocalDate eventTargetPeriodEndDate;
        public Long eventTargetPeriodRelativeNumberValue;
        public String eventTargetPeriodRelativePeriodUnit;
        public String targetItemCategory;
        public String segmentName;
        public String description;
        public String sqlEditFlag;
        public String editedSql;
        public String status;
        public String eventTypeList;
        public String additionalConditionLogicalOperatorType;
        public List<SegmentConditionResponseDto> segmentConditionList = new ArrayList<>();

        public SegmentCreateResponseDto(Segment segment, List<SegmentCondition> conditionList) {
            this.segmentSequence = segment.getSegmentSequence();
            this.businessType = segment.getBusinessType();
            this.brandId = segment.getBrandId();
            this.countryId = segment.getCountryId();
            this.deliveryScheduledMonth = segment.getDeliveryScheduledMonth();
            this.eventTargetPeriodType = segment.getEventTargetPeriodType();
            this.eventTargetPeriodStartDate = segment.getEventTargetPeriodStartDate();
            this.eventTargetPeriodEndDate = segment.getEventTargetPeriodEndDate();
            this.eventTargetPeriodRelativeNumberValue = segment.getEventTargetPeriodRelativeNumberValue();
            this.eventTargetPeriodRelativePeriodUnit = segment.getEventTargetPeriodRelativePeriodUnit();
            this.targetItemCategory = segment.getTargetItemCategory();
            this.segmentName = segment.getSegmentName();
            this.description = segment.getDescription();
            this.sqlEditFlag = segment.getSqlEditFlag();
            this.editedSql = segment.getEditedSql();
            this.status = segment.getStatus();
            this.eventTypeList = segment.getEventTypeList();
            this.additionalConditionLogicalOperatorType = segment.getAdditionalConditionLogicalOperatorType();
            this.deletionFlagForAudit = segment.getDeletionFlagForAudit();
            this.createDateTimeForAudit = segment.getCreateDateTimeForAudit();
            this.createUserIdForAudit = segment.getCreateUserIdForAudit();
            this.updateDateTimeForAudit = segment.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = segment.getUpdateUserIdForAudit();

            for (SegmentCondition condition : conditionList) {
                this.segmentConditionList.add(new SegmentConditionResponseDto(condition));
            }
        }
    }

    @JsonPropertyOrder({
            "segmentConditionSequence",
            "segmentItemForScreenSequence",
            "segmentItemForSqlSequence",
            "operatorType",
            "comparisonValue",
            "segmentConditionBlockOrder",
            "logicalOperatorType",
            "deletionFlagForAudit",
            "createDateTimeForAudit",
            "createUserIdForAudit",
            "deletionDateTimeForAudit",
            "deletionUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit"
    })
    public static class SegmentConditionResponseDto extends BaseAuditResponse {

        public Long segmentConditionSequence;
        public Long segmentItemForScreenSequence;
        public Long segmentItemForSqlSequence;
        public String operatorType;
        public String comparisonValue;
        public Integer segmentConditionBlockOrder;
        public String logicalOperatorType;

        public SegmentConditionResponseDto(SegmentCondition condition) {
            this.segmentConditionSequence = condition.getSegmentConditionSequence();
            this.segmentItemForScreenSequence = condition.getSegmentItemForScreenSequence();
            this.segmentItemForSqlSequence = condition.getSegmentItemForSqlSequence();
            this.operatorType = condition.getOperatorType();
            this.comparisonValue = condition.getComparisonValue();
            this.segmentConditionBlockOrder = condition.getSegmentConditionBlockOrder();
            this.logicalOperatorType = condition.getLogicalOperatorType();
            this.deletionFlagForAudit = condition.getDeletionFlagForAudit();
            this.createDateTimeForAudit = condition.getCreateDateTimeForAudit();
            this.createUserIdForAudit = condition.getCreateUserIdForAudit();
            this.updateDateTimeForAudit = condition.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = condition.getUpdateUserIdForAudit();
        }
    }
}
