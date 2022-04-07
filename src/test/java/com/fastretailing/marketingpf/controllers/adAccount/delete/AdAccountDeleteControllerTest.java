package com.fastretailing.marketingpf.controllers.adAccount.delete;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class AdAccountDeleteControllerTest extends BaseSpringBootTest {

    @MockBean
    private AdAccountDeleteController adAccountDeleteController;

    @Autowired
    private MockMvc mvc;

    @Test
    public void expectingDeleteSuccess() throws Exception {
        AdAccount adAccount = new AdAccount();
        adAccount.setAdAccount(new AdAccountDto());
        adAccount.getAdAccount().setAdAccountSequence(1001L);
        adAccount.getAdAccount().setAdsPlatformAccountId("adsPlatformAccount_01");
        adAccount.getAdAccount().setAdsPlatformSequence(2001L);
        adAccount.getAdAccount().setApiAuthenticationInformationSequence(3001L);
        adAccount.getAdAccount().setBrandId("brand_01");
        adAccount.getAdAccount().setCountryId("country_01");
        adAccount.getAdAccount().setAccountName("account_01");
        adAccount.getAdAccount().setDeletionFlagForAudit("false");
        adAccount.getAdAccount().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        adAccount.getAdAccount().setCreateUserIdForAudit("user_01");
        adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 2, 2, 2));
        adAccount.getAdAccount().setDeletionUserIdForAudit("user_02");
        adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 3, 3, 3));
        adAccount.getAdAccount().setUpdateUserIdForAudit("user_03");
        AdAccountDeleteResponse response = new AdAccountDeleteResponse(adAccount);
        Mockito.doReturn(new ResponseEntity<Object>(response, HttpStatus.OK)).when(adAccountDeleteController).delete(Mockito.anyLong());

        mvc.perform(delete("/api/marketingpf/v1/fr/jp/ad_account/1001?loginUserId=user_02").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(""
                        + "{"
                            + "\"adAccountSequence\":1001,"
                            + "\"adsPlatformAccountId\":\"adsPlatformAccount_01\","
                            + "\"adsPlatformSequence\":2001,"
                            + "\"apiAuthenticationInformationSequence\":3001,"
                            + "\"brandId\":\"brand_01\","
                            + "\"countryId\":\"country_01\","
                            + "\"accountName\":\"account_01\","
                            + "\"deletionFlagForAudit\":\"false\","
                            + "\"createDateTimeForAudit\":\"2021-01-01 01:01:01\","
                            + "\"createUserIdForAudit\":\"user_01\","
                            + "\"deletionDateTimeForAudit\":\"2021-01-01 02:02:02\","
                            + "\"deletionUserIdForAudit\":\"user_02\","
                            + "\"updateDateTimeForAudit\":\"2021-01-01 03:03:03\","
                            + "\"updateUserIdForAudit\":\"user_03\""
                        + "}"));
    }
}
