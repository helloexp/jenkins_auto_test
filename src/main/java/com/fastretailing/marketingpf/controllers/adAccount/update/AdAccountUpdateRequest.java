package com.fastretailing.marketingpf.controllers.adAccount.update;

import com.fastretailing.marketingpf.validators.BrandValid;
import com.fastretailing.marketingpf.validators.CountryValid;
import lombok.Data;

@Data
public class AdAccountUpdateRequest {

    @BrandValid
    private String brandId;

    @CountryValid
    private String countryId;
}
