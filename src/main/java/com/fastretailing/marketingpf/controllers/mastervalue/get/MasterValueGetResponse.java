package com.fastretailing.marketingpf.controllers.mastervalue.get;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"choicesList"})
public class MasterValueGetResponse extends BaseResponse {

    public Map<String, List<Map<String, String>>> choicesList;

    public MasterValueGetResponse(String choicesList) {
        this.choicesList = JsonUtils.fromJson(choicesList, new TypeReference<Map<String, List<Map<String, String>>>>(){});
    }
}
