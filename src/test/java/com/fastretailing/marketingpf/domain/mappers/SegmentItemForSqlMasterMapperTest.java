package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SegmentItemForSqlMasterMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

        @Test
        @Sql("/domain/mappers/SegmentItemForSqlMasterMapperTest/FindByIdTest/data.sql")
        public void expectingFindSuccess_givingAvailableParam() {
            SegmentItemForSqlMaster result = segmentItemForSqlMasterMapper.findById(1001L);
            assertThat(result.getSegmentItemForSqlSequence()).isEqualTo(1001L);
            assertThat(result.getSegmentItemForScreenSequence()).isEqualTo(2001L);
            assertThat(result.getBrandId()).isEqualTo("1");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getEventType()).isEqualTo("1");
            assertThat(result.getSegmentItemName()).isEqualTo("name_01");
            assertThat(result.getQueryForSegment()).isEqualTo("query_01");

            result = segmentItemForSqlMasterMapper.findById(1003L);
            assertThat(result.getSegmentItemForSqlSequence()).isEqualTo(1003L);
            assertThat(result.getSegmentItemForScreenSequence()).isEqualTo(2003L);
            assertThat(result.getBrandId()).isEqualTo("3");
            assertThat(result.getCountryId()).isEqualTo("3");
            assertThat(result.getEventType()).isEqualTo("3");
            assertThat(result.getSegmentItemName()).isEqualTo("name_03");
            assertThat(result.getQueryForSegment()).isEqualTo("query_03");
        }
    }

    @Nested
    public class FindBySegmentItemForScreenSequenceAndConditionsTest {

        @Autowired
        public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

        @Test
        @Sql("/domain/mappers/SegmentItemForSqlMasterMapperTest/FindBySegmentItemForScreenSequenceAndConditionsTest/data.sql")
        public void expectingFindSuccess_givingAvailableParam() {
            List<SegmentItemForSqlMaster> list1 = segmentItemForSqlMasterMapper.findBySegmentItemForScreenSequenceAndConditions(2001L, Arrays.asList("13", "23", "33"), "11", "12");
            SegmentItemForSqlMaster result_1001 = list1.get(0);
            assertThat(result_1001.getSegmentItemForSqlSequence()).isEqualTo(1001L);
            assertThat(result_1001.getSegmentItemForScreenSequence()).isEqualTo(2001L);
            assertThat(result_1001.getBrandId()).isEqualTo("11");
            assertThat(result_1001.getCountryId()).isEqualTo("12");
            assertThat(result_1001.getEventType()).isEqualTo("13");

            List<SegmentItemForSqlMaster> list2 = segmentItemForSqlMasterMapper.findBySegmentItemForScreenSequenceAndConditions(2002L, Arrays.asList("43"), "41", "42");
            SegmentItemForSqlMaster result_1004 = list2.get(0);
            assertThat(result_1004.getSegmentItemForSqlSequence()).isEqualTo(1004L);
            assertThat(result_1004.getSegmentItemForScreenSequence()).isEqualTo(2002L);
            assertThat(result_1004.getBrandId()).isEqualTo("41");
            assertThat(result_1004.getCountryId()).isEqualTo("42");
            assertThat(result_1004.getEventType()).isEqualTo("43");

            List<SegmentItemForSqlMaster> list3  = segmentItemForSqlMasterMapper.findBySegmentItemForScreenSequenceAndConditions(2003L, Arrays.asList("1", "2", "3"), "1", "1");
            assertThat(list3).isEmpty();
        }
    }

    @Nested
    public class FindByConditionsTest {

        @Autowired
        public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

        @Test
        @Sql("/domain/mappers/SegmentItemForSqlMasterMapperTest/FindByConditionsTest/data.sql")
        public void expectingFindSuccess_givingAvailableParam() {
            SegmentItemForSqlMaster result_1001 = segmentItemForSqlMasterMapper.findByConditions(2001L, "11", "12");
            assertThat(result_1001.getSegmentItemForSqlSequence()).isEqualTo(1001L);
            assertThat(result_1001.getSegmentItemForScreenSequence()).isEqualTo(2001L);
            assertThat(result_1001.getBrandId()).isEqualTo("11");
            assertThat(result_1001.getCountryId()).isEqualTo("12");
            assertThat(result_1001.getEventType()).isEqualTo("13");

            SegmentItemForSqlMaster result_1004 = segmentItemForSqlMasterMapper.findByConditions(2002L, "41", "42");
            assertThat(result_1004.getSegmentItemForSqlSequence()).isEqualTo(1004L);
            assertThat(result_1004.getSegmentItemForScreenSequence()).isEqualTo(2002L);
            assertThat(result_1004.getBrandId()).isEqualTo("41");
            assertThat(result_1004.getCountryId()).isEqualTo("42");
            assertThat(result_1004.getEventType()).isEqualTo("43");

            SegmentItemForSqlMaster result_null = segmentItemForSqlMasterMapper.findByConditions(2003L, "1", "1");
            assertThat(result_null).isEqualTo(null);

            // expecting throw exception when result more than 1
            try {
                segmentItemForSqlMasterMapper.findByConditions(2003L, "71", "72");
                fail("");
            } catch (Exception ignored) {}
        }
    }
}
