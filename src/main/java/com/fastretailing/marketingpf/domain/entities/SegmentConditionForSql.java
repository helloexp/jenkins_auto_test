package com.fastretailing.marketingpf.domain.entities;

import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SegmentConditionForSql extends BaseEntity {

    private Long segmentConditionForSqlSequence;
    private Long segmentConditionForScreenSequence;
    private Long segmentItemForSqlSequence;
    private String operatorType;
    private String comparisonValue;
    private String logicalOperatorType;
    private String priorityPrevious;
    private String priorityAfter;

    /**
     * Get operatorType as enum
     *
     * @return OPERATOR_TYPE
     */
    public OPERATOR_TYPE getOperatorTypeAsEnum() {
        return OPERATOR_TYPE.createFromValue(this.operatorType);
    }

    /**
     * Get logicalOperatorType as enum
     *
     * @return LOGICAL_OPERATOR_TYPE
     */
    public LOGICAL_OPERATOR_TYPE getLogicalOperatorTypeAsEnum() {
        return LOGICAL_OPERATOR_TYPE.createFromValue(this.logicalOperatorType);
    }
}
