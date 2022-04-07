package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

public class OutboundSettingSegmentIntermediateCreateResponse extends BaseResponse {

    public OutboundSettingSegmentIntermediateCreateResponseDto targetOutboundSetting;

    public OutboundSettingSegmentIntermediateCreateResponse(OutboundSetting outboundSetting, List<OutboundSettingSegmentIntermediate> outboundSettingSegmentIntermediateList, List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList) {
        this.targetOutboundSetting = new OutboundSettingSegmentIntermediateCreateResponseDto(outboundSetting, outboundSettingSegmentIntermediateList, outputPlatformOutboundSettingIntermediateList);
    }

    @JsonPropertyOrder({
        "outboundSettingSegmentIntermediateSequenceList",
        "segmentSequenceList",
        "outboundSettingSequence",
        "brandId",
        "countryId",
        "businessType",
        "submissionTimingType",
        "reserveSubmissionDateTime",
        "regularlySubmissionFrequencyDateTimeBasis",
        "regularlySubmissionFrequencyPeriodNumberValue",
        "regularlySubmissionFrequencyPeriodUnit",
        "submissionCompletionContact",
        "outboundSettingName",
        "outputPlatformOutboundSettingIntermediateInfoList"
    })
    @Data
    public static class OutboundSettingSegmentIntermediateCreateResponseDto {

        private List<Long> outboundSettingSegmentIntermediateSequenceList;
        private String segmentSequenceList;
        private Long outboundSettingSequence;
        private String brandId;
        private String countryId;
        private String businessType;
        private String submissionTimingType;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reserveSubmissionDateTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime regularlySubmissionFrequencyDateTimeBasis;
        private Integer regularlySubmissionFrequencyPeriodNumberValue;
        private Integer regularlySubmissionFrequencyPeriodUnit;
        private String submissionCompletionContact;
        private String outboundSettingName;
        private List<OutputPlatformOutboundSettingIntermediateDto> outputPlatformOutboundSettingIntermediateInfoList = new ArrayList<>();

        public OutboundSettingSegmentIntermediateCreateResponseDto(OutboundSetting outboundSetting, List<OutboundSettingSegmentIntermediate> outboundSettingSegmentIntermediateList, List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList) {
            this.outboundSettingSegmentIntermediateSequenceList = outboundSettingSegmentIntermediateList.stream().map(s -> s.getOutboundSettingSegmentIntermediateSequence()).collect(Collectors.toList());
            this.segmentSequenceList = JsonUtils.toJson(outboundSettingSegmentIntermediateList.stream().map(s -> s.getSegmentSequence()).collect(Collectors.toList()));
            this.outboundSettingSequence = outboundSetting.getOutboundSettingSequence();
            this.brandId = outboundSetting.getBrandId();
            this.countryId = outboundSetting.getCountryId();
            this.businessType = outboundSetting.getBusinessType();
            this.submissionTimingType = outboundSetting.getSubmissionTimingType();
            this.reserveSubmissionDateTime = outboundSetting.getReserveSubmissionDateTime();
            this.regularlySubmissionFrequencyDateTimeBasis = outboundSetting.getRegularlySubmissionFrequencyDateTimeBasis();
            this.regularlySubmissionFrequencyPeriodNumberValue = outboundSetting.getRegularlySubmissionFrequencyPeriodNumberValue();
            this.regularlySubmissionFrequencyPeriodUnit = outboundSetting.getRegularlySubmissionFrequencyPeriodUnit();
            this.submissionCompletionContact = outboundSetting.getSubmissionCompletionContact();
            this.outboundSettingName = outboundSetting.getOutboundSettingName();
            for (OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSettingIntermediate : outputPlatformOutboundSettingIntermediateList) {
                this.outputPlatformOutboundSettingIntermediateInfoList.add(new OutputPlatformOutboundSettingIntermediateDto(outputPlatformOutboundSettingIntermediate));
            }
        }
    }

    @JsonPropertyOrder({
        "outputPlatformOutboundSettingIntermediateSequence",
        "outboundSettingSequence",
        "outputPlatformSequence",
        "outputPlatformType",
        "adAccountSequence",
        "adsPlatformSequence",
        "crmPlatformSequence",
        "audienceId",
        "userListName",
        "extractionTargetId"
    })
    @Data
    public static class OutputPlatformOutboundSettingIntermediateDto {

        public Long outputPlatformOutboundSettingIntermediateSequence;
        public Long outboundSettingSequence;
        public Long outputPlatformSequence;
        public String outputPlatformType;
        public Long adAccountSequence;
        public Long adsPlatformSequence;
        public Long crmPlatformSequence;
        public String audienceId;
        public String userListName;
        public String extractionTargetId;

        public OutputPlatformOutboundSettingIntermediateDto(OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSettingIntermediate) {
            this.outputPlatformOutboundSettingIntermediateSequence = outputPlatformOutboundSettingIntermediate.getOutputPlatformOutboundSettingIntermediateSequence();
            this.outboundSettingSequence = outputPlatformOutboundSettingIntermediate.getOutboundSettingSequence();
            this.outputPlatformSequence = outputPlatformOutboundSettingIntermediate.getOutputPlatformSequence();
            this.outputPlatformType = outputPlatformOutboundSettingIntermediate.getOutputPlatformType();
            this.adAccountSequence = outputPlatformOutboundSettingIntermediate.getAdAccountSequence();
            this.adsPlatformSequence = outputPlatformOutboundSettingIntermediate.getAdsPlatformSequence();
            this.crmPlatformSequence = outputPlatformOutboundSettingIntermediate.getCrmPlatformSequence();
            this.audienceId = outputPlatformOutboundSettingIntermediate.getAudienceId();
            this.userListName = outputPlatformOutboundSettingIntermediate.getUserListName();
            this.extractionTargetId = outputPlatformOutboundSettingIntermediate.getExtractionTargetId();
        }
    }
}
