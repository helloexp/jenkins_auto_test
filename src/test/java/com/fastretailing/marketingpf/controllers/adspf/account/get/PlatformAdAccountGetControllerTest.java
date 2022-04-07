package com.fastretailing.marketingpf.controllers.adspf.account.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.PlatformAdAccountDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.tests.JsonContentMatchers;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class PlatformAdAccountGetControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public GoogleAdsService googleAdsService;

    @MockBean
    public FacebookAdsService facebookAdsService;

    @MockBean
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @MockBean
    private PlatformAdAccountGetValidator platformAdAccountGetValidator;

    @Test
    public void expectingSuccess() throws Exception {
        ApiAuthenticationInformation googleAuth = new ApiAuthenticationInformation();
        googleAuth.setApiAuthenticationInformationSequence(2001L);
        googleAuth.setAdsPlatformSequence(1L);
        googleAuth.setAdsPfLoginUserId("loginUserId");
        googleAuth.setApiToken("{\"refresh_token\": \"refresh_token\"}");

        PlatformAdAccountDto platformAdAccountDto = new PlatformAdAccountDto("AdAccountId", "AdAccountName");
        PlatformAdAccountDto platformAdAccountDto2 = new PlatformAdAccountDto("AdAccountId2", "AdAccountName2");
        PlatformAdAccountDto platformAdAccountDto3 = new PlatformAdAccountDto("AdAccountId3", "AdAccountName3");

        Mockito.doNothing().when(platformAdAccountGetValidator).validate(Mockito.anyLong());
        Mockito.doReturn(googleAuth).when(apiAuthenticationInformationService).findById(Mockito.any());
        Mockito.doReturn(Arrays.asList(platformAdAccountDto, platformAdAccountDto2)).when(googleAdsService).fetchAdAccounts(Mockito.anyString());

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/adspf/accounts/2001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(JsonContentMatchers.json().contentEquals("{" +
                        "    \"adAccountList\": [{" +
                        "            \"adAccountId\": \"AdAccountId\"," +
                        "            \"adAccountName\": \"AdAccountName\"" +
                        "        }, {" +
                        "            \"adAccountId\": \"AdAccountId2\"," +
                        "            \"adAccountName\": \"AdAccountName2\"" +
                        "        }" +
                        "    ]" +
                        "}"));

        ApiAuthenticationInformation facebookAuth = new ApiAuthenticationInformation();
        facebookAuth.setApiAuthenticationInformationSequence(2002L);
        facebookAuth.setAdsPlatformSequence(2L);
        facebookAuth.setAdsPfLoginUserId("loginUserId_facebook");
        facebookAuth.setApiToken("{\"access_token\": \"access_token\"}");

        Mockito.doReturn(facebookAuth).when(apiAuthenticationInformationService).findById(Mockito.any());
        Mockito.doReturn(Arrays.asList(platformAdAccountDto2, platformAdAccountDto3)).when(facebookAdsService).fetchAdAccounts(Mockito.anyString());
        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/adspf/accounts/2001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(JsonContentMatchers.json().contentEquals("{" +
                        "    \"adAccountList\": [{" +
                        "            \"adAccountId\": \"AdAccountId2\"," +
                        "            \"adAccountName\": \"AdAccountName2\"" +
                        "        }, {" +
                        "            \"adAccountId\": \"AdAccountId3\"," +
                        "            \"adAccountName\": \"AdAccountName3\"" +
                        "        }" +
                        "    ]" +
                        "}"));

        Mockito.doThrow(new ResourceNotFoundException()).when(platformAdAccountGetValidator).validate(Mockito.anyLong());
        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/adspf/accounts/9999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
