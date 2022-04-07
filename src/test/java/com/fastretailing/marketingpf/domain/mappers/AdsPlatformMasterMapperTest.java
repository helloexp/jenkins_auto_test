package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class AdsPlatformMasterMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        private AdsPlatformMasterMapper adsPlatformMasterMapper;

        @Test
        @Sql("/domain/mappers/AdsPlatformMasterMapperTest/FindByIdTest/m_ads_pltfrm.sql")
        public void expectingFindSuccess_givingManyCases() {
            AdsPlatformMaster adsPlatformMaster = adsPlatformMasterMapper.findById(1001L);
            assertThat(adsPlatformMaster.getAdsPlatformSequence()).isEqualTo(1001L);
            assertThat(adsPlatformMaster.getAdsPlatformName()).isEqualTo("Google Ads");
            assertThat(adsPlatformMaster.getApiToken()).isEqualTo("{\"client_id\": \"googleusercontent.com\"}");
            assertThat(adsPlatformMaster.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 1));
            assertThat(adsPlatformMaster.getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(adsPlatformMaster.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 3));
            assertThat(adsPlatformMaster.getUpdateUserIdForAudit()).isEqualTo("user1");

            adsPlatformMaster = adsPlatformMasterMapper.findById(1002L);
            assertThat(adsPlatformMaster.getAdsPlatformSequence()).isEqualTo(1002L);
            assertThat(adsPlatformMaster.getAdsPlatformName()).isEqualTo("Facebook Ads");
            assertThat(adsPlatformMaster.getApiToken()).isEqualTo("{\"app_id\": \"AppId\"}");
            assertThat(adsPlatformMaster.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 03, 0, 0, 1));
            assertThat(adsPlatformMaster.getCreateUserIdForAudit()).isEqualTo("user1");
            assertThat(adsPlatformMaster.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 03, 0, 0, 3));
            assertThat(adsPlatformMaster.getUpdateUserIdForAudit()).isEqualTo("user1");

            // Not found
            adsPlatformMaster = adsPlatformMasterMapper.findById(9999L);
            assertThat(adsPlatformMaster).isEqualTo(null);

            // Null
            adsPlatformMaster = adsPlatformMasterMapper.findById(null);
            assertThat(adsPlatformMaster).isEqualTo(null);
        }
    }
}
