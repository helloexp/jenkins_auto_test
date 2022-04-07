package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.get;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class ApiAuthenticationInformationCreateControllerTest extends BaseSpringBootTest {

    @MockBean
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @MockBean
    public GoogleAdsService googleAdsService;

    @MockBean
    public FacebookAdsService facebookAdsService;

    @Autowired
    public MockMvc mvc;

    @Test
    public void expectingGetSuccess() throws Exception {
        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setApiAuthenticationInformationSequence(1001L);
        apiAuthenticationInformation.setAdsPlatformSequence(2001L);
        apiAuthenticationInformation.setApiToken("{\"refresh_token\":\"refreshToken\"}");
        apiAuthenticationInformation.setAdsPfLoginUserId("userId");
        apiAuthenticationInformation.setDeletionFlagForAudit("f");
        apiAuthenticationInformation.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        apiAuthenticationInformation.setCreateUserIdForAudit("user_02");
        apiAuthenticationInformation.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        apiAuthenticationInformation.setUpdateUserIdForAudit("user_02");
        Mockito.doReturn("refresh_token").when(googleAdsService).exchangeCodeForRefreshToken(Mockito.anyString());
        Mockito.doReturn("userId").when(googleAdsService).getAuthUserId(Mockito.anyString());
        Mockito.doReturn("userId").when(facebookAdsService).getAuthUserId(Mockito.anyString());
        Mockito.doReturn(apiAuthenticationInformation).when(apiAuthenticationInformationService).upsert(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(get("/api/marketingpf/v1/fr/jp/auth/api_auth_info_create/?code=code&state=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.FOUND.value()));

        mvc.perform(get("/api/marketingpf/v1/fr/jp/auth/api_auth_info_create/?longLivedToken=longLivedToken&state=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.FOUND.value()));

        mvc.perform(get("/api/marketingpf/v1/fr/jp/auth/api_auth_info_create/?code=code&state=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(json().errorContentEquals("{" +
                        "    \"errorCode\": \"E00400\"," +
                        "    \"errorMessage\": \"Bad request\"," +
                        "    \"errorDetail\": \"Validation failed\"," +
                        "    \"validationErrors\": [{" +
                        "            \"field\": \"state\"," +
                        "            \"message\": \"Invalid state\"" +
                        "        }" +
                        "    ]" +
                        "}"));
    }
}
