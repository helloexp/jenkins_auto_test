package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.dtos.AdAccount.AdAccountDto;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_STATUS;
import com.fastretailing.marketingpf.domain.enums.BATCH_JOB_TYPE;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserListUploadService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SchedulerUserListControllerTest extends BaseSpringBootTest  {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserListUploadService userListUploadService;

    @MockBean
    private AdAccountService adAccountService;

    @MockBean
    public SchedulerUserListValidator schedulerUserListValidator;

    @MockBean
    public SegmentService segmentService;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("oid", "user01");
        claims.put("name", "Fuga");
        claims.put("sub", "sub");
        OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
        DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @Sql("/controllers/scheduler/userlist/SchedulerUserListControllerTest/t_otpt_pltfrm_outbound_stng_intrm.sql")
    @Sql("/controllers/scheduler/userlist/SchedulerUserListControllerTest/t_outbound_stng_sgmt_intrm.sql")
    public void expectingSuccess() throws Exception {
        AdAccountDto adAccountDto = new AdAccountDto();
        adAccountDto.setAdAccountSequence(1101L);
        adAccountDto.setAccountName("accountName");
        adAccountDto.setLoginCustomerId("98521");
        adAccountDto.setAdsPlatformSequence(1L);
        adAccountDto.setAdsPlatformAccountId("456");
        adAccountDto.setBrandId(BRAND.GU.getCode());
        adAccountDto.setBrandId(COUNTRY.JP.getCode());
        adAccountDto.setApiAuthenticationInformationSequence(1001L);

        AdAccount adAccount = new AdAccount();
        adAccount.setAdAccount(adAccountDto);

        BatchJobDto batchJobDto = new BatchJobDto();
        batchJobDto.setBatchJobSequence(1001L);
        batchJobDto.setBatchJobType(BATCH_JOB_TYPE.UPLOAD.getValueAsString());
        batchJobDto.setSegmentSequenceList("[1001,1002]");
        batchJobDto.setOutboundSettingSequence(2001L);
        batchJobDto.setStatus(BATCH_JOB_STATUS.REQUESTED.getValueAsString());
        batchJobDto.setAuditInfoForCreation("user01", LocalDateTime.of(2021, 1, 1, 1, 1));

        BatchJob batchJob = new BatchJob();
        batchJob.setJob(batchJobDto);

        doReturn(adAccount).when(adAccountService).findById(Mockito.anyLong());
        doReturn(batchJob).when(userListUploadService).uploadUserList(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString());
        doReturn("query").when(segmentService).createMultiSegmentQuery(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(post("/api/marketingpf/v1/fr/jp/scheduler/userlist/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "    \"outboundSettingSequence\": 2001" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals("{" +
                        "    \"batchJob\": {" +
                        "        \"batchJobSequence\": 1001," +
                        "        \"outboundSettingSequence\": 2001" +
                        "    }" +
                        "}"));

        // Not found
        doThrow(new ResourceNotFoundException()).when(schedulerUserListValidator).validate(Mockito.anyLong());
        mvc.perform(post("/api/marketingpf/v1/fr/jp/scheduler/userlist/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "    \"outboundSettingSequence\": 2001" +
                        "}"))
                .andExpect(status().isNotFound());
    }
}
