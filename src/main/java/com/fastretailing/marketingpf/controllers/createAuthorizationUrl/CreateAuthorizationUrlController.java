package com.fastretailing.marketingpf.controllers.createAuthorizationUrl;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
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
public class CreateAuthorizationUrlController extends BaseController {

    @Autowired
    public GoogleAdsService googleAdsService;

    @Autowired
    public FacebookAdsService facebookAdsService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = CreateAuthorizationUrlResponse.class))}),
    })
    @GetMapping("/auth/create_url/{adsPlatformSequence}")
    public ResponseEntity<Object> get(@PathVariable Long adsPlatformSequence) {
        ADS_PLATFORM adsPlatform = ADS_PLATFORM.create(adsPlatformSequence.intValue());
        if (adsPlatform == null) {
            throw new ResourceNotFoundException();
        }
        if (adsPlatform == ADS_PLATFORM.GOOGLE_ADS) {
            return ok(new CreateAuthorizationUrlResponse(googleAdsService.getAuthorizationUrl()));
        }
        return ok(new CreateAuthorizationUrlResponse(facebookAdsService.getAuthorizationUrl()));
    }
}
