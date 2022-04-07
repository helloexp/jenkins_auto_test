package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SegmentItemForScreenMasterMapper {

    List<SegmentItemForScreenMaster> findAll();

    SegmentItemForScreenMaster findById(Long segmentItemForScreenSequence);
}
