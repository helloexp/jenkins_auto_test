package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.list;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.tests.JsonAssert;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ApiAuthenticationInformationListResponseTest {

    @Test
    public void testApiAuthenticationInformationListResponse() {
        ApiAuthenticationInformation api_1001 = new ApiAuthenticationInformation();
        api_1001.setApiAuthenticationInformationSequence(1001L);
        api_1001.setAdsPlatformSequence(2001L);
        api_1001.setAdsPfLoginUserId("user_01");
        api_1001.setApiToken("token_01");
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

        ApiAuthenticationInformationListResponse response = new ApiAuthenticationInformationListResponse(apiAuthenticationInformationList);

        JsonAssert.assertJsonOutput(response).isSameContentAs(
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
                + "}");
    }
}
