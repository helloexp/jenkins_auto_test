package com.fastretailing.marketingpf.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = false)
public class SegmentItemForScreenMaster extends BaseEntity {

    private Long segmentItemForScreenSequence;
    private String category;
    private String detailCategory;
    private String segmentItemName;
    private String inputType;
    private String operatorList;
    private String choicesList;
    private String urlForApiAccess;

    /**
     * Get inputType as ENUM
     *
     * @return INPUT_TYPE
     */
    public INPUT_TYPE getInputTypeAsEnum() {
        return INPUT_TYPE.createFromValue(this.inputType);
    }

    /**
     * Get operatorList as List<OPERATOR_TYPE>
     *
     * @return List<OPERATOR_TYPE>
     */
    public List<OPERATOR_TYPE> getOperatorListAsEnumList() {
        OperatorListDto dto = JsonUtils.fromJson(this.operatorList, OperatorListDto.class);
        List<OPERATOR_TYPE> operatorTypeListAsEnumList = new ArrayList<>();
        for (String value : dto.values) {
            operatorTypeListAsEnumList.add(OPERATOR_TYPE.createFromValue(value));
        }
        return operatorTypeListAsEnumList;
    }

    public static class OperatorListDto {
        @JsonProperty("operatorList")
        public List<String> values;
    }

    public static enum BASIC_SEGMENT_ITEM {

        DEPT_ELMNT(1L, "部門", "dept_elmnt_value"),
        G_DEPT_ELMNT(2L, "2桁部門", "g_dept_elmnt_value"),
        ITM_NAME_INCLUDE(3L, "商品名(部分一致)", "itm_name_like_value"),
        ITM_NAME_EXCLUDE(4L, "商品名除外(除外部分一致)", "itm_name_not_like_value"),
        ;

        @Getter
        private Long segmentItemForScreenSequence;

        @Getter
        private String segmentItemName;

        @Getter
        private String valueReplaceKey;

        private BASIC_SEGMENT_ITEM(Long segmentItemForScreenSequence, String segmentItemName, String valueReplaceKey) {
            this.segmentItemForScreenSequence = segmentItemForScreenSequence;
            this.segmentItemName = segmentItemName;
            this.valueReplaceKey = valueReplaceKey;
        }
    }

    @Data
    public static class ChoicesListDto {
        public List<ChoicesDetail> choicesList;

        @Data
        public static class ChoicesDetail {
            public String name;

            public String value;

            public ChoicesDetail() {
            }

            public ChoicesDetail(String name, String value) {
                this.name = name;
                this.value = value;
            }
        }
    }

    /**
     * get choicesList as Dto
     *
     * @return ChoicesListDto
     */
    public ChoicesListDto getChoicesListAsDto() {
        return JsonUtils.fromJson(this.getChoicesList(), ChoicesListDto.class);
    }
}
