package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.mappers.AdsPlatformMasterMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class GoogleAdsServiceTest extends BaseSpringBootTest {

    @Nested
    public class GetAuthorizationUrlTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @MockBean
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Disabled("Provide client_id and client_secret and comment this line")
        @Test
        public void getGetAuthorizationUrl() {
            String clientId = "";
            String clientSecret = "";
            AdsPlatformMaster adsPlatformMaster = new AdsPlatformMaster();
            adsPlatformMaster.setAdsPlatformSequence(1L);
            adsPlatformMaster.setApiToken("{\n"
                    + "    \"client_id\": \"" + clientId + "\",\n"
                    + "    \"client_secret\": \"" + clientSecret + "\"\n"
                    + "}");
            doReturn(adsPlatformMaster).when(adsPlatformMasterMapper).findById(any());
            System.out.println(googleAdsService.getAuthorizationUrl());
        }
    }

    @Nested
    public class ExchangeCodeForRefreshTokenTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @MockBean
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Disabled("Provide code and comment this line")
        @Test
        public void testExchangeCodeForRefreshToken() {
            String clientId = "";
            String clientSecret = "";
            AdsPlatformMaster adsPlatformMaster = new AdsPlatformMaster();
            adsPlatformMaster.setAdsPlatformSequence(1L);
            adsPlatformMaster.setApiToken("{\n"
                    + "    \"client_id\": \"" + clientId + "\",\n"
                    + "    \"client_secret\": \"" + clientSecret + "\"\n"
                    + "}");
            doReturn(adsPlatformMaster).when(adsPlatformMasterMapper).findById(any());
            String code = "4/0AX4XfWi4R1jPXOqZPfFRmRjaSFR_WEH30ZS9toR4A8WRkdTpNtNTGr2ii24nOGBjpt8YGw";
            System.out.println(googleAdsService.exchangeCodeForRefreshToken(code));
        }
    }

    @Nested
    public class CreateAudienceTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @Disabled("Provide access token and comment this line")
        @Test
        public void testCreateAudience() {
            String developerToken = "";
            String clientId = "";
            String clientSecret = "";
            String refreshToken = "";
            Long loginCustomerId = 1001L;
            Long customerId = 1234567L;
            String audienceName = "1234567_MAIL";
            String description = "";

            String userId = googleAdsService.createAudience(
                    developerToken,
                    clientId,
                    clientSecret,
                    refreshToken,
                    loginCustomerId,
                    customerId,
                    audienceName,
                    description,
                    EXTRACTION_TARGET_ID.IDFA,
                    BRAND.UQ,
                    COUNTRY.JP);
            System.out.println(userId);
        }
    }

    @Nested
    public class FetchAdAccountsManualTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @Disabled("Provide access token and comment this line")
        @Test
        public void testFetchAdAccounts() {
            String developerToken = "";
            String clientId = "";
            String clientSecret = "";
            String refreshToken = "";
            for (PlatformAdAccountDto a : googleAdsService.fetchAdAccounts(developerToken, clientId, clientSecret, refreshToken)) {
                System.out.println(a.getLoginCustomerId() + " | " + a.getAdAccountId() + "->" + a.getAdAccountName());
            }
        }
    }

    @Nested
    public class FetchAdAccountsTest {

        @MockBean
        private GoogleAdsService googleAdsService;

        @Autowired
        private Config config;

        @MockBean
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Test
        public void testFetchAdAccounts() {
            AdsPlatformMaster googleAdsMaster = new AdsPlatformMaster();
            googleAdsMaster.setAdsPlatformName("Google Ads");
            googleAdsMaster.setAdsPlatformSequence(1L);
            googleAdsMaster.setApiToken("{\"client_id\": \"client_id\",\"client_secret\": \"client_secret\"}");
            googleAdsService.adsPlatformMasterMapper = adsPlatformMasterMapper;
            googleAdsService.config = config;
            doReturn(googleAdsMaster).when(adsPlatformMasterMapper).findById(any());
            doReturn(Arrays.asList(new PlatformAdAccountDto("adAccountId", "adAccountName"))).when(googleAdsService).fetchAdAccounts(any(), any(), any(), any());
            doCallRealMethod().when(googleAdsService).fetchAdAccounts(any());
            List<PlatformAdAccountDto> platformAdAccountDtoList = googleAdsService.fetchAdAccounts("Vd7GPAtz0l+im5mt/H81dw==");
            assertThat(platformAdAccountDtoList.size()).isEqualTo(1);
            assertThat(platformAdAccountDtoList.get(0).getAdAccountId()).isEqualTo("adAccountId");
            assertThat(platformAdAccountDtoList.get(0).getAdAccountName()).isEqualTo("adAccountName");
        }
    }

    @Nested
    public class GetLoginCustomerIdManualTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @Disabled("Provide access token and comment this line")
        @Test
        public void testGetLoginCustomerId() {
            String developerToken = "";
            String clientId = "";
            String clientSecret = "";
            String refreshToken = "";
            Long customerId = 6053564576L;
            Long loginCustomerId = googleAdsService.getLoginCustomerId(developerToken, clientId, clientSecret, refreshToken, customerId);
            System.out.println("loginCustomerId: " + loginCustomerId);
        }
    }

    @Nested
    public class GetLoginCustomerIdTest {

        @MockBean
        private GoogleAdsService googleAdsService;

        @MockBean
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Autowired
        private Config config;

        @Test
        public void testGetLoginCustomerId() {
            AdsPlatformMaster googleAdsMaster = new AdsPlatformMaster();
            googleAdsMaster.setAdsPlatformName("Google Ads");
            googleAdsMaster.setAdsPlatformSequence(1L);
            googleAdsMaster.setApiToken("{\"client_id\": \"client_id\",\"client_secret\": \"client_secret\"}");
            doReturn(googleAdsMaster).when(adsPlatformMasterMapper).findById(any());
            googleAdsService.adsPlatformMasterMapper = adsPlatformMasterMapper;
            googleAdsService.config = config;
            doReturn(1234567L).when(googleAdsService).getLoginCustomerId(any(), any(), any(), any(), anyLong());
            doCallRealMethod().when(googleAdsService).getLoginCustomerId(anyString(), anyLong());
            Long loginCustomerId = googleAdsService.getLoginCustomerId("Vd7GPAtz0l+im5mt/H81dw==", 22222L);
            assertThat(loginCustomerId).isEqualTo(1234567L);
        }
    }

    @Nested
    public class GetAuthUserIdTest {

        @MockBean
        private GoogleAdsService googleAdsService;

        @MockBean
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Test
        public void testGetAuthUserId() {
            AdsPlatformMaster adsPlatformMaster = new AdsPlatformMaster();
            adsPlatformMaster.setAdsPlatformName("Google Ads");
            adsPlatformMaster.setApiToken("{\"client_id\": \"client_id\", \"client_secret\": \"client_secret\", \"developer_token\":\"developer_token\"}");
            adsPlatformMaster.setAdsPlatformSequence(1L);
            googleAdsService.adsPlatformMasterMapper = adsPlatformMasterMapper;
            ArgumentCaptor<String> refreshTokenCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> clientSecretCaptor = ArgumentCaptor.forClass(String.class);
            doReturn(adsPlatformMaster).when(adsPlatformMasterMapper).findById(Mockito.any());
            doReturn("hoge@fuga.piyo").when(googleAdsService).getAuthUserId(clientIdCaptor.capture(), clientSecretCaptor.capture(), refreshTokenCaptor.capture());
            doCallRealMethod().when(googleAdsService).getAuthUserId(Mockito.anyString());

            String mail = googleAdsService.getAuthUserId("hoge_fuga");
            assertThat(mail).isEqualTo("hoge@fuga.piyo");
            assertThat(refreshTokenCaptor.getValue()).isEqualTo("hoge_fuga");
            assertThat(clientIdCaptor.getValue()).isEqualTo("client_id");
            assertThat(clientSecretCaptor.getValue()).isEqualTo("client_secret");
        }
    }

    @Nested
    public class GetAuthUserIdManualTest {

        @Autowired
        private GoogleAdsService googleAdsService;

        @Disabled("Provide access token and comment this line")
        @Test
        public void testGetAuthUserId() {
            String clientId = "";
            String clientSecret = "";
            String refreshToken = "";
            String userId = googleAdsService.getAuthUserId(clientId, clientSecret, refreshToken);
            System.out.println(userId);
        }
    }
}
