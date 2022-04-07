package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SegmentItemForSqlMasterMapper {

    SegmentItemForSqlMaster findById(Long segmentItemForSqlSequence);

    List<SegmentItemForSqlMaster> findBySegmentItemForScreenSequenceAndConditions(Long segmentItemForScreenSequence, List<String> eventTypeList, String brandId, String countryId);

    SegmentItemForSqlMaster findByConditions(Long segmentItemForScreenSequence, String brandId, String countryId);
}
