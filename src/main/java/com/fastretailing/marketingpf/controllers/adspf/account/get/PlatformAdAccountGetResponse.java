package com.fastretailing.marketingpf.controllers.adspf.account.get;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

public class PlatformAdAccountGetResponse extends BaseResponse {

    public List<PlatformAdAccountResponse> adAccountList = new ArrayList<>();

    public PlatformAdAccountGetResponse(List<PlatformAdAccountDto> platformAdAccountList) {
        for (PlatformAdAccountDto platformAdAccountDto : platformAdAccountList) {
            this.adAccountList.add(new PlatformAdAccountResponse(platformAdAccountDto));
        }
    }

    @Data
    @JsonPropertyOrder({"adAccountId", "adAccountName"})
    public static class PlatformAdAccountResponse {

        private String adAccountId;
        private String adAccountName;

        public PlatformAdAccountResponse(PlatformAdAccountDto platformAdAccountDto) {
            this.adAccountId = platformAdAccountDto.getAdAccountId();
            this.adAccountName = platformAdAccountDto.getAdAccountName();
        }
    }
}
