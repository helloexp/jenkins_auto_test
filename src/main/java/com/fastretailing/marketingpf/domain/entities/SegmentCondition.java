package com.fastretailing.marketingpf.domain.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SegmentCondition extends BaseEntity {

    private Long segmentConditionSequence;
    private Long segmentSequence;
    private Long segmentItemForScreenSequence;
    private Long segmentItemForSqlSequence;
    private String operatorType;
    private String comparisonValue;
    private Integer segmentConditionBlockOrder;
    private String logicalOperatorType;
    private SegmentItemForSqlMaster segmentItemForSqlMaster;
    private SegmentItemForScreenMaster segmentItemForScreenMaster;

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

    /**
     * Get comparisionValue as String list<br>
     *
     *
     * @return List<String>
     */
    public List<String> getComparisonValueAsStringList() {
        return JsonUtils.fromJson(comparisonValue, new TypeReference<List<String>>() {});
    }
}
