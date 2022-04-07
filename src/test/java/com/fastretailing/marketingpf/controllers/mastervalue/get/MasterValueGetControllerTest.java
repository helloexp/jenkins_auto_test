package com.fastretailing.marketingpf.controllers.mastervalue.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.services.GetMasterValueService;
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
class MasterValueGetControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetMasterValueService getMasterValueService;

    @Test
    public void expectingSuccess() throws Exception {
        Mockito.doReturn("{\"choicesList\": [{\"name\":\"10y\", \"value\": \"10\"},{\"name\":\"20y\", \"value\": \"20\"}]}").when(getMasterValueService).getMasterValue(Mockito.anyString());

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/get_master_value/?urlForApiAccess=hoge.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(JsonContentMatchers.json().contentEquals("{"
                        + "    \"choicesList\": {"
                        + "        \"choicesList\": [{"
                        + "                \"name\": \"10y\","
                        + "                \"value\": \"10\""
                        + "            }, {"
                        + "                \"name\": \"20y\","
                        + "                \"value\": \"20\""
                        + "            }"
                        + "        ]"
                        + "    }"
                        + "}"));

        mockMvc.perform(get("/api/marketingpf/v1/fr/jp/get_master_value/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(JsonContentMatchers.json().errorContentEquals("{" +
                        "    \"errorCode\": \"E00400\"," +
                        "    \"errorMessage\": \"Bad request\"," +
                        "    \"errorDetail\": \"Validation failed\"," +
                        "    \"validationErrors\": [{" +
                        "            \"field\": \"urlForApiAccess\"," +
                        "            \"message\": \"errors.required\"" +
                        "        }" +
                        "    ]" +
                        "}"));
    }
}
