package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class ApiAuthenticationInformationServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByAdsPlatformSequenceAndLoginUserIdTest {

        @Autowired
        public ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Test
        @Sql("/services/ApiAuthenticationInformationServiceTest/FindByAdsPlatformSequenceAndLoginUserIdTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            // Giving available param
            ApiAuthenticationInformation result = apiAuthenticationInformationService.findByAdsPlatformSequenceAndLoginUserId(3001L, "user_01");
            assertThat(result.getApiAuthenticationInformationSequence()).isEqualTo(2001L);
            assertThat(result.getAdsPlatformSequence()).isEqualTo(3001L);
            assertThat(result.getApiToken()).isEqualTo("{}");
            assertThat(result.getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(result.getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(result.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
            assertThat(result.getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
            assertThat(result.getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
            assertThat(result.getUpdateUserIdForAudit()).isEqualTo("user_01");

            // Giving not found param
            result = apiAuthenticationInformationService.findByAdsPlatformSequenceAndLoginUserId(3002L, "hoge");
            assertThat(result).isEqualTo(null);

            // Giving null param
            result = apiAuthenticationInformationService.findByAdsPlatformSequenceAndLoginUserId(3003L, null);
            assertThat(result).isEqualTo(null);
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/services/ApiAuthenticationInformationServiceTest/CreateTest/t_api_auth_info.sql")
        @Sql("/services/ApiAuthenticationInformationServiceTest/CreateTest/m_ads_pltfrm.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            apiAuthenticationInformationService.upsert(ADS_PLATFORM.GOOGLE_ADS , "{\"refresh_token\":\"refreshToken\"}", "userId", LocalDateTime.of(2021, 2, 2, 0, 0, 0), "user1");
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
            assertThat(resultList.get(1).getAdsPfLoginUserId()).isEqualTo("user_02");
            // Check created records
            assertThat(resultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getApiToken()).isEqualTo("{\"refresh_token\":\"refreshToken\"}");
            assertThat(resultList.get(2).getAdsPfLoginUserId()).isEqualTo("userId");
            assertThat(resultList.get(2).getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(resultList.get(2).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(resultList.get(2).getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(resultList.get(2).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(resultList.get(2).getUpdateUserIdForAudit()).isEqualTo("user1");

            // Duplicate user account
            apiAuthenticationInformationService.upsert(ADS_PLATFORM.GOOGLE_ADS , "{\"refresh_token\":\"updatedRefreshToken\"}", "userId", LocalDateTime.of(2022, 2, 2, 0, 0, 0), "user2");
            List<ApiAuthenticationInformation> updatedResultList = apiAuthenticationInformationMapper.findAll();
            assertThat(updatedResultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(updatedResultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(updatedResultList.get(0).getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(updatedResultList.get(0).getApiToken()).isEqualTo("{}");
            assertThat(updatedResultList.get(0).getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(updatedResultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(updatedResultList.get(1).getAdsPlatformSequence()).isEqualTo(2002L);
            assertThat(updatedResultList.get(1).getApiToken()).isEqualTo("{}");
            assertThat(updatedResultList.get(1).getAdsPfLoginUserId()).isEqualTo("user_02");
            // Check updated records
            assertThat(updatedResultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
            assertThat(updatedResultList.get(2).getApiToken()).isEqualTo("{\"refresh_token\":\"updatedRefreshToken\"}");
            assertThat(updatedResultList.get(2).getAdsPfLoginUserId()).isEqualTo("userId");
            assertThat(updatedResultList.get(2).getDeletionFlagForAudit()).isEqualTo("f");
            assertThat(updatedResultList.get(2).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(updatedResultList.get(2).getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(updatedResultList.get(2).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2022, 2, 2, 0, 0, 0));
            assertThat(updatedResultList.get(2).getUpdateUserIdForAudit()).isEqualTo("user2");
        }
    }

    @Nested
    public class FindByIdTest {

        @Autowired
        private ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Test
        @Sql("/services/ApiAuthenticationInformationServiceTest/FindByIdTest/t_api_auth_info.sql")
        public void expectingFindSuccess_givingManyCases() {
            ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationService.findById(1001L);
            assertThat(apiAuthenticationInformation.getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(apiAuthenticationInformation.getAdsPfLoginUserId()).isEqualTo("user_01");
            assertThat(apiAuthenticationInformation.getApiToken()).isEqualTo("{\"refresh_token\": \"refresh_token\"}");
            assertThat(apiAuthenticationInformation.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 01, 01, 0, 0, 1));
            assertThat(apiAuthenticationInformation.getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(apiAuthenticationInformation.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 01, 01, 0, 0, 3));
            assertThat(apiAuthenticationInformation.getUpdateUserIdForAudit()).isEqualTo("user1");

            apiAuthenticationInformation = apiAuthenticationInformationService.findById(1002L);
            assertThat(apiAuthenticationInformation.getAdsPlatformSequence()).isEqualTo(2002L);
            assertThat(apiAuthenticationInformation.getAdsPfLoginUserId()).isEqualTo("user_02");
            assertThat(apiAuthenticationInformation.getApiToken()).isEqualTo("{\"access_token\": \"access_token\"}");
            assertThat(apiAuthenticationInformation.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 1));
            assertThat(apiAuthenticationInformation.getCreateUserIdForAudit()).isEqualTo("user2");
            assertThat(apiAuthenticationInformation.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 3));
            assertThat(apiAuthenticationInformation.getUpdateUserIdForAudit()).isEqualTo("user2");

            // Not found
            apiAuthenticationInformation = apiAuthenticationInformationService.findById(1003L);
            assertThat(apiAuthenticationInformation).isEqualTo(null);

            // Null
            apiAuthenticationInformation = apiAuthenticationInformationService.findById(null);
            assertThat(apiAuthenticationInformation).isEqualTo(null);
        }
    }

    @Nested
    public class FindAllTest {

        @MockBean
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Autowired
        public ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Test
        public void expectingFindListSuccess() {
            ApiAuthenticationInformation api_1001 = new ApiAuthenticationInformation();
            api_1001.setApiAuthenticationInformationSequence(1001L);
            api_1001.setAdsPlatformSequence(2001L);
            ApiAuthenticationInformation api_1002 = new ApiAuthenticationInformation();
            api_1002.setApiAuthenticationInformationSequence(1002L);
            api_1002.setAdsPlatformSequence(2001L);
            ApiAuthenticationInformation api_1003 = new ApiAuthenticationInformation();
            api_1003.setApiAuthenticationInformationSequence(1003L);
            api_1003.setAdsPlatformSequence(2001L);
            List<ApiAuthenticationInformation> apiAuthenticationInformationList = Arrays.asList(api_1001, api_1002, api_1003);
            Mockito.doReturn(apiAuthenticationInformationList).when(apiAuthenticationInformationMapper).findAll();

            List<ApiAuthenticationInformation> resultList = apiAuthenticationInformationService.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class FindListByAdsPlatformSequenceTest {

        @MockBean
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Autowired
        public ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Test
        public void expectingFindListSuccess() {
            ApiAuthenticationInformation api_1001 = new ApiAuthenticationInformation();
            api_1001.setApiAuthenticationInformationSequence(1001L);
            api_1001.setAdsPlatformSequence(2001L);
            ApiAuthenticationInformation api_1002 = new ApiAuthenticationInformation();
            api_1002.setApiAuthenticationInformationSequence(1002L);
            api_1002.setAdsPlatformSequence(2001L);
            ApiAuthenticationInformation api_1003 = new ApiAuthenticationInformation();
            api_1003.setApiAuthenticationInformationSequence(1003L);
            api_1003.setAdsPlatformSequence(2001L);
            List<ApiAuthenticationInformation> apiAuthenticationInformationList = Arrays.asList(api_1001, api_1002, api_1003);
            Mockito.doReturn(apiAuthenticationInformationList).when(apiAuthenticationInformationMapper).findListByAdsPlatformSequence(Mockito.anyLong());

            List<ApiAuthenticationInformation> resultList = apiAuthenticationInformationService.findListByAdsPlatformSequence(2001L);
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getApiAuthenticationInformationSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getApiAuthenticationInformationSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getApiAuthenticationInformationSequence()).isEqualTo(1003L);
        }
    }
}
