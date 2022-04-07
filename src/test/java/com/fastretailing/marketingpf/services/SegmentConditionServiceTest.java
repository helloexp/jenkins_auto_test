package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SegmentConditionServiceTest extends BaseSpringBootTest {

    @Nested
    public class CreateTest {

        @Autowired
        public SegmentConditionService segmentConditionService;

        @Test
        @Sql("/services/SegmentConditionServiceTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            assertQueryResult("SELECT * FROM t_sgmt_cond ORDER BY sgmt_cond_seq ASC")
                    .isSameDataAs(
                            "1001,2001,3001,4001,1,[\"val_01\"],1,1,f,2021-01-01 00:00:01,user_01,null,null,2021-01-01 00:00:02,user_01",
                            "1002,2002,3002,4002,2,[\"val_02\"],1,2,f,2021-02-02 00:00:01,user_02,null,null,2021-02-02 00:00:02,user_02");

            segmentConditionService.create(2003L, 3003L, 4003L, OPERATOR_TYPE.IN, "[\"val_03\"]", 1, LOGICAL_OPERATOR_TYPE.AND, LocalDateTime.of(2021, 3, 3, 0, 0, 1), "user_03");

            assertQueryResult("SELECT * FROM t_sgmt_cond ORDER BY sgmt_cond_seq ASC").isSameDataAs(
                    "1001,2001,3001,4001,1,[\"val_01\"],1,1,f,2021-01-01 00:00:01,user_01,null,null,2021-01-01 00:00:02,user_01",
                    "1002,2002,3002,4002,2,[\"val_02\"],1,2,f,2021-02-02 00:00:01,user_02,null,null,2021-02-02 00:00:02,user_02",
                    "1003,2003,3003,4003,3,[\"val_03\"],1,1,f,2021-03-03 00:00:01,user_03,null,null,2021-03-03 00:00:01,user_03");
        }
    }

    @Nested
    public class FindListBySegmentSequenceTest {

        @Autowired
        public SegmentConditionService segmentConditionService;

        @Test
        @Sql("/services/SegmentConditionServiceTest/FindListBySegmentSequence/data.sql")
        public void expectingFindListSuccess() {
            List<SegmentCondition> resultList = segmentConditionService.findListBySegmentSequence(2001L);
            assertThat(resultList.size()).isEqualTo(5);
            assertThat(resultList.get(0).getSegmentConditionSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentConditionSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getSegmentConditionSequence()).isEqualTo(1003L);
            assertThat(resultList.get(3).getSegmentConditionSequence()).isEqualTo(1005L);
            assertThat(resultList.get(4).getSegmentConditionSequence()).isEqualTo(1006L);
        }
    }

    @Nested
    public class DeleteTest {

        @Autowired
        public SegmentConditionService segmentConditionService;

        @Test
        @Sql("/services/SegmentConditionServiceTest/DeleteTest/data.sql")
        public void expectingDeleteSuccess_givingAvailableParams() {
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,f,null,null",
                    "1002,f,null,null",
                    "1003,f,null,null"
            );

            segmentConditionService.delete(1001L, LocalDateTime.of(2021, 1, 1, 0, 0, 11), "user_11");
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,t,2021-01-01 00:00:11,user_11",
                    "1002,f,null,null",
                    "1003,f,null,null"
            );

            segmentConditionService.delete(1003L, LocalDateTime.of(2021, 3, 3, 0, 0, 13), "user_13");
            assertQueryResult("SELECT sgmt_cond_seq, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_sgmt_cond ORDER BY sgmt_cond_seq").isSameDataAs(
                    "1001,t,2021-01-01 00:00:11,user_11",
                    "1002,f,null,null",
                    "1003,t,2021-03-03 00:00:13,user_13"
            );
        }

        @Test
        public void expectingResourceNotFoundException_givingInvalidParams() {
            try {
                segmentConditionService.delete(9999L, LocalDateTime.of(2021, 1, 1, 0, 0, 11), "user_11");
                fail("");
            } catch (ResourceNotFoundException ignored) {}

            try {
                segmentConditionService.delete(null, LocalDateTime.of(2021, 1, 1, 0, 0, 11), "user_11");
                fail("");
            } catch (ResourceNotFoundException ignored) {}
        }
    }
}
