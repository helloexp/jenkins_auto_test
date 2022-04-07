package com.fastretailing.marketingpf.controllers.segment.delete;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.services.SegmentConditionService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentDeleteControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    public SegmentService segmentService;

    @MockBean
    public SegmentConditionService segmentConditionService;

    @MockBean
    public SegmentDeleteValidator segmentDeleteValidator;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("oid", "user_01");
        claims.put("name", "Fuga");
        claims.put("sub", "sub");
        OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
        DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void expectingDeleteSuccess_givingAvailableRequest() throws Exception {
        Segment segment = new Segment();
        segment.setSegmentSequence(1001L);
        segment.setBusinessType("business_01");
        segment.setBrandId("brand_01");
        segment.setCountryId("country_01");
        segment.setDeliveryScheduledMonth("schedule_month_01");
        segment.setEventTargetPeriodType("periodType_01");
        segment.setDeliveryScheduledMonth("period_type_01");
        segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 11, 30));
        segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 12, 30));
        segment.setEventTargetPeriodRelativeNumberValue(2001L);
        segment.setEventTargetPeriodRelativePeriodUnit("unit_01");
        segment.setTargetItemCategory("item_category_1");
        segment.setSegmentName("segment_name_1");
        segment.setDescription("desc_1");
        segment.setSqlEditFlag("flg_1");
        segment.setEditedSql("sql_1");
        segment.setStatus("stt_1");
        segment.setEventTypeList("{}");
        segment.setAdditionalConditionLogicalOperatorType("1");
        segment.setDeletionFlagForAudit("t");
        segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
        segment.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
        segment.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
        segment.setCreateUserIdForAudit("user_01");
        segment.setUpdateUserIdForAudit("user_01");
        segment.setDeletionUserIdForAudit("user_01");

        SegmentCondition condition_01 = new SegmentCondition();
        condition_01.setSegmentSequence(1001L);
        condition_01.setSegmentConditionSequence(2001L);
        condition_01.setSegmentItemForScreenSequence(3001L);
        condition_01.setSegmentItemForSqlSequence(4001L);
        condition_01.setOperatorType(OPERATOR_TYPE.EQUAL.getValueAsString());
        condition_01.setComparisonValue("val_01");
        condition_01.setSegmentConditionBlockOrder(1);
        condition_01.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
        condition_01.setDeletionFlagForAudit("t");
        condition_01.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
        condition_01.setCreateUserIdForAudit("user_01");
        condition_01.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
        condition_01.setDeletionUserIdForAudit("user_01");
        condition_01.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 3));
        condition_01.setUpdateUserIdForAudit("user_01");

        Mockito.doNothing().when(segmentDeleteValidator).validate(Mockito.any());
        Mockito.doReturn(segment).when(segmentService).delete(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doReturn(Arrays.asList(condition_01)).when(segmentConditionService).findListBySegmentSequence(Mockito.any());
        Mockito.doReturn(condition_01).when(segmentConditionService).delete(Mockito.any(), Mockito.any(), Mockito.any());

        mvc.perform(delete("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/1001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals(
                        "{"
                                + "    \"targetSegment\": {"
                                + "        \"segmentSequence\": 1001,"
                                + "        \"businessType\": \"business_01\","
                                + "        \"brandId\": \"brand_01\","
                                + "        \"countryId\": \"country_01\","
                                + "        \"deliveryScheduledMonth\": \"period_type_01\","
                                + "        \"eventTargetPeriodType\": \"periodType_01\","
                                + "        \"eventTargetPeriodStartDate\": \"2021-11-30\","
                                + "        \"eventTargetPeriodEndDate\": \"2021-12-30\","
                                + "        \"eventTargetPeriodRelativeNumberValue\": 2001,"
                                + "        \"eventTargetPeriodRelativePeriodUnit\": \"unit_01\","
                                + "        \"targetItemCategory\": \"item_category_1\","
                                + "        \"segmentName\": \"segment_name_1\","
                                + "        \"description\": \"desc_1\","
                                + "        \"sqlEditFlag\": \"flg_1\","
                                + "        \"editedSql\": \"sql_1\","
                                + "        \"status\": \"stt_1\","
                                + "        \"eventTypeList\": \"{}\","
                                + "        \"additionalConditionLogicalOperatorType\": \"1\","
                                + "        \"segmentConditionList\": [{"
                                + "                \"segmentConditionSequence\": 2001,"
                                + "                \"segmentItemForScreenSequence\": 3001,"
                                + "                \"segmentItemForSqlSequence\": 4001,"
                                + "                \"operatorType\": \"1\","
                                + "                \"comparisonValue\": \"val_01\","
                                + "                \"segmentConditionBlockOrder\": 1,"
                                + "                \"logicalOperatorType\": \"1\","
                                + "                \"deletionFlagForAudit\": \"t\","
                                + "                \"createDateTimeForAudit\": \"2021-01-01 00:00:01\","
                                + "                \"createUserIdForAudit\": \"user_01\","
                                + "                \"deletionDateTimeForAudit\": \"2021-01-01 00:00:02\","
                                + "                \"deletionUserIdForAudit\": \"user_01\","
                                + "                \"updateDateTimeForAudit\": \"2021-01-01 00:00:03\","
                                + "                \"updateUserIdForAudit\": \"user_01\""
                                + "            }"
                                + "        ],"
                                + "        \"deletionFlagForAudit\": \"t\","
                                + "        \"createDateTimeForAudit\": \"2021-01-01 00:00:01\","
                                + "        \"createUserIdForAudit\": \"user_01\","
                                + "        \"deletionDateTimeForAudit\": \"2021-01-01 00:00:02\","
                                + "        \"deletionUserIdForAudit\": \"user_01\","
                                + "        \"updateDateTimeForAudit\": \"2021-01-01 00:00:03\","
                                + "        \"updateUserIdForAudit\": \"user_01\""
                                + "    }"
                                + "}"));
    }
}
