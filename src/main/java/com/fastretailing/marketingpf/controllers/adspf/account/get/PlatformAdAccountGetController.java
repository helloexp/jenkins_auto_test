package com.fastretailing.marketingpf.controllers.adspf.account.get;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlatformAdAccountGetController extends BaseController {

    @Autowired
    public GoogleAdsService googleAdsService;

    @Autowired
    public FacebookAdsService facebookAdsService;

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public PlatformAdAccountGetValidator platformAdAccountGetValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = PlatformAdAccountGetResponse.class))}),
    })
    @GetMapping("/adspf/accounts/{apiAuthenticationInformationSequence}")
    public ResponseEntity<Object> get(@PathVariable Long apiAuthenticationInformationSequence) {
        platformAdAccountGetValidator.validate(apiAuthenticationInformationSequence);
        ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationService.findById(apiAuthenticationInformationSequence);
        if (apiAuthenticationInformation.getPlatformSequenceAsEnum() == ADS_PLATFORM.GOOGLE_ADS) {
            return ok(new PlatformAdAccountGetResponse(googleAdsService.fetchAdAccounts(apiAuthenticationInformation.getGoogleAuthApiTokenDto().getRefeshToken())));
        }
        return ok(new PlatformAdAccountGetResponse(facebookAdsService.fetchAdAccounts(apiAuthenticationInformation.getAdsPfLoginUserId())));
    }
}
