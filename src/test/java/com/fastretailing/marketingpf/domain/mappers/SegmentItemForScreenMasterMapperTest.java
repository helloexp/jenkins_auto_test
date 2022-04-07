package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SegmentItemForScreenMasterMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindAllTest {
        @Autowired
        public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

        @Test
        @Sql("/domain/mappers/SegmentItemForScreenMasterMapperTest/FindAllTest/m_sgmt_itm_f_scrn.sql")
        public void expectingSuccess() {
            List<SegmentItemForScreenMaster> segmentItemForScreenMasterList = segmentItemForScreenMasterMapper.findAll();
            assertThat(segmentItemForScreenMasterList.size()).isEqualTo(2);
            assertThat(segmentItemForScreenMasterList.get(0).getSegmentItemForScreenSequence()).isEqualTo(1002L);
            assertThat(segmentItemForScreenMasterList.get(0).getCategory()).isEqualTo("large2");
            assertThat(segmentItemForScreenMasterList.get(0).getOperatorList()).isEqualTo("{}");
            assertThat(segmentItemForScreenMasterList.get(1).getSegmentItemForScreenSequence()).isEqualTo(1003L);
            assertThat(segmentItemForScreenMasterList.get(1).getCategory()).isEqualTo("large3");
            assertThat(segmentItemForScreenMasterList.get(1).getOperatorList()).isEqualTo("{}");
        }
    }

    @Nested
    public class FindByIdTest {
        @Autowired
        public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

        @Test
        @Sql("/domain/mappers/SegmentItemForScreenMasterMapperTest/FindByIdTest/m_sgmt_itm_f_scrn.sql")
        public void expectingFindSuccess() {
            SegmentItemForScreenMaster segmentItemForScreenMaster_1001 = segmentItemForScreenMasterMapper.findById(1001L);
            assertThat(segmentItemForScreenMaster_1001.getSegmentItemForScreenSequence()).isEqualTo(1001L);
            assertThat(segmentItemForScreenMaster_1001.getCategory()).isEqualTo("cat_1001");
            assertThat(segmentItemForScreenMaster_1001.getDetailCategory()).isEqualTo("detail_cat_1001");
            assertThat(segmentItemForScreenMaster_1001.getSegmentItemName()).isEqualTo("name_1001");
            assertThat(segmentItemForScreenMaster_1001.getInputTypeAsEnum()).isEqualTo(INPUT_TYPE.TEXT_BOX_TEXT);
            assertThat(segmentItemForScreenMaster_1001.getOperatorList()).isEqualTo("{\"operatorList\": [1]}");
            assertThat(segmentItemForScreenMaster_1001.getChoicesList()).isEqualTo("{\"choicesList\": [{\"name\":\"Women\", \"value\": 2},{\"name\": \"Men\", \"value\": 3},{\"name\":\"Kids\", \"value\":1},{\"name\":\"Other\", \"value\":8}]}");
            assertThat(segmentItemForScreenMaster_1001.getUrlForApiAccess()).isEqualTo(null);

            SegmentItemForScreenMaster segmentItemForScreenMaster_1003 = segmentItemForScreenMasterMapper.findById(1003L);
            assertThat(segmentItemForScreenMaster_1003.getSegmentItemForScreenSequence()).isEqualTo(1003L);
            assertThat(segmentItemForScreenMaster_1003.getCategory()).isEqualTo("cat_1003");
            assertThat(segmentItemForScreenMaster_1003.getDetailCategory()).isEqualTo("detail_cat_1003");
            assertThat(segmentItemForScreenMaster_1003.getSegmentItemName()).isEqualTo("name_1003");
            assertThat(segmentItemForScreenMaster_1003.getInputTypeAsEnum()).isEqualTo(INPUT_TYPE.SINGLE_SELECTION_TEXT);
            assertThat(segmentItemForScreenMaster_1003.getOperatorList()).isEqualTo("{\"operatorList\": [3]}");
            assertThat(segmentItemForScreenMaster_1003.getChoicesList()).isEqualTo("{\"choicesList\": [{\"name\":\"Women\", \"value\": 2},{\"name\": \"Men\", \"value\": 3},{\"name\":\"Kids\", \"value\":1},{\"name\":\"Other\", \"value\":8}]}");
            assertThat(segmentItemForScreenMaster_1003.getUrlForApiAccess()).isEqualTo(null);

            assertThat(segmentItemForScreenMasterMapper.findById(9999L)).isEqualTo(null);
        }
    }
}
