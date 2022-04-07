package com.fastretailing.marketingpf.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class PlatformAdAccountDto {

    private String adAccountId;

    private String adAccountName;

    @JsonInclude(Include.NON_NULL)
    private String loginCustomerId;

    public PlatformAdAccountDto(String adAccountId, String adAccountName, String loginCustomerId) {
        this.adAccountId = adAccountId;
        this.adAccountName = adAccountName;
        this.loginCustomerId = loginCustomerId;
    }

    public PlatformAdAccountDto(String adAccountId, String adAccountName) {
        this(adAccountId, adAccountName, null);
    }
}
