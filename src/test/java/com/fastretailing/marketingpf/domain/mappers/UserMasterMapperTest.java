package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.UserMaster;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class UserMasterMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {

        @Autowired
        private UserMasterMapper userMasterMapper;

        @Test
        @Sql("/domain/mappers/UserMasterMapperTest/FindAllTest/data.sql")
        public void testFindAll() {
            List<UserMaster> resultList = userMasterMapper.findAll();
            assertThat(resultList.size()).isEqualTo(4);
            assertThat(resultList.get(0).getUserId()).isEqualTo("1001");
            assertThat(resultList.get(1).getUserId()).isEqualTo("1002");
            assertThat(resultList.get(2).getUserId()).isEqualTo("1004");
            assertThat(resultList.get(3).getUserId()).isEqualTo("1005");
        }
    }

    @Nested
    public class FindByIdTest {

        @Autowired
        private UserMasterMapper userMasterMapper;

        @Test
        @Sql("/domain/mappers/UserMasterMapperTest/FindByIdTest/data.sql")
        public void testFindById() {
            UserMaster u1002 = userMasterMapper.findById("1002");
            assertThat(u1002.getUserId()).isEqualTo("1002");
            assertThat(u1002.getUserFullName()).isEqualTo("User_2");
            assertThat(u1002.getRoleSequenceList()).isEqualTo("{\"authority\": 2}");
            assertThat(u1002.getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(u1002.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2022, 1, 2, 3, 4, 21));
            assertThat(u1002.getCreateUserIdForAudit()).isEqualTo("user_id_21");
            assertThat(u1002.getDeletionDateTimeForAudit()).isNull();
            assertThat(u1002.getDeletionUserIdForAudit()).isNull();
            assertThat(u1002.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2022, 1, 2, 3, 4, 23));
            assertThat(u1002.getUpdateUserIdForAudit()).isEqualTo("user_id_23");

            UserMaster u1005 = userMasterMapper.findById("1005");
            assertThat(u1005.getUserId()).isEqualTo("1005");
            assertThat(u1005.getUserFullName()).isEqualTo("User_5");
            assertThat(u1005.getRoleSequenceList()).isEqualTo("{\"authority\": 5}");
            assertThat(u1005.getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(u1005.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2022, 1, 2, 3, 4, 51));
            assertThat(u1005.getCreateUserIdForAudit()).isEqualTo("user_id_51");
            assertThat(u1005.getDeletionDateTimeForAudit()).isNull();
            assertThat(u1005.getDeletionUserIdForAudit()).isNull();
            assertThat(u1005.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2022, 1, 2, 3, 4, 53));
            assertThat(u1005.getUpdateUserIdForAudit()).isEqualTo("user_id_53");

            assertThat(userMasterMapper.findById("1003")).isNull();
            assertThat(userMasterMapper.findById("0")).isNull();
            assertThat(userMasterMapper.findById(null)).isNull();
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        private UserMasterMapper userMasterMapper;

        @Test
        @Sql("/domain/mappers/UserMasterMapperTest/CreateTest/data.sql")
        public void testCreate() {
            UserMaster hoge = new UserMaster();
            hoge.setUserId("hoge");
            hoge.setUserFullName("Mr hoge");
            hoge.setRoleSequenceList("{\"authority\": \"hoge\"}");
            hoge.setAuditInfoForCreation("0", LocalDateTime.of(2022, 1, 2, 3, 4, 5));
            userMasterMapper.create(hoge);

            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1,{\"authority\": 1},f,2022-01-02 03:04:11,user_id_11,null,null,2022-01-02 03:04:13,user_id_13",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53",
                    "hoge,Mr hoge,{\"authority\": \"hoge\"},f,2022-01-02 03:04:05,0,null,null,2022-01-02 03:04:05,0");

            UserMaster fuga = new UserMaster();
            fuga.setUserId("fuga");
            fuga.setUserFullName("Mr fuga");
            fuga.setRoleSequenceList("{\"authority\": \"fuga\"}");
            fuga.setAuditInfoForCreation("user_fuga", LocalDateTime.of(2022, 1, 2, 3, 4, 6));
            userMasterMapper.create(fuga);

            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1,{\"authority\": 1},f,2022-01-02 03:04:11,user_id_11,null,null,2022-01-02 03:04:13,user_id_13",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53",
                    "hoge,Mr hoge,{\"authority\": \"hoge\"},f,2022-01-02 03:04:05,0,null,null,2022-01-02 03:04:05,0",
                    "fuga,Mr fuga,{\"authority\": \"fuga\"},f,2022-01-02 03:04:06,user_fuga,null,null,2022-01-02 03:04:06,user_fuga");
        }
    }

    @Nested
    public class UpdateTest {

        @Autowired
        private UserMasterMapper userMasterMapper;

        @Test
        @Sql("/domain/mappers/UserMasterMapperTest/UpdateTest/data.sql")
        public void testUpdate() {
            UserMaster u1001 = new UserMaster();
            u1001.setUserId("1001");
            u1001.setUserFullName("User_1_update");
            u1001.setRoleSequenceList("{\"authority\": \"1_update\"}");
            u1001.setAuditInfoForUpdate("user_id_13_update", LocalDateTime.of(2022, 2, 2, 3, 4, 5));
            userMasterMapper.update(u1001);

            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1_update,{\"authority\": \"1_update\"},f,2022-01-02 03:04:11,user_id_11,null,null,2022-02-02 03:04:05,user_id_13_update",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5,{\"authority\": 5},f,2022-01-02 03:04:51,user_id_51,null,null,2022-01-02 03:04:53,user_id_53");

            UserMaster u1005 = new UserMaster();
            u1005.setUserId("1005");
            u1005.setUserFullName("User_5_update");
            u1005.setRoleSequenceList("{\"authority\": \"5_update\"}");
            u1005.setAuditInfoForUpdate("user_id_53_update", LocalDateTime.of(2022, 2, 2, 3, 4, 6));
            userMasterMapper.update(u1005);

            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1_update,{\"authority\": \"1_update\"},f,2022-01-02 03:04:11,user_id_11,null,null,2022-02-02 03:04:05,user_id_13_update",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5_update,{\"authority\": \"5_update\"},f,2022-01-02 03:04:51,user_id_51,null,null,2022-02-02 03:04:06,user_id_53_update");

            UserMaster u1003 = new UserMaster();
            u1003.setUserId("1003");
            u1003.setUserFullName("User_3_update");
            u1003.setRoleSequenceList("{\"authority\": \"3_update\"}");
            u1003.setAuditInfoForUpdate("user_id_33_update", LocalDateTime.of(2022, 3, 2, 3, 4, 6));
            userMasterMapper.update(u1003);

            UserMaster u9999 = new UserMaster();
            u9999.setUserId("9999");
            userMasterMapper.update(u9999);

            assertQueryResult("SELECT * FROM m_user ORDER BY user_id ASC").isSameDataAs(
                    "1001,User_1_update,{\"authority\": \"1_update\"},f,2022-01-02 03:04:11,user_id_11,null,null,2022-02-02 03:04:05,user_id_13_update",
                    "1002,User_2,{\"authority\": 2},f,2022-01-02 03:04:21,user_id_21,null,null,2022-01-02 03:04:23,user_id_23",
                    "1003,User_3,{\"authority\": 3},t,2022-01-02 03:04:31,user_id_31,2022-01-02 03:04:32,user_id_32,2022-01-02 03:04:33,user_id_33",
                    "1004,User_4,{\"authority\": 4},f,2022-01-02 03:04:41,user_id_41,null,null,2022-01-02 03:04:43,user_id_43",
                    "1005,User_5_update,{\"authority\": \"5_update\"},f,2022-01-02 03:04:51,user_id_51,null,null,2022-02-02 03:04:06,user_id_53_update");
        }
    }
}
