package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingSegmentIntermediateMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboundSettingSegmentIntermediateService {

    @Autowired
    public OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

    /**
     * find list by outboundSettingSequence
     *
     * @param outboundSettingSequence
     * @return List<<code>OutboundSettingSegmentIntermediate</code>>
     */
    public List<OutboundSettingSegmentIntermediate> findByOutboundSettingSequence(Long outboundSettingSequence) {
        return outboundSettingSegmentIntermediateMapper.findByOutboundSettingSequence(outboundSettingSequence);
    }

    /**
     * Create new OutboundSettingSegmentIntermediate
     *
     * @param outboundSettingSequence
     * @param segmentSequence
     * @param createDateTimeForAudit
     * @param createUserIdForAudit
     * @return OutboundSettingSegmentIntermediate
     */
    public OutboundSettingSegmentIntermediate create(
            Long outboundSettingSequence,
            Long segmentSequence,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate = new OutboundSettingSegmentIntermediate();
        outboundSettingSegmentIntermediate.setOutboundSettingSequence(outboundSettingSequence);
        outboundSettingSegmentIntermediate.setSegmentSequence(segmentSequence);
        outboundSettingSegmentIntermediate.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);

        outboundSettingSegmentIntermediateMapper.create(outboundSettingSegmentIntermediate);
        return outboundSettingSegmentIntermediate;
    }
}
