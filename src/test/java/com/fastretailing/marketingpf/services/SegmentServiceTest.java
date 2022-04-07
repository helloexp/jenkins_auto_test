package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.SEGMENT_STATUS;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(properties = {"datasetGaUqjp=208208769", "datasetGaGujp=75672114", "datasetGaUqjpApp=77805412"})
class SegmentServiceTest extends BaseSpringBootTest {

    @Nested
    public class CreateTest {

        @Mock
        public SegmentService segmentService;

        @Mock
        public SegmentMapper segmentMapper;

        @Test
        public void expectingCreateSuccess() {
            Segment segment = new Segment();
            segment.setSegmentSequence(1001L);
            segment.setBusinessType("biz_1");
            segment.setBrandId("1");
            segment.setCountryId("1");
            segment.setDeliveryScheduledMonth("m_1");
            segment.setEventTargetPeriodType("1");
            segment.setEventTargetPeriodStartDate(LocalDate.of(1970, 1, 1));
            segment.setEventTargetPeriodEndDate(LocalDate.of(1970, 2, 2));
            segment.setEventTargetPeriodRelativeNumberValue(2001L);
            segment.setEventTargetPeriodRelativePeriodUnit("1");
            segment.setTargetItemCategory("item_category_1");
            segment.setSegmentName("segment_name_1");
            segment.setDescription("desc_1");
            segment.setSqlEditFlag("1");
            segment.setEditedSql("sql_1");
            segment.setStatus("1");
            segment.setEventTypeList("{}");
            segment.setAdditionalConditionLogicalOperatorType("2");
            segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            segment.setCreateUserIdForAudit("user_1");
            segment.setUpdateDateTimeForAudit((LocalDateTime.of(2021, 11, 20, 10, 10, 10)));
            segment.setUpdateUserIdForAudit("user_1");

            Mockito.doNothing().when(segmentMapper).create(Mockito.any());
            Mockito.doReturn(segment).when(segmentService)
                    .create(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                            Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

            Segment result = segmentService.create("", "", "", "202101", "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1), 2001L, "", "", "", "", "", "", "", "", null, null, null);
            assertEquals(1001L, result.getSegmentSequence());
            assertEquals("biz_1", result.getBusinessType());
            assertEquals("1", result.getBrandId());
            assertEquals("1", result.getCountryId());
            assertEquals("m_1", result.getDeliveryScheduledMonth());
            assertEquals("1", result.getEventTargetPeriodType());
            assertEquals(LocalDate.of(1970, 1, 1), result.getEventTargetPeriodStartDate());
            assertEquals(LocalDate.of(1970, 2, 2), result.getEventTargetPeriodEndDate());
            assertEquals(2001L, result.getEventTargetPeriodRelativeNumberValue());
            assertEquals("1", result.getEventTargetPeriodRelativePeriodUnit());
            assertEquals("item_category_1", result.getTargetItemCategory());
            assertEquals("segment_name_1", result.getSegmentName());
            assertEquals("desc_1", result.getDescription());
            assertEquals("1", result.getSqlEditFlag());
            assertEquals("sql_1", result.getEditedSql());
            assertEquals("1", result.getStatus());
            assertEquals("{}", result.getEventTypeList());
            assertEquals("2", result.getAdditionalConditionLogicalOperatorType());
            assertEquals(LocalDateTime.of(2021, 11, 20, 10, 10, 10), result.getCreateDateTimeForAudit());
            assertEquals("user_1", result.getCreateUserIdForAudit());
            assertEquals(LocalDateTime.of(2021, 11, 20, 10, 10, 10), result.getUpdateDateTimeForAudit());
            assertEquals("user_1", result.getUpdateUserIdForAudit());
        }
    }

    @Nested
    public class CreateSqlSegmentTest {

        @Autowired
        public SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/CreateSqlSegmentTest/data.sql")
        public void expectingCreateSuccess() {
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,biz_1,1,1,m_1,1,1970-01-01,1970-02-02,2001,1,item_category_1,segment_name_1,desc_1,1,sql_1,1,{},1,f,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1",
                    "1002,biz_2,2,2,m_2,2,1970-01-01,1970-02-02,2002,2,item_category_2,segment_name_2,desc_2,2,sql_2,2,{},2,f,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2",
                    "1003,biz_3,3,3,m_3,3,1970-01-01,1970-02-02,2003,3,item_category_3,segment_name_3,desc_3,3,sql_3,3,{},1,f,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3"
            );

            segmentService.createSqlSegment("biz_1", "1", "1", "m_1", SQL_EDIT_FLAG.SQL_SEGMENT, "sql_1", "segment_name_1", "1", LocalDateTime.of(2021, 11, 20, 10, 10, 10), "user_1");
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,biz_1,1,1,m_1,1,1970-01-01,1970-02-02,2001,1,item_category_1,segment_name_1,desc_1,1,sql_1,1,{},1,f,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1",
                    "1002,biz_2,2,2,m_2,2,1970-01-01,1970-02-02,2002,2,item_category_2,segment_name_2,desc_2,2,sql_2,2,{},2,f,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2",
                    "1003,biz_3,3,3,m_3,3,1970-01-01,1970-02-02,2003,3,item_category_3,segment_name_3,desc_3,3,sql_3,3,{},1,f,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3",
                    "1004,biz_1,1,1,m_1,null,null,null,null,null,null,segment_name_1,null,1,sql_1,1,null,null,f,2021-11-20 10:10:10,user_1,null,null,2021-11-20 10:10:10,user_1"
            );
        }
    }

    @Nested
    public class UpdateTest {

        @Autowired
        private SegmentService segmentService;

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/services/SegmentServiceTest/UpdateTest/data.sql")
        public void expectingUpdateSuccess_givingAvailableParams() {
            segmentService.update(1001L, "new_biz_1", "1", "1", "202101", "1", LocalDate.of(2021, 11, 21), LocalDate.of(2021, 11, 22), 2011L,
                    "1", "new_item_category_1", "new_segment_name_1", "new_desc_1", "1", "new_sql_1", "1", "{\"name\":\"hoge\"}", LOGICAL_OPERATOR_TYPE.AND, LocalDateTime.of(2021, 11, 11, 11, 11, 11),
                    "user_11");
            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check updated records
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getBusinessType()).isEqualTo("new_biz_1");
            assertThat(resultList.get(0).getBrandId()).isEqualTo("1");
            assertThat(resultList.get(0).getCountryId()).isEqualTo("1");
            assertThat(resultList.get(0).getDeliveryScheduledMonth()).isEqualTo("202101");
            assertThat(resultList.get(0).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(2021, 11, 21));
            assertThat(resultList.get(0).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(2021, 11, 22));
            assertThat(resultList.get(0).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2011L);
            assertThat(resultList.get(0).getTargetItemCategory()).isEqualTo("new_item_category_1");
            assertThat(resultList.get(0).getSegmentName()).isEqualTo("new_segment_name_1");
            assertThat(resultList.get(0).getDescription()).isEqualTo("new_desc_1");
            assertThat(resultList.get(0).getSqlEditFlag()).isEqualTo("1");
            assertThat(resultList.get(0).getEditedSql()).isEqualTo("new_sql_1");
            assertThat(resultList.get(0).getStatus()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTypeList()).isEqualTo("{\"name\":\"hoge\"}");
            assertThat(resultList.get(0).getAddLogicalOperatorTypeAsEnum()).isEqualTo(LOGICAL_OPERATOR_TYPE.AND);
            // Check origin records
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getBusinessType()).isEqualTo("biz_2");
            assertThat(resultList.get(1).getBrandId()).isEqualTo("2");
            assertThat(resultList.get(1).getCountryId()).isEqualTo("2");
            assertThat(resultList.get(1).getDeliveryScheduledMonth()).isEqualTo("m_2");
            assertThat(resultList.get(1).getEventTargetPeriodType()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(1).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(1).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2002L);
            assertThat(resultList.get(1).getTargetItemCategory()).isEqualTo("item_category_2");
            assertThat(resultList.get(1).getSegmentName()).isEqualTo("segment_name_2");
            assertThat(resultList.get(1).getDescription()).isEqualTo("desc_2");
            assertThat(resultList.get(1).getSqlEditFlag()).isEqualTo("2");
            assertThat(resultList.get(1).getEditedSql()).isEqualTo("sql_2");
            assertThat(resultList.get(1).getStatus()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getBusinessType()).isEqualTo("biz_3");
            assertThat(resultList.get(2).getBrandId()).isEqualTo("3");
            assertThat(resultList.get(2).getCountryId()).isEqualTo("3");
            assertThat(resultList.get(2).getDeliveryScheduledMonth()).isEqualTo("m_3");
            assertThat(resultList.get(2).getEventTargetPeriodType()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(2).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(2).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2003L);
            assertThat(resultList.get(2).getTargetItemCategory()).isEqualTo("item_category_3");
            assertThat(resultList.get(2).getSegmentName()).isEqualTo("segment_name_3");
            assertThat(resultList.get(2).getDescription()).isEqualTo("desc_3");
            assertThat(resultList.get(2).getSqlEditFlag()).isEqualTo("3");
            assertThat(resultList.get(2).getEditedSql()).isEqualTo("sql_3");
            assertThat(resultList.get(2).getStatus()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTypeList()).isEqualTo("{}");
        }

        @Test
        @Sql("/services/SegmentServiceTest/UpdateTest/data.sql")
        public void expectingDoNotUpdate_givingNullParams() {
            segmentService.update(1001L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, LocalDateTime.now(), "user_1");
            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getBusinessType()).isEqualTo("biz_1");
            assertThat(resultList.get(0).getBrandId()).isEqualTo("1");
            assertThat(resultList.get(0).getCountryId()).isEqualTo("1");
            assertThat(resultList.get(0).getDeliveryScheduledMonth()).isEqualTo("m_1");
            assertThat(resultList.get(0).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(0).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(0).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2001L);
            assertThat(resultList.get(0).getTargetItemCategory()).isEqualTo("item_category_1");
            assertThat(resultList.get(0).getSegmentName()).isEqualTo("segment_name_1");
            assertThat(resultList.get(0).getDescription()).isEqualTo("desc_1");
            assertThat(resultList.get(0).getSqlEditFlag()).isEqualTo("1");
            assertThat(resultList.get(0).getEditedSql()).isEqualTo("sql_1");
            assertThat(resultList.get(0).getStatus()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getBusinessType()).isEqualTo("biz_2");
            assertThat(resultList.get(1).getBrandId()).isEqualTo("2");
            assertThat(resultList.get(1).getCountryId()).isEqualTo("2");
            assertThat(resultList.get(1).getDeliveryScheduledMonth()).isEqualTo("m_2");
            assertThat(resultList.get(1).getEventTargetPeriodType()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(1).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(1).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2002L);
            assertThat(resultList.get(1).getTargetItemCategory()).isEqualTo("item_category_2");
            assertThat(resultList.get(1).getSegmentName()).isEqualTo("segment_name_2");
            assertThat(resultList.get(1).getDescription()).isEqualTo("desc_2");
            assertThat(resultList.get(1).getSqlEditFlag()).isEqualTo("2");
            assertThat(resultList.get(1).getEditedSql()).isEqualTo("sql_2");
            assertThat(resultList.get(1).getStatus()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getBusinessType()).isEqualTo("biz_3");
            assertThat(resultList.get(2).getBrandId()).isEqualTo("3");
            assertThat(resultList.get(2).getCountryId()).isEqualTo("3");
            assertThat(resultList.get(2).getDeliveryScheduledMonth()).isEqualTo("m_3");
            assertThat(resultList.get(2).getEventTargetPeriodType()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(2).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(2).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2003L);
            assertThat(resultList.get(2).getTargetItemCategory()).isEqualTo("item_category_3");
            assertThat(resultList.get(2).getSegmentName()).isEqualTo("segment_name_3");
            assertThat(resultList.get(2).getDescription()).isEqualTo("desc_3");
            assertThat(resultList.get(2).getSqlEditFlag()).isEqualTo("3");
            assertThat(resultList.get(2).getEditedSql()).isEqualTo("sql_3");
            assertThat(resultList.get(2).getStatus()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTypeList()).isEqualTo("{}");
        }

        @Test
        public void expectingResourceNotFoundException_givingNullId() {
            try {
                segmentService.update(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                fail("");
            } catch (ResourceNotFoundException e) {
            }
        }
    }

    @Nested
    public class DeleteTest {

        @Autowired
        private SegmentService segmentService;

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/services/SegmentServiceTest/DeleteTest/data.sql")
        public void expectingDeleteSuccess_givingAvailableParams() {
            segmentService.delete(1001L, LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0), "hoge");
            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1003L);
        }

        @Test
        public void expectingResourceNotFoundException_givingInvalidParams() {
            // Giving not found segmentSequence
            try {
                segmentService.delete(9999L, LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0), "hoge");
                fail("");
            } catch (ResourceNotFoundException e) {
            }

            // Giving null segmentSequence
            try {
                segmentService.delete(null, LocalDateTime.of(2021, 1, 1, 0, 0, 0, 0), "hoge");
                fail("");
            } catch (ResourceNotFoundException e) {
            }
        }
    }

    @Nested
    public class FindByIdTest {

        @MockBean
        public SegmentMapper segmentMapper;

        @Autowired
        public SegmentService segmentService;

        @Test
        public void expectingFindSuccess() {
            Segment segment = new Segment();
            segment.setSegmentSequence(1001L);
            segment.setBusinessType("biz_1");
            segment.setBrandId("1");
            segment.setCountryId("1");
            segment.setDeliveryScheduledMonth("2021-01-01");
            segment.setEventTargetPeriodType("1");
            segment.setEventTargetPeriodStartDate(LocalDate.of(1970, 1, 1));
            segment.setEventTargetPeriodEndDate(LocalDate.of(1970, 2, 2));
            segment.setEventTargetPeriodRelativeNumberValue(2001L);
            segment.setTargetItemCategory("item_category_1");
            segment.setSegmentName("segment_name_1");
            segment.setDescription("desc_1");
            segment.setSqlEditFlag("1");
            segment.setEditedSql("sql_1");
            segment.setStatus("1");
            segment.setEventTypeList("{}");
            segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            segment.setCreateUserIdForAudit("user_1");
            segment.setUpdateDateTimeForAudit((LocalDateTime.of(2021, 11, 20, 10, 10, 10)));
            segment.setUpdateUserIdForAudit("user_1");
            Mockito.doReturn(segment).when(segmentMapper).findById(Mockito.anyLong());

            Segment result = segmentService.findById(1001L);
            assertThat(result.getSegmentSequence()).isEqualTo(1001L);
            assertThat(result.getBusinessType()).isEqualTo("biz_1");
            assertThat(result.getBrandId()).isEqualTo("1");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getDeliveryScheduledMonth()).isEqualTo("2021-01-01");
            assertThat(result.getEventTargetPeriodType()).isEqualTo("1");
            assertThat(result.getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(result.getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(result.getEventTargetPeriodRelativeNumberValue()).isEqualTo(2001L);
            assertThat(result.getTargetItemCategory()).isEqualTo("item_category_1");
            assertThat(result.getSegmentName()).isEqualTo("segment_name_1");
            assertThat(result.getDescription()).isEqualTo("desc_1");
            assertThat(result.getSqlEditFlag()).isEqualTo("1");
            assertThat(result.getEditedSql()).isEqualTo("sql_1");
            assertThat(result.getStatus()).isEqualTo("1");
            assertThat(result.getEventTypeList()).isEqualTo("{}");
            assertThat(result.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            assertThat(result.getCreateUserIdForAudit()).isEqualTo("user_1");
            assertThat(result.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            assertThat(result.getUpdateUserIdForAudit()).isEqualTo("user_1");
        }
    }

    @Nested
    public class FindListBySegmentNameTest {

        @MockBean
        public SegmentMapper segmentMapper;

        @Autowired
        public SegmentService segmentService;

        @Test
        public void expectingSearchSuccess() {
            Segment s_1 = new Segment();
            s_1.setSegmentSequence(1001L);
            s_1.setSegmentName("name_01");
            Segment s_2 = new Segment();
            s_2.setSegmentSequence(1002L);
            s_2.setSegmentName("name_02");
            Segment s_3 = new Segment();
            s_3.setSegmentSequence(1003L);
            s_3.setSegmentName("name_03");
            List<Segment> segmentList = Arrays.asList(s_1, s_2, s_3);
            Mockito.doReturn(segmentList).when(segmentMapper).findListBySegmentName(Mockito.anyString());

            List<Segment> resultList = segmentService.findListBySegmentName("name_");
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getSegmentName()).isEqualTo("name_01");
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getSegmentName()).isEqualTo("name_02");
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getSegmentName()).isEqualTo("name_03");
        }
    }

    @Nested
    public class SearchTest {

        @Autowired
        private SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/SearchTest/t_sgmt.sql")
        public void expectingSuccess() {
            // Giving segmentSequence = 1001
            List<Segment> segmentList = segmentService.search(1001L, null, null, null, null, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(1);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);

            // Giving businessType = "2"
            segmentList = segmentService.search(null, BUSINESS_TYPE.CRM, null, null, null, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getBusinessType()).isEqualTo("2");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getBusinessType()).isEqualTo("2");
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getBusinessType()).isEqualTo("2");

            // Giving deliveryScheduledMonth = "m_2"
            segmentList = segmentService.search(null, null, "m_2", null, null, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(2);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(segmentList.get(0).getDeliveryScheduledMonth()).isEqualTo("m_2");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1004L);
            assertThat(segmentList.get(1).getDeliveryScheduledMonth()).isEqualTo("m_2");

            // Giving brandId = 010
            segmentList = segmentService.search(null, null, null, BRAND.UQ, null, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getBrandId()).isEqualTo("010");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getBrandId()).isEqualTo("010");
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getBrandId()).isEqualTo("010");

            // Giving countryId = "1"
            segmentList = segmentService.search(null, null, null, BRAND.GU, COUNTRY.JP, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(2);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(segmentList.get(0).getCountryId()).isEqualTo("1");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1004L);
            assertThat(segmentList.get(1).getCountryId()).isEqualTo("1");

            // Giving status = "1"
            segmentList = segmentService.search(null, null, null, null, null, SEGMENT_STATUS.TEMPORARY_REGISTRATION, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getStatus()).isEqualTo("1");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getStatus()).isEqualTo("1");
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getStatus()).isEqualTo("1");

            // Giving eventTargetPeriodType = "1"
            segmentList = segmentService.search(null, null, null, null, null, null, null, EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getEventTargetPeriodType()).isEqualTo("1");

            // Giving eventTargetPeriodStartDate = 2021-11-16
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, LocalDate.of(2021, 11, 16), null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(2);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(segmentList.get(0).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(2021, 11, 16));
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1004L);
            assertThat(segmentList.get(1).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(2021, 11, 16));

            // Giving eventTargetPeriodEndDate = 2021-11-16
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, null, LocalDate.of(2021, 11, 16), null, null, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(2021, 11, 16));
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(2021, 11, 16));
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(2021, 11, 16));

            // Giving eventTargetPeriodRelativeNumberValue = 1000
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, null, null, 1000, null, null);
            assertThat(segmentList.size()).isEqualTo(2);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(segmentList.get(0).getEventTargetPeriodRelativeNumberValue()).isEqualTo(1000);
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1004L);
            assertThat(segmentList.get(1).getEventTargetPeriodRelativeNumberValue()).isEqualTo(1000);

            // Giving eventTargetPeriodRelativePeriodUnit = 3
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, null, null, null, EVENT_TARGET_PERIOD_RELATIVE_PERIOD_UNIT.MONTH, null);
            assertThat(segmentList.size()).isEqualTo(3);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getEventTargetPeriodRelativePeriodUnit()).isEqualTo("3");
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(1).getEventTargetPeriodRelativePeriodUnit()).isEqualTo("3");
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1005L);
            assertThat(segmentList.get(2).getEventTargetPeriodRelativePeriodUnit()).isEqualTo("3");

            // Giving segmentName = "segment_1"
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, null, null, null, null, "segment_name_1");
            assertThat(segmentList.size()).isEqualTo(1);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(0).getSegmentName()).isEqualTo("segment_name_1");

            // Giving all params = null
            segmentList = segmentService.search(null, null, null, null, null, null, null, null, null, null, null, null, null);
            assertThat(segmentList.size()).isEqualTo(5);
            assertThat(segmentList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(segmentList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(segmentList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(segmentList.get(3).getSegmentSequence()).isEqualTo(1004L);
            assertThat(segmentList.get(4).getSegmentSequence()).isEqualTo(1005L);
        }
    }

    @Nested
    public class BuildSqlBySegmentSequenceTest {

        @Autowired
        public SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/BuildSqlBySegmentSequenceTest/data.sql")
        public void testBuildSqlBySegmentSequence() {
            assertThat(segmentService.buildSqlBySegmentSequence(2101L)).isEqualTo(
                    "((SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col41_text = 'val\\'01') INTERSECT DISTINCT (SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col42_num != 1234)) "
                            + "INTERSECT DISTINCT ((SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col43_text IN ('option1', 'option2')) UNION DISTINCT (SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col44_num NOT IN (1234, 56.78))) "
                            + "INTERSECT DISTINCT (SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col45_text LIKE '%ho\\\\%ge fu\\\\_ga pi\\\\\\\\yo%')");
            try {
                segmentService.buildSqlBySegmentSequence(2201L);
                fail("");
            } catch (RuntimeException e) {
            } catch (Exception e) {
                fail(e.getMessage());
            }
            assertThat(segmentService.buildSqlBySegmentSequence(2301L)).isEqualTo(
                    "SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col46_text NOT LIKE '%ho\\\\%ge fu\\\\_ga pi\\\\\\\\yo%'");

            assertThat(segmentService.buildSqlBySegmentSequence(2401L))
                    .isEqualTo("(SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1  \n"
                            + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20210101') AS TIMESTAMP)\n"
                            + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20210102') AS TIMESTAMP)\n"
                            + " \n"
                            + " \n"
                            + " AND col49_text > 5678) UNION DISTINCT (SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1  \n"
                            + "    AND _TABLE_SUFFIX BETWEEN '20210101'\n"
                            + "    AND '20210102'\n"
                            + " \n"
                            + " \n"
                            + " AND col48_text >= 12.34)");
            assertThat(segmentService.buildSqlBySegmentSequence(2501L)).isEqualTo("(SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1  \n"
                    + "    AND _TABLE_SUFFIX BETWEEN '20220102'\n"
                    + "    AND '20220304'\n"
                    + " \n"
                    + " \n"
                    + " AND col50_text <= 12.34) INTERSECT DISTINCT (SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1  \n"
                    + "    AND p.trn_dtime  >=  CAST(PARSE_DATE('%Y%m%d', '20220102') AS TIMESTAMP)\n"
                    + "    AND p.trn_dtime  <  CAST(PARSE_DATE('%Y%m%d', '20220304') AS TIMESTAMP)\n"
                    + " \n"
                    + " \n"
                    + " AND col51_text < 5678)");
            assertThat(segmentService.buildSqlBySegmentSequence(2601L)).isEqualTo("(SELECT uid FROM fr-dev-mdb.208208769.hoge WHERE 1=1 and col_1 = 123) "
                    + "INTERSECT DISTINCT (SELECT uid FROM fr-dev-mdb.75672114.hoge WHERE 1=1 and col_1 = 456) "
                    + "INTERSECT DISTINCT (SELECT uid FROM fr-dev-mdb.77805412.hoge WHERE 1=1 and col_1 = 789)");

            assertThat(segmentService.buildSqlBySegmentSequence(2701L)).isEqualTo("SELECT uid FROM fr-dev-mdb.208208769.hoge WHERE 1=1 and  \n"
                    + " \n"
                    + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -3 YEAR) AS STRING FORMAT 'YYYYMMDD' ) \n"
                    + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                    + " \n"
                    + " and col_1 IN 1, 2");

            assertThat(segmentService.buildSqlBySegmentSequence(2801L)).isEqualTo("((SELECT DISTINCT a.uid FROM `fr-dev-safelake.75672114.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-dev-mdb.non_pii_dev_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-dev-mdb.pii_dev_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1  \n"
                    + " \n"
                    + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -3 YEAR) AS STRING FORMAT 'YYYYMMDD' ) \n"
                    + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                    + " \n"
                    + " AND h.ecommerceaction.action_type = '6' AND c.dept_elmnt.id IN ( '1', '2' )) INTERSECT DISTINCT (SELECT DISTINCT a.uid FROM `fr-dev-safelake.75672114.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-dev-mdb.non_pii_dev_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-dev-mdb.pii_dev_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1  \n"
                    + " \n"
                    + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -3 YEAR) AS STRING FORMAT 'YYYYMMDD' ) \n"
                    + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                    + " \n"
                    + " AND h.eCommerceAction.step in (1,2,3,4) AND c.g_dept_elmnt.id IN ( '21', '22' )) INTERSECT DISTINCT (SELECT DISTINCT p.uid FROM fr-dev-mdb.pii_dev_gujp.dmp_dmp_vw_t_prchs_hist_item_ctlg_gujp  AS p WHERE 1=1 AND p.trn_type_cd = 'SALE' AND p.recpt_sts = 'P' AND p.itm_type = 'ITEM' AND p.sls_qty  >=  1 AND p.mmbr_id IS NOT NULL  \n"
                    + " \n"
                    + "    AND p.trn_dtime  >=  CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -3 YEAR) AS TIMESTAMP)\n"
                    + "    AND p.trn_dtime  <  CAST(CURRENT_DATE('Asia/Tokyo') AS TIMESTAMP)\n"
                    + " \n"
                    + " AND p.trn_dtime IS NOT NULL AND p.vndr_invc_num IS NOT NULL AND p.itm_name LIKE '%エアリズ%') INTERSECT DISTINCT (SELECT DISTINCT a.uid FROM `fr-dev-safelake.75672114.ga_sessions_*` AS log, UNNEST(hits) AS h , UNNEST(h.product) AS p  INNER JOIN fr-dev-mdb.non_pii_dev_gujp.dmp_vw_m_ctlg_gujp AS c ON c.lvl_2_itm_cd = p.productSKU  INNER JOIN fr-dev-mdb.pii_dev_frjp.dmp_vw_m_acnt_jp AS a ON (SELECT MAX(IF(index=14, value, '')) FROM UNNEST(log.customDimensions)) = a.mmbr_id_hash_sub_id WHERE 1=1  \n"
                    + " \n"
                    + "    AND _TABLE_SUFFIX BETWEEN CAST(DATE_ADD(CURRENT_DATE('Asia/Tokyo'), INTERVAL -3 YEAR) AS STRING FORMAT 'YYYYMMDD' ) \n"
                    + "    AND CAST(CURRENT_DATE('Asia/Tokyo') AS STRING FORMAT 'YYYYMMDD' )\n"
                    + " \n"
                    + " AND h.eCommerceAction.step in (1,2,3,4) AND c.itm_name NOT LIKE '%インナー%')) "
                    + ""
                    + "INTERSECT DISTINCT ("
                    + "(SELECT DISTINCT a.uid fr-dev-mdb.pii_dev_frjp.dmp_vw_m_acnt_jp AS a WHERE 1=1 AND c.dept_elmnt.id IN ( '1', '2' ) AND p.g_dept_elmnt.id IN ( '21', '22' ) AND c.itm_name LIKE '%エアリズ%' AND p.itm_name NOT LIKE '%インナー%' AND h.eCommerceAction.step in (1,2,3,4) AND c.itm_name = 'hoge') "
                    + "UNION DISTINCT "
                    + "(SELECT DISTINCT b.uid fr-dev-mdb.pii_dev_frjp.b_table AS b WHERE 1=1 AND c.itm_name != 'hoge')"
                    + ")");
        }
    }

    @Nested
    public class GetSegmentQueryBySegmentSequenceTest {

        @Autowired
        public SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/GetSegmentQueryBySegmentSequenceTest/data.sql")
        public void testGetSegmentQueryBySegmentSequence() {
            assertThat(segmentService.getSegmentQueryBySegmentSequence(2101L)).isEqualTo("SELECT uid FROM fr-dev-mdb.pii_dev_frjp WHERE 1=1 AND col41_text = 'val\\'01'");
            assertThat(segmentService.getSegmentQueryBySegmentSequence(2201L)).isEqualTo("select * from hoge");
            try {
                segmentService.getSegmentQueryBySegmentSequence(9999L);
                fail();
            } catch(ResourceNotFoundException e ) {

            } catch(Exception e) {
                fail();
            }
        }
    }

    @Nested
    public class CreateMultiSegmentQueryTest {

        @MockBean
        private SegmentService segmentService;

        @Autowired
        public Config config;

        @Test
        public void expectingSuccess() {
            segmentService.config = config;
            doReturn("select uid from segment_query").when(segmentService).getSegmentQueryBySegmentSequence(Mockito.any());
            doCallRealMethod().when(segmentService).getDatasetNameAndTargetUidMobileIdViewByBrandCountry(Mockito.any(), Mockito.any());
            doCallRealMethod().when(segmentService).createMultiSegmentQuery(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
            String result = segmentService.createMultiSegmentQuery(Arrays.asList(1001L, 1002L, 1003L), BRAND.GU, COUNTRY.JP, Arrays.asList(EXTRACTION_TARGET_ID.MAIL_ADDRESS));
            assertThat(result).isEqualTo("(select uid from segment_query) UNION DISTINCT (select uid from segment_query) UNION DISTINCT (select uid from segment_query)");

            result = segmentService.createMultiSegmentQuery(Arrays.asList(1001L, 1002L), BRAND.GU, COUNTRY.JP, Arrays.asList(EXTRACTION_TARGET_ID.MAIL_ADDRESS, EXTRACTION_TARGET_ID.ADID));
            assertThat(result).isEqualTo("select segment_query.uid as uid, mobile_ids.advrtsng_id as adid, mobile_ids.idfa as idfa from ((select uid from segment_query) UNION DISTINCT (select uid from segment_query)) as segment_query left join fr-dev-mdb.pii_dev_gujp.dmp_vw_m_apsflyr_gujp as mobile_ids on mobile_ids.uid = segment_query.uid");

            result = segmentService.createMultiSegmentQuery(Arrays.asList(1001L, 1002L), BRAND.UQ, COUNTRY.JP, Arrays.asList(EXTRACTION_TARGET_ID.MAIL_ADDRESS, EXTRACTION_TARGET_ID.ADID));
            assertThat(result).isEqualTo("select segment_query.uid as uid, mobile_ids.advrtsng_id as adid, mobile_ids.idfa as idfa from ((select uid from segment_query) UNION DISTINCT (select uid from segment_query)) as segment_query left join fr-dev-mdb.pii_dev_uqjp.dmp_vw_m_apsflyr_uqjp as mobile_ids on mobile_ids.uid = segment_query.uid");

            try {
                segmentService.createMultiSegmentQuery(Arrays.asList(1001L, 1002L), null, COUNTRY.JP, Arrays.asList(EXTRACTION_TARGET_ID.MAIL_ADDRESS, EXTRACTION_TARGET_ID.ADID));
                fail("");
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("This service is only support brand GU/UQ and country JP");
            }

            try {
                segmentService.createMultiSegmentQuery(Arrays.asList(1001L, 1002L), BRAND.GU, null, Arrays.asList(EXTRACTION_TARGET_ID.MAIL_ADDRESS, EXTRACTION_TARGET_ID.ADID));
                fail("");
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("This service is only support brand GU/UQ and country JP");
            }
        }
    }

    @Nested
    public class GetDatasetNameAndTargetUidMobileIdViewByBrandCountryTest {

        @Autowired
        private SegmentService segmentService;

        @Test
        public void expectingSuccess() {
            String result = segmentService.getDatasetNameAndTargetUidMobileIdViewByBrandCountry(BRAND.GU, COUNTRY.JP);
            assertThat(result).isEqualTo("pii_dev_gujp.dmp_vw_m_apsflyr_gujp");

            result = segmentService.getDatasetNameAndTargetUidMobileIdViewByBrandCountry(BRAND.UQ, COUNTRY.JP);
            assertThat(result).isEqualTo("pii_dev_uqjp.dmp_vw_m_apsflyr_uqjp");

            try {
                segmentService.getDatasetNameAndTargetUidMobileIdViewByBrandCountry(null, COUNTRY.JP);
                fail("");
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("This service is only support brand GU/UQ and country JP");
            }

            try {
                segmentService.getDatasetNameAndTargetUidMobileIdViewByBrandCountry(BRAND.GU, null);
                fail("");
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("This service is only support brand GU/UQ and country JP");
            }
        }
    }

    @Nested
    public class UpdateStatusBySegmentSequenceTest {

        @Autowired
        private SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/UpdateStatusBySegmentSequenceTest/data.sql")
        public void expectingUpdateSuccess_givingAvailableParams() {
            segmentService.updateStatusBySegmentSequence(1001L, SEGMENT_STATUS.UPLOADED, "login_user_01", LocalDateTime.of(2022, 3, 17, 0, 0, 1));
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,2,010,1,202101,1,1970-01-01,2021-11-16,2000,3,item_category_1,segment_name_1,desc_1,0,sql_1,3,[1, 2],1,f,1970-01-01 00:00:01,user_id_1,1970-01-01 00:00:01,user_id_1,2022-03-17 00:00:01,login_user_01",
                    "1002,1,090,1,202101,2,2021-11-16,1970-02-02,1000,1,item_category_2,segment_name_2,desc_2,1,sql_2,2,[1, 2],1,f,1970-01-01 00:00:01,user_id_2,1970-01-01 00:00:01,user_id_2,2021-11-10 00:00:01,user_id_2",
                    "1003,2,010,1,202103,1,1970-01-01,2021-11-16,2000,3,item_category_3,segment_name_3,desc_3,0,sql_3,1,[1, 2],1,f,1970-01-01 00:00:01,user_id_3,1970-01-01 00:00:01,user_id_3,2021-12-10 00:00:01,user_id_3"
            );

            segmentService.updateStatusBySegmentSequence(1003L, SEGMENT_STATUS.UPLOADED, "login_user_03", LocalDateTime.of(2022, 3, 17, 0, 0, 3));
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,2,010,1,202101,1,1970-01-01,2021-11-16,2000,3,item_category_1,segment_name_1,desc_1,0,sql_1,3,[1, 2],1,f,1970-01-01 00:00:01,user_id_1,1970-01-01 00:00:01,user_id_1,2022-03-17 00:00:01,login_user_01",
                    "1002,1,090,1,202101,2,2021-11-16,1970-02-02,1000,1,item_category_2,segment_name_2,desc_2,1,sql_2,2,[1, 2],1,f,1970-01-01 00:00:01,user_id_2,1970-01-01 00:00:01,user_id_2,2021-11-10 00:00:01,user_id_2",
                    "1003,2,010,1,202103,1,1970-01-01,2021-11-16,2000,3,item_category_3,segment_name_3,desc_3,0,sql_3,3,[1, 2],1,f,1970-01-01 00:00:01,user_id_3,1970-01-01 00:00:01,user_id_3,2022-03-17 00:00:03,login_user_03"
            );

            try {
                segmentService.updateStatusBySegmentSequence(9999L, SEGMENT_STATUS.UPLOADED, "login_user_01", LocalDateTime.of(2022, 3, 17, 0, 0, 1));
                fail();
            } catch (ResourceNotFoundException ignored) {}
        }
    }

    public class UpdateSqlSegmentTest {

        @Autowired
        private SegmentService segmentService;

        @Test
        @Sql("/services/SegmentServiceTest/UpdateSqlSegmentTest/data.sql")
        public void testUpdateSqlSegment() {
            try {
                segmentService.updateSqlSegment(1001L, "new_biz_1", "090", "1", "202101", "new_segment_name_1", "new_desc_1", "new_sql_1", "1", LocalDateTime.of(2021, 11, 11, 11, 11, 11), "user_11");
                fail();
            } catch(ResourceNotFoundException e) {
            }
            segmentService.updateSqlSegment(1002L, "new_biz_22", "010", "2", "202104", "new_segment_name_22", "new_desc_22", "new_sql_22", "1", LocalDateTime.of(2021, 2, 22, 22, 22, 22), "user_22");
            assertQueryResult("select sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, "
                    + "evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, \"desc\", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, del_user_id_f_adt, update_user_id_f_adt from t_sgmt order by sgmt_seq").isSameDataAs(
                            "1001,biz_1,090,1,202203,1,2022-01-01,2022-02-11,2001,1,item_category_1,segment_name_1,desc_1,0,null,1,{},null,t,user_id_1,user_id_1,user_id_1",
                            "1002,new_biz_22,010,2,202104,null,null,null,null,null,null,new_segment_name_22,new_desc_22,1,new_sql_22,1,null,null,f,user_id_2,user_id_2,user_22",
                            "1003,biz_3,010,1,202203,2,2022-01-03,2022-02-13,2003,3,item_category_3,segment_name_3,desc_3,0,null,3,{},null,f,user_id_3,user_id_3,user_id_3",
                            "1004,biz_4,010,1,202203,null,null,null,null,null,null,segment_name_3,desc_3,1,sql_3,2,{},null,f,u41,u42,u43");
            segmentService.updateSqlSegment(1003L, "new_biz_33", "090", "3", "202105", "new_segment_name_33", "new_desc_33", "new_sql_33", "2", LocalDateTime.of(2021, 3, 31, 3, 33, 33), "user_33");
            assertQueryResult("select sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, "
                    + "evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, \"desc\", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, del_user_id_f_adt, update_user_id_f_adt from t_sgmt order by sgmt_seq").isSameDataAs(
                            "1001,biz_1,090,1,202203,1,2022-01-01,2022-02-11,2001,1,item_category_1,segment_name_1,desc_1,0,null,1,{},null,t,user_id_1,user_id_1,user_id_1",
                            "1002,new_biz_22,010,2,202104,null,null,null,null,null,null,new_segment_name_22,new_desc_22,1,new_sql_22,1,null,null,f,user_id_2,user_id_2,user_22",
                            "1003,new_biz_33,090,3,202105,null,null,null,null,null,null,new_segment_name_33,new_desc_33,1,new_sql_33,2,null,null,f,user_id_3,user_id_3,user_33",
                            "1004,biz_4,010,1,202203,null,null,null,null,null,null,segment_name_3,desc_3,1,sql_3,2,{},null,f,u41,u42,u43");
            segmentService.updateSqlSegment(1004L, "new_biz_44", "010", "4", "202106", "new_segment_name_44", "new_desc_44", "new_sql_44", "1", LocalDateTime.of(2021, 4, 24, 14, 44, 44), "user_44");
            assertQueryResult("select sgmt_seq, biz_type, brnd_id, cntry_id, dlvr_sched_mo, evt_trgt_perd_type, evt_trgt_perd_start_date, evt_trgt_perd_end_date, evt_trgt_perd_rltv_num_val, "
                    + "evt_trgt_perd_rltv_perd_unit, trgt_itm_ctgry, sgmt_name, \"desc\", sql_edit_flg, editd_sql, sts, evt_type_list, add_cond_lgcl_oprtr_type, "
                    + "del_flg_f_adt, create_user_id_f_adt, del_user_id_f_adt, update_user_id_f_adt from t_sgmt order by sgmt_seq").isSameDataAs(
                            "1001,biz_1,090,1,202203,1,2022-01-01,2022-02-11,2001,1,item_category_1,segment_name_1,desc_1,0,null,1,{},null,t,user_id_1,user_id_1,user_id_1",
                            "1002,new_biz_22,010,2,202104,null,null,null,null,null,null,new_segment_name_22,new_desc_22,1,new_sql_22,1,null,null,f,user_id_2,user_id_2,user_22",
                            "1003,new_biz_33,090,3,202105,null,null,null,null,null,null,new_segment_name_33,new_desc_33,1,new_sql_33,2,null,null,f,user_id_3,user_id_3,user_33",
                            "1004,new_biz_44,010,4,202106,null,null,null,null,null,null,new_segment_name_44,new_desc_44,1,new_sql_44,1,null,null,f,u41,u42,user_44");
        }
    }
}
