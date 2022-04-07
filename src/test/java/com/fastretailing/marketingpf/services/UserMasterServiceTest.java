package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.fastretailing.marketingpf.domain.entities.UserMaster;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;

class UserMasterServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {

        @Autowired
        private UserMasterService userMasterService;

        @Test
        @Sql("/services/UserMasterServiceTest/FindAllTest/data.sql")
        public void testFindAll() {
            List<UserMaster> resultList = userMasterService.findAll();
            assertThat(resultList.size()).isEqualTo(4);
            assertThat(resultList.get(0).getUserId()).isEqualTo("1001");
            assertThat(resultList.get(1).getUserId()).isEqualTo("1002");
            assertThat(resultList.get(2).getUserId()).isEqualTo("1004");
            assertThat(resultList.get(3).getUserId()).isEqualTo("1005");
        }
    }

    @Nested
    public class UpsertTest {

        @Autowired
        private UserMasterService userMasterService;

        @Test
        @Sql("/services/UserMasterServiceTest/UpsertTest/data.sql")
        public void testUpsert() {
            // Insert
            userMasterService.upsert("1006", "User_6", "{\"authority\": 6}", LocalDateTime.of(2022, 1, 2, 3, 4, 16));
            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1,{\"authority\": 1},f,2022-01-02 03:04:11,user_id_11,null,null,2022-01-02 03:04:13,user_id_13",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53",
                    "1006,User_6,{\"authority\": 6},f,2022-01-02 03:04:16,1006,null,null,2022-01-02 03:04:16,1006");
            // Update
            userMasterService.upsert("1001", "User_1_update", "{\"authority\": 1}", LocalDateTime.of(2022, 2, 2, 3, 4, 13));
            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1_update,{\"authority\": 1},f,2022-01-02 03:04:11,user_id_11,null,null,2022-02-02 03:04:13,1001",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53",
                    "1006,User_6,{\"authority\": 6},f,2022-01-02 03:04:16,1006,null,null,2022-01-02 03:04:16,1006");

            // Update twice is ok
            userMasterService.upsert("1002", "User_2_update", "{\"authority\": 2}", LocalDateTime.of(2022, 2, 2, 3, 4, 23));
            userMasterService.upsert("1002", "User_2_update", "{\"authority\": 2}", LocalDateTime.of(2022, 2, 2, 3, 4, 23));
            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1_update,{\"authority\": 1},f,2022-01-02 03:04:11,user_id_11,null,null,2022-02-02 03:04:13,1001",
                    "1002,User_2_update,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-02-02 03:04:23,1002",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53",
                    "1006,User_6,{\"authority\": 6},f,2022-01-02 03:04:16,1006,null,null,2022-01-02 03:04:16,1006");
        }
    }
}
