package com.fastretailing.marketingpf.controllers.segment.update;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.services.SegmentConditionService;
import com.fastretailing.marketingpf.services.SegmentItemForSqlMasterService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentUpdateControllerTest extends BaseSpringBootTest {

    @Nested
    public class UpdateTest1 {

        @Autowired
        public MockMvc mvc;

        @MockBean
        public SegmentService segmentService;

        @MockBean
        public SegmentConditionService segmentConditionService;

        @MockBean
        public SegmentItemForSqlMasterService segmentItemForSqlMasterService;

        @MockBean
        public SegmentUpdateValidator segmentUpdateValidator;

        @MockBean
        public SegmentMapper segmentMapper;

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
        public void expectingUpdateSuccess() throws Exception {
            Segment segment = new Segment();
            segment.setSegmentSequence(1001L);
            segment.setBusinessType("1");
            segment.setBrandId("1");
            segment.setCountryId("1");
            segment.setDeliveryScheduledMonth("202101");
            segment.setEventTargetPeriodType("1");
            segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 1, 1));
            segment.setEventTargetPeriodEndDate(LocalDate.of(2021, 1, 1));
            segment.setEventTargetPeriodRelativeNumberValue(6000L);
            segment.setEventTargetPeriodRelativePeriodUnit("1");
            segment.setTargetItemCategory("item_01");
            segment.setSegmentName("segment_01");
            segment.setDescription("desc_01");
            segment.setSqlEditFlag(SQL_EDIT_FLAG.BASIC_SEGMENT.getValueAsString());
            segment.setEditedSql("sql_01");
            segment.setStatus("status_01");
            segment.setEventTypeList("{}");
            segment.setAdditionalConditionLogicalOperatorType("1");
            segment.setDeletionFlagForAudit("f");
            segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            segment.setCreateUserIdForAudit("user_01");
            segment.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            segment.setUpdateUserIdForAudit("user_01");

            Mockito.doNothing().when(segmentUpdateValidator).validate(Mockito.any(), Mockito.any());

            Mockito.doReturn(segment).when(segmentService)
                    .update(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                            Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

            Segment existingSegment = new Segment();
            existingSegment.setSqlEditFlag(SQL_EDIT_FLAG.BASIC_SEGMENT.getValueAsString());
            Mockito.doReturn(existingSegment).when(segmentService).findById(anyLong());

            SegmentItemForSqlMaster segmentItemForSqlMaster = new SegmentItemForSqlMaster();
            segmentItemForSqlMaster.setSegmentItemForSqlSequence(3001L);

            Mockito.doReturn(Arrays.asList(segmentItemForSqlMaster)).when(segmentItemForSqlMasterService).findBySegmentItemForScreenSequenceAndConditions(Mockito.any(), Mockito.any(), Mockito.any(),
                    Mockito.any());
            Mockito.doReturn(segmentItemForSqlMaster).when(segmentItemForSqlMasterService).findByConditions(Mockito.any(), Mockito.any(), Mockito.any());

            SegmentCondition condition = new SegmentCondition();
            condition.setSegmentConditionSequence(2001L);
            condition.setSegmentSequence(1001L);
            condition.setSegmentItemForScreenSequence(2222L);
            condition.setSegmentItemForSqlSequence(3333L);
            condition.setOperatorType("1");
            condition.setComparisonValue("01");
            condition.setSegmentConditionBlockOrder(1);
            condition.setLogicalOperatorType("1");
            condition.setDeletionFlagForAudit("f");
            condition.setCreateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
            condition.setCreateUserIdForAudit("user_01");
            condition.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
            condition.setUpdateUserIdForAudit("user_01");

            Mockito.doReturn(Arrays.asList(condition)).when(segmentConditionService).findListBySegmentSequence(Mockito.any());
            Mockito.doReturn(condition).when(segmentConditionService)
                    .create(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/2001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"1\","
                            + "    \"brandId\": \"010\","
                            + "    \"countryId\": \"1\","
                            + "    \"deliveryScheduledMonth\": \"202111\","
                            + "    \"segmentName\": \"segment\","
                            + "    \"segmentConditionList\": ["
                            + "      {"
                            + "        \"segmentItemForScreenSequence\": 2222,"
                            + "        \"segmentItemForSqlSequence\": 3333,"
                            + "        \"operatorType\": \"1\","
                            + "        \"comparisonValue\": [\"01\"],"
                            + "        \"segmentConditionBlockOrder\": 1,"
                            + "        \"logicalOperatorType\": \"1\""
                            + "      }"
                            + "    ]"
                            + "  }"
                            + "}"))
                    .andExpect(status().isOk())
                    .andExpect(json().contentEquals(
                            "{" +
                            "    \"targetSegment\": {" +
                            "        \"segmentSequence\": 1001," +
                            "        \"businessType\": \"1\"," +
                            "        \"brandId\": \"1\"," +
                            "        \"countryId\": \"1\"," +
                            "        \"deliveryScheduledMonth\": \"202101\"," +
                            "        \"eventTargetPeriodType\": \"1\"," +
                            "        \"eventTargetPeriodStartDate\": \"2021-01-01\"," +
                            "        \"eventTargetPeriodEndDate\": \"2021-01-01\"," +
                            "        \"eventTargetPeriodRelativeNumberValue\": 6000," +
                            "        \"eventTargetPeriodRelativePeriodUnit\": \"1\"," +
                            "        \"targetItemCategory\": \"item_01\"," +
                            "        \"segmentName\": \"segment_01\"," +
                            "        \"description\": \"desc_01\"," +
                            "        \"sqlEditFlag\": \"0\"," +
                            "        \"editedSql\": \"sql_01\"," +
                            "        \"status\": \"status_01\"," +
                            "        \"eventTypeList\": \"{}\"," +
                            "        \"additionalConditionLogicalOperatorType\": \"1\"," +
                            "        \"segmentConditionList\": [{" +
                            "                \"segmentConditionSequence\": 2001," +
                            "                \"segmentItemForScreenSequence\": 2222," +
                            "                \"segmentItemForSqlSequence\": 3333," +
                            "                \"operatorType\": \"1\"," +
                            "                \"comparisonValue\": \"01\"," +
                            "                \"segmentConditionBlockOrder\": 1," +
                            "                \"logicalOperatorType\": \"1\"," +
                            "                \"deletionFlagForAudit\": \"f\"," +
                            "                \"createDateTimeForAudit\": \"2021-01-01 00:00:01\"," +
                            "                \"createUserIdForAudit\": \"user_01\"," +
                            "                \"updateDateTimeForAudit\": \"2021-01-01 00:00:01\"," +
                            "                \"updateUserIdForAudit\": \"user_01\"" +
                            "            }" +
                            "        ]," +
                            "        \"deletionFlagForAudit\": \"f\"," +
                            "        \"createDateTimeForAudit\": \"2021-01-01 00:00:00\"," +
                            "        \"createUserIdForAudit\": \"user_01\"," +
                            "        \"updateDateTimeForAudit\": \"2021-01-01 00:00:00\"," +
                            "        \"updateUserIdForAudit\": \"user_01\"" +
                            "    }" +
                            "}"));

            // Check validate request
            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/2001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"9\","
                            + "    \"brandId\": \"9\","
                            + "    \"countryId\": \"9\","
                            + "    \"deliveryScheduledMonth\": \"202113\","
                            + "    \"eventTargetPeriodType\": \"9\","
                            + "    \"eventTargetPeriodStartDate\": \"2022-01-10\","
                            + "    \"eventTargetPeriodEndDate\": \"2022-01-10\","
                            + "    \"eventTargetPeriodRelativeNumberValue\": 1000,"
                            + "    \"eventTargetPeriodRelativePeriodUnit\": \"1\","
                            + "    \"targetItemCategory\": \"item_category\","
                            + "    \"segmentName\": \"segment\","
                            + "    \"description\": \"description\","
                            + "    \"sqlEditFlag\": \"1\","
                            + "    \"editedSql\": \"editedSql\","
                            + "    \"status\": \"9\","
                            + "    \"eventTypeList\": [],"
                            + "    \"additionalConditionLogicalOperatorType\": \"9\","
                            + "    \"segmentConditionList\": ["
                            + "      {"
                            + "        \"segmentItemForScreenSequence\": 2222,"
                            + "        \"segmentItemForSqlSequence\": 3333,"
                            + "        \"operatorType\": \"9\","
                            + "        \"comparisonValue\": [],"
                            + "        \"segmentConditionBlockOrder\": 1,"
                            + "        \"logicalOperatorType\": \"9\""
                            + "      }"
                            + "    ]"
                            + "  }"
                            + "}"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(json().errorContentEquals(
                            "{"
                                    + "    \"errorCode\": \"E00400\","
                                    + "    \"errorMessage\": \"Bad request\","
                                    + "    \"errorDetail\": \"Validation failed\","
                                    + "    \"validationErrors\": [{"
                                    + "            \"field\": \"targetSegment.additionalConditionLogicalOperatorType\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.brandId\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.countryId\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.deliveryScheduledMonth\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.eventTargetPeriodType\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.segmentConditionList[0].logicalOperatorType\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }, {"
                                    + "            \"field\": \"targetSegment.status\","
                                    + "            \"message\": \"errors.invalid\""
                                    + "        }"
                                    + "    ]"
                                    + "}"));

            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/2001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"1\","
                            + "    \"brandId\": \"010\","
                            + "    \"countryId\": \"1\","
                            + "    \"deliveryScheduledMonth\": \"202205\","
                            + "    \"eventTargetPeriodType\": \"2\","
                            + "    \"eventTargetPeriodRelativeNumberValue\": \"2\","
                            + "    \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                            + "    \"targetItemCategory\": \"targetItemCategory_2\","
                            + "    \"eventTypeList\": [\"3\"],"
                            + "    \"segmentName\": \"segment\","
                            + "    \"description\": \"description\","
                            + "    \"sqlEditFlag\": \"0\","
                            + "    \"editedSql\": \"\","
                            + "    \"status\": \"1\","
                            + "    \"additionalConditionLogicalOperatorType\": \"1\","
                            + "    \"segmentConditionList\": []"
                            + "  }"
                            + "}"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(json().errorContentEquals("{" +
                            "    \"errorCode\": \"E00400\"," +
                            "    \"errorMessage\": \"Bad request\"," +
                            "    \"errorDetail\": \"Validation failed\"," +
                            "    \"validationErrors\": [{" +
                            "            \"field\": \"segmentConditionList\"," +
                            "            \"message\": \"segmentConditionList has no valid item\"" +
                            "        }" +
                            "    ]" +
                            "}"));

            Segment segment2 = new Segment();
            segment2.setSegmentSequence(2001L);
            segment2.setBusinessType("2");
            segment2.setBrandId("090");
            segment2.setCountryId("1");
            segment2.setDeliveryScheduledMonth("202111");
            segment2.setEventTargetPeriodType("2");
            segment2.setEventTargetPeriodRelativeNumberValue(1000L);
            segment2.setEventTargetPeriodRelativePeriodUnit("");
            segment2.setTargetItemCategory("item_category");
            segment2.setSegmentName("segment");
            segment2.setDescription("description");
            segment2.setSqlEditFlag(SQL_EDIT_FLAG.BASIC_SEGMENT.getValueAsString());
            segment2.setEditedSql("0");
            segment2.setStatus("1");
            segment2.setEventTypeList("[\"3\"");
            segment2.setAdditionalConditionLogicalOperatorType("1");
            segment2.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            doCallRealMethod().when(segmentUpdateValidator).validate(anyLong(), Mockito.any());
            doCallRealMethod().when(segmentUpdateValidator).validateBasicSegment(Mockito.any());
            segmentUpdateValidator.segmentMapper = segmentMapper;
            doReturn(segment2).when(segmentMapper).findById(anyLong());

            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/2001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"2\","
                            + "    \"brandId\": \"090\","
                            + "    \"countryId\": \"1\","
                            + "    \"deliveryScheduledMonth\": \"202111\","
                            + "    \"eventTargetPeriodType\": \"2\","
                            + "    \"eventTargetPeriodRelativeNumberValue\": 1000,"
                            + "    \"eventTargetPeriodRelativePeriodUnit\": \"\","
                            + "    \"targetItemCategory\": \"item_category\","
                            + "    \"segmentName\": \"segment\","
                            + "    \"description\": \"description\","
                            + "    \"sqlEditFlag\": \"0\","
                            + "    \"editedSql\": \"\","
                            + "    \"status\": \"1\","
                            + "    \"eventTypeList\": [\"3\"],"
                            + "    \"additionalConditionLogicalOperatorType\": \"1\","
                            + "    \"segmentConditionList\": ["
                            + "      {"
                            + "        \"segmentItemForScreenSequence\": 2222,"
                            + "        \"segmentItemForSqlSequence\": 3333,"
                            + "        \"operatorType\": \"9\","
                            + "        \"comparisonValue\": [],"
                            + "        \"segmentConditionBlockOrder\": 1,"
                            + "        \"logicalOperatorType\": \"1\""
                            + "      }"
                            + "    ]"
                            + "  }"
                            + "}"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(json().errorContentEquals("{" +
                            "    \"errorCode\": \"E00400\"," +
                            "    \"errorMessage\": \"Bad request\"," +
                            "    \"errorDetail\": \"Validation failed\"," +
                            "    \"validationErrors\": [{" +
                            "            \"field\": \"eventTargetPeriodRelativePeriodUnit\"," +
                            "            \"message\": \"EventTargetPeriodRelativePeriodUnit is invalid\"" +
                            "        }" +
                            "    ]" +
                            "}"));
        }
    }

    @Nested
    public class UpdateTest2 {

        @Autowired
        public MockMvc mvc;

        @BeforeEach
        public void setup() {
            Map<String, Object> claims = new HashMap<>();
            claims.put("oid", "user_02");
            claims.put("name", "Piyo");
            claims.put("sub", "sub");
            DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), new OidcIdToken("token", null, null, claims));
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_GENERAL_USER.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        @Sql("/controllers/segment/update/SegmentUpdateControllerTest/UpdateTest2/test_update_basic_segment.sql")
        @Test
        public void testUpdateBasicSegment() throws Exception {
            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/1001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"1\","
                            + "    \"brandId\": \"010\","
                            + "    \"countryId\": \"1\","
                            + "    \"deliveryScheduledMonth\": \"202202\","
                            + "    \"eventTargetPeriodType\": \"1\","
                            + "    \"eventTargetPeriodStartDate\": \"2022-04-07\","
                            + "    \"eventTargetPeriodEndDate\": \"2022-05-07\","
                            + "    \"eventTargetPeriodRelativeNumberValue\": null,"
                            + "    \"eventTargetPeriodRelativePeriodUnit\": null,"
                            + "    \"targetItemCategory\": \"targetItemCategory_update\","
                            + "    \"eventTypeList\": [\"1\",\"4\"],"
                            + "    \"segmentName\": \"segment2\","
                            + "    \"description\": \"description2\","
                            + "    \"sqlEditFlag\": \"0\","
                            + "    \"editedSql\": \"\","
                            + "    \"status\": \"1\","
                            + "    \"additionalConditionLogicalOperatorType\": \"1\","
                            + "    \"segmentConditionList\": ["
                            + "      {\"segmentItemForScreenSequence\": 1, \"operatorType\": \"3\",\"comparisonValue\": [\"hoge_update\"],\"segmentConditionBlockOrder\": 0,\"logicalOperatorType\": \"1\"},"
                            + "      {\"segmentItemForScreenSequence\": 3, \"operatorType\": \"5\",\"comparisonValue\": [\"fuga_update\"],\"segmentConditionBlockOrder\": 0,\"logicalOperatorType\": \"1\"},"
                            + "      {\"segmentItemForScreenSequence\": 4, \"operatorType\": \"6\",\"comparisonValue\": [\"olaf_update\"],\"segmentConditionBlockOrder\": 0,\"logicalOperatorType\": \"1\"},"
                            + "      {\"segmentItemForScreenSequence\": 37, \"operatorType\": \"1\",\"comparisonValue\": [\"456\"],\"segmentConditionBlockOrder\": 1,\"logicalOperatorType\": \"2\"}"
                            + "    ]"
                            + "  }"
                            + "}"))
                    .andExpect(status().isOk());
            assertQueryResult("select sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, update_user_id_f_adt from t_sgmt_cond order by sgmt_cond_seq").isSameDataAs(
                            "2001,1001,1,1001,3,[\"hoge\"],0,1,t,user_01,user_01",
                            "2002,1001,1,1002,3,[\"hoge\"],0,1,t,user_01,user_01",
                            "2003,1001,3,1003,5,[\"fuga\"],0,1,t,user_01,user_01",
                            "2004,1001,4,1004,6,[\"aloha\"],0,1,t,user_01,user_01",
                            "2005,1001,4,1005,6,[\"aloha\"],0,1,t,user_01,user_01",
                            "2006,1001,35,1006,4,[\"北海道\"],1,2,t,user_01,user_01",
                            "2007,1001,37,1007,1,[\"123\"],1,2,t,user_01,user_01",
                            "2008,1002,1,1001,3,[\"hoge\"],0,1,f,user_01,user_01",
                            "2009,1002,35,1006,4,[\"北海道\"],1,2,f,user_01,user_01",
                            "2011,1001,1,1001,3,[\"hoge_update\"],0,1,f,user_02,user_02",
                            "2012,1001,3,1003,5,[\"fuga_update\"],0,1,f,user_02,user_02",
                            "2013,1001,4,1005,6,[\"olaf_update\"],0,1,f,user_02,user_02",
                            "2014,1001,37,1007,1,[\"456\"],1,2,f,user_02,user_02");
            assertQueryResult("select sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, "
                    + "evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, \"desc\", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, update_user_id_f_adt from t_sgmt order by sgmt_seq").isSameDataAs(
                            "1001,1,010,1,202202,1,2022-04-07,2022-05-07,null,null,targetItemCategory_update,segment2,description2,0,,1,[\"1\",\"4\"],1,f,user_01,user_02",
                            "1002,1,010,1,202202,2,null,null,1,2,targetItemCategory,segment,description,0,,1,[\"1\"],1,f,user_01,user_01");

            mvc.perform(put("/api/marketingpf/v1/fr/jp/control_of_segment_related_tables/1002")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{"
                            + "  \"targetSegment\": {"
                            + "    \"businessType\": \"1\","
                            + "    \"brandId\": \"010\","
                            + "    \"countryId\": \"1\","
                            + "    \"deliveryScheduledMonth\": \"202205\","
                            + "    \"eventTargetPeriodType\": \"2\","
                            + "    \"eventTargetPeriodRelativeNumberValue\": \"2\","
                            + "    \"eventTargetPeriodRelativePeriodUnit\": \"3\","
                            + "    \"targetItemCategory\": \"targetItemCategory_2\","
                            + "    \"eventTypeList\": [\"3\"],"
                            + "    \"segmentName\": \"segment\","
                            + "    \"description\": \"description\","
                            + "    \"sqlEditFlag\": \"0\","
                            + "    \"editedSql\": \"\","
                            + "    \"status\": \"1\","
                            + "    \"additionalConditionLogicalOperatorType\": \"1\","
                            + "    \"segmentConditionList\": ["
                            + "      {\"segmentItemForScreenSequence\": 1, \"operatorType\": \"3\",\"comparisonValue\": [\"hoge_2\"],\"segmentConditionBlockOrder\": 0,\"logicalOperatorType\": \"1\"},"
                            + "      {\"segmentItemForScreenSequence\": 35, \"operatorType\": \"4\",\"comparisonValue\": [\"青森県\"],\"segmentConditionBlockOrder\": 1,\"logicalOperatorType\": \"2\"}"
                            + "    ]"
                            + "  }"
                            + "}"))
                    .andExpect(status().isOk());

            assertQueryResult("select sgmt_cond_seq, sgmt_seq, sgmt_itm_f_scrn_seq, sgmt_itm_f_sql_seq, oprtr_type, cmprsn_val, sgmt_cond_blok_ordr, lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, update_user_id_f_adt from t_sgmt_cond order by sgmt_cond_seq").isSameDataAs(
                            "2001,1001,1,1001,3,[\"hoge\"],0,1,t,user_01,user_01",
                            "2002,1001,1,1002,3,[\"hoge\"],0,1,t,user_01,user_01",
                            "2003,1001,3,1003,5,[\"fuga\"],0,1,t,user_01,user_01",
                            "2004,1001,4,1004,6,[\"aloha\"],0,1,t,user_01,user_01",
                            "2005,1001,4,1005,6,[\"aloha\"],0,1,t,user_01,user_01",
                            "2006,1001,35,1006,4,[\"北海道\"],1,2,t,user_01,user_01",
                            "2007,1001,37,1007,1,[\"123\"],1,2,t,user_01,user_01",
                            "2008,1002,1,1001,3,[\"hoge\"],0,1,t,user_01,user_01",
                            "2009,1002,35,1006,4,[\"北海道\"],1,2,t,user_01,user_01",
                            "2011,1001,1,1001,3,[\"hoge_update\"],0,1,f,user_02,user_02",
                            "2012,1001,3,1003,5,[\"fuga_update\"],0,1,f,user_02,user_02",
                            "2013,1001,4,1005,6,[\"olaf_update\"],0,1,f,user_02,user_02",
                            "2014,1001,37,1007,1,[\"456\"],1,2,f,user_02,user_02",
                            "2015,1002,1,1002,3,[\"hoge_2\"],0,1,f,user_02,user_02",
                            "2016,1002,35,1006,4,[\"青森県\"],1,2,f,user_02,user_02");
            assertQueryResult("select sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, "
                    + "evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, \"desc\", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, update_user_id_f_adt from t_sgmt order by sgmt_seq")
                            .isSameDataAs(
                                    "1001,1,010,1,202202,1,2022-04-07,2022-05-07,null,null,targetItemCategory_update,segment2,description2,0,,1,[\"1\",\"4\"],1,f,user_01,user_02",
                                    "1002,1,010,1,202205,2,null,null,2,3,targetItemCategory_2,segment,description,0,,1,[\"3\"],1,f,user_01,user_02");
        }
    }
}
