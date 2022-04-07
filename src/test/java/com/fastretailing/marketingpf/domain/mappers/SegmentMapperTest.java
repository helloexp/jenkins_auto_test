package com.fastretailing.marketingpf.domain.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class SegmentMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/FindAllTest/data.sql")
        public void expectingFindListSuccess_givingAvailableData() {
            List<Segment> resultList = segmentMapper.findAll();
            assertEquals(3, resultList.size());
            assertEquals(1001L, resultList.get(0).getSegmentSequence());
            assertEquals(1002L, resultList.get(1).getSegmentSequence());
            assertEquals(1003L, resultList.get(2).getSegmentSequence());
        }
    }

    @Nested
    public class SearchTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/SearchTest/data.sql")
        public void expectingFindListSuccess_givingAvailableData() {
            // Giving segmentSequence = 1001
            List<Segment> segmentList = segmentMapper.search(1001L, null, null, null, null, null, null, null, null, null, null, null, null);
            assertEquals(1, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());

            // Giving businessType = "biz_1"
            segmentList = segmentMapper.search(null, "biz_1", null, null, null, null, null, null, null, null, null, null, null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("biz_1", segmentList.get(0).getBusinessType());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals("biz_1", segmentList.get(1).getBusinessType());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals("biz_1", segmentList.get(2).getBusinessType());

            // Giving deliveryScheduledMonth = "m_2"
            segmentList = segmentMapper.search(null, null, "m_2", null, null, null, null, null, null, null, null, null, null);
            assertEquals(2, segmentList.size());
            assertEquals(1002L, segmentList.get(0).getSegmentSequence());
            assertEquals("m_2", segmentList.get(0).getDeliveryScheduledMonth());
            assertEquals(1004L, segmentList.get(1).getSegmentSequence());
            assertEquals("m_2", segmentList.get(1).getDeliveryScheduledMonth());

            // Giving brandId = "1"
            segmentList = segmentMapper.search(null, null, null, "1", null, null, null, null, null, null, null, null, null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("1", segmentList.get(0).getBrandId());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals("1", segmentList.get(1).getBrandId());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals("1", segmentList.get(2).getBrandId());

            // Giving countryId = "2"
            segmentList = segmentMapper.search(null, null, null, null, "2", null, null, null, null, null, null, null, null);
            assertEquals(2, segmentList.size());
            assertEquals(1002L, segmentList.get(0).getSegmentSequence());
            assertEquals("2", segmentList.get(0).getCountryId());
            assertEquals(1004L, segmentList.get(1).getSegmentSequence());
            assertEquals("2", segmentList.get(1).getCountryId());

            // Giving status = "1"
            segmentList = segmentMapper.search(null, null, null, null, null, "1", null, null, null, null, null, null, null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("1", segmentList.get(0).getStatus());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals("1", segmentList.get(1).getStatus());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals("1", segmentList.get(2).getStatus());

            // Giving eventTargetPeriodType = "1"
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, "1", null, null, null, null, null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("1", segmentList.get(0).getEventTargetPeriodType());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals("1", segmentList.get(1).getEventTargetPeriodType());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals("1", segmentList.get(2).getEventTargetPeriodType());

            // Giving eventTargetPeriodStartDate = 2021-11-16
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, LocalDate.of(2021, 11, 16), null, null, null, null);
            assertEquals(2, segmentList.size());
            assertEquals(1002L, segmentList.get(0).getSegmentSequence());
            assertEquals(LocalDate.of(2021, 11, 16), segmentList.get(0).getEventTargetPeriodStartDate());
            assertEquals(1004L, segmentList.get(1).getSegmentSequence());
            assertEquals(LocalDate.of(2021, 11, 16), segmentList.get(1).getEventTargetPeriodStartDate());

            // Giving eventTargetPeriodEndDate = 2021-11-16
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, null, LocalDate.of(2021, 11, 16), null, null, null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals(LocalDate.of(2021, 11, 16), segmentList.get(0).getEventTargetPeriodEndDate());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals(LocalDate.of(2021, 11, 16), segmentList.get(1).getEventTargetPeriodEndDate());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals(LocalDate.of(2021, 11, 16), segmentList.get(2).getEventTargetPeriodEndDate());

            // Giving eventTargetPeriodRelativeNumberValue = 1000
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, null, null, 1000, null, null);
            assertEquals(2, segmentList.size());
            assertEquals(1002L, segmentList.get(0).getSegmentSequence());
            assertEquals(1000, segmentList.get(0).getEventTargetPeriodRelativeNumberValue());
            assertEquals(1004L, segmentList.get(1).getSegmentSequence());
            assertEquals(1000, segmentList.get(1).getEventTargetPeriodRelativeNumberValue());

            // Giving eventTargetPeriodRelativePeriodUnit = 4
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, null, null, null, "4", null);
            assertEquals(3, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("4", segmentList.get(0).getEventTargetPeriodRelativePeriodUnit());
            assertEquals(1003L, segmentList.get(1).getSegmentSequence());
            assertEquals("4", segmentList.get(1).getEventTargetPeriodRelativePeriodUnit());
            assertEquals(1005L, segmentList.get(2).getSegmentSequence());
            assertEquals("4", segmentList.get(2).getEventTargetPeriodRelativePeriodUnit());

            // Giving segmentName = "segment_1"
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, null, null, null, null, "segment_name_1");
            assertEquals(1, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals("segment_name_1", segmentList.get(0).getSegmentName());

            // Giving all params = null
            segmentList = segmentMapper.search(null, null, null, null, null, null, null, null, null, null, null, null, null);
            assertEquals(5, segmentList.size());
            assertEquals(1001L, segmentList.get(0).getSegmentSequence());
            assertEquals(1002L, segmentList.get(1).getSegmentSequence());
            assertEquals(1003L, segmentList.get(2).getSegmentSequence());
            assertEquals(1004L, segmentList.get(3).getSegmentSequence());
            assertEquals(1005L, segmentList.get(4).getSegmentSequence());
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            Segment segment = new Segment();
            segment.setBusinessType("biz_1");
            segment.setBrandId("1");
            segment.setCountryId("1");
            segment.setDeliveryScheduledMonth("m_1");
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
            segment.setAdditionalConditionLogicalOperatorType("2");
            segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            segment.setCreateUserIdForAudit("user_1");
            segment.setUpdateDateTimeForAudit((LocalDateTime.of(2021, 11, 20, 10, 10, 10)));
            segment.setUpdateUserIdForAudit("user_1");
            segmentMapper.create(segment);
            List<Segment> resultList = segmentMapper.findAll();
            assertEquals(4, resultList.size());
            assertEquals(1001L, resultList.get(0).getSegmentSequence());
            assertEquals(1002L, resultList.get(1).getSegmentSequence());
            assertEquals(1003L, resultList.get(2).getSegmentSequence());
            // Check new records
            assertEquals("biz_1", resultList.get(3).getBusinessType());
            assertEquals("1", resultList.get(3).getBrandId());
            assertEquals("1", resultList.get(3).getCountryId());
            assertEquals("m_1", resultList.get(3).getDeliveryScheduledMonth());
            assertEquals("1", resultList.get(3).getEventTargetPeriodType());
            assertEquals(LocalDate.of(1970, 1, 1), resultList.get(3).getEventTargetPeriodStartDate());
            assertEquals(LocalDate.of(1970, 2, 2), resultList.get(3).getEventTargetPeriodEndDate());
            assertEquals(2001L, resultList.get(3).getEventTargetPeriodRelativeNumberValue());
            assertEquals("item_category_1", resultList.get(3).getTargetItemCategory());
            assertEquals("segment_name_1", resultList.get(3).getSegmentName());
            assertEquals("desc_1", resultList.get(3).getDescription());
            assertEquals("1", resultList.get(3).getSqlEditFlag());
            assertEquals("sql_1", resultList.get(3).getEditedSql());
            assertEquals("1", resultList.get(3).getStatus());
            assertEquals("{}", resultList.get(3).getEventTypeList());
            assertEquals("2", resultList.get(3).getAdditionalConditionLogicalOperatorType());
            assertEquals(LocalDateTime.of(2021, 11, 20, 10, 10, 10), resultList.get(3).getCreateDateTimeForAudit());
            assertEquals("user_1", resultList.get(3).getCreateUserIdForAudit());
            assertEquals(LocalDateTime.of(2021, 11, 20, 10, 10, 10), resultList.get(3).getUpdateDateTimeForAudit());
            assertEquals("user_1", resultList.get(3).getUpdateUserIdForAudit());
        }
    }

    @Nested
    public class CreateSqlSegmentTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,biz_1,1,1,m_1,1,1970-01-01,1970-02-02,2001,1,item_category_1,segment_name_1,desc_1,1,sql_1,1,{},1,f,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1",
                    "1002,biz_2,2,2,m_2,2,1970-01-01,1970-02-02,2002,2,item_category_2,segment_name_2,desc_2,2,sql_2,2,{},2,f,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2",
                    "1003,biz_3,3,3,m_3,3,1970-01-01,1970-02-02,2003,3,item_category_3,segment_name_3,desc_3,3,sql_3,3,{},1,f,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3"
            );

            Segment segment = new Segment();
            segment.setBusinessType("biz_1");
            segment.setBrandId("1");
            segment.setCountryId("1");
            segment.setDeliveryScheduledMonth("m_1");
            segment.setSegmentName("segment_name_1");
            segment.setSqlEditFlag("1");
            segment.setEditedSql("sql_1");
            segment.setStatus("1");
            segment.setCreateDateTimeForAudit(LocalDateTime.of(2021, 11, 20, 10, 10, 10));
            segment.setCreateUserIdForAudit("user_1");
            segment.setUpdateDateTimeForAudit((LocalDateTime.of(2021, 11, 20, 10, 10, 10)));
            segment.setUpdateUserIdForAudit("user_1");
            segmentMapper.createSqlSegment(segment);
            assertQueryResult("SELECT * FROM t_sgmt ORDER BY sgmt_seq ASC").isSameDataAs(
                    "1001,biz_1,1,1,m_1,1,1970-01-01,1970-02-02,2001,1,item_category_1,segment_name_1,desc_1,1,sql_1,1,{},1,f,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1,1970-01-01 00:00:00,user_id_1",
                    "1002,biz_2,2,2,m_2,2,1970-01-01,1970-02-02,2002,2,item_category_2,segment_name_2,desc_2,2,sql_2,2,{},2,f,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2,1970-01-01 00:00:00,user_id_2",
                    "1003,biz_3,3,3,m_3,3,1970-01-01,1970-02-02,2003,3,item_category_3,segment_name_3,desc_3,3,sql_3,3,{},1,f,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3,1970-01-01 00:00:00,user_id_3",
                    "1004,biz_1,1,1,m_1,null,null,null,null,null,null,segment_name_1,null,1,sql_1,1,null,null,f,2021-11-20 10:10:10,user_1,null,null,2021-11-20 10:10:10,user_1"
            );
        }
    }

    @Nested
    public class FindByIdTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/FindByIdTest/data.sql")
        public void expectingFindSuccess_givingManyCases() {
            // Giving available id
            Segment result = segmentMapper.findById(1001L);
            assertThat(result.getSegmentSequence()).isEqualTo(1001L);
            assertThat(result.getBusinessType()).isEqualTo("biz_1");
            assertThat(result.getBrandId()).isEqualTo("1");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getDeliveryScheduledMonth()).isEqualTo("m_1");
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
            assertThat(result.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(result.getCreateUserIdForAudit()).isEqualTo("user_id_1");
            assertThat(result.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(result.getUpdateUserIdForAudit()).isEqualTo("user_id_1");

            // Giving not found id
            result = segmentMapper.findById(9999L);
            assertThat(result).isEqualTo(null);

            // Giving null id
            result = segmentMapper.findById(null);
            assertThat(result).isEqualTo(null);
        }
    }

    @Nested
    public class UpdateTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/UpdateTest/data.sql")
        public void expectingUpdateSuccess_givingAvailableParams() {
            // Giving available id
            Segment segment = segmentMapper.findById(1001L);
            segment.setBusinessType("new_biz_1");
            segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 11, 20));
            segment.setSegmentName("new_segment_name_1");
            segmentMapper.update(segment);

            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin values
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getBrandId()).isEqualTo("1");
            assertThat(resultList.get(0).getCountryId()).isEqualTo("1");
            assertThat(resultList.get(0).getDeliveryScheduledMonth()).isEqualTo("m_1");
            assertThat(resultList.get(0).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(0).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2001L);
            assertThat(resultList.get(0).getTargetItemCategory()).isEqualTo("item_category_1");
            assertThat(resultList.get(0).getDescription()).isEqualTo("desc_1");
            assertThat(resultList.get(0).getSqlEditFlag()).isEqualTo("1");
            assertThat(resultList.get(0).getEditedSql()).isEqualTo("sql_1");
            assertThat(resultList.get(0).getStatus()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(0).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getCreateUserIdForAudit()).isEqualTo("user_id_1");
            assertThat(resultList.get(0).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getUpdateUserIdForAudit()).isEqualTo("user_id_1");
            // Check new updated values
            assertThat(resultList.get(0).getBusinessType()).isEqualTo("new_biz_1");
            assertThat(resultList.get(0).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(2021, 11, 20));
            assertThat(resultList.get(0).getSegmentName()).isEqualTo("new_segment_name_1");
            // Check unchanged records
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getBrandId()).isEqualTo("2");
            assertThat(resultList.get(1).getCountryId()).isEqualTo("2");
            assertThat(resultList.get(1).getDeliveryScheduledMonth()).isEqualTo("m_2");
            assertThat(resultList.get(1).getEventTargetPeriodType()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(1).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2002L);
            assertThat(resultList.get(1).getTargetItemCategory()).isEqualTo("item_category_2");
            assertThat(resultList.get(1).getDescription()).isEqualTo("desc_2");
            assertThat(resultList.get(1).getSqlEditFlag()).isEqualTo("2");
            assertThat(resultList.get(1).getEditedSql()).isEqualTo("sql_2");
            assertThat(resultList.get(1).getStatus()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(1).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(1).getCreateUserIdForAudit()).isEqualTo("user_id_2");
            assertThat(resultList.get(1).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(1).getUpdateUserIdForAudit()).isEqualTo("user_id_2");
            assertThat(resultList.get(1).getBusinessType()).isEqualTo("biz_2");
            assertThat(resultList.get(1).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(1).getSegmentName()).isEqualTo("segment_name_2");
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getBrandId()).isEqualTo("3");
            assertThat(resultList.get(2).getCountryId()).isEqualTo("3");
            assertThat(resultList.get(2).getDeliveryScheduledMonth()).isEqualTo("m_3");
            assertThat(resultList.get(2).getEventTargetPeriodType()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(2).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2003L);
            assertThat(resultList.get(2).getTargetItemCategory()).isEqualTo("item_category_3");
            assertThat(resultList.get(2).getDescription()).isEqualTo("desc_3");
            assertThat(resultList.get(2).getSqlEditFlag()).isEqualTo("3");
            assertThat(resultList.get(2).getEditedSql()).isEqualTo("sql_3");
            assertThat(resultList.get(2).getStatus()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(2).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(2).getCreateUserIdForAudit()).isEqualTo("user_id_3");
            assertThat(resultList.get(2).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(2).getUpdateUserIdForAudit()).isEqualTo("user_id_3");
            assertThat(resultList.get(2).getBusinessType()).isEqualTo("biz_3");
            assertThat(resultList.get(2).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(2).getSegmentName()).isEqualTo("segment_name_3");
        }

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/UpdateTest/data.sql")
        public void expectingDoNotUpdate_givingNotFoundId() {
            Segment segment = segmentMapper.findById(1002L);
            segment.setSegmentSequence(9999L);
            segment.setBusinessType("new_biz_1");
            segment.setEventTargetPeriodStartDate(LocalDate.of(2021, 11, 20));
            segment.setSegmentName("new_segment_name_1");
            segmentMapper.update(segment);

            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin values
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getBrandId()).isEqualTo("1");
            assertThat(resultList.get(0).getCountryId()).isEqualTo("1");
            assertThat(resultList.get(0).getDeliveryScheduledMonth()).isEqualTo("m_1");
            assertThat(resultList.get(0).getEventTargetPeriodType()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(0).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2001L);
            assertThat(resultList.get(0).getTargetItemCategory()).isEqualTo("item_category_1");
            assertThat(resultList.get(0).getDescription()).isEqualTo("desc_1");
            assertThat(resultList.get(0).getSqlEditFlag()).isEqualTo("1");
            assertThat(resultList.get(0).getEditedSql()).isEqualTo("sql_1");
            assertThat(resultList.get(0).getStatus()).isEqualTo("1");
            assertThat(resultList.get(0).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(0).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getCreateUserIdForAudit()).isEqualTo("user_id_1");
            assertThat(resultList.get(0).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getUpdateUserIdForAudit()).isEqualTo("user_id_1");
            assertThat(resultList.get(0).getBusinessType()).isEqualTo("biz_1");
            assertThat(resultList.get(0).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(0).getSegmentName()).isEqualTo("segment_name_1");
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getBrandId()).isEqualTo("2");
            assertThat(resultList.get(1).getCountryId()).isEqualTo("2");
            assertThat(resultList.get(1).getDeliveryScheduledMonth()).isEqualTo("m_2");
            assertThat(resultList.get(1).getEventTargetPeriodType()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(1).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2002L);
            assertThat(resultList.get(1).getTargetItemCategory()).isEqualTo("item_category_2");
            assertThat(resultList.get(1).getDescription()).isEqualTo("desc_2");
            assertThat(resultList.get(1).getSqlEditFlag()).isEqualTo("2");
            assertThat(resultList.get(1).getEditedSql()).isEqualTo("sql_2");
            assertThat(resultList.get(1).getStatus()).isEqualTo("2");
            assertThat(resultList.get(1).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(1).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(1).getCreateUserIdForAudit()).isEqualTo("user_id_2");
            assertThat(resultList.get(1).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(1).getUpdateUserIdForAudit()).isEqualTo("user_id_2");
            assertThat(resultList.get(1).getBusinessType()).isEqualTo("biz_2");
            assertThat(resultList.get(1).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(1).getSegmentName()).isEqualTo("segment_name_2");
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
            assertThat(resultList.get(2).getBrandId()).isEqualTo("3");
            assertThat(resultList.get(2).getCountryId()).isEqualTo("3");
            assertThat(resultList.get(2).getDeliveryScheduledMonth()).isEqualTo("m_3");
            assertThat(resultList.get(2).getEventTargetPeriodType()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTargetPeriodEndDate()).isEqualTo(LocalDate.of(1970, 2, 2));
            assertThat(resultList.get(2).getEventTargetPeriodRelativeNumberValue()).isEqualTo(2003L);
            assertThat(resultList.get(2).getTargetItemCategory()).isEqualTo("item_category_3");
            assertThat(resultList.get(2).getDescription()).isEqualTo("desc_3");
            assertThat(resultList.get(2).getSqlEditFlag()).isEqualTo("3");
            assertThat(resultList.get(2).getEditedSql()).isEqualTo("sql_3");
            assertThat(resultList.get(2).getStatus()).isEqualTo("3");
            assertThat(resultList.get(2).getEventTypeList()).isEqualTo("{}");
            assertThat(resultList.get(2).getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(2).getCreateUserIdForAudit()).isEqualTo("user_id_3");
            assertThat(resultList.get(2).getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
            assertThat(resultList.get(2).getUpdateUserIdForAudit()).isEqualTo("user_id_3");
            assertThat(resultList.get(2).getBusinessType()).isEqualTo("biz_3");
            assertThat(resultList.get(2).getEventTargetPeriodStartDate()).isEqualTo(LocalDate.of(1970, 1, 1));
            assertThat(resultList.get(2).getSegmentName()).isEqualTo("segment_name_3");
        }
    }

    @Nested
    public class DeleteTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/DeleteTest/data.sql")
        public void expectingDeleteSuccess_givingAvailableParams() {
            // Giving available id
            Segment segment = segmentMapper.findById(1001L);
            segmentMapper.delete(segment);
            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1003L);
        }

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/DeleteTest/data.sql")
        public void expectingDoNotDelete_givingInvalidParams() {
            Segment segment = segmentMapper.findById(9999L);
            segmentMapper.delete(segment);
            List<Segment> resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);

            segmentMapper.delete(null);
            resultList = segmentMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class FindListBySegmentNameTest {

        @Autowired
        private SegmentMapper segmentMapper;

        @Test
        @Sql("/domain/mappers/SegmentMapperTest/FindListBySegmentNameTest/data.sql")
        public void expectingDeleteSuccess_givingAvailableParams() {
            List<Segment> resultList = segmentMapper.findListBySegmentName("segment_name");
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(1003L);

            resultList = segmentMapper.findListBySegmentName("segment_name_1");
            assertThat(resultList.size()).isEqualTo(1);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(1001L);

            resultList = segmentMapper.findListBySegmentName("hoge");
            assertThat(resultList.size()).isEqualTo(0);
        }
    }
}
