package com.fastretailing.marketingpf.controllers.adAccount.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.domain.dtos.AdAccount;

@JsonPropertyOrder({
        "adAccountSequence",
        "adsPlatformAccountId",
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
        "updateUserIdForAudit"})
public class AdAccountGetResponse extends BaseAuditResponse {

    public Long adAccountSequence;
    public String adsPlatformAccountId;
    public Long adsPlatformSequence;
    public Long apiAuthenticationInformationSequence;
    public String brandId;
    public String countryId;
    public String accountName;
    @JsonInclude(Include.NON_NULL)
    public String loginCustomerId;

    public AdAccountGetResponse(AdAccount adAccount) {
        this.adAccountSequence = adAccount.getAdAccount().getAdAccountSequence();
        this.adsPlatformAccountId = adAccount.getAdAccount().getAdsPlatformAccountId();
        this.adsPlatformSequence = adAccount.getAdAccount().getAdsPlatformSequence();
        this.apiAuthenticationInformationSequence = adAccount.getAdAccount().getApiAuthenticationInformationSequence();
        this.brandId = adAccount.getAdAccount().getBrandId();
        this.countryId = adAccount.getAdAccount().getCountryId();
        this.accountName = adAccount.getAdAccount().getAccountName();
        this.loginCustomerId = adAccount.getAdAccount().getLoginCustomerId();
        this.deletionFlagForAudit = adAccount.getAdAccount().getDeletionFlagForAudit();
        this.createDateTimeForAudit = adAccount.getAdAccount().getCreateDateTimeForAudit();
        this.createUserIdForAudit = adAccount.getAdAccount().getCreateUserIdForAudit();
        this.updateDateTimeForAudit = adAccount.getAdAccount().getUpdateDateTimeForAudit();
        this.updateUserIdForAudit = adAccount.getAdAccount().getUpdateUserIdForAudit();
        this.deletionDateTimeForAudit = adAccount.getAdAccount().getDeletionDateTimeForAudit();
        this.deletionUserIdForAudit = adAccount.getAdAccount().getDeletionUserIdForAudit();
    }
}
