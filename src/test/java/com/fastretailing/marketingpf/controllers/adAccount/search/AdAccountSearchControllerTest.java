package com.fastretailing.marketingpf.controllers.adAccount.search;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class AdAccountSearchControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @MockBean
    public AdAccountService adAccountService;

    @MockBean
    public UserMasterService userMasterService;

    @Test
    public void expectingSearchSuccess() throws Exception {
        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setApiAuthenticationInformationSequence(3001L);
        apiAuthenticationInformation.setAdsPfLoginUserId("user_01");
        Mockito.doReturn(apiAuthenticationInformation).when(apiAuthenticationInformationService).findByAdsPlatformSequenceAndLoginUserId(Mockito.any(), Mockito.any());

        ApiAuthenticationInformation apiAuthenticationInformation_01 = new ApiAuthenticationInformation();
        apiAuthenticationInformation_01.setAdsPfLoginUserId("adsPfLoginUser_01");
        apiAuthenticationInformation_01.setAdsPlatformSequence(2001L);
        apiAuthenticationInformation_01.setApiAuthenticationInformationSequence(3001L);
        Mockito.doReturn(apiAuthenticationInformation_01).when(apiAuthenticationInformationService).findById(Mockito.any());

        AdAccountListDto adAccountDto_01 = new AdAccountListDto();
        adAccountDto_01.setAdAccountSequence(1001L);
        adAccountDto_01.setAdsPlatformAdAccountId("ad_account_01");
        adAccountDto_01.setAdsPlatformSequence(2001L);
        adAccountDto_01.setApiAuthenticationInformationSequence(3001L);
        adAccountDto_01.setBrandId("Brand_01");
        adAccountDto_01.setCountryId("Country_01");
        adAccountDto_01.setAccountName("Account_01");
        adAccountDto_01.setLoginCustomerId("customer_01");
        adAccountDto_01.setDeletionFlagForAudit("f");
        adAccountDto_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        adAccountDto_01.setCreateUserIdForAudit("user_01");
        adAccountDto_01.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        adAccountDto_01.setDeletionUserIdForAudit("user_01");
        adAccountDto_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        adAccountDto_01.setUpdateUserIdForAudit("user_01");
        AdAccountListDto adAccountDto_02 = new AdAccountListDto();
        adAccountDto_02.setAdAccountSequence(1002L);
        adAccountDto_02.setAdsPlatformAdAccountId("ad_account_02");
        adAccountDto_02.setAdsPlatformSequence(2002L);
        adAccountDto_02.setApiAuthenticationInformationSequence(3001L);
        adAccountDto_02.setBrandId("Brand_02");
        adAccountDto_02.setCountryId("Country_02");
        adAccountDto_02.setAccountName("Account_02");
        adAccountDto_02.setLoginCustomerId("customer_02");
        adAccountDto_02.setDeletionFlagForAudit("f");
        adAccountDto_02.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        adAccountDto_02.setCreateUserIdForAudit("user_02");
        adAccountDto_02.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        adAccountDto_02.setDeletionUserIdForAudit("user_02");
        adAccountDto_02.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        adAccountDto_02.setUpdateUserIdForAudit("user_02");

        Mockito.doReturn(Arrays.asList(adAccountDto_01, adAccountDto_02)).when(adAccountService).searchWithAdsPfLoginUserId(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        Map<String, String> userMasterMap = new HashMap<String, String>();
        userMasterMap.put("user_01", "user_fullname_01");
        userMasterMap.put("user_02", "user_fullname_02");
        Mockito.doReturn(userMasterMap).when(userMasterService).getUserIdToUserNameMap();

        mvc.perform(get("/api/marketingpf/v1/fr/jp/ad_accounts/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"adAccountList\": [{"
                                + "            \"adsPfLoginUserId\": \"adsPfLoginUser_01\","
                                + "            \"adAccountSequence\": 1001,"
                                + "            \"adsPlatformAdAccountId\": \"ad_account_01\","
                                + "            \"adsPlatformSequence\": 2001,"
                                + "            \"apiAuthenticationInformationSequence\": 3001,"
                                + "            \"brandId\": \"Brand_01\","
                                + "            \"countryId\": \"Country_01\","
                                + "            \"accountName\": \"Account_01\","
                                + "            \"loginCustomerId\": \"customer_01\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 01:01:01\","
                                + "            \"createUserIdForAudit\": \"user_fullname_01\","
                                + "            \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_01\""
                                + "        }, {"
                                + "            \"adsPfLoginUserId\": \"adsPfLoginUser_01\","
                                + "            \"adAccountSequence\": 1002,"
                                + "            \"adsPlatformAdAccountId\": \"ad_account_02\","
                                + "            \"adsPlatformSequence\": 2002,"
                                + "            \"apiAuthenticationInformationSequence\": 3001,"
                                + "            \"brandId\": \"Brand_02\","
                                + "            \"countryId\": \"Country_02\","
                                + "            \"accountName\": \"Account_02\","
                                + "            \"loginCustomerId\": \"customer_02\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 01:01:01\","
                                + "            \"createUserIdForAudit\": \"user_fullname_02\","
                                + "            \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                                + "            \"updateUserIdForAudit\": \"user_fullname_02\""
                                + "        }"
                                + "    ]"
                                + "}"));
    }
}
