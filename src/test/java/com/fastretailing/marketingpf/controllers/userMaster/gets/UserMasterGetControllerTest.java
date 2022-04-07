package com.fastretailing.marketingpf.controllers.userMaster.gets;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastretailing.marketingpf.domain.entities.UserMaster;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;

@AutoConfigureMockMvc(addFilters = false)
class UserMasterGetControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public UserMasterService UserMasterService;

    @Test
    public void expectingGetSuccess() throws Exception {
        UserMaster userMaster1 = new UserMaster();
        userMaster1.setUserId("1001");
        userMaster1.setUserFullName("User_Full_Name_1");
        userMaster1.setRoleSequenceList("{\"authority\": 1}");
        userMaster1.setDeletionFlagForAudit("f");
        userMaster1.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        userMaster1.setCreateUserIdForAudit("user_01");
        userMaster1.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        userMaster1.setDeletionUserIdForAudit("user_02");
        userMaster1.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        userMaster1.setUpdateUserIdForAudit("user_03");

        UserMaster userMaster2 = new UserMaster();
        userMaster2.setUserId("1002");
        userMaster2.setUserFullName("User_Full_Name_2");
        userMaster2.setRoleSequenceList("{\"authority\": 2}");
        userMaster2.setDeletionFlagForAudit("f");
        userMaster2.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        userMaster2.setCreateUserIdForAudit("user_01");
        userMaster2.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        userMaster2.setDeletionUserIdForAudit("user_02");
        userMaster2.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        userMaster2.setUpdateUserIdForAudit("user_03");

        UserMaster userMaster3 = new UserMaster();
        userMaster3.setUserId("1003");
        userMaster3.setUserFullName("User_Full_Name_3");
        userMaster3.setRoleSequenceList("{\"authority\": 3}");
        userMaster3.setDeletionFlagForAudit("f");
        userMaster3.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        userMaster3.setCreateUserIdForAudit("user_01");
        userMaster3.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        userMaster3.setDeletionUserIdForAudit("user_02");
        userMaster3.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        userMaster3.setUpdateUserIdForAudit("user_03");

        List<UserMaster> userMasterList = new ArrayList<>();
        userMasterList.add(userMaster1);
        userMasterList.add(userMaster2);
        userMasterList.add(userMaster3);

        Mockito.doReturn(userMasterList).when(UserMasterService).findAll();
        mvc.perform(get("/api/marketingpf/v1/fr/jp/users/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(json().contentEquals(
                "{"
                        + "\"userMasterList\":["
                        + "{"
                        + "\"userId\":\"1001\","
                        + "\"userFullName\":\"User_Full_Name_1\","
                        + "\"roleSequenceList\":\"{\\\"authority\\\": 1}\","
                        + "\"deletionFlagForAudit\":\"f\","
                        + "\"createDateTimeForAudit\":\"2021-01-01 01:01:01\","
                        + "\"createUserIdForAudit\":\"user_01\","
                        + "\"deletionDateTimeForAudit\":\"2021-02-02 02:02:02\","
                        + "\"deletionUserIdForAudit\":\"user_02\","
                        + "\"updateDateTimeForAudit\":\"2021-03-03 03:03:03\","
                        + "\"updateUserIdForAudit\":\"user_03\""
                        + "},"
                        + "{"
                        + "\"userId\":\"1002\","
                        + "\"userFullName\":\"User_Full_Name_2\","
                        + "\"roleSequenceList\":\"{\\\"authority\\\": 2}\","
                        + "\"deletionFlagForAudit\":\"f\","
                        + "\"createDateTimeForAudit\":\"2021-01-01 01:01:01\","
                        + "\"createUserIdForAudit\":\"user_01\","
                        + "\"deletionDateTimeForAudit\":\"2021-02-02 02:02:02\","
                        + "\"deletionUserIdForAudit\":\"user_02\","
                        + "\"updateDateTimeForAudit\":\"2021-03-03 03:03:03\","
                        + "\"updateUserIdForAudit\":\"user_03\""
                        + "},"
                        + "{"
                        + "\"userId\":\"1003\","
                        + "\"userFullName\":\"User_Full_Name_3\","
                        + "\"roleSequenceList\":\"{\\\"authority\\\": 3}\","
                        + "\"deletionFlagForAudit\":\"f\","
                        + "\"createDateTimeForAudit\":\"2021-01-01 01:01:01\","
                        + "\"createUserIdForAudit\":\"user_01\","
                        + "\"deletionDateTimeForAudit\":\"2021-02-02 02:02:02\","
                        + "\"deletionUserIdForAudit\":\"user_02\","
                        + "\"updateDateTimeForAudit\":\"2021-03-03 03:03:03\","
                        + "\"updateUserIdForAudit\":\"user_03\""
                        + "}"
                        + "]}"));
    }
}
