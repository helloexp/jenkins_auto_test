package com.fastretailing.marketingpf.controllers.adAccount.create;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdAccountCreateValidator {
    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    /**
     * validate for create account
     *
     * @param request
     */
    public void validate(AdAccountCreateRequest request) {
        ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationService.findById(request.getApiAuthenticationInformationSequence());
        if (apiAuthenticationInformation == null) {
            throw new ResourceNotFoundException();
        }
    }
}
