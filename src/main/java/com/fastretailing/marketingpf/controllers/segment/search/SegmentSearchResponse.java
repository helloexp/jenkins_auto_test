package com.fastretailing.marketingpf.controllers.segment.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;
import com.fastretailing.marketingpf.domain.entities.Segment;

public class SegmentSearchResponse extends BaseResponse {

    public List<SegmentResponse> segmentList = new ArrayList<>();

    public SegmentSearchResponse(List<Segment> segmentList, Map<Long, SegmentBreakdown> segmentBreakdownMap, Map<String, String> userMasterMap) {
        for (Segment segment : segmentList) {
            this.segmentList.add(new SegmentResponse(segment, segmentBreakdownMap.get(segment.getSegmentSequence()), userMasterMap));
        }
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
            "status",
            "eventTypeList",
            "targetNumberOfPeopleBySegment",
            "targetNumberOfPeopleBySegmentDateTime",
            "deletionFlagForAudit",
            "createDateTimeForAudit",
            "createUserIdForAudit",
            "deletionDateTimeForAudit",
            "deletionUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit"
    })
    public static class SegmentResponse extends BaseAuditResponse {

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
        public String status;
        public String eventTypeList;
        public Long targetNumberOfPeopleBySegment;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime targetNumberOfPeopleBySegmentDateTime;

        public SegmentResponse(Segment segment, SegmentBreakdown segmentBreakdown, Map<String, String> userMasterMap) {
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
            this.status = segment.getStatus();
            this.eventTypeList = segment.getEventTypeList();
            if(!Objects.isNull(segmentBreakdown)) {
              this.targetNumberOfPeopleBySegment = segmentBreakdown.getTargetNumberOfPeopleBySegment();
              this.targetNumberOfPeopleBySegmentDateTime = segmentBreakdown.getUpdateDateTimeForAudit();
            }
            this.deletionFlagForAudit = segment.getDeletionFlagForAudit();
            this.createDateTimeForAudit = segment.getCreateDateTimeForAudit();
            this.createUserIdForAudit = userMasterMap.get(segment.getCreateUserIdForAudit());
            this.updateDateTimeForAudit = segment.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = userMasterMap.get(segment.getUpdateUserIdForAudit());
        }
    }
}
