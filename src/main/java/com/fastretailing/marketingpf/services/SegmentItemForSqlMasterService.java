package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForSqlMasterMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegmentItemForSqlMasterService {

    @Autowired
    public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

    /**
     * Find by SegmentItemForScreenSequence and conditions (eventTypeList, brandId, countryId)
     * Method will return only 1 SegmentItemForSqlMaster, if method return more than 1, it must be error
     *
     * @param segmentItemForScreenSequence
     * @param eventTypeList
     * @param brandId
     * @param countryId
     * @return List<SegmentItemForSqlMaster>
     */
    public List<SegmentItemForSqlMaster> findBySegmentItemForScreenSequenceAndConditions(Long segmentItemForScreenSequence, List<String> eventTypeList, String brandId, String countryId) {
        return segmentItemForSqlMasterMapper.findBySegmentItemForScreenSequenceAndConditions(segmentItemForScreenSequence, eventTypeList, brandId, countryId);
    }

    /**
     * Find extra segment condition by conditions (segmentItemForScreenSequence, brandId, countryId)
     * Method will return only 1 SegmentItemForSqlMaster, if method return more than 1, it must be error
     *
     * @param segmentItemForScreenSequence
     * @param brandId
     * @param countryId
     * @return SegmentItemForSqlMaster
     */
    public SegmentItemForSqlMaster findByConditions(Long segmentItemForScreenSequence, String brandId, String countryId) {
        return segmentItemForSqlMasterMapper.findByConditions(segmentItemForScreenSequence, brandId, countryId);
    }
}
