package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class ApiAuthenticationInformationMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/FindAllTest/data.sql")
        public void expectingFindListSuccess() {
            List<ApiAuthenticationInformation> resultList = apiAuthenticationInformationMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(resultList.get(0).getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(resultList.get(0).getApiToken()).isEqualTo("{}");
            assertThat(resultList.get(0).getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(resultList.get(0).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            ApiAuthenticationInformation authentication_01 = new ApiAuthenticationInformation();
            authentication_01.setApiToken("{}");
            authentication_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            authentication_01.setCreateUserIdForAudit("user_02");
            authentication_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            authentication_01.setUpdateUserIdForAudit("user_02");
            apiAuthenticationInformationMapper.upsert(authentication_01);

            List<ApiAuthenticationInformation> resultList = apiAuthenticationInformationMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(resultList.get(0).getApiToken()).isEqualTo("{}");
            assertThat(resultList.get(0).getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getAdsPlatformSequence()).isEqualTo(2002L);
            assertThat(resultList.get(1).getApiToken()).isEqualTo("{}");
            assertThat(resultList.get(1).getAdsPfLoginUserId()).isEqualTo("user_01");
            // Check created records
            assertThat(resultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getApiToken()).isEqualTo("{}");
            assertThat(resultList.get(2).getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(resultList.get(2).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(resultList.get(2).getCreateUserIdForAudit()).isEqualTo("user_02");
            assertThat(resultList.get(2).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(resultList.get(2).getUpdateUserIdForAudit()).isEqualTo("user_02");
        }
    }

    @Nested
    public class FindByAdsPlatformSequenceAndLoginUserIdTest {

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/FindByAdsPlatformSequenceAndLoginUserIdTest/data.sql")
        public void expectingFindSuccess_givingManyCases() {
            // Giving available loginUserId
            ApiAuthenticationInformation result = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(2001L, "user_01");
            assertThat(result.getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(result.getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(result.getApiToken()).isEqualTo("{}");
            assertThat(result.getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(result.getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(result.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
            assertThat(result.getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
            assertThat(result.getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
            assertThat(result.getUpdateUserIdForAudit()).isEqualTo("user_01");

            // Giving not found loginUserId
            result = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(2002L, "hogehoge");
            assertThat(result).isEqualTo(null);

            // Giving not found adsPlatformSequence
            result = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(9999L, "user_02");
            assertThat(result).isEqualTo(null);

            // Giving null loginUserId
            result = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(2001L, null);
            assertThat(result).isEqualTo(null);

            // Giving null adsPlatformSequence
            result = apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(null, "user_02");
            assertThat(result).isEqualTo(null);
        }
    }

    @Nested
    public class FindByIdTest {

        @Autowired
        private ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/FindByIdTest/t_api_auth_info.sql")
        public void expectingFindSuccess_givingManyCases() {
            ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(1001L);
            assertThat(apiAuthenticationInformation.getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(apiAuthenticationInformation.getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(apiAuthenticationInformation.getApiToken()).isEqualTo("{\"refresh_token\": \"refresh_token\"}");
            assertThat(apiAuthenticationInformation.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 01, 01, 0, 0, 1));
            assertThat(apiAuthenticationInformation.getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(apiAuthenticationInformation.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 01, 01, 0, 0, 3));
            assertThat(apiAuthenticationInformation.getUpdateUserIdForAudit()).isEqualTo("user1");

            apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(1002L);
            assertThat(apiAuthenticationInformation.getAdsPlatformSequence()).isEqualTo(2002L);
            assertThat(apiAuthenticationInformation.getAdsPfLoginUserId()).isEqualTo("user_02");
            assertThat(apiAuthenticationInformation.getApiToken()).isEqualTo("{\"access_token\": \"access_token\"}");
            assertThat(apiAuthenticationInformation.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 1));
            assertThat(apiAuthenticationInformation.getCreateUserIdForAudit()).isEqualTo("user2");
            assertThat(apiAuthenticationInformation.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 3));
            assertThat(apiAuthenticationInformation.getUpdateUserIdForAudit()).isEqualTo("user2");

            // Not found
            apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(1003L);
            assertThat(apiAuthenticationInformation).isEqualTo(null);

            // Null
            apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(null);
            assertThat(apiAuthenticationInformation).isEqualTo(null);
        }
    }

    @Nested
    public class FindListByAdsPlatformSequenceTest {

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/FindListByAdsPlatformSequenceTest/data.sql")
        public void expectingFindSuccess_givingManyCases() {
            List<ApiAuthenticationInformation> resultList = apiAuthenticationInformationMapper.findListByAdsPlatformSequence(2001L);
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1003L);

            resultList = apiAuthenticationInformationMapper.findListByAdsPlatformSequence(2002L);
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1004L);

            resultList = apiAuthenticationInformationMapper.findListByAdsPlatformSequence(9999L);
            assertThat(resultList.size()).isEqualTo(0);
        }
    }

    @Nested
    public class FindByAdsPfLoginUserIdTest {

        @Autowired
        private ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/domain/mappers/ApiAuthenticationInformationMapperTest/FindByAdsPfLoginUserIdTest/t_api_auth_info.sql")
        public void testFindByAdsPfLoginUserId() {
            {
                List<ApiAuthenticationInformation> result = apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest( "ad01");
                assertThat(result.size()).isEqualTo(2);
                assertThat(result.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
                assertThat(result.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1005L);
            }
            {
                List<ApiAuthenticationInformation> result = apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest("ad02");
                assertThat(result.size()).isEqualTo(1);
                assertThat(result.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
            }
            {
                List<ApiAuthenticationInformation> result = apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest("ad04");
                assertThat(result.size()).isEqualTo(2);
                assertThat(result.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1004L);
                assertThat(result.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1006L);
            }
            assertThat(apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest("ad03")).isEmpty();
            assertThat(apiAuthenticationInformationMapper.findByAdsPfLoginUserIdTest("ad08")).isEmpty();
        }
    }
}
