package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.list;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import java.util.ArrayList;
import java.util.List;

public class ApiAuthenticationInformationListResponse extends BaseResponse {

    public List<ApiAuthenticationInformationListResponseDto> apiAuthenticationInformationList = new ArrayList<>();

    public ApiAuthenticationInformationListResponse(List<ApiAuthenticationInformation> apiList) {
        for (ApiAuthenticationInformation api : apiList) {
            this.apiAuthenticationInformationList.add(new ApiAuthenticationInformationListResponseDto(api));
        }
    }

    @JsonPropertyOrder({
            "apiAuthenticationInformationSequence",
            "adsPlatformSequence",
            "adsPfLoginUserId",
            "deletionFlagForAudit",
            "createDateTimeForAudit",
            "createUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit"
    })
    public static class ApiAuthenticationInformationListResponseDto extends BaseAuditResponse {

        public Long apiAuthenticationInformationSequence;
        public Long adsPlatformSequence;
        public String adsPfLoginUserId;

        public ApiAuthenticationInformationListResponseDto(ApiAuthenticationInformation api) {
            this.apiAuthenticationInformationSequence = api.getApiAuthenticationInformationSequence();
            this.adsPlatformSequence = api.getAdsPlatformSequence();
            this.adsPfLoginUserId = api.getAdsPfLoginUserId();
            this.deletionFlagForAudit = api.getDeletionFlagForAudit();
            this.createDateTimeForAudit = api.getCreateDateTimeForAudit();
            this.createUserIdForAudit = api.getCreateUserIdForAudit();
            this.updateDateTimeForAudit = api.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = api.getUpdateUserIdForAudit();
        }
    }
}
