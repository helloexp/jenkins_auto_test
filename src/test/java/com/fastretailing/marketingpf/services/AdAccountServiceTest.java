package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class AdAccountServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @MockBean
        private AdAccountService adAccountService;

        @Test
        public void expectingFindSuccess() {
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
            adAccount.getAdAccount().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setCreateUserIdForAudit("user_01");
            adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setDeletionUserIdForAudit("user_01");
            adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setUpdateUserIdForAudit("user_01");
            Mockito.doReturn(adAccount).when(adAccountService).findById(Mockito.anyLong());

            AdAccount result = adAccountService.findById(1001L);
            assertThat(result.getAdAccount().getAdAccountSequence()).isEqualTo(1001L);
            assertThat(result.getAdAccount().getAdsPlatformAccountId()).isEqualTo("adsPlatformAccount_01");
            assertThat(result.getAdAccount().getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(result.getAdAccount().getApiAuthenticationInformationSequence()).isEqualTo(3001L);
            assertThat(result.getAdAccount().getBrandId()).isEqualTo("brand_01");
            assertThat(result.getAdAccount().getCountryId()).isEqualTo("country_01");
            assertThat(result.getAdAccount().getAccountName()).isEqualTo("account_01");
            assertThat(result.getAdAccount().getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getAdAccount().getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getUpdateUserIdForAudit()).isEqualTo("user_01");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(adAccountService).findById(Mockito.anyLong());

            try {
                adAccountService.findById(1002L);
                fail("");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class CreateTest {

        @MockBean
        private AdAccountService adAccountService;

        @Test
        public void expectingCreateSuccess() {
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
            adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            adAccount.getAdAccount().setDeletionUserIdForAudit("user_02");
            adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            adAccount.getAdAccount().setUpdateUserIdForAudit("user_03");
            Mockito.doReturn(adAccount).when(adAccountService).create(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                    Mockito.anyLong(), Mockito.anyString());

            AdAccount result = adAccountService.create("adsPlatformAccount_01", 2001L, 3001L, "brand_01", "country_01", "account_01", 1234567L, "user_03");
            assertThat(result.getAdAccount().getAdAccountSequence()).isEqualTo(1001L);
            assertThat(result.getAdAccount().getAdsPlatformAccountId()).isEqualTo("adsPlatformAccount_01");
            assertThat(result.getAdAccount().getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(result.getAdAccount().getApiAuthenticationInformationSequence()).isEqualTo(3001L);
            assertThat(result.getAdAccount().getBrandId()).isEqualTo("brand_01");
            assertThat(result.getAdAccount().getCountryId()).isEqualTo("country_01");
            assertThat(result.getAdAccount().getAccountName()).isEqualTo("account_01");
            assertThat(result.getAdAccount().getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getAdAccount().getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            assertThat(result.getAdAccount().getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            assertThat(result.getAdAccount().getDeletionUserIdForAudit()).isEqualTo("user_02");
            assertThat(result.getAdAccount().getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            assertThat(result.getAdAccount().getUpdateUserIdForAudit()).isEqualTo("user_03");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(adAccountService).create(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
                    Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());

            try {
                adAccountService.create("adsPlatformAccount_01", 2001L, 3001L, "brand_01", "country_01", "account_01", 1234567L, "user_03");
                fail("");
            } catch (MkdbApiUpstreamFailureException ignored) {
            }
        }
    }

    @Nested
    public class UpdateTest {

        @MockBean
        private AdAccountService adAccountService;

        @Test
        public void expectingUpdateSuccess() {
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
            adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            adAccount.getAdAccount().setDeletionUserIdForAudit("user_02");
            adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            adAccount.getAdAccount().setUpdateUserIdForAudit("user_03");
            Mockito.doReturn(adAccount).when(adAccountService).update(
                    Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

            AdAccount result = adAccountService.update(1001L, "brand_01", "country_01", "user_03");
            assertThat(result.getAdAccount().getAdAccountSequence()).isEqualTo(1001L);
            assertThat(result.getAdAccount().getAdsPlatformAccountId()).isEqualTo("adsPlatformAccount_01");
            assertThat(result.getAdAccount().getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(result.getAdAccount().getApiAuthenticationInformationSequence()).isEqualTo(3001L);
            assertThat(result.getAdAccount().getBrandId()).isEqualTo("brand_01");
            assertThat(result.getAdAccount().getCountryId()).isEqualTo("country_01");
            assertThat(result.getAdAccount().getAccountName()).isEqualTo("account_01");
            assertThat(result.getAdAccount().getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getAdAccount().getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
            assertThat(result.getAdAccount().getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
            assertThat(result.getAdAccount().getDeletionUserIdForAudit()).isEqualTo("user_02");
            assertThat(result.getAdAccount().getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
            assertThat(result.getAdAccount().getUpdateUserIdForAudit()).isEqualTo("user_03");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(adAccountService).update(
                    Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

            try {
                adAccountService.update(1001L, "brand_01", "country_01", "user_03");
                fail("");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class DeleteTest {

        @MockBean
        private AdAccountService adAccountService;

        @Test
        public void expectingFindSuccess() {
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
            adAccount.getAdAccount().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setCreateUserIdForAudit("user_01");
            adAccount.getAdAccount().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setDeletionUserIdForAudit("user_01");
            adAccount.getAdAccount().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            adAccount.getAdAccount().setUpdateUserIdForAudit("user_01");
            Mockito.doReturn(adAccount).when(adAccountService).delete(Mockito.anyLong(), Mockito.anyString());

            AdAccount result = adAccountService.delete(1001L, "user_01");
            assertThat(result.getAdAccount().getAdAccountSequence()).isEqualTo(1001L);
            assertThat(result.getAdAccount().getAdsPlatformAccountId()).isEqualTo("adsPlatformAccount_01");
            assertThat(result.getAdAccount().getAdsPlatformSequence()).isEqualTo(2001L);
            assertThat(result.getAdAccount().getApiAuthenticationInformationSequence()).isEqualTo(3001L);
            assertThat(result.getAdAccount().getBrandId()).isEqualTo("brand_01");
            assertThat(result.getAdAccount().getCountryId()).isEqualTo("country_01");
            assertThat(result.getAdAccount().getAccountName()).isEqualTo("account_01");
            assertThat(result.getAdAccount().getDeletionFlagForAudit()).isEqualTo("false");
            assertThat(result.getAdAccount().getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getCreateUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getDeletionUserIdForAudit()).isEqualTo("user_01");
            assertThat(result.getAdAccount().getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getAdAccount().getUpdateUserIdForAudit()).isEqualTo("user_01");
        }

        @Test
        public void expectingMkdbApiUpstreamFailureException() {
            Mockito.doThrow(MkdbApiUpstreamFailureException.class).when(adAccountService).delete(Mockito.anyLong(), Mockito.anyString());

            try {
                adAccountService.delete(1001L, "user_01");
                fail("");
            } catch (MkdbApiUpstreamFailureException e) {
            }
        }
    }

    @Nested
    public class SearchTest {

        @MockBean
        public AdAccountService adAccountService;

        @Test
        public void expectingSearchSuccess() {
            AdAccountListDto adAccountDto_01 = new AdAccountListDto();
            adAccountDto_01.setAdAccountSequence(1001L);
            adAccountDto_01.setAdsPlatformAdAccountId("adsPlatformAccount_01");
            adAccountDto_01.setAdsPlatformSequence(2001L);
            adAccountDto_01.setApiAuthenticationInformationSequence(3001L);

            AdAccountListDto adAccountDto_02 = new AdAccountListDto();
            adAccountDto_02.setAdAccountSequence(1002L);
            adAccountDto_02.setAdsPlatformAdAccountId("adsPlatformAccount_02");
            adAccountDto_02.setAdsPlatformSequence(2001L);
            adAccountDto_02.setApiAuthenticationInformationSequence(3002L);

            AdAccountListDto adAccountDto_03 = new AdAccountListDto();
            adAccountDto_03.setAdAccountSequence(1003L);
            adAccountDto_03.setAdsPlatformAdAccountId("adsPlatformAccount_03");
            adAccountDto_03.setAdsPlatformSequence(2001L);
            adAccountDto_03.setApiAuthenticationInformationSequence(3003L);

            AdAccountList adAccountList = new AdAccountList();
            adAccountList.setAdsAccountList(Arrays.asList(adAccountDto_01, adAccountDto_02, adAccountDto_03));

            Mockito.doReturn(adAccountList).when(adAccountService).search(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong());

            AdAccountList result = adAccountService.search(2001L, "brand", "country", 3001L);
            assertThat(result.getAdsAccountList().size()).isEqualTo(3);
            assertThat(result.getAdsAccountList().get(0).getAdAccountSequence()).isEqualTo(1001L);
            assertThat(result.getAdsAccountList().get(1).getAdAccountSequence()).isEqualTo(1002L);
            assertThat(result.getAdsAccountList().get(2).getAdAccountSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class SearchWithAdsPfLoginUserIdTest {

        @MockBean
        public AdAccountService adAccountService;

        @Autowired
        public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

        @Test
        @Sql("/services/AdAccountServiceTest/SearchWithAdsPfLoginUserIdTest/t_api_auth_info.sql")
        public void testSearchWithAdsPfLoginUserId() {
            adAccountService.apiAuthenticationInformationMapper = apiAuthenticationInformationMapper;
            doCallRealMethod().when(adAccountService).searchWithAdsPfLoginUserId(any(), any(), any(), any());
            {
                AdAccountListDto a1 = new AdAccountListDto();
                a1.setAdAccountSequence(2001L);
                AdAccountListDto a2 = new AdAccountListDto();
                a2.setAdAccountSequence(2002L);
                AdAccountList adAccountList = new AdAccountList();
                adAccountList.setAdsAccountList(Arrays.asList(a1, a2));
                doReturn(adAccountList).when(adAccountService).search(any(), any(), any(), any());
                List<AdAccountListDto> r = adAccountService.searchWithAdsPfLoginUserId(1L, BRAND.GU.getCode(), COUNTRY.JP.getCode(), null);
                assertThat(r.size()).isEqualTo(2);
                assertThat(r.get(0).getAdAccountSequence()).isEqualTo(2001L);
                assertThat(r.get(1).getAdAccountSequence()).isEqualTo(2002L);
            }
            {
                AdAccountListDto a3 = new AdAccountListDto();
                a3.setAdAccountSequence(2003L);
                AdAccountList adAccountList = new AdAccountList();
                adAccountList.setAdsAccountList(Arrays.asList(a3));
                doReturn(adAccountList).when(adAccountService).search(any(), any(), any(), eq(1003L));
                List<AdAccountListDto> r = adAccountService.searchWithAdsPfLoginUserId(1L, BRAND.GU.getCode(), COUNTRY.JP.getCode(), "ad02");
                assertThat(r.size()).isEqualTo(1);
                assertThat(r.get(0).getAdAccountSequence()).isEqualTo(2003L);
            }
            {
                AdAccountListDto a2 = new AdAccountListDto();
                a2.setAdAccountSequence(2002L);
                AdAccountListDto a5 = new AdAccountListDto();
                a5.setAdAccountSequence(2005L);

                AdAccountList adAccountList2 = new AdAccountList();
                adAccountList2.setAdsAccountList(Arrays.asList(a2));
                AdAccountList adAccountList5 = new AdAccountList();
                adAccountList5.setAdsAccountList(Arrays.asList(a5));

                doReturn(adAccountList2).when(adAccountService).search(any(), any(), any(), eq(1002L));
                doReturn(adAccountList5).when(adAccountService).search(any(), any(), any(), eq(1005L));
                List<AdAccountListDto> r = adAccountService.searchWithAdsPfLoginUserId(1L, BRAND.GU.getCode(), COUNTRY.JP.getCode(), "ad01");
                assertThat(r.size()).isEqualTo(2);
                assertThat(r.get(0).getAdAccountSequence()).isEqualTo(2002L);
                assertThat(r.get(1).getAdAccountSequence()).isEqualTo(2005L);
            }
            {
                AdAccountListDto a2 = new AdAccountListDto();
                a2.setAdAccountSequence(2002L);
                AdAccountListDto a3 = new AdAccountListDto();
                a3.setAdAccountSequence(2003L);
                AdAccountListDto a5 = new AdAccountListDto();
                a5.setAdAccountSequence(2005L);
                AdAccountListDto a4 = new AdAccountListDto();
                a4.setAdAccountSequence(2004L);

                AdAccountList adAccountList2 = new AdAccountList();
                adAccountList2.setAdsAccountList(Arrays.asList(a2, a3));
                AdAccountList adAccountList5 = new AdAccountList();
                adAccountList5.setAdsAccountList(Arrays.asList(a5, a4));

                doReturn(adAccountList2).when(adAccountService).search(any(), any(), any(), eq(1002L));
                doReturn(adAccountList5).when(adAccountService).search(any(), any(), any(), eq(1005L));
                List<AdAccountListDto> r = adAccountService.searchWithAdsPfLoginUserId(1L, BRAND.GU.getCode(), COUNTRY.JP.getCode(), "ad01");
                assertThat(r.size()).isEqualTo(4);
                assertThat(r.get(0).getAdAccountSequence()).isEqualTo(2002L);
                assertThat(r.get(1).getAdAccountSequence()).isEqualTo(2003L);
                assertThat(r.get(2).getAdAccountSequence()).isEqualTo(2005L);
                assertThat(r.get(3).getAdAccountSequence()).isEqualTo(2004L);
            }
        }
    }
}
