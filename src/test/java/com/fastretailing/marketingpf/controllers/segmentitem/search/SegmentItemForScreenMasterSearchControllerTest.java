package com.fastretailing.marketingpf.controllers.segmentitem.search;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.services.SegmentItemForScreenMasterService;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
class SegmentItemForScreenMasterSearchControllerTest extends BaseSpringBootTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    public SegmentItemForScreenMasterService segmentItemForScreenMasterService;

    @Test
    public void expectingSearchSuccess() throws Exception {
        SegmentItemForScreenMaster segmentItemForScreenMaster = new SegmentItemForScreenMaster();
        segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
        segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"male\", \"value\": \"male\"},{\"name\":\"female\", \"value\": \"female\"}]}");
        segmentItemForScreenMaster.setInputType("inputType");
        segmentItemForScreenMaster.setSegmentItemName("Gender");
        segmentItemForScreenMaster.setCategory("largeClassification");
        segmentItemForScreenMaster.setDetailCategory("smallClassification");
        segmentItemForScreenMaster.setOperatorList("{\"opeartorList\": [3, 4]}");
        segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");

        SegmentItemForScreenMaster segmentItemForScreenMaster2 = new SegmentItemForScreenMaster();
        segmentItemForScreenMaster2.setSegmentItemForScreenSequence(1002L);
        segmentItemForScreenMaster2.setChoicesList("{\"choicesList\": [{\"name\":\"30y\", \"value\": \"30\"},{\"name\":\"40y\", \"value\": \"40\"}]}");
        segmentItemForScreenMaster2.setInputType("inputType2");
        segmentItemForScreenMaster2.setSegmentItemName("Age");
        segmentItemForScreenMaster2.setCategory("largeClassification");
        segmentItemForScreenMaster2.setDetailCategory("smallClassification");
        segmentItemForScreenMaster2.setOperatorList("{\"opeartorList\": [5, 6]}");
        segmentItemForScreenMaster2.setUrlForApiAccess("fuga.com");

        List<SegmentItemForScreenMaster> segmentItemForScreenMasterList = new ArrayList<>();
        segmentItemForScreenMasterList.add(segmentItemForScreenMaster);
        segmentItemForScreenMasterList.add(segmentItemForScreenMaster2);

        Mockito.doReturn(segmentItemForScreenMasterList).when(segmentItemForScreenMasterService).findAll();
        mvc.perform(get("/api/marketingpf/v1/fr/jp/segment_item_for_screen_master/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().contentEquals("{"
                        + "    \"segmentItemForScreenMasterList\": [{"
                        + "            \"segmentItemForScreenSequence\": 1001,"
                        + "            \"category\": \"largeClassification\","
                        + "            \"detailCategory\": \"smallClassification\","
                        + "            \"segmentItemName\": \"Gender\","
                        + "            \"inputType\": \"inputType\","
                        + "            \"operatorList\": {"
                        + "                \"opeartorList\": [3, 4]"
                        + "            },"
                        + "            \"choicesList\": {"
                        + "                \"choicesList\": [{"
                        + "                        \"name\": \"male\","
                        + "                        \"value\": \"male\""
                        + "                    }, {"
                        + "                        \"name\": \"female\","
                        + "                        \"value\": \"female\""
                        + "                    }"
                        + "                ]"
                        + "            },"
                        + "            \"urlForApiAccess\": \"hoge.com\""
                        + "        }, {"
                        + "            \"segmentItemForScreenSequence\": 1002,"
                        + "            \"category\": \"largeClassification\","
                        + "            \"detailCategory\": \"smallClassification\","
                        + "            \"segmentItemName\": \"Age\","
                        + "            \"inputType\": \"inputType2\","
                        + "            \"operatorList\": {"
                        + "                \"opeartorList\": [5, 6]"
                        + "            },"
                        + "            \"choicesList\": {"
                        + "                \"choicesList\": [{"
                        + "                        \"name\": \"30y\","
                        + "                        \"value\": \"30\""
                        + "                    }, {"
                        + "                        \"name\": \"40y\","
                        + "                        \"value\": \"40\""
                        + "                    }"
                        + "                ]"
                        + "            },"
                        + "            \"urlForApiAccess\": \"fuga.com\""
                        + "        }"
                        + "    ]"
                        + "}"));
    }
}
