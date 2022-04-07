package com.fastretailing.marketingpf.controllers.batchJob.create;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.BatchJob.BatchJobDto;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.services.BatchJobService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class BatchJobCreateControllerTest extends BaseSpringBootTest {

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));

        Map<String, Object> claims = new HashMap<>();
        claims.put("oid", "user_01");
        claims.put("name", "Fuga");
        claims.put("sub", "sub");
        OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
        DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @MockBean
    public SegmentService segmentService;

    @MockBean
    public BatchJobService batchJobService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void expectingFindSuccess() throws Exception {
        String sql = "hoge";
        Mockito.doReturn(sql).when(segmentService).buildSqlBySegmentSequence(Mockito.any());

        BatchJob batchJob = new BatchJob();
        batchJob.setJob(new BatchJobDto());
        batchJob.getJob().setBatchJobSequence(1001L);
        batchJob.getJob().setBatchJobType("batchJobType_01");
        batchJob.getJob().setSegmentSequenceList("[1001,1002]");
        batchJob.getJob().setOutboundSettingSequence(3001L);
        batchJob.getJob().setStatus("status_01");
        batchJob.getJob().setDeletionFlagForAudit("false");
        batchJob.getJob().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1));
        batchJob.getJob().setCreateUserIdForAudit("user_01");
        batchJob.getJob().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        batchJob.getJob().setDeletionUserIdForAudit("user_02");
        batchJob.getJob().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        batchJob.getJob().setUpdateUserIdForAudit("user_03");

        Segment segment = new Segment();
        segment.setSegmentName("segmentName");
        segment.setSegmentSequence(1001L);
        Mockito.doReturn(segment).when(segmentService).findById(Mockito.any());

        BatchJobCreateRequest request = new BatchJobCreateRequest();
        request.setSegmentSequence(1001L);
        Mockito.doReturn(batchJob).when(batchJobService).create(Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(post("/api/marketingpf/v1/fr/jp/request_calculation_number_of_people/").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                        + "    \"batchJobSequence\": 1001,"
                        + "    \"batchJobType\": \"batchJobType_01\","
                        + "    \"segmentSequenceList\": \"[1001,1002]\","
                        + "    \"outboundSettingSequence\": 3001,"
                        + "    \"status\": \"status_01\","
                        + "    \"deletionFlagForAudit\": \"false\","
                        + "    \"createDateTimeForAudit\": \"2021-01-01 01:01:00\","
                        + "    \"createUserIdForAudit\": \"user_01\","
                        + "    \"deletionDateTimeForAudit\": \"2021-02-02 02:02:02\","
                        + "    \"deletionUserIdForAudit\": \"user_02\","
                        + "    \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                        + "    \"updateUserIdForAudit\": \"user_03\""
                        + "}"));
    }

    @Test
    public void expectingFindSuccess_givingSqlSegment() throws Exception {
        BatchJob batchJob = new BatchJob();
        batchJob.setJob(new BatchJobDto());
        batchJob.getJob().setBatchJobSequence(1001L);
        batchJob.getJob().setBatchJobType("batchJobType_01");
        batchJob.getJob().setSegmentSequenceList("[1001,1002]");
        batchJob.getJob().setOutboundSettingSequence(3001L);
        batchJob.getJob().setStatus("status_01");
        batchJob.getJob().setDeletionFlagForAudit("false");
        batchJob.getJob().setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 1, 1));
        batchJob.getJob().setCreateUserIdForAudit("user_01");
        batchJob.getJob().setDeletionDateTimeForAudit(LocalDateTime.of(2021, 2, 2, 2, 2, 2));
        batchJob.getJob().setDeletionUserIdForAudit("user_02");
        batchJob.getJob().setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 3, 3, 3));
        batchJob.getJob().setUpdateUserIdForAudit("user_03");

        Segment segment = new Segment();
        segment.setSegmentName("segmentName");
        segment.setSegmentSequence(1002L);
        segment.setSqlEditFlag("1");
        segment.setEditedSql("sql");
        Mockito.doReturn(segment).when(segmentService).findById(Mockito.any());

        BatchJobCreateRequest request = new BatchJobCreateRequest();
        request.setSegmentSequence(1001L);
        Mockito.doReturn(batchJob).when(batchJobService).create(Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(post("/api/marketingpf/v1/fr/jp/request_calculation_number_of_people/").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"batchJobSequence\": 1001,"
                                + "    \"batchJobType\": \"batchJobType_01\","
                                + "    \"segmentSequenceList\": \"[1001,1002]\","
                                + "    \"outboundSettingSequence\": 3001,"
                                + "    \"status\": \"status_01\","
                                + "    \"deletionFlagForAudit\": \"false\","
                                + "    \"createDateTimeForAudit\": \"2021-01-01 01:01:00\","
                                + "    \"createUserIdForAudit\": \"user_01\","
                                + "    \"deletionDateTimeForAudit\": \"2021-02-02 02:02:02\","
                                + "    \"deletionUserIdForAudit\": \"user_02\","
                                + "    \"updateDateTimeForAudit\": \"2021-03-03 03:03:03\","
                                + "    \"updateUserIdForAudit\": \"user_03\""
                                + "}"));
    }
}
