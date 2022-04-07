package com.fastretailing.marketingpf.domain.dtos;

import com.fastretailing.marketingpf.domain.entities.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to map data from MKDB when get AdAccountList.<br>
 * AdAccount entity is actually being managed in MKDB.
 */
@Data
public class AdAccountList {

    private List<AdAccountListDto> adsAccountList = new ArrayList<>();

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class AdAccountListDto extends BaseEntity {

        private Long adAccountSequence;
        private String adsPlatformAdAccountId;
        private Long adsPlatformSequence;
        private Long apiAuthenticationInformationSequence;
        private String brandId;
        private String countryId;
        private String accountName;
        private String loginCustomerId;
    }
}

