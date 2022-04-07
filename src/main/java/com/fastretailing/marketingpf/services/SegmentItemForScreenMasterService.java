package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForScreenMasterMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegmentItemForScreenMasterService {

    @Autowired
    public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

    /**
     * find all SegmentItemForScreenMaster with deletionFlagForAudit = false
     *
     * @return List<<code>SegmentItemForScreenMaster</code>>
     */
    public List<SegmentItemForScreenMaster> findAll() {
        return segmentItemForScreenMasterMapper.findAll();
    }
}
