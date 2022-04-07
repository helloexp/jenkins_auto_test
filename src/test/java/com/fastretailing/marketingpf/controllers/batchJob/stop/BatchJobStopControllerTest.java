package com.fastretailing.marketingpf.controllers.batchJob.stop;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.services.BatchJobService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class BatchJobStopControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatchJobService batchJobService;

    @Test
    public void expectingSuccess() throws Exception {
        doReturn(new BatchJob()).when(batchJobService).stop(Mockito.anyLong());
        mockMvc.perform(put("/api/marketingpf/v1/fr/jp/request_stop_job/1001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
