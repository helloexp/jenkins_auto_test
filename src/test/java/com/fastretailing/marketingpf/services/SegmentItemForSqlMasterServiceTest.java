package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForSqlMaster;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForSqlMasterMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class SegmentItemForSqlMasterServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindBySegmentItemForScreenSequenceAndConditionsTest {

        @MockBean
        public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

        @Autowired
        public SegmentItemForSqlMasterService segmentItemForSqlMasterService;

        @Test
        public void expectingFindSuccess() {
            SegmentItemForSqlMaster segmentItemForSqlMaster = new SegmentItemForSqlMaster();
            segmentItemForSqlMaster.setSegmentItemForSqlSequence(1001L);
            segmentItemForSqlMaster.setSegmentItemForScreenSequence(2001L);
            segmentItemForSqlMaster.setBrandId("1");
            segmentItemForSqlMaster.setCountryId("1");
            segmentItemForSqlMaster.setEventType("1");

            Mockito.doReturn(Arrays.asList(segmentItemForSqlMaster)).when(segmentItemForSqlMasterMapper).findBySegmentItemForScreenSequenceAndConditions(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
            List<SegmentItemForSqlMaster> list = segmentItemForSqlMasterService.findBySegmentItemForScreenSequenceAndConditions(2001L, Arrays.asList("1","2","3"), "1", "1");
            SegmentItemForSqlMaster result = list.get(0);
            assertThat(result.getSegmentItemForSqlSequence()).isEqualTo(1001L);
            assertThat(result.getSegmentItemForScreenSequence()).isEqualTo(2001L);
            assertThat(result.getBrandId()).isEqualTo("1");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getEventType()).isEqualTo("1");
        }
    }

    @Nested
    public class FindByConditionsTest {

        @MockBean
        public SegmentItemForSqlMasterMapper segmentItemForSqlMasterMapper;

        @Autowired
        public SegmentItemForSqlMasterService segmentItemForSqlMasterService;

        @Test
        public void expectingFindSuccess() {
            SegmentItemForSqlMaster segmentItemForSqlMaster = new SegmentItemForSqlMaster();
            segmentItemForSqlMaster.setSegmentItemForSqlSequence(1001L);
            segmentItemForSqlMaster.setSegmentItemForScreenSequence(2001L);
            segmentItemForSqlMaster.setBrandId("1");
            segmentItemForSqlMaster.setCountryId("1");
            segmentItemForSqlMaster.setEventType("1");

            Mockito.doReturn(segmentItemForSqlMaster).when(segmentItemForSqlMasterMapper).findByConditions(Mockito.any(), Mockito.any(), Mockito.any());
            SegmentItemForSqlMaster result = segmentItemForSqlMasterService.findByConditions(2001L, "1", "1");
            assertThat(result.getSegmentItemForSqlSequence()).isEqualTo(1001L);
            assertThat(result.getSegmentItemForScreenSequence()).isEqualTo(2001L);
            assertThat(result.getBrandId()).isEqualTo("1");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getEventType()).isEqualTo("1");
        }
    }
}
