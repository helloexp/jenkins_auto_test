package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutputPlatformMasterMapper {

    OutputPlatformMaster findById(Long outputPlatformSequence);
}
