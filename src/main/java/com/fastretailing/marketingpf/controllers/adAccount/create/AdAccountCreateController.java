package com.fastretailing.marketingpf.controllers.adAccount.create;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdAccountCreateController extends BaseController {

    @Autowired
    public AdAccountService adAccountService;

    @Autowired
    public GoogleAdsService googleAdsService;

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public AdAccountCreateValidator adAccountCreateValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = AdAccountCreateResponse.class))}),
    })
    @PostMapping("/ad_account/")
    public ResponseEntity<Object> create(@Valid @RequestBody AdAccountCreateRequest request) {
        adAccountCreateValidator.validate(request);
        if (request.getAdsPlatform() == ADS_PLATFORM.GOOGLE_ADS) {
            ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationService.findById(request.getApiAuthenticationInformationSequence());
            Long loginCustomerId = googleAdsService.getLoginCustomerId(apiAuthenticationInformation.getGoogleAuthApiTokenDto().getRefeshToken(), Long.valueOf(request.getAdsPlatformAccountId()));
            if(loginCustomerId == null) {
                throw new RuntimeException("Unable to find loginCustomerId for adsPlatformAccountId = " + request.getAdsPlatformAccountId());
            }
            return ok(new AdAccountCreateResponse(adAccountService.create(
                    request.getAdsPlatformAccountId(),
                    request.getAdsPlatformSequence(),
                    request.getApiAuthenticationInformationSequence(),
                    request.getBrandId(),
                    request.getCountryId(),
                    request.getAccountName(),
                    loginCustomerId,
                    getLoginUserId())));
        }
        return ok(new AdAccountCreateResponse(adAccountService.create(
                request.getAdsPlatformAccountId(),
                request.getAdsPlatformSequence(),
                request.getApiAuthenticationInformationSequence(),
                request.getBrandId(),
                request.getCountryId(),
                request.getAccountName(),
                null,
                getLoginUserId())));
    }
}
