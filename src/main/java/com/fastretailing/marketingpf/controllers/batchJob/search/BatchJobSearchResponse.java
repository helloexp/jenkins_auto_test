package com.fastretailing.marketingpf.controllers.batchJob.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList.BatchJobListDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.entities.Segment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BatchJobSearchResponse extends BaseResponse {

    public List<BatchJobSearchResponseDto> jobList;

    public BatchJobSearchResponse(List<BatchJobSearchResponseDto> jobSearchResponseDtoList) {
        this.jobList = jobSearchResponseDtoList;
    }

    @JsonPropertyOrder({
            "audienceIdList",
            "businessType",
            "submissionTimingType",
            "reserveSubmissionDateTime",
            "regularlySubmissionFrequencyDateTimeBasis",
            "regularlySubmissionFrequencyPeriodNumberValue",
            "regularlySubmissionFrequencyPeriodUnit",
            "batchJobSequence",
            "batchJobType",
            "segmentList",
            "outboundSettingSequence",
            "status",
            "deletionFlagForAudit",
            "createUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit",
    })
    public static class BatchJobSearchResponseDto extends BaseAuditResponse {

        public List<String> audienceIdList;
        public String businessType;
        public String submissionTimingType;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime reserveSubmissionDateTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime regularlySubmissionFrequencyDateTimeBasis;
        public Integer regularlySubmissionFrequencyPeriodNumberValue;
        public Integer regularlySubmissionFrequencyPeriodUnit;
        public Long batchJobSequence;
        public String batchJobType;
        public List<SegmentDto> segmentInfoList = new ArrayList<>();
        public Long outboundSettingSequence;
        public String status;

        public BatchJobSearchResponseDto(List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList, OutboundSetting outboundSetting, BatchJobListDto job,
                List<Segment> segmentList, Map<String, String> userMasterMap) {
            this.audienceIdList = outputPlatformOutboundSettingIntermediateList.stream().map(o -> o.getUserListName() + "(" + o.getAudienceId() + ")").collect(Collectors.toList());
            if (outboundSetting != null) {
                this.businessType = outboundSetting.getBusinessType();
                this.submissionTimingType = outboundSetting.getSubmissionTimingType();
                this.reserveSubmissionDateTime = outboundSetting.getReserveSubmissionDateTime();
                this.regularlySubmissionFrequencyDateTimeBasis = outboundSetting.getRegularlySubmissionFrequencyDateTimeBasis();
                this.regularlySubmissionFrequencyPeriodNumberValue = outboundSetting.getRegularlySubmissionFrequencyPeriodNumberValue();
                this.regularlySubmissionFrequencyPeriodUnit = outboundSetting.getRegularlySubmissionFrequencyPeriodUnit();
            }
            this.batchJobSequence = job.getBatchJobSequence();
            this.batchJobType = job.getBatchJobType();
            for (Segment segment : segmentList) {
                this.segmentInfoList.add(new SegmentDto(segment));
            }
            this.outboundSettingSequence = job.getOutboundSettingSequence();
            this.status = job.getStatus();
            this.deletionFlagForAudit = job.getDeletionFlagForAudit();
            this.createDateTimeForAudit = job.getCreateDateTimeForAudit();
            this.createUserIdForAudit = userMasterMap.get(job.getCreateUserIdForAudit());
            this.updateDateTimeForAudit = job.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = userMasterMap.get(job.getUpdateUserIdForAudit());
        }

        @JsonPropertyOrder({
                "segmentSequence",
                "segmentName"
        })
        public static class SegmentDto {

            public Long segmentSequence;
            public String segmentName;

            public SegmentDto(Segment segment) {
                this.segmentSequence = segment.getSegmentSequence();
                this.segmentName = segment.getSegmentName();
            }
        }
    }
}
