package com.fastretailing.marketingpf.domain.mappers;

import com.fastretailing.marketingpf.domain.entities.Segment;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SegmentMapper {

    List<Segment> findAll();

    List<Segment> search(
            Long segmentSequence,
            String businessType,
            String deliveryScheduledMonth,
            String brandId,
            String countryId,
            String status,
            List<Long> eventTypeList,
            String eventTargetPeriodType,
            LocalDate eventTargetPeriodStartDate,
            LocalDate eventTargetPeriodEndDate,
            Integer eventTargetPeriodRelativeNumberValue,
            String eventTargetPeriodRelativePeriodUnit,
            String segmentName);

    void create(Segment segment);

    void createSqlSegment(Segment segment);

    Segment findById(Long segmentSequence);

    void update(Segment segment);

    void delete(Segment segment);

    List<Segment> findListBySegmentName(String segmentName);
}
