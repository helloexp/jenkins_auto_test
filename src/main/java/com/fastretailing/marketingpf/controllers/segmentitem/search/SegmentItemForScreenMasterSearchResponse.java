package com.fastretailing.marketingpf.controllers.segmentitem.search;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fastretailing.marketingpf.configs.security.LoginSuccessHandler;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegmentItemForScreenMasterSearchResponse extends BaseResponse {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    public List<SegmentItemForScreenMasterResponse> segmentItemForScreenMasterList = new ArrayList<>();

    public SegmentItemForScreenMasterSearchResponse(List<SegmentItemForScreenMaster> segmentItemForScreenMasterList) {
        for(SegmentItemForScreenMaster segmentItemForScreenMaster : segmentItemForScreenMasterList) {
            logger.debug("segmentItemForScreenMaster: {}", segmentItemForScreenMaster);
            this.segmentItemForScreenMasterList.add(new SegmentItemForScreenMasterResponse(segmentItemForScreenMaster));
        }
    }

    @JsonPropertyOrder({
        "segmentItemForScreenSequence",
        "category",
        "detailCategory",
        "segmentItemName",
        "inputType",
        "operatorList",
        "choicesList",
        "urlForApiAccess",
    })
    public static class SegmentItemForScreenMasterResponse {

        public Long segmentItemForScreenSequence;
        public String category;
        public String detailCategory;
        public String segmentItemName;
        public String inputType;
        public Map<String, List<Integer>> operatorList;
        public Map<String, List<Map<String, String>>> choicesList;
        public String urlForApiAccess;

        public SegmentItemForScreenMasterResponse(SegmentItemForScreenMaster segmentItemForScreenMaster) {
            this.segmentItemForScreenSequence = segmentItemForScreenMaster.getSegmentItemForScreenSequence();
            this.category = segmentItemForScreenMaster.getCategory();
            this.detailCategory = segmentItemForScreenMaster.getDetailCategory();
            this.segmentItemName = segmentItemForScreenMaster.getSegmentItemName();
            this.inputType = segmentItemForScreenMaster.getInputType();
            this.operatorList = JsonUtils.fromJson(segmentItemForScreenMaster.getOperatorList(),
                new TypeReference<Map<String, List<Integer>>>() {
                });
            this.choicesList = JsonUtils.fromJson(segmentItemForScreenMaster.getChoicesList(),
                new TypeReference<Map<String, List<Map<String, String>>>>() {
                });
            this.urlForApiAccess = segmentItemForScreenMaster.getUrlForApiAccess();
        }
    }
}
