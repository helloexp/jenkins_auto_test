package com.fastretailing.marketingpf.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.utils.JsonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdsPlatformMaster extends BaseEntity {

    private Long adsPlatformSequence;
    private Long outputPlatformSequence;
    private String adsPlatformName;
    private String apiToken;
    private String extractionTargetIdList;

    /**
     * Get adsPlatformSequence as enum
     *
     * @return ADS_PLATFORM
     */
    public ADS_PLATFORM getPlatformSequenceAsEnum() {
        return ADS_PLATFORM.create(this.adsPlatformSequence.intValue());
    }

    /**
     *
     * @return GoogleApiTokenDto
     */
    public GoogleApiTokenDto getGoogleApiTokenDto() {
        if(this.getPlatformSequenceAsEnum() != ADS_PLATFORM.GOOGLE_ADS) {
            throw new RuntimeException("Not support");
        }
        return JsonUtils.fromJson(apiToken, GoogleApiTokenDto.class);
    }

    /**
     *
     * @return FacebookApiTokenDto
     */
    public FacebookApiTokenDto getFacebookApiTokenDto() {
        if(this.getPlatformSequenceAsEnum() != ADS_PLATFORM.FACEBOOK_ADS) {
            throw new RuntimeException("Not support");
        }
        return JsonUtils.fromJson(apiToken, FacebookApiTokenDto.class);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GoogleApiTokenDto {

        @JsonProperty("developer_token")
        private String developerToken;

        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("client_secret")
        private String clientSecret;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FacebookApiTokenDto {

        @JsonProperty("app_id")
        private String appId;

        @JsonProperty("app_secret")
        private String appSecret;
    }
}
