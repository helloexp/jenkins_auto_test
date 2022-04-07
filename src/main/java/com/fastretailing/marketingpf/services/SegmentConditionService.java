package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.mappers.SegmentConditionMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegmentConditionService {

    @Autowired
    public SegmentConditionMapper segmentConditionMapper;

    /**
     * Create SegmentCondition
     *
     * @param segmentSequence
     * @param segmentItemForScreenSequence
     * @param segmentItemForSqlSequence
     * @param operatorType
     * @param comparisonValue
     * @param segmentConditionBlockOrder
     * @param logicalOperatorType
     * @param createDateTimeForAudit
     * @param createUserIdForAudit
     * @return SegmentCondition
     */
    public SegmentCondition create(
            Long segmentSequence,
            Long segmentItemForScreenSequence,
            Long segmentItemForSqlSequence,
            OPERATOR_TYPE operatorType,
            String comparisonValue,
            Integer segmentConditionBlockOrder,
            LOGICAL_OPERATOR_TYPE logicalOperatorType,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        SegmentCondition segmentCondition = new SegmentCondition();
        segmentCondition.setSegmentSequence(segmentSequence);
        segmentCondition.setSegmentItemForScreenSequence(segmentItemForScreenSequence);
        segmentCondition.setSegmentItemForSqlSequence(segmentItemForSqlSequence);
        segmentCondition.setOperatorType(operatorType.getValueAsString());
        segmentCondition.setComparisonValue(comparisonValue);
        segmentCondition.setSegmentConditionBlockOrder(segmentConditionBlockOrder);
        segmentCondition.setLogicalOperatorType(logicalOperatorType.getValueAsString());
        segmentCondition.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);

        segmentConditionMapper.create(segmentCondition);
        return segmentCondition;
    }

    /**
     * Find list by segmentSequence
     *
     * @param segmentSequence
     * @return List<SegmentCondition>
     */
    public List<SegmentCondition> findListBySegmentSequence(Long segmentSequence) {
        return segmentConditionMapper.findListBySegmentSequence(segmentSequence);
    }

    /**
     * Delete SegmentCondition by segmentConditionSequence
     *
     * @param segmentConditionSequence
     * @param deletionDateTimeForAudit
     * @param deletionUserIdForAudit
     * @return SegmentCondition
     */
    public SegmentCondition delete(Long segmentConditionSequence, LocalDateTime deletionDateTimeForAudit, String deletionUserIdForAudit) {
        SegmentCondition segmentCondition = segmentConditionMapper.findById(segmentConditionSequence);
        if (segmentCondition == null) {
            throw new ResourceNotFoundException();
        }

        segmentCondition.setAuditInfoForDeletion(deletionUserIdForAudit, deletionDateTimeForAudit);
        segmentConditionMapper.delete(segmentCondition);
        return segmentCondition;
    }
}
