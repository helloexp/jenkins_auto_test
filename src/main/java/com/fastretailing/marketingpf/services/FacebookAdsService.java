package com.fastretailing.marketingpf.services;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.APIRequest;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.CustomAudience;
import com.facebook.ads.sdk.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.FacebookApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.FacebookAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.mappers.AdsPlatformMasterMapper;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.exceptions.AudienceCreateException;
import com.fastretailing.marketingpf.utils.AesUtils;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FacebookAdsService {

    private static final String API_VERSION = "v12.0";

    @Autowired
    public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

    @Autowired
    public AdsPlatformMasterMapper adsPlatformMasterMapper;

    @Autowired
    protected Config config;

    /**
     * Get authorization url
     *
     * @return String
     */
    public String getAuthorizationUrl() {
        AdsPlatformMaster facebookAdsPfMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.FACEBOOK_ADS.getValue());
        if (facebookAdsPfMaster == null) {
            throw new RuntimeException("Missing configuration for FacebookAds");
        }
        FacebookApiTokenDto apiTokenDto = facebookAdsPfMaster.getFacebookApiTokenDto();
        try {
            return UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("www.facebook.com")
                    .path(API_VERSION + "/dialog/oauth")
                    .queryParam("response_type", "code")
                    .queryParam("display", "popup")
                    .queryParam("client_id", apiTokenDto.getAppId())
                    .queryParam("redirect_uri", URLEncoder.encode(config.getMkpf().getPlatformAuthCallbackUrl(), StandardCharsets.UTF_8.toString()))
                    .queryParam("auth_type", "rerequest")
                    .queryParam("scope", URLEncoder.encode("ads_management,public_profile", StandardCharsets.UTF_8.toString()))
                    .queryParam("state", ADS_PLATFORM.FACEBOOK_ADS.getValue())
                    .build().toUriString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exchange code for refresh token
     *
     * @param code
     * @return String
     */
    public String exchangeCodeForAccessToken(String code) {
        AdsPlatformMaster facebookAdsPfMaster = adsPlatformMasterMapper.findById((long) ADS_PLATFORM.FACEBOOK_ADS.getValue());
        if (facebookAdsPfMaster == null) {
            throw new RuntimeException("Missing configuration for FacebookAds");
        }
        FacebookApiTokenDto apiTokenDto = facebookAdsPfMaster.getFacebookApiTokenDto();
        FacebookAccessTokenResponse accessTokenResponse = WebClient.builder()
                .baseUrl("https://graph.facebook.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .get().uri(uriBuilder -> uriBuilder.path(API_VERSION + "/oauth/access_token")
                        .queryParam("client_id", apiTokenDto.getAppId())
                        .queryParam("client_secret", apiTokenDto.getAppSecret())
                        .queryParam("redirect_uri", config.getMkpf().getPlatformAuthCallbackUrl())
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .onStatus(status -> status != HttpStatus.OK, response -> {
                    throw new RuntimeException("Cannot exchange code for token. Status = " + response.statusCode() + ", message = " + response.bodyToMono(String.class));
                })
                .bodyToMono(FacebookAccessTokenResponse.class).block();
        return accessTokenResponse.getAccessToken();
    }

    /**
     *
     * <code>
     * {
     *   "access_token": "EAAFNt7Bo2xMBAP",
     *   "token_type": "bearer",
     *   "expires_in": 5179099
     * }
     * </code>
     */
    @Data
    public static class FacebookAccessTokenResponse {

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private String expiresIn;
    }

    /**
     * Get user id of access token
     *
     * @param accessToken
     * @return String
     */
    public String getAuthUserId(String accessToken) {
        try {
            APIContext context = new APIContext(accessToken);
            APIRequest<User> request = new APIRequest<User>(context, "me", "", "GET", User.getParser());
            @SuppressWarnings("unchecked")
            APINodeList<User> users = (APINodeList<User>) (request.execute());
            if (CollectionUtils.isEmpty(users)) {
                return null;
            }
            return users.get(0).getFieldId();
        } catch (APIException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get list of managed ad accounts
     *
     * @param adsPfLoginUserId
     * @return List<PlatformAdAccountDto>
     */
    public List<PlatformAdAccountDto> fetchAdAccounts(String adsPfLoginUserId) {
        ApiAuthenticationInformation authInfo = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId((long) ADS_PLATFORM.FACEBOOK_ADS.getValue(), adsPfLoginUserId);
        FacebookAuthApiTokenDto apiTokenDto = authInfo.getFacebookAuthApiTokenDto();
        APINodeList<AdAccount> adAccountList = fetchAdAccounts(adsPfLoginUserId, AesUtils.decrypt(config.getAes().getSecretKey(), apiTokenDto.getAccessToken()));
        return adAccountList.stream().map(e -> new PlatformAdAccountDto(e.getFieldAccountId(), e.getFieldName())).collect(Collectors.toList());
    }

    /**
     * Get list of managed ad accounts
     *
     * @param adsPfLoginUserId
     * @param accessToken
     * @return APINodeList<AdAccount>
     */
    public APINodeList<AdAccount> fetchAdAccounts(String adsPfLoginUserId, String accessToken) {
        APIContext context = new APIContext(accessToken);
        try {
            return new User(adsPfLoginUserId, context).getAdAccounts().requestAccountIdField().requestNameField().execute();
        } catch (APIException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create custom user provided audience
     *
     * @param encryptedAccessToken
     * @param adAccountId
     * @param audienceName
     * @param description
     * @return CustomAudience
     */
    public CustomAudience createCustomUserProvidedAudience(String encryptedAccessToken, String adAccountId, String audienceName, String description) {
        try {
            APIContext context = new APIContext(AesUtils.decrypt(config.getAes().getSecretKey(), encryptedAccessToken));
            return new AdAccount(adAccountId, context).createCustomAudience()
                    .setName(audienceName)
                    .setSubtype(CustomAudience.EnumSubtype.VALUE_CUSTOM)
                    .setDescription(description)
                    .setCustomerFileSource(CustomAudience.EnumCustomerFileSource.VALUE_USER_PROVIDED_ONLY)
                    .execute();
        } catch (APIException e) {
            throw new AudienceCreateException(this.getErrorMessage(e.getRawResponse()), e);
        }
    }

    /**
     * Get error description.
     *
     * @return String
     */
    public String getErrorMessage(String response) {
        if (!JsonUtils.isValidJson(response)) {
            return response;
        }
        JsonNode errorNode = JsonUtils.fromJson(response, JsonNode.class).get("error");
        if (errorNode == null || errorNode.isNull() || StringUtils.isAllBlank(JsonUtils.toJson(errorNode))) {
            return null;
        }
        JsonNode messageNode = errorNode.get("message");
        if (messageNode == null) {
            return null;
        }
        return messageNode.asText();
    }
}
