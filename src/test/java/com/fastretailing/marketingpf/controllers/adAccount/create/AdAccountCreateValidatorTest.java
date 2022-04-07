package com.fastretailing.marketingpf.controllers.adAccount.create;

import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


class AdAccountCreateValidatorTest extends BaseSpringBootTest {
    @Nested
    public class vadidateTest {
        @MockBean
        public ApiAuthenticationInformationService apiAuthenticationInformationService;

        @Autowired
        public AdAccountCreateValidator adAccountCreateValidator;

        @Test
        public void testVadidateSuccess() throws Exception {
            AdAccountCreateRequest adAccountCreateRequest = new AdAccountCreateRequest();
            adAccountCreateRequest.setAdsPlatformAccountId("1234");
            adAccountCreateRequest.setAdsPlatformSequence(3L);
            adAccountCreateRequest.setApiAuthenticationInformationSequence(1001L);
            adAccountCreateRequest.setBrandId("090");
            adAccountCreateRequest.setCountryId("1");
            adAccountCreateRequest.setAccountName("account_01");

            ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
            apiAuthenticationInformation.setApiAuthenticationInformationSequence(2001L);
            apiAuthenticationInformation.setAdsPlatformSequence(1L);
            apiAuthenticationInformation.setAdsPfLoginUserId("loginUserId");
            apiAuthenticationInformation.setApiToken("{\"Token\": \"Token\"}");
            
            Mockito.doReturn(apiAuthenticationInformation).when(apiAuthenticationInformationService).findById(Mockito.any());
            try {
                adAccountCreateValidator.validate(adAccountCreateRequest);
            } catch (ResourceNotFoundException e) {
                fail();
            }
        }
        
        @Test
        public void testVadidateFalse() throws Exception {
            AdAccountCreateRequest adAccountCreateRequest = new AdAccountCreateRequest();
            adAccountCreateRequest.setAdsPlatformAccountId("1234");
            adAccountCreateRequest.setAdsPlatformSequence(3L);
            adAccountCreateRequest.setApiAuthenticationInformationSequence(1001L);
            adAccountCreateRequest.setBrandId("090");
            adAccountCreateRequest.setCountryId("1");
            adAccountCreateRequest.setAccountName("account_01");

            Mockito.doReturn(null).when(apiAuthenticationInformationService).findById(Mockito.any());
            try {
                adAccountCreateValidator.validate(adAccountCreateRequest);
                fail();
            } catch (ResourceNotFoundException e) {
            }
        }
    }
}
