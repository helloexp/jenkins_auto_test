package com.fastretailing.marketingpf.domain.dtos;

import com.fastretailing.marketingpf.domain.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to map data from MKDB
 * This table is actually being managed in MKDB
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdAccount {

    private AdAccountDto adAccount;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class AdAccountDto extends BaseEntity {

        private Long adAccountSequence;
        private String adsPlatformAccountId;
        private Long adsPlatformSequence;
        private Long apiAuthenticationInformationSequence;
        private String brandId;
        private String countryId;
        private String accountName;
        private String loginCustomerId;
    }
}
