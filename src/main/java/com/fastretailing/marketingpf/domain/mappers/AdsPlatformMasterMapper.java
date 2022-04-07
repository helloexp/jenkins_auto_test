package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdsPlatformMasterMapper {

    AdsPlatformMaster findById(Long adsPlatformSequence);
}
