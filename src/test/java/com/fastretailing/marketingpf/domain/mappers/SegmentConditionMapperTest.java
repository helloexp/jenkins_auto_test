package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SegmentConditionMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindListBySegmentSequenceTest {

        @Autowired
        public SegmentConditionMapper segmentConditionMapper;

        @Test
        @Sql("/domain/mappers/SegmentConditionMapperTest/FindListBySegmentSequenceTest/data.sql")
        public void expectingFindListSuccess_givingAvailableParams() {
            List<SegmentCondition> resultList = segmentConditionMapper.findListBySegmentSequence(2001L);
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getSegmentConditionSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentConditionSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getSegmentConditionSequence()).isEqualTo(1003L);
            // Check reference records
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getSegmentItemForScreenSequence()).isEqualTo(3001L);
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getSegmentItemForSqlSequence()).isEqualTo(4001L);
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getBrandId()).isEqualTo("090");
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getCountryId()).isEqualTo("1");
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getEventType()).isEqualTo("12");
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getSegmentItemName()).isEqualTo("name_01");
            assertThat(resultList.get(0).getSegmentItemForSqlMaster().getQueryForSegment()).isEqualTo("query_01");

            assertThat(resultList.get(1).getSegmentItemForSqlMaster().getSegmentItemForSqlSequence()).isEqualTo(4002L);
            assertThat(resultList.get(2).getSegmentItemForSqlMaster().getSegmentItemForSqlSequence()).isEqualTo(4003L);

            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getSegmentItemForScreenSequence()).isEqualTo(3001L);
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getCategory()).isEqualTo("cat1");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getDetailCategory()).isEqualTo("dcat1");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getSegmentItemName()).isEqualTo("segment_item_name_1");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getInputType()).isEqualTo("1");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getOperatorList()).isEqualTo("[1]");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getChoicesList()).isEqualTo("[]");
            assertThat(resultList.get(0).getSegmentItemForScreenMaster().getUrlForApiAccess()).isEqualTo("http://hoge.com");

            assertThat(resultList.get(1).getSegmentItemForScreenMaster().getSegmentItemForScreenSequence()).isEqualTo(3002L);
            assertThat(resultList.get(2).getSegmentItemForScreenMaster().getSegmentItemForScreenSequence()).isEqualTo(3003L);

            resultList = segmentConditionMapper.findListBySegmentSequence(9999L);
            assertThat(resultList.size()).isEqualTo(0);

            resultList = segmentConditionMapper.findListBySegmentSequence(null);
            assertThat(resultList.size()).isEqualTo(0);

            List<SegmentCondition> resultList2 = segmentConditionMapper.findListBySegmentSequence(2006L);
            assertThat(resultList2.size()).isEqualTo(1);
            assertThat(resultList2.get(0).getSegmentConditionSequence()).isEqualTo(1006L);
            assertThat(resultList2.get(0).getSegmentItemForSqlMaster()).isNull();
            assertThat(resultList2.get(0).getSegmentItemForScreenMaster()).isNull();
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public SegmentConditionMapper segmentConditionMapper;

        @Test
        @Sql("/domain/mappers/SegmentConditionMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            assertQueryResult("SELECT * FROM t_sgmt_cond ORDER BY sgmt_cond_seq ASC")
                    .isSameDataAs(
                    "1001,2001,3001,4001,1,[\"1\"],1,1,f,2021-01-01 00:00:01,user_01,null,null,2021-01-01 00:00:02,user_01",
                    "1002,2002,3002,4002,2,[\"2\"],1,2,f,2021-02-02 00:00:01,user_02,null,null,2021-02-02 00:00:02,user_02");

            SegmentCondition condition_03 = new SegmentCondition();
            condition_03.setSegmentSequence(2003L);
            condition_03.setSegmentItemForScreenSequence(3003L);
            condition_03.setSegmentItemForSqlSequence(4003L);
            condition_03.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            condition_03.setComparisonValue("[\"hoge\"]");
            condition_03.setSegmentConditionBlockOrder(1);
            condition_03.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            condition_03.setDeletionFlagForAudit("f");
            condition_03.setCreateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 1));
            condition_03.setCreateUserIdForAudit("user_03");
            condition_03.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 2));
            condition_03.setUpdateUserIdForAudit("user_03");
            segmentConditionMapper.create(condition_03);

            assertQueryResult("SELECT * FROM t_sgmt_cond ORDER BY sgmt_cond_seq ASC").isSameDataAs(
                    "1001,2001,3001,4001,1,[\"1\"],1,1,f,2021-01-01 00:00:01,user_01,null,null,2021-01-01 00:00:02,user_01",
                    "1002,2002,3002,4002,2,[\"2\"],1,2,f,2021-02-02 00:00:01,user_02,null,null,2021-02-02 00:00:02,user_02",
                    "1003,2003,3003,4003,3,[\"hoge\"],1,1,f,2021-03-03 00:00:01,user_03,null,null,2021-03-03 00:00:02,user_03");
        }
    }

    @Nested
    public class FindByIdTest {

        @Autowired
        public SegmentConditionMapper segmentConditionMapper;

        @Test
        @Sql("/domain/mappers/SegmentConditionMapperTest/FindByIdTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            SegmentCondition condition_1001 = segmentConditionMapper.findById(1001L);
            assertThat(condition_1001.getSegmentConditionSequence()).isEqualTo(1001L);
            assertThat(condition_1001.getSegmentSequence()).isEqualTo(2001L);
            assertThat(condition_1001.getSegmentItemForScreenSequence()).isEqualTo(3001L);
            assertThat(condition_1001.getSegmentItemForSqlSequence()).isEqualTo(4001L);
            assertThat(condition_1001.getOperatorTypeAsEnum()).isEqualTo(OPERATOR_TYPE.EQUAL);
            assertThat(condition_1001.getComparisonValue()).isEqualTo("val_01");
            assertThat(condition_1001.getSegmentConditionBlockOrder()).isEqualTo(1);
            assertThat(condition_1001.getLogicalOperatorTypeAsEnum()).isEqualTo(LOGICAL_OPERATOR_TYPE.AND);

            SegmentCondition condition_1003 = segmentConditionMapper.findById(1003L);
            assertThat(condition_1003.getSegmentConditionSequence()).isEqualTo(1003L);
            assertThat(condition_1003.getSegmentSequence()).isEqualTo(2001L);
            assertThat(condition_1003.getSegmentItemForScreenSequence()).isEqualTo(3003L);
            assertThat(condition_1003.getSegmentItemForSqlSequence()).isEqualTo(4003L);
            assertThat(condition_1003.getOperatorTypeAsEnum()).isEqualTo(OPERATOR_TYPE.IN);
            assertThat(condition_1003.getComparisonValue()).isEqualTo("val_03");
            assertThat(condition_1003.getSegmentConditionBlockOrder()).isEqualTo(1);
            assertThat(condition_1003.getLogicalOperatorTypeAsEnum()).isEqualTo(LOGICAL_OPERATOR_TYPE.AND);

            assertThat(segmentConditionMapper.findById(9999L)).isEqualTo(null);
            assertThat(segmentConditionMapper.findById(null)).isEqualTo(null);
        }
    }

    @Nested
    public class DeleteTest {

        @Autowired
        public SegmentConditionMapper segmentConditionMapper;

        @Test
        @Sql("/domain/mappers/SegmentConditionMapperTest/DeleteTest/data.sql")
        public void expectingDeleteSuccess_givingAvailableParams() {
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,f,null,null",
                    "1002,f,null,null",
                    "1003,f,null,null"
            );

            SegmentCondition segmentCondition_1001 = new SegmentCondition();
            segmentCondition_1001.setSegmentConditionSequence(1001L);
            segmentCondition_1001.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 1, 1, 0, 0, 11));
            segmentCondition_1001.setDeletionUserIdForAudit("user_11");
            segmentConditionMapper.delete(segmentCondition_1001);
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,t,2021-01-01 00:00:11,user_11",
                    "1002,f,null,null",
                    "1003,f,null,null"
            );

            SegmentCondition segmentCondition_1003 = new SegmentCondition();
            segmentCondition_1003.setSegmentConditionSequence(1003L);
            segmentCondition_1003.setDeletionDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 13));
            segmentCondition_1003.setDeletionUserIdForAudit("user_13");
            segmentConditionMapper.delete(segmentCondition_1003);
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,t,2021-01-01 00:00:11,user_11",
                    "1002,f,null,null",
                    "1003,t,2021-03-03 00:00:13,user_13"
            );
        }
    }
}
