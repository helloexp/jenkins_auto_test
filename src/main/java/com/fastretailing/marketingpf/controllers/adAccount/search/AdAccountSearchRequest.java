package com.fastretailing.marketingpf.controllers.adAccount.search;

import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import lombok.Data;

@Data
public class AdAccountSearchRequest {

    public Long adsPlatformSequence;

    @BrandValid
    public String brandId;

    @CountryValid
    public String countryId;

    public String adsPfLoginUserId;
}
