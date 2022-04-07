package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SegmentItemForScreenMasterServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {
        @Autowired
        public SegmentItemForScreenMasterService segmentItemForScreenMasterService;

        @Test
        @Sql("/services/SegmentItemForScreenMasterServiceTest/FindAllTest/m_sgmt_itm_f_scrn.sql")
        public void expectingSuccess() {
            List<SegmentItemForScreenMaster> segmentItemForScreenMasterList = segmentItemForScreenMasterService.findAll();
            assertThat(segmentItemForScreenMasterList.size()).isEqualTo(2);
            assertThat(segmentItemForScreenMasterList.get(0).getSegmentItemForScreenSequence()).isEqualTo(1002L);
            assertThat(segmentItemForScreenMasterList.get(0).getCategory()).isEqualTo("large2");
            assertThat(segmentItemForScreenMasterList.get(0).getOperatorList()).isEqualTo("{}");
            assertThat(segmentItemForScreenMasterList.get(1).getSegmentItemForScreenSequence()).isEqualTo(1003L);
            assertThat(segmentItemForScreenMasterList.get(1).getCategory()).isEqualTo("large3");
            assertThat(segmentItemForScreenMasterList.get(1).getOperatorList()).isEqualTo("{}");
        }
    }
}
