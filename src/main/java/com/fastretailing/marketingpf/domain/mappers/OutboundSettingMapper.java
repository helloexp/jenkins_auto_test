package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundSettingMapper {

    OutboundSetting findById(Long outboundSettingSequence);

    List<OutboundSetting> findAll();

    void create(OutboundSetting outboundSetting);
}
