package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundSettingSegmentIntermediateMapper {

    List<OutboundSettingSegmentIntermediate> findAll();

    List<OutboundSettingSegmentIntermediate> findByOutboundSettingSequence(Long outboundSettingSequence);

    void create(OutboundSettingSegmentIntermediate outboundSettingSegmentIntermediate);
}
