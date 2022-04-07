package com.fastretailing.marketingpf.controllers.adAccount.search;

import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AdAccountSearchResponseTest {

    @Test
    public void expectingGetResponseSuccess_givingAvailableParams() {
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
        adAccountDto_02.setApiAuthenticationInformationSequence(3002L);
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

        List<AdAccountListDto> adAccountList = Arrays.asList(adAccountDto_01, adAccountDto_02);

        Map<Long, String> authSeqToAdsPfLoginUserIdMap = new HashMap<>();
        authSeqToAdsPfLoginUserIdMap.put(3001L, "adsPfLoginUser_01");
        authSeqToAdsPfLoginUserIdMap.put(3002L, "adsPfLoginUser_02");

        Map<String, String> userMasterMap = new HashMap<>();
        userMasterMap.put("user_01", "user_fullname_01");
        userMasterMap.put("user_02", "user_fullname_02");

        AdAccountSearchResponse response = new AdAccountSearchResponse(adAccountList, authSeqToAdsPfLoginUserIdMap, userMasterMap);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
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
                        + "            \"adsPfLoginUserId\": \"adsPfLoginUser_02\","
                        + "            \"adAccountSequence\": 1002,"
                        + "            \"adsPlatformAdAccountId\": \"ad_account_02\","
                        + "            \"adsPlatformSequence\": 2002,"
                        + "            \"apiAuthenticationInformationSequence\": 3002,"
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
                        + "}");
    }
}
