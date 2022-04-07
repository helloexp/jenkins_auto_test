package com.fastretailing.marketingpf.controllers.adAccount.create;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AdAccountCreateResponseTest {

    @Test
    public void expectingCreateSuccess() throws Exception {
        AdAccount adAccount = new AdAccount();
        adAccount.setAdAccount(new AdAccountDto());
        adAccount.getAdAccount().setAdAccountSequence(1001L);
        adAccount.getAdAccount().setAdsPlatformAccountId("adsPlatformAccount_01");
        adAccount.getAdAccount().setAdsPlatformSequence(2001L);
        adAccount.getAdAccount().setApiAuthenticationInformationSequence(3001L);
        adAccount.getAdAccount().setBrandId("brand_01");
        adAccount.getAdAccount().setCountryId("country_01");
        adAccount.getAdAccount().setAccountName("account_01");
        adAccount.getAdAccount().setLoginCustomerId("1234567");
        adAccount.getAdAccount().setDeletionFlagForAudit("false");
        adAccount.getAdAccount().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        adAccount.getAdAccount().setCreateUserIdForAudit("user_01");
        adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 2, 2, 2));
        adAccount.getAdAccount().setDeletionUserIdForAudit("user_02");
        adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 3, 3, 3));
        adAccount.getAdAccount().setUpdateUserIdForAudit("user_03");

        AdAccountCreateResponse response = new AdAccountCreateResponse(adAccount);
        JsonAssert.assertJsonOutput(response).isSameContentAs("{"
                + "\"adAccountSequence\":1001,"
                + "\"adsPlatformAccountId\":\"adsPlatformAccount_01\","
                + "\"adsPlatformSequence\":2001,"
                + "\"apiAuthenticationInformationSequence\":3001,"
                + "\"brandId\":\"brand_01\","
                + "\"countryId\":\"country_01\","
                + "\"accountName\":\"account_01\","
                + "\"loginCustomerId\":\"1234567\","
                + "\"deletionFlagForAudit\":\"false\","
                + "\"createDateTimeForAudit\":\"2021-01-01 01:01:01\","
                + "\"createUserIdForAudit\":\"user_01\","
                + "\"deletionDateTimeForAudit\":\"2021-01-01 02:02:02\","
                + "\"deletionUserIdForAudit\":\"user_02\","
                + "\"updateDateTimeForAudit\":\"2021-01-01 03:03:03\","
                + "\"updateUserIdForAudit\":\"user_03\""
                + "}");
    }
}
