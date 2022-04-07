package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SegmentConditionMapper {

    List<SegmentCondition> findListBySegmentSequence(Long segmentSequence);

    void create(SegmentCondition segmentCondition);

    SegmentCondition findById(Long segmentConditionSequence);

    void delete(SegmentCondition segmentConditionSequence);
}
