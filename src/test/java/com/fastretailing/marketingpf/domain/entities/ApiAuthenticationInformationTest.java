package com.fastretailing.marketingpf.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.FacebookAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.GoogleAuthApiTokenDto;
import org.junit.jupiter.api.Test;

class ApiAuthenticationInformationTest {

    @Test
    public void testGetGoogleAuthApiTokenDto() {
        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setApiToken("{\"refresh_token\":\"refresh_token\"}");
        GoogleAuthApiTokenDto googleAuthApiTokenDto = apiAuthenticationInformation.getGoogleAuthApiTokenDto();
        assertThat(googleAuthApiTokenDto.getRefeshToken()).isEqualTo("refresh_token");
    }

    @Test
    public void testGetFacebookApiTokenDto() {
        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setApiToken("{\"access_token\":\"access_token\"}");
        FacebookAuthApiTokenDto facebookAuthApiTokenDto = apiAuthenticationInformation.getFacebookAuthApiTokenDto();
        assertThat(facebookAuthApiTokenDto.getAccessToken()).isEqualTo("access_token");
    }
}
