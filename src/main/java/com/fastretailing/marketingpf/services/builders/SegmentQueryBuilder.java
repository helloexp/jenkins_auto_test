package com.fastretailing.marketingpf.services.builders;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster.BASIC_SEGMENT_ITEM;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class SegmentQueryBuilder {

    private Segment segment;
    private List<SegmentCondition> segmentConditionList;
    private Map<String, String> paramMap;

    public SegmentQueryBuilder(Segment segment, List<SegmentCondition> segmentConditionList, Map<String, String> paramMap) {
        this.segment = segment;
        this.segmentConditionList = segmentConditionList;
        this.paramMap = new HashMap<String, String>(paramMap);
        prepareBasicSegmentParam(BASIC_SEGMENT_ITEM.DEPT_ELMNT);
        prepareBasicSegmentParam(BASIC_SEGMENT_ITEM.G_DEPT_ELMNT);
        prepareBasicSegmentParam(BASIC_SEGMENT_ITEM.ITM_NAME_INCLUDE);
        prepareBasicSegmentParam(BASIC_SEGMENT_ITEM.ITM_NAME_EXCLUDE);
    }

    /**
     * Find and add additional parameter for basic segment item
     *
     * @param basicSegmentItem
     */
    private void prepareBasicSegmentParam(BASIC_SEGMENT_ITEM basicSegmentItem) {
        Optional<SegmentCondition> targetSegmentCondition = segmentConditionList.stream()
                .filter(e -> e.getSegmentConditionBlockOrder() == 0 && e.getSegmentItemForScreenSequence() == basicSegmentItem.getSegmentItemForScreenSequence())
                .findFirst();
        if (targetSegmentCondition.isPresent()) {
            SegmentCondition segmentCondition = targetSegmentCondition.get();
            this.paramMap.put(basicSegmentItem.getValueReplaceKey(), this.formatValue(
                    segmentCondition.getSegmentItemForScreenMaster().getInputTypeAsEnum(), segmentCondition.getOperatorTypeAsEnum(), segmentCondition.getComparisonValueAsStringList()));
        }
    }

    private final int BASIC_CONDITION_BLOCK_NUMBER = 0;

    /**
     * Build query
     *
     * @return String
     */
    public String build() {
        if (CollectionUtils.isEmpty(segmentConditionList)) {
            throw new RuntimeException("Segment has no segment condition");
        }
        if (!hasBasicSegmentCondition()) {
            return buildExtraConditionQuery();
        }
        if (!hasExtraSegmentCondition()) {
            return buildBasicConditionQuery();
        }
        return "(" + buildBasicConditionQuery() + ") INTERSECT DISTINCT (" + buildExtraConditionQuery() + ")";
    }

    /**
     * Check if at least one basic condition
     *
     * @return boolean
     */
    private boolean hasBasicSegmentCondition() {
        return CollectionUtils.isNotEmpty(getBasicSegmentCondition());
    }

    /**
     * Check if at least one extra condition
     *
     * @return boolean
     */
    private boolean hasExtraSegmentCondition() {
        return CollectionUtils.isNotEmpty(getExtraSegmentCondition());
    }

    /**
     * Get basic segment condition list
     *
     * @return List<SegmentCondition>
     */
    private List<SegmentCondition> getBasicSegmentCondition() {
        return segmentConditionList.stream().filter(e -> e.getSegmentConditionBlockOrder() == BASIC_CONDITION_BLOCK_NUMBER).collect(Collectors.toList());
    }

    /**
     * Get extra segment condition list
     *
     * @return List<SegmentCondition>
     */
    private List<SegmentCondition> getExtraSegmentCondition() {
        return segmentConditionList.stream().filter(e -> e.getSegmentConditionBlockOrder() != BASIC_CONDITION_BLOCK_NUMBER).collect(Collectors.toList());
    }

    /**
     * Build basic condition query
     *
     * @return String
     */
    private String buildBasicConditionQuery() {
        return buildBlockQuery(getBasicSegmentCondition());
    }

    /**
     * Build extra condition query
     *
     * @return String
     */
    private String buildExtraConditionQuery() {
        Map<Integer, List<SegmentCondition>> extraSegmentConditionMap = getExtraSegmentCondition().stream().collect(Collectors.groupingBy(SegmentCondition::getSegmentConditionBlockOrder));
        List<Integer> blockOrderList = extraSegmentConditionMap.keySet().stream().sorted().collect(Collectors.toList());
        List<String> blockQueryList = blockOrderList.stream().map(blockOrder -> buildBlockQuery(extraSegmentConditionMap.get(blockOrder))).collect(Collectors.toList());
        if (segment.getAddLogicalOperatorTypeAsEnum() == LOGICAL_OPERATOR_TYPE.OR) {
            return buildUnionDistinctQuery(blockQueryList);
        } else {
            return buildIntersectDistinctQuery(blockQueryList);
        }
    }

    /**
     * Convert segment condition list to single query<br>
     * Logical operator of all condition must be same<br>
     *
     * @param list
     * @return Single query
     */
    public String buildBlockQuery(List<SegmentCondition> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("Empty block query list found");
        }
        if (list.stream().map(e -> e.getLogicalOperatorTypeAsEnum()).distinct().count() > 1) {
            throw new RuntimeException("Logical operator in a block must be same");
        }
        LOGICAL_OPERATOR_TYPE blockLogicalOperatorType = list.get(0).getLogicalOperatorTypeAsEnum();
        List<String> queryList = list.stream().map(e -> buildConditionQuery(e)).collect(Collectors.toList());
        if (blockLogicalOperatorType == LOGICAL_OPERATOR_TYPE.OR) {
            return buildUnionDistinctQuery(queryList);
        } else {
            return buildIntersectDistinctQuery(queryList);
        }
    }

    /**
     * Join query list using INTERSECT DISTICT
     *
     * @param queryList
     * @return Single query
     */
    public String buildIntersectDistinctQuery(List<String> queryList) {
        if(CollectionUtils.size(queryList) == 1) {
            return queryList.get(0);
        }
        return queryList.stream().map(query -> "(" + query + ")").collect(Collectors.joining(" INTERSECT DISTINCT "));
    }

    /**
     * Join query list using UNION DISTICT
     *
     * @param queryList
     * @return Single query
     */
    public String buildUnionDistinctQuery(List<String> queryList) {
        if(CollectionUtils.size(queryList) == 1) {
            return queryList.get(0);
        }
        return queryList.stream().map(query -> "(" + query + ")").collect(Collectors.joining(" UNION DISTINCT "));
    }

    /**
     * Build query for single segment condition
     *
     * @param segmentCondition
     * @return String
     */
    public String buildConditionQuery(SegmentCondition segmentCondition) {
        SegmentItemForSqlMaster segmentItemForSql = segmentCondition.getSegmentItemForSqlMaster();
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.putAll(this.paramMap);
        if (segment.getEventTargetPeriodTypeAsEnum() == EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE) {
            parameterMap.put("eventTargetPeriodStartDate", formatDateValueYyyyMmDd(segment.getEventTargetPeriodStartDate()));
            parameterMap.put("eventTargetPeriodEndDate", formatDateValueYyyyMmDd(segment.getEventTargetPeriodEndDate()));
        } else {
            parameterMap.put("eventTargetPeriodRelativeNumberValue", Long.toString(segment.getEventTargetPeriodRelativeNumberValue()));
            parameterMap.put("eventTargetPeriodRelativePeriodUnit", segment.getEventTargetPeriodRelativePeriodUnitAsEnum().getBigqueryInterval());
        }
        parameterMap.put("eventTargetPeriodType", segment.getEventTargetPeriodType());
        parameterMap.put("operator", segmentCondition.getOperatorTypeAsEnum().getName());
        parameterMap.put("value", formatValue(segmentCondition.getSegmentItemForScreenMaster().getInputTypeAsEnum(),
                segmentCondition.getOperatorTypeAsEnum(),
                segmentCondition.getComparisonValueAsStringList()));
        return new TemplateQueryBuilder(segmentItemForSql.getQueryForSegment(), parameterMap).build();
    }

    /**
     * Format comparison value<br>
     *
     * @param inputType
     * @param operatorType
     * @param values
     * @return String
     */
    public String formatValue(INPUT_TYPE inputType, OPERATOR_TYPE operatorType, List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            throw new RuntimeException("Empty value found");
        }
        if (Arrays.asList(OPERATOR_TYPE.IN, OPERATOR_TYPE.NOT_IN).contains(operatorType)) {
            if (inputType.isStringType()) {
                return values.stream().map(e -> "'" + escapeStringValue(e) + "'").collect(Collectors.joining(", "));
            }
            return values.stream().map(e -> formatNumericValue(e)).collect(Collectors.joining(", "));
        }
        if (values.size() != 1) {
            throw new RuntimeException("Single value required");
        }
        String value = values.get(0);
        if (Arrays.asList(OPERATOR_TYPE.LIKE, OPERATOR_TYPE.NOT_LIKE).contains(operatorType)) {
            return escapeLikeValue(value);
        }
        if (inputType.isStringType()) {
            return escapeStringValue(value);
        }
        return formatNumericValue(value);
    }

    /**
     * Format LocalDate type as yyyyMMdd
     *
     * @param date
     * @return String
     */
    public String formatDateValueYyyyMmDd(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * Format numeric type value
     *
     * @param value
     * @return String
     */
    public String formatNumericValue(String comparisionValue) {
        if (comparisionValue == null) {
            throw new RuntimeException("Invalid numeric value");
        }
        String value = comparisionValue.trim();
        try {
            Double.parseDouble(value);
            return value;
        } catch (Exception e) {
            /* Ignore */}
        try {
            Long.parseLong(value);
            return value;
        } catch (Exception e) {
            /* Ignore */}
        throw new RuntimeException("Invalid numeric value");
    }

    /**
     * Escape value for string
     *
     * @param str
     * @return
     */
    public String escapeStringValue(String str) {
        if (StringUtils.isAllBlank(str)) {
            str = "";
        }
        if (str.contains("\\")) {
            str = str.replace("\\", "\\\\");
        }
        if (str.contains("\'")) {
            str = str.replace("\'", "\\\'");
        }
        return str;
    }

    /**
     * Escape value for like value<br>
     * https://cloud.google.com/bigquery/docs/reference/standard-sql/operators#comparison_operators
     *
     * @param str
     * @return String
     */
    public String escapeLikeValue(String str) {
        if (StringUtils.isAllBlank(str)) {
            str = "";
        }
        if (str.contains("\\")) {
            str = str.replace("\\", "\\\\\\\\");
        }
        if (str.contains("%")) {
            str = str.replace("%", "\\\\%");
        }
        if (str.contains("_")) {
            str = str.replace("_", "\\\\_");
        }
        if (str.contains("\'")) {
            str = str.replace("\'", "\\\'");
        }
        return str;
    }
}
