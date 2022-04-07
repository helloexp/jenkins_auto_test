package com.fastretailing.marketingpf.controllers.adspf.account.get;

import com.fastretailing.marketingpf.controllers.adspf.account.get.PlatformAdAccountGetResponse.PlatformAdAccountResponse;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.tests.JsonAssert;
import org.junit.jupiter.api.Test;

class PlatformAdAccountGetResponseTest extends BaseSpringBootTest {

    @Test
    public void expectingSuccess() throws Exception {
        PlatformAdAccountDto platformAdAccountDto = new PlatformAdAccountDto("AdAccountId", "AdAccountName");

        PlatformAdAccountResponse response = new PlatformAdAccountResponse(platformAdAccountDto);
        JsonAssert.assertJsonOutput(response).isSameContentAs("{" +
                "    \"adAccountId\": \"AdAccountId\"," +
                "    \"adAccountName\": \"AdAccountName\"" +
                "}");
    }
}
