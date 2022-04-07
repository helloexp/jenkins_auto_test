package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.domain.mappers.OutputPlatformOutboundSettingIntermediateMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputPlatformOutboundSettingIntermediateService {

    @Autowired
    public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

    /**
     * Find By OutboundSettingSequence
     *
     * @param outboundSettingSequence
     * @return OutputPlatformOutboundSettingIntermediate
     */
    public OutputPlatformOutboundSettingIntermediate findByOutboundSettingSequence(Long outboundSettingSequence) {
        return outputPlatformOutboundSettingIntermediateMapper.findByOutboundSettingSequence(outboundSettingSequence);
    }

    /**
     * Find list By OutboundSettingSequence
     *
     * @param outboundSettingSequence
     * @return List<<code>OutputPlatformOutboundSettingIntermediate</code>>
     */
    public List<OutputPlatformOutboundSettingIntermediate> findListByOutboundSettingSequence(Long outboundSettingSequence) {
        return outputPlatformOutboundSettingIntermediateMapper.findListByOutboundSettingSequence(outboundSettingSequence);
    }

    /**
     * Create OutputPlatformOutboundSettingIntermediate
     *
     * @param outboundSettingSequence
     * @param outputPlatformSequence
     * @param outputPlatformType
     * @param adAccountSequence
     * @param adsPlatformSequence
     * @param crmPlatformSequence
     * @param audienceId
     * @param outboundSettingName
     * @param extractionTargetId
     * @param createDateTimeForAudit
     * @param createUserIdForAudit
     * @return OutputPlatformOutboundSettingIntermediate
     */
    public OutputPlatformOutboundSettingIntermediate create(
            Long outboundSettingSequence,
            Long outputPlatformSequence,
            OUTPUT_PLATFORM_TYPE outputPlatformType,
            Long adAccountSequence,
            Long adsPlatformSequence,
            Long crmPlatformSequence,
            String audienceId,
            String userListName,
            String outboundSettingName,
            EXTRACTION_TARGET_ID extractionTargetId,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSettingIntermediate = new OutputPlatformOutboundSettingIntermediate();
        outputPlatformOutboundSettingIntermediate.setOutboundSettingSequence(outboundSettingSequence);
        outputPlatformOutboundSettingIntermediate.setOutputPlatformSequence(outputPlatformSequence);
        outputPlatformOutboundSettingIntermediate.setOutputPlatformType(outputPlatformType.getValueAsString());
        outputPlatformOutboundSettingIntermediate.setAdAccountSequence(adAccountSequence);
        outputPlatformOutboundSettingIntermediate.setAdsPlatformSequence(adsPlatformSequence);
        outputPlatformOutboundSettingIntermediate.setCrmPlatformSequence(crmPlatformSequence);
        outputPlatformOutboundSettingIntermediate.setAudienceId(audienceId);
        outputPlatformOutboundSettingIntermediate.setUserListName(userListName);
        outputPlatformOutboundSettingIntermediate.setExtractionTargetId(extractionTargetId.getValueAsString());
        outputPlatformOutboundSettingIntermediate.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);

        outputPlatformOutboundSettingIntermediateMapper.create(outputPlatformOutboundSettingIntermediate);
        return outputPlatformOutboundSettingIntermediate;
    }
}
