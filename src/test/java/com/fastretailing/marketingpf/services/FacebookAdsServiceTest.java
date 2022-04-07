package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.CustomAudience;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

public class FacebookAdsServiceTest extends BaseSpringBootTest {

    @Nested
    public class GetAuthorizationUrlTest {

        @Autowired
        private FacebookAdsService facebookAdsService;

        @Test
        @Sql("/services/FacebookAdsServiceTest/GetAuthorizationUrlTest/m_ads_pltfrm.sql")
        public void getGetAuthorizationUrl() throws UnsupportedEncodingException {
            String encodedCallbackUrl = URLEncoder.encode(config.getMkpf().getPlatformAuthCallbackUrl(), StandardCharsets.UTF_8.toString());
            assertThat(facebookAdsService.getAuthorizationUrl()).isEqualTo(
                    "https://www.facebook.com/v12.0/dialog/oauth?response_type=code&display=popup&client_id=366926310726419&redirect_uri=" + encodedCallbackUrl
                            + "&auth_type=rerequest&scope=ads_management%2Cpublic_profile&state=2");
        }
    }

    @Nested
    public class GetAuthUserIdTest {

        @Autowired
        private FacebookAdsService facebookAdsService;

        @Disabled("Provide access token and comment this line")
        @Test
        public void testGetAuthUserId() {
            String accessToken = "";
            String userId = facebookAdsService.getAuthUserId(accessToken);
            System.out.println(userId);
        }
    }

    @Nested
    public class FetchAdAccountsTest {

        @MockBean
        private FacebookAdsService facebookAdsService;

        @Test
        public void testFetchAdAccounts() {
            ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
            apiAuthenticationInformation.setAdsPfLoginUserId("userId");
            apiAuthenticationInformation.setApiAuthenticationInformationSequence(1001L);
            apiAuthenticationInformation.setAdsPlatformSequence(2001L);
            apiAuthenticationInformation.setApiToken("{\"access_token\": \"Vd7GPAtz0l+im5mt/H81dw==\"}");
            facebookAdsService.apiAuthenticationInformationMapper = spy(ApiAuthenticationInformationMapper.class);
            facebookAdsService.config = config;
            ArgumentCaptor<String> adsPfLoginUserIdCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> accessTokenCaptor = ArgumentCaptor.forClass(String.class);
            doReturn(apiAuthenticationInformation).when(facebookAdsService.apiAuthenticationInformationMapper).findByAdsPlatformSequenceAndLoginUserId(Mockito.any(), Mockito.any());
            doReturn(new APINodeList<AdAccount>(null, null, null)).when(facebookAdsService).fetchAdAccounts(adsPfLoginUserIdCaptor.capture(), accessTokenCaptor.capture());
            doCallRealMethod().when(facebookAdsService).fetchAdAccounts(Mockito.anyString());
            List<PlatformAdAccountDto> list = facebookAdsService.fetchAdAccounts("123456789");
            assertThat(list.size()).isEqualTo(0);
            assertThat(adsPfLoginUserIdCaptor.getValue()).isEqualTo("123456789");
            assertThat(accessTokenCaptor.getValue()).isEqualTo("hoge");
        }
    }

    @Nested
    public class FetchAdAccountsManualTest {

        @Autowired
        private FacebookAdsService facebookAdsService;

        @Disabled("Provide account id and access token and comment this line")
        @Test
        public void testFetchAdAccounts() {
            String accountId = "";
            String accessToken = "";
            List<AdAccount> accounts = facebookAdsService.fetchAdAccounts(accountId, accessToken);
            for (AdAccount a : accounts) {
                System.out.println(a);
            }
        }
    }

    @Nested
    public class CreateCustomUserProvidedAudienceTest {

        @Autowired
        private FacebookAdsService facebookAdsService;

        @Disabled("Provide account id and access token and comment this line")
        @Test
        public void testCreateCustomUserProvidedAudience() {
            String adAccountId = "";
            String accessToken = "";
            CustomAudience audience = facebookAdsService.createCustomUserProvidedAudience(accessToken, adAccountId, "AudienceName", "Desc");
            System.out.println(audience);
        }
    }

    @Nested
    public class GetErrorMessageTest {

        @Autowired
        private FacebookAdsService facebookAdsService;

        @Test
        public void testGetErrorMessage() {
            String response = "{" +
                    "    \"error\": {" +
                    "        \"message\": \"Unsupported post request. Object with ID 'act_1111' does not exist\"," +
                    "        \"type\": \"GraphMethodException\"," +
                    "        \"code\": 100," +
                    "        \"error_subcode\": 33" +
                    "    }" +
                    "}";
            assertThat(facebookAdsService.getErrorMessage(response)).isEqualTo("Unsupported post request. Object with ID 'act_1111' does not exist");

            response = "{" +
                    "    \"errors\": {" +
                    "        \"message\": \"Unsupported post request. Object with ID 'act_' does not exist\"," +
                    "        \"type\": \"GraphMethodException\"," +
                    "        \"code\": 100," +
                    "        \"error_subcode\": 33" +
                    "    }" +
                    "}";
            assertThat(facebookAdsService.getErrorMessage(response)).isEqualTo(null);

            response = "{" +
                    "    \"error\": {" +
                    "        \"messageError\": \"Unsupported post request. Object with ID 'act_' does not exist\"," +
                    "        \"type\": \"GraphMethodException\"," +
                    "        \"code\": 100," +
                    "        \"error_subcode\": 33" +
                    "    }" +
                    "}";
            assertThat(facebookAdsService.getErrorMessage(response)).isEqualTo(null);

            response = "<html></html>";
            assertThat(facebookAdsService.getErrorMessage(response)).isEqualTo("<html></html>");
        }
    }
}
