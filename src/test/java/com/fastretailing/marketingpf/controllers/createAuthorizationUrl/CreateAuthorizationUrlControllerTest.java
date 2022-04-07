package com.fastretailing.marketingpf.controllers.createAuthorizationUrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.tests.JsonContentMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class CreateAuthorizationUrlControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacebookAdsService facebookAdsService;

    @MockBean
    private GoogleAdsService googleAdsService;

    @Test
    public void expectingSuccess() throws Exception {
        Mockito.doReturn("http://facebook.com").when(facebookAdsService).getAuthorizationUrl();
        Mockito.doReturn("http://google.com").when(googleAdsService).getAuthorizationUrl();

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/auth/create_url/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(JsonContentMatchers.json().contentEquals("{\"authorizationUrl\": \"http://google.com\"}"));

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/auth/create_url/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(JsonContentMatchers.json().contentEquals("{\"authorizationUrl\": \"http://facebook.com\"}"));

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/auth/create_url/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
