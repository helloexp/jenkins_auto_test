package com.fastretailing.marketingpf.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.FacebookApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.GoogleApiTokenDto;

public class AdsPlatformMasterTest {

    @Test
    public void testGetGoogleApiTokenDto() {
        AdsPlatformMaster adsPlatformMaster = new AdsPlatformMaster();
        adsPlatformMaster.setAdsPlatformSequence(1L);
        adsPlatformMaster.setApiToken("{\"developer_token\":\"dev_token\", \"client_id\":\"clientId\", \"client_secret\":\"clientSecret\"}");
        GoogleApiTokenDto googleApiTokenDto = adsPlatformMaster.getGoogleApiTokenDto();
        assertThat(googleApiTokenDto.getDeveloperToken()).isEqualTo("dev_token");
        assertThat(googleApiTokenDto.getClientId()).isEqualTo("clientId");
        assertThat(googleApiTokenDto.getClientSecret()).isEqualTo("clientSecret");
    }

    @Test
    public void testGetFacebookApiTokenDto() {
        AdsPlatformMaster adsPlatformMaster = new AdsPlatformMaster();
        adsPlatformMaster.setAdsPlatformSequence(2L);
        adsPlatformMaster.setApiToken("{\"app_id\":\"appId\", \"app_secret\":\"appSecret\"}");
        FacebookApiTokenDto facebookApiTokenDto = adsPlatformMaster.getFacebookApiTokenDto();
        assertThat(facebookApiTokenDto.getAppId()).isEqualTo("appId");
        assertThat(facebookApiTokenDto.getAppSecret()).isEqualTo("appSecret");
    }
}
