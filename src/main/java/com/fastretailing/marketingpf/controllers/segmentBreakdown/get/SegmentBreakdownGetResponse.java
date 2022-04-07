package com.fastretailing.marketingpf.controllers.segmentBreakdown.get;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.domain.dtos.SegmentBreakdown;

@JsonPropertyOrder({
        "segmentBreakdownSequence",
        "segmentSequence",
        "targetNumberOfPeopleBySegment",
        "segmentBreakdown",
        "deletionFlagForAudit",
        "createDateTimeForAudit",
        "createUserIdForAudit",
        "deletionDateTimeForAudit",
        "deletionUserIdForAudit",
        "updateDateTimeForAudit",
        "updateUserIdForAudit"
})
public class SegmentBreakdownGetResponse extends BaseAuditResponse {

    public Long segmentBreakdownSequence;
    public Long segmentSequence;
    public Long targetNumberOfPeopleBySegment;
    public String segmentBreakdown;

    public SegmentBreakdownGetResponse(SegmentBreakdown segmentBreakdown) {
        if (segmentBreakdown == null) {
            return;
        }
        this.segmentBreakdownSequence = segmentBreakdown.getSegmentBreakdownSequence();
        this.segmentSequence = segmentBreakdown.getSegmentSequence();
        this.targetNumberOfPeopleBySegment = segmentBreakdown.getTargetNumberOfPeopleBySegment();
        this.segmentBreakdown = segmentBreakdown.getSegmentBreakdown();
        this.deletionFlagForAudit = segmentBreakdown.getDeletionFlagForAudit();
        this.createDateTimeForAudit = segmentBreakdown.getCreateDateTimeForAudit();
        this.createUserIdForAudit = segmentBreakdown.getCreateUserIdForAudit();
        this.updateDateTimeForAudit = segmentBreakdown.getUpdateDateTimeForAudit();
        this.updateUserIdForAudit = segmentBreakdown.getUpdateUserIdForAudit();
    }
}
