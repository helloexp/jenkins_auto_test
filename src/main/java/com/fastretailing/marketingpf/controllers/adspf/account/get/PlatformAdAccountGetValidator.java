package com.fastretailing.marketingpf.controllers.adspf.account.get;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformAdAccountGetValidator {

    @Autowired
    public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

    /**
     * validate for get platform ad account
     *
     * @param apiAuthenticationInformationSequence
     */
    public void validate(Long apiAuthenticationInformationSequence) {
        ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(apiAuthenticationInformationSequence);
        if (apiAuthenticationInformation == null) {
            throw new ResourceNotFoundException();
        }
        if (!Arrays.asList(ADS_PLATFORM.FACEBOOK_ADS, ADS_PLATFORM.GOOGLE_ADS).contains(apiAuthenticationInformation.getPlatformSequenceAsEnum())) {
            throw new ValidationFailException(new ValidationErrors("adsPlatform", "adsPlatform invalid"));
        }
    }
}
