package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutputPlatformOutboundSettingIntermediateMapper {

    OutputPlatformOutboundSettingIntermediate findByOutboundSettingSequence(Long outboundSettingSequence);

    List<OutputPlatformOutboundSettingIntermediate> findListByOutboundSettingSequence(Long outboundSettingSequence);

    List<OutputPlatformOutboundSettingIntermediate> findAll();

    void create(OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSettingIntermediate);
}
