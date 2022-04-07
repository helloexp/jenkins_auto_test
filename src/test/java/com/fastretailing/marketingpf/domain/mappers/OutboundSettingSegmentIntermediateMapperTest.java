package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutboundSettingSegmentIntermediateMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {

        @Autowired
        public OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingSegmentIntermediateMapperTest/FindAllTest/data.sql")
        public void expectingFindSuccess() {
            List<OutboundSettingSegmentIntermediate> resultList = outboundSettingSegmentIntermediateMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class FindByOutboundSettingSequenceTest {

        @Autowired
        public OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingSegmentIntermediateMapperTest/FindByOutboundSettingSequenceTest/t_outbound_stng_sgmt_intrm.sql")
        public void expectingSuccess() {
            List<OutboundSettingSegmentIntermediate> resultList = outboundSettingSegmentIntermediateMapper.findByOutboundSettingSequence(2001L);
            assertThat(resultList.size()).isEqualTo(2);
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1005L);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(3001L);
            assertThat(resultList.get(1).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(3002L);

            resultList = outboundSettingSegmentIntermediateMapper.findByOutboundSettingSequence(2002L);
            assertThat(resultList.size()).isEqualTo(1);
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1002L);
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingSegmentIntermediateMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            OutboundSettingSegmentIntermediate outbound_03 = new OutboundSettingSegmentIntermediate();
            outbound_03.setOutboundSettingSequence(2003L);
            outbound_03.setSegmentSequence(3003L);
            outbound_03.setDeletionFlagForAudit("f");
            outbound_03.setCreateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outbound_03.setCreateUserIdForAudit("user_03");
            outbound_03.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outbound_03.setUpdateUserIdForAudit("user_03");
            outboundSettingSegmentIntermediateMapper.create(outbound_03);

            List<OutboundSettingSegmentIntermediate> resultList = outboundSettingSegmentIntermediateMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(resultList.get(0).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(resultList.get(0).getSegmentSequence()).isEqualTo(3001L);
            assertThat(resultList.get(1).getOutboundSettingSegmentIntermediateSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getOutboundSettingSequence()).isEqualTo(2002L);
            assertThat(resultList.get(1).getSegmentSequence()).isEqualTo(3002L);
            // Check new records
            assertThat(resultList.get(2).getOutboundSettingSequence()).isEqualTo(2003L);
            assertThat(resultList.get(2).getSegmentSequence()).isEqualTo(3003L);
        }
    }
}
