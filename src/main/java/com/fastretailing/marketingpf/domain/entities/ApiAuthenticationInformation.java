package com.fastretailing.marketingpf.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.utils.JsonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiAuthenticationInformation extends BaseEntity {

    private Long apiAuthenticationInformationSequence;

    private Long adsPlatformSequence;

    private String apiToken;

    private String adsPfLoginUserId;

    /**
     *
     * @return FacebookAuthApiTokenDto
     */
    public FacebookAuthApiTokenDto getFacebookAuthApiTokenDto() {
        return JsonUtils.fromJson(this.apiToken, FacebookAuthApiTokenDto.class);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FacebookAuthApiTokenDto {

        @JsonProperty("access_token")
        private String accessToken;

        public String toJson() {
            return JsonUtils.toJson(this);
        }

        public FacebookAuthApiTokenDto() {
        }

        public FacebookAuthApiTokenDto(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    /**
     *
     * @return GoogleAuthApiTokenDto
     */
    public GoogleAuthApiTokenDto getGoogleAuthApiTokenDto() {
        return JsonUtils.fromJson(this.apiToken, GoogleAuthApiTokenDto.class);
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GoogleAuthApiTokenDto {

        @JsonProperty("refresh_token")
        private String refeshToken;

        public String toJson() {
            return JsonUtils.toJson(this);
        }

        public GoogleAuthApiTokenDto() {
        }

        public GoogleAuthApiTokenDto(String refreshToken) {
            this.refeshToken = refreshToken;
        }
    }

    public ADS_PLATFORM getPlatformSequenceAsEnum() {
        return ADS_PLATFORM.create(this.adsPlatformSequence.intValue());
    }
}
