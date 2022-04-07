package com.fastretailing.marketingpf.controllers.adAccount.create;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class AdAccountCreateControllerTest extends BaseSpringBootTest {

    @MockBean
    private AdAccountService adAccountService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    public GoogleAdsService googleAdsService;

    @MockBean
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("oid", "user_02");
        claims.put("name", "Fuga");
        claims.put("sub", "sub");
        OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
        DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void expectingCreateSuccess() throws Exception {

        AdAccountCreateRequest facebookRequest = new AdAccountCreateRequest();
        facebookRequest.setAdsPlatformAccountId("99999");
        facebookRequest.setAdsPlatformSequence(2L);
        facebookRequest.setApiAuthenticationInformationSequence(4001L);
        facebookRequest.setBrandId("090");
        facebookRequest.setCountryId("1");
        facebookRequest.setAccountName("account_02");

        AdAccount facebookAdAccount = new AdAccount();
        facebookAdAccount.setAdAccount(new AdAccountDto());
        facebookAdAccount.getAdAccount().setAdAccountSequence(1001L);
        facebookAdAccount.getAdAccount().setAdsPlatformAccountId("99999");
        facebookAdAccount.getAdAccount().setAdsPlatformSequence(2001L);
        facebookAdAccount.getAdAccount().setApiAuthenticationInformationSequence(3001L);
        facebookAdAccount.getAdAccount().setBrandId("brand_01");
        facebookAdAccount.getAdAccount().setCountryId("country_01");
        facebookAdAccount.getAdAccount().setAccountName("account_01");
        facebookAdAccount.getAdAccount().setDeletionFlagForAudit("false");
        facebookAdAccount.getAdAccount().setAuditInfoForCreation("user_02", LocalDateTime.of(2021, 1, 1, 1, 1, 2));
        facebookAdAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 2, 2, 2));
        facebookAdAccount.getAdAccount().setDeletionUserIdForAudit("user_02");

        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setApiAuthenticationInformationSequence(2001L);
        apiAuthenticationInformation.setAdsPlatformSequence(1L);
        apiAuthenticationInformation.setAdsPfLoginUserId("loginUserId");
        apiAuthenticationInformation.setApiToken("{\"Token\": \"Token\"}");
        Mockito.doReturn(apiAuthenticationInformation).when(apiAuthenticationInformationService).findById(Mockito.any());

        Mockito.doReturn(facebookAdAccount).when(adAccountService).create(anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString(), Mockito.any(), anyString());
        mvc.perform(post("/api/marketingpf/v1/fr/jp/ad_account/").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(facebookRequest)))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(""
                        + "{" +
                        "    \"adAccountSequence\": 1001," +
                        "    \"adsPlatformAccountId\": \"99999\"," +
                        "    \"adsPlatformSequence\": 2001," +
                        "    \"apiAuthenticationInformationSequence\": 3001," +
                        "    \"brandId\": \"brand_01\"," +
                        "    \"countryId\": \"country_01\"," +
                        "    \"accountName\": \"account_01\"," +
                        "    \"loginCustomerId\": null," +
                        "    \"deletionFlagForAudit\": \"f\"," +
                        "    \"createDateTimeForAudit\": \"2021-01-01 01:01:02\"," +
                        "    \"createUserIdForAudit\": \"user_02\"," +
                        "    \"deletionDateTimeForAudit\": \"2021-01-01 02:02:02\"," +
                        "    \"deletionUserIdForAudit\": \"user_02\"," +
                        "    \"updateDateTimeForAudit\": \"2021-01-01 01:01:02\"," +
                        "    \"updateUserIdForAudit\": \"user_02\"" +
                        "}"));

        AdAccountCreateRequest googleRequest = new AdAccountCreateRequest();
        googleRequest.setAdsPlatformAccountId("99999");
        googleRequest.setAdsPlatformSequence(1L);
        googleRequest.setApiAuthenticationInformationSequence(3001L);
        googleRequest.setBrandId("090");
        googleRequest.setCountryId("1");
        googleRequest.setAccountName("account_01");

        AdAccount googleAdAccount = new AdAccount();
        googleAdAccount.setAdAccount(new AdAccountDto());
        googleAdAccount.getAdAccount().setAdAccountSequence(1001L);
        googleAdAccount.getAdAccount().setAdsPlatformAccountId("99999");
        googleAdAccount.getAdAccount().setAdsPlatformSequence(2001L);
        googleAdAccount.getAdAccount().setApiAuthenticationInformationSequence(3001L);
        googleAdAccount.getAdAccount().setBrandId("brand_01");
        googleAdAccount.getAdAccount().setCountryId("country_01");
        googleAdAccount.getAdAccount().setAccountName("account_01");
        googleAdAccount.getAdAccount().setLoginCustomerId("4001");
        googleAdAccount.getAdAccount().setDeletionFlagForAudit("false");
        googleAdAccount.getAdAccount().setAuditInfoForCreation("user_01", LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        googleAdAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 2, 2, 2));
        googleAdAccount.getAdAccount().setDeletionUserIdForAudit("user_02");

        ApiAuthenticationInformation googleAuth = new ApiAuthenticationInformation();
        googleAuth.setApiAuthenticationInformationSequence(2001L);
        googleAuth.setAdsPlatformSequence(1L);
        googleAuth.setAdsPfLoginUserId("loginUserId");
        googleAuth.setApiToken("{\"refresh_token\": \"refresh_token\"}");
        Mockito.doReturn(googleAdAccount).when(adAccountService).create(anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString(), anyLong(), anyString());

        Mockito.doReturn(googleAuth).when(apiAuthenticationInformationService).findById(Mockito.any());
        Mockito.doReturn(1234567L).when(googleAdsService).getLoginCustomerId(anyString(), anyLong());

        mvc.perform(post("/api/marketingpf/v1/fr/jp/ad_account/").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(googleRequest)))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(""
                        + "{" +
                        "    \"adAccountSequence\": 1001," +
                        "    \"adsPlatformAccountId\": \"99999\"," +
                        "    \"adsPlatformSequence\": 2001," +
                        "    \"apiAuthenticationInformationSequence\": 3001," +
                        "    \"brandId\": \"brand_01\"," +
                        "    \"countryId\": \"country_01\"," +
                        "    \"accountName\": \"account_01\"," +
                        "    \"loginCustomerId\": \"4001\"," +
                        "    \"deletionFlagForAudit\": \"f\"," +
                        "    \"createDateTimeForAudit\": \"2021-01-01 01:01:01\"," +
                        "    \"createUserIdForAudit\": \"user_01\"," +
                        "    \"deletionDateTimeForAudit\": \"2021-01-01 02:02:02\"," +
                        "    \"deletionUserIdForAudit\": \"user_02\"," +
                        "    \"updateDateTimeForAudit\": \"2021-01-01 01:01:01\"," +
                        "    \"updateUserIdForAudit\": \"user_01\"" +
                        "}"));

        AdAccountCreateRequest invalidRequest = new AdAccountCreateRequest();
        invalidRequest.setAdsPlatformAccountId(null);
        invalidRequest.setAdsPlatformSequence(0L);
        invalidRequest.setApiAuthenticationInformationSequence(-1L);
        invalidRequest.setBrandId("brand_01");
        invalidRequest.setCountryId("country_01");
        invalidRequest.setAccountName("");

        mvc.perform(post("/api/marketingpf/v1/fr/jp/ad_account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(json().errorContentEquals("{"
                        + "    \"errorCode\": \"E00400\","
                        + "    \"errorMessage\": \"Bad request\","
                        + "    \"errorDetail\": \"Validation failed\","
                        + "    \"validationErrors\": [{"
                        + "            \"field\": \"accountName\","
                        + "            \"message\": \"errors.required\""
                        + "        }, {"
                        + "            \"field\": \"adsPlatformAccountId\","
                        + "            \"message\": \"errors.required\""
                        + "        }, {"
                        + "            \"field\": \"adsPlatformSequence\","
                        + "            \"message\": \"errors.invalid\""
                        + "        }, {"
                        + "            \"field\": \"apiAuthenticationInformationSequence\","
                        + "            \"message\": \"errors.invalid\""
                        + "        }, {"
                        + "            \"field\": \"brandId\","
                        + "            \"message\": \"errors.invalid\""
                        + "        }, {"
                        + "            \"field\": \"countryId\","
                        + "            \"message\": \"errors.invalid\""
                        + "        }"
                        + "    ]"
                        + "}"));
    }
}
