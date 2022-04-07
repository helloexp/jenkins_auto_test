package com.fastretailing.marketingpf.controllers.adAccount.create;

import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import com.fastretailing.marketingpf.validators.Required;
import com.fastretailing.marketingpf.validators.SequenceValid;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
public class AdAccountCreateRequest {

    @Required
    private String adsPlatformAccountId;

    @Required
    @SequenceValid
    private Long adsPlatformSequence;

    @Required
    @SequenceValid
    private Long apiAuthenticationInformationSequence;

    @Required
    @BrandValid
    private String brandId;

    @Required
    @CountryValid
    private String countryId;

    @Required
    private String accountName;

    @Hidden
    public ADS_PLATFORM getAdsPlatform() {
        return ADS_PLATFORM.create(adsPlatformSequence.intValue());
    }
}
