package com.fastretailing.marketingpf.controllers.batchJob.create;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchJobCreateValidator {

    @Autowired
    public SegmentMapper segmentMapper;

    /**
     * Validate for batchJobCreate
     *
     * @param segmentSequence
     */
    public void validate(Long segmentSequence) {
        Segment segment = segmentMapper.findById(segmentSequence);
        if (segment == null) {
            throw new ResourceNotFoundException();
        }
        if(segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
            AuthUtils.preAuthorizeForSqlSegmentRole();
        }
    }
}
