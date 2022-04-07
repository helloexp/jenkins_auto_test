package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.list;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class ApiAuthenticationInformationListControllerTest extends BaseSpringBootTest {

    @MockBean
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public MockMvc mvc;

    @Test
    public void expectingGetListSuccess() throws Exception {
        ApiAuthenticationInformation api_1001 = new ApiAuthenticationInformation();
        api_1001.setApiAuthenticationInformationSequence(1001L);
        api_1001.setAdsPlatformSequence(2001L);
        api_1001.setApiToken("token_01");
        api_1001.setAdsPfLoginUserId("user_01");
        api_1001.setDeletionFlagForAudit("f");
        api_1001.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        api_1001.setCreateUserIdForAudit("user_01");
        api_1001.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        api_1001.setUpdateUserIdForAudit("user_01");
        ApiAuthenticationInformation api_1002 = new ApiAuthenticationInformation();
        api_1002.setApiAuthenticationInformationSequence(1002L);
        api_1002.setAdsPlatformSequence(2001L);
        api_1002.setApiToken("token_02");
        api_1002.setAdsPfLoginUserId("user_02");
        api_1002.setDeletionFlagForAudit("f");
        api_1002.setCreateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        api_1002.setCreateUserIdForAudit("user_02");
        api_1002.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
        api_1002.setUpdateUserIdForAudit("user_02");
        ApiAuthenticationInformation api_1003 = new ApiAuthenticationInformation();
        api_1003.setApiAuthenticationInformationSequence(1003L);
        api_1003.setAdsPlatformSequence(2001L);
        api_1003.setApiToken("token_03");
        api_1003.setAdsPfLoginUserId("user_03");
        api_1003.setDeletionFlagForAudit("f");
        api_1003.setCreateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        api_1003.setCreateUserIdForAudit("user_03");
        api_1003.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
        api_1003.setUpdateUserIdForAudit("user_03");
        List<ApiAuthenticationInformation> apiAuthenticationInformationList = Arrays.asList(api_1001, api_1002, api_1003);
        Mockito.doReturn(apiAuthenticationInformationList).when(apiAuthenticationInformationService).findListByAdsPlatformSequence(Mockito.anyLong());

        mvc.perform(get("/api/marketingpf/v1/fr/jp/auth/api_auth_info_list/?adsPlatformSequence=2001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"apiAuthenticationInformationList\": [{"
                                + "            \"apiAuthenticationInformationSequence\": 1001,"
                                + "            \"adsPlatformSequence\": 2001,"
                                + "            \"adsPfLoginUserId\": \"user_01\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"createUserIdForAudit\": \"user_01\","
                                + "            \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_01\""
                                + "        }, {"
                                + "            \"apiAuthenticationInformationSequence\": 1002,"
                                + "            \"adsPlatformSequence\": 2001,"
                                + "            \"adsPfLoginUserId\": \"user_02\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-02-02 00:00:00\","
                                + "            \"createUserIdForAudit\": \"user_02\","
                                + "            \"updateDateTimeForAudit\": \"2021-02-02 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_02\""
                                + "        }, {"
                                + "            \"apiAuthenticationInformationSequence\": 1003,"
                                + "            \"adsPlatformSequence\": 2001,"
                                + "            \"adsPfLoginUserId\": \"user_03\","
                                + "            \"deletionFlagForAudit\": \"f\","
                                + "            \"createDateTimeForAudit\": \"2021-03-03 00:00:00\","
                                + "            \"createUserIdForAudit\": \"user_03\","
                                + "            \"updateDateTimeForAudit\": \"2021-03-03 00:00:00\","
                                + "            \"updateUserIdForAudit\": \"user_03\""
                                + "        }"
                                + "    ]"
                                + "}"));
    }
}
