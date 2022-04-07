package com.fastretailing.marketingpf.controllers.createAuthorizationUrl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;

@JsonPropertyOrder({"authorizationUrl"})
public class CreateAuthorizationUrlResponse extends BaseResponse {

    public String authorizationUrl;

    public CreateAuthorizationUrlResponse(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }
}
