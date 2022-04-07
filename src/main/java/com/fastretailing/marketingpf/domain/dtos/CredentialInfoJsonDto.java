package com.fastretailing.marketingpf.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CredentialInfoJsonDto {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("developer_token")
    private String developerToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("app_secret")
    private String appSecret;

    @JsonProperty("access_token")
    private String accessToken;

    public CredentialInfoJsonDto() {
    }

    public CredentialInfoJsonDto(String clientId, String clientSecret, String developerToken, String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.developerToken = developerToken;
        this.refreshToken = refreshToken;
    }

    public CredentialInfoJsonDto(String appId, String appSecret, String accessToken) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.accessToken = accessToken;
    }
}
