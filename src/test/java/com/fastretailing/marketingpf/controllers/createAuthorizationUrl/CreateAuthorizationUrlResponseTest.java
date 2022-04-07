package com.fastretailing.marketingpf.controllers.createAuthorizationUrl;

import com.fastretailing.marketingpf.tests.JsonAssert;
import org.junit.jupiter.api.Test;

class CreateAuthorizationUrlResponseTest {

    @Test
    public void expectingSuccess() throws Exception {
        CreateAuthorizationUrlResponse response = new CreateAuthorizationUrlResponse("hoge.com");
        JsonAssert.assertJsonOutput(response).isSameContentAs("{\"authorizationUrl\": \"hoge.com\"}");
    }
}
