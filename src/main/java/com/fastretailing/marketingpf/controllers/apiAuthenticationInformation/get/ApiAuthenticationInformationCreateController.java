package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.get;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.FacebookAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.GoogleAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.utils.AesUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ApiAuthenticationInformationCreateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuthenticationInformationCreateController.class);

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public GoogleAdsService googleAdsService;

    @Autowired
    public FacebookAdsService facebookAdsService;

    @Autowired
    private Config config;

    @Operation(summary = "Get ad account list", description = "Get a list of ad account")
    @Parameters({
            @Parameter(name = "code", in = ParameterIn.QUERY, schema = @Schema(description = "Code for google ads", type = "String")),
            @Parameter(name = "long_lived_token", in = ParameterIn.QUERY, schema = @Schema(description = "Long Lived Token for facebook", type = "String")),
            @Parameter(name = "state", in = ParameterIn.QUERY, schema = @Schema(description = "State", type = "String", allowableValues = {"1", "2"})),
    })
    @ApiResponse(responseCode = "Found", description = "Failed to fetch.")
    @GetMapping("/auth/api_auth_info_create")
    public ResponseEntity<Object> get(@Parameter(hidden = true) @Valid @ModelAttribute ApiAuthenticationInformationCreateRequest request, HttpServletRequest httpRequest, HttpServletResponse response)
            throws IOException {
        try {
            if (request.getAdsPlatform() == ADS_PLATFORM.GOOGLE_ADS) {
                String refreshToken = googleAdsService.exchangeCodeForRefreshToken(request.getCode());
                String userId = googleAdsService.getAuthUserId(refreshToken);
                ApiAuthenticationInformation newAuthInfo = apiAuthenticationInformationService.upsert(ADS_PLATFORM.GOOGLE_ADS,
                        new GoogleAuthApiTokenDto(AesUtils.encrypt(config.getAes().getSecretKey(), refreshToken)).toJson(), userId, LocalDateTime.now(), "0");
                response.sendRedirect(this.getSuccessCallbackUrl(newAuthInfo.getApiAuthenticationInformationSequence()));
                return found();
            }
            String longLivedToken = facebookAdsService.exchangeCodeForAccessToken(request.getCode());
            String userId = facebookAdsService.getAuthUserId(longLivedToken);
            ApiAuthenticationInformation newAuthInfo = apiAuthenticationInformationService.upsert(ADS_PLATFORM.FACEBOOK_ADS,
                    new FacebookAuthApiTokenDto(AesUtils.encrypt(config.getAes().getSecretKey(), longLivedToken)).toJson(), userId, LocalDateTime.now(), "0");
            response.sendRedirect(this.getSuccessCallbackUrl(newAuthInfo.getApiAuthenticationInformationSequence()));
            return found();
        } catch (RuntimeException e) {
            logger.error("Unable to create new auth " + e.getMessage(), StructuredArguments.keyValue("XID", httpRequest.getAttribute("requestId")),
                    StructuredArguments.keyValue("error", e),
                    StructuredArguments.keyValue("queryString", httpRequest.getQueryString()));
            response.sendRedirect(this.getFailCallbackUrl());
            return found();
        }
    }

    /**
     * URL returned when authentication success
     *
     * @param apiAuthenticationInformationSequence
     * @return String
     */
    public String getSuccessCallbackUrl(Long apiAuthenticationInformationSequence) {
        return config.getMkpf().getFrontendAuthCallbackUrl() + "?" +
                UriComponentsBuilder.newInstance()
                        .queryParam("apiAuthenticationInformationSequence", apiAuthenticationInformationSequence)
                        .queryParam("status", true)
                        .build().getQuery();
    }

    /**
     * URL returned when authentication fail
     *
     * @param apiAuthenticationInformationSequence
     * @return String
     */
    public String getFailCallbackUrl() {
        return config.getMkpf().getFrontendAuthCallbackUrl() + "?" +
                UriComponentsBuilder.newInstance()
                        .queryParam("status", false)
                        .build().getQuery();
    }
}
