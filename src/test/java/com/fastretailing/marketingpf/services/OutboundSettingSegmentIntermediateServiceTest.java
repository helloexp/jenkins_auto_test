package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutboundSettingSegmentIntermediateServiceTest extends BaseSpringBootTest {

    @Nested
    public class CreateTest {

        @Autowired
        public OutboundSettingSegmentIntermediateService outboundSettingSegmentIntermediateService;

        @Test
        @Sql("/services/OutboundSettingSegmentIntermediateServiceTest/CreateTest/t_outbound_stng_sgmt_intrm.sql")
        public void expectingCreateSuccess() {
            outboundSettingSegmentIntermediateService.create(2003L, 3003L, LocalDateTime.of(2021, 3, 3, 0, 0, 0), "user_03");
            assertQueryResult("SELECT * FROM t_outbound_stng_sgmt_intrm ORDER BY outbound_stng_sgmt_intrm_seq ASC").isSameDataAs(
                    "1001,2001,3001,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2002,3002,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,2003,3003,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03");

            outboundSettingSegmentIntermediateService.create(2004L, 3001L, LocalDateTime.of(2021, 3, 4, 0, 0, 0), "user_04");
            assertQueryResult("SELECT * FROM t_outbound_stng_sgmt_intrm ORDER BY outbound_stng_sgmt_intrm_seq ASC").isSameDataAs(
                    "1001,2001,3001,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2002,3002,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,2003,3003,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03",
                    "1004,2004,3001,f,2021-03-04 00:00:00,user_04,null,null,2021-03-04 00:00:00,user_04");
        }
    }

    @Nested
    public class FindByOutboundSettingSequenceTest {

        @Autowired
        public OutboundSettingSegmentIntermediateService outboundSettingSegmentIntermediateService;

        @Test
        @Sql("/services/OutboundSettingSegmentIntermediateServiceTest/FindByOutboundSettingSequenceTest/t_outbound_stng_sgmt_intrm.sql")
        public void expectingSuccess() {
            List<OutboundSettingSegmentIntermediate> resultList = outboundSettingSegmentIntermediateService.findByOutboundSettingSequence(2001L);
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1005L);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(3001L);
            assertThat(resultList.get(1).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(3002L);

            resultList = outboundSettingSegmentIntermediateService.findByOutboundSettingSequence(2002L);
            assertThat(resultList.size()).isEqualTo(1);
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1002L);
        }
    }
}
