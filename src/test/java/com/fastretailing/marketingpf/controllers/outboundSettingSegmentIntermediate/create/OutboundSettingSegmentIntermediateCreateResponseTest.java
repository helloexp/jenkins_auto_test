package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create.OutboundSettingSegmentIntermediateCreateResponse.OutboundSettingSegmentIntermediateCreateResponseDto;
import com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create.OutboundSettingSegmentIntermediateCreateResponse.OutputPlatformOutboundSettingIntermediateDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class OutboundSettingSegmentIntermediateCreateResponseTest {

    @Test
    public void testOutboundSettingSegmentIntermediateCreateResponseDto() {
        OutboundSetting outboundSetting = new OutboundSetting();
        outboundSetting.setOutboundSettingSequence(1001L);
        outboundSetting.setBusinessType("1");
        outboundSetting.setBrandId("010");
        outboundSetting.setCountryId("1");
        outboundSetting.setSubmissionTimingType("1");
        outboundSetting.setReserveSubmissionDateTime(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        outboundSetting.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        outboundSetting.setRegularlySubmissionFrequencyPeriodNumberValue(3000);
        outboundSetting.setRegularlySubmissionFrequencyPeriodUnit(3);
        outboundSetting.setSubmissionCompletionContact("[mail1, mail2]");
        outboundSetting.setOutboundSettingName("outboundSettingName");
        outboundSetting.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 2, 2, 2, 2));

        OutputPlatformOutboundSettingIntermediate outputPlatform = new OutputPlatformOutboundSettingIntermediate();
        outputPlatform.setOutputPlatformOutboundSettingIntermediateSequence((2001L));
        outputPlatform.setOutboundSettingSequence(2003L);
        outputPlatform.setOutputPlatformSequence(3003L);
        outputPlatform.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
        outputPlatform.setAdAccountSequence(4003L);
        outputPlatform.setAdsPlatformSequence(1L);
        outputPlatform.setCrmPlatformSequence(6003L);
        outputPlatform.setAudienceId("10001");
        outputPlatform.setUserListName("userListName1");
        outputPlatform.setExtractionTargetId("1");
        outputPlatform.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 2, 2, 2, 2));

        OutputPlatformOutboundSettingIntermediate outputPlatform2 = new OutputPlatformOutboundSettingIntermediate();
        outputPlatform2.setOutputPlatformOutboundSettingIntermediateSequence((2002L));
        outputPlatform2.setOutboundSettingSequence(2003L);
        outputPlatform2.setOutputPlatformSequence(3003L);
        outputPlatform2.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
        outputPlatform2.setAdAccountSequence(4003L);
        outputPlatform2.setAdsPlatformSequence(1L);
        outputPlatform2.setCrmPlatformSequence(6003L);
        outputPlatform2.setAudienceId("10002");
        outputPlatform2.setUserListName("userListName2");
        outputPlatform2.setExtractionTargetId("2");
        outputPlatform2.setAuditInfoForCreation("user2", LocalDateTime.of(2021, 2, 2, 2, 2));

        OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate = new OutboundSettingSegmentIntermediate();
        outboundSettingSegmentIntermediate.setOutboundSettingSegmentIntermediateSequence(4001L);
        outboundSettingSegmentIntermediate.setOutboundSettingSequence(2001L);
        outboundSettingSegmentIntermediate.setSegmentSequence(3001L);

        OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate2 = new OutboundSettingSegmentIntermediate();
        outboundSettingSegmentIntermediate2.setOutboundSettingSegmentIntermediateSequence(4002L);
        outboundSettingSegmentIntermediate2.setOutboundSettingSequence(2001L);
        outboundSettingSegmentIntermediate2.setSegmentSequence(3002L);

        OutboundSettingSegmentIntermediateCreateResponseDto result = new OutboundSettingSegmentIntermediateCreateResponseDto(
                outboundSetting, Arrays.asList(outboundSettingSegmentIntermediate, outboundSettingSegmentIntermediate2), Arrays.asList(outputPlatform, outputPlatform2));
        JsonAssert.assertJsonOutput(result).isSameContentAs("{" +
                "    \"outboundSettingSegmentIntermediateSequenceList\": [4001, 4002]," +
                "    \"segmentSequenceList\": \"[3001,3002]\"," +
                "    \"outboundSettingSequence\": 1001," +
                "    \"brandId\": \"010\"," +
                "    \"countryId\": \"1\"," +
                "    \"businessType\": \"1\"," +
                "    \"submissionTimingType\": \"1\"," +
                "    \"reserveSubmissionDateTime\": \"2021-03-03 00:00:00\"," +
                "    \"regularlySubmissionFrequencyDateTimeBasis\": \"2021-03-03 00:00:00\"," +
                "    \"regularlySubmissionFrequencyPeriodNumberValue\": 3000," +
                "    \"regularlySubmissionFrequencyPeriodUnit\": 3," +
                "    \"submissionCompletionContact\": \"[mail1, mail2]\"," +
                "    \"outboundSettingName\": \"outboundSettingName\"," +
                "    \"outputPlatformOutboundSettingIntermediateInfoList\": [{" +
                "            \"outputPlatformOutboundSettingIntermediateSequence\": 2001," +
                "            \"outboundSettingSequence\": 2003," +
                "            \"outputPlatformSequence\": 3003," +
                "            \"outputPlatformType\": \"1\"," +
                "            \"adAccountSequence\": 4003," +
                "            \"adsPlatformSequence\": 1," +
                "            \"crmPlatformSequence\": 6003," +
                "            \"audienceId\": \"10001\"," +
                "            \"userListName\": \"userListName1\"," +
                "            \"extractionTargetId\": \"1\"" +
                "        }, {" +
                "            \"outputPlatformOutboundSettingIntermediateSequence\": 2002," +
                "            \"outboundSettingSequence\": 2003," +
                "            \"outputPlatformSequence\": 3003," +
                "            \"outputPlatformType\": \"1\"," +
                "            \"adAccountSequence\": 4003," +
                "            \"adsPlatformSequence\": 1," +
                "            \"crmPlatformSequence\": 6003," +
                "            \"audienceId\": \"10002\"," +
                "            \"userListName\": \"userListName2\"," +
                "            \"extractionTargetId\": \"2\"" +
                "        }" +
                "    ]" +
                "}");
    }

    @Test
    public void testOutputPlatformOutboundSettingIntermediateDto() {
        OutputPlatformOutboundSettingIntermediate outputPlatform = new OutputPlatformOutboundSettingIntermediate();
        outputPlatform.setOutputPlatformOutboundSettingIntermediateSequence((2001L));
        outputPlatform.setOutboundSettingSequence(2003L);
        outputPlatform.setOutputPlatformSequence(3003L);
        outputPlatform.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
        outputPlatform.setAdAccountSequence(4003L);
        outputPlatform.setAdsPlatformSequence(1L);
        outputPlatform.setCrmPlatformSequence(6003L);
        outputPlatform.setAudienceId("10001");
        outputPlatform.setUserListName("userListName1");
        outputPlatform.setExtractionTargetId("1");
        outputPlatform.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 2, 2, 2, 2));

        OutputPlatformOutboundSettingIntermediateDto result = new OutputPlatformOutboundSettingIntermediateDto(outputPlatform);
        JsonAssert.assertJsonOutput(result).isSameContentAs("{" +
                "    \"outputPlatformOutboundSettingIntermediateSequence\": 2001," +
                "    \"outboundSettingSequence\": 2003," +
                "    \"outputPlatformSequence\": 3003," +
                "    \"outputPlatformType\": \"1\"," +
                "    \"adAccountSequence\": 4003," +
                "    \"adsPlatformSequence\": 1," +
                "    \"crmPlatformSequence\": 6003," +
                "    \"audienceId\": \"10001\"," +
                "    \"userListName\": \"userListName1\"," +
                "    \"extractionTargetId\": \"1\"" +
                "}");
    }
}
