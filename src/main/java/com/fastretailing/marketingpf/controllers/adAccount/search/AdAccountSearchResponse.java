package com.fastretailing.marketingpf.controllers.adAccount.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdAccountSearchResponse extends BaseResponse {

    public List<AdAccountSearchResponseDto> adAccountList = new ArrayList<>();

    public AdAccountSearchResponse(List<AdAccountListDto> adsAccountList, Map<Long, String> authSeqToAdsPfLoginUserIdMap, Map<String, String> userMasterMap) {
        this.adAccountList = adsAccountList.stream().map(adAccount -> new AdAccountSearchResponseDto(adAccount, authSeqToAdsPfLoginUserIdMap, userMasterMap)).collect(Collectors.toList());
    }

    @JsonPropertyOrder({
            "adsPfLoginUserId",
            "adAccountSequence",
            "adsPlatformAdAccountId",
            "adsPlatformSequence",
            "apiAuthenticationInformationSequence",
            "brandId",
            "countryId",
            "accountName",
            "loginCustomerId",
            "deletionFlagForAudit",
            "createDateTimeForAudit",
            "createUserIdForAudit",
            "deletionDateTimeForAudit",
            "deletionUserIdForAudit",
            "updateDateTimeForAudit",
            "updateUserIdForAudit"
    })
    public static class AdAccountSearchResponseDto extends BaseAuditResponse {

        public String adsPfLoginUserId;
        public Long adAccountSequence;
        public String adsPlatformAdAccountId;
        public Long adsPlatformSequence;
        public Long apiAuthenticationInformationSequence;
        public String brandId;
        public String countryId;
        public String accountName;
        @JsonInclude(Include.NON_NULL)
        public String loginCustomerId;

        public AdAccountSearchResponseDto(AdAccountListDto adAccountDto, Map<Long, String> authSeqToAdsPfLoginUserIdMap, Map<String, String> userMasterMap) {
            this.adsPfLoginUserId = authSeqToAdsPfLoginUserIdMap.get(adAccountDto.getApiAuthenticationInformationSequence());
            this.adsPlatformAdAccountId = adAccountDto.getAdsPlatformAdAccountId();
            this.adAccountSequence = adAccountDto.getAdAccountSequence();
            this.adsPlatformSequence = adAccountDto.getAdsPlatformSequence();
            this.apiAuthenticationInformationSequence = adAccountDto.getApiAuthenticationInformationSequence();
            this.brandId = adAccountDto.getBrandId();
            this.countryId = adAccountDto.getCountryId();
            this.accountName = adAccountDto.getAccountName();
            this.loginCustomerId = adAccountDto.getLoginCustomerId();

            this.deletionFlagForAudit = adAccountDto.getDeletionFlagForAudit();
            this.createDateTimeForAudit = adAccountDto.getCreateDateTimeForAudit();
            this.createUserIdForAudit = userMasterMap.get(adAccountDto.getCreateUserIdForAudit());
            this.updateDateTimeForAudit = adAccountDto.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = userMasterMap.get(adAccountDto.getUpdateUserIdForAudit());
        }
    }
}
