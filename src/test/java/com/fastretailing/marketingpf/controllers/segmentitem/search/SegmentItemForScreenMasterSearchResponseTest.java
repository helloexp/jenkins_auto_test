package com.fastretailing.marketingpf.controllers.segmentitem.search;

import com.fastretailing.marketingpf.controllers.segmentitem.search.SegmentItemForScreenMasterSearchResponse.SegmentItemForScreenMasterResponse;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.tests.JsonAssert;
import org.junit.jupiter.api.Test;

class SegmentItemForScreenMasterSearchResponseTest {

    @Test
    public void testSegmentItemForScreenMasterResponse() {
        SegmentItemForScreenMaster segmentItemForScreenMaster = new SegmentItemForScreenMaster();
        segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
        segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"10y\", \"value\": \"10\"},{\"name\":\"20y\", \"value\": \"20\"}]}");
        segmentItemForScreenMaster.setInputType("4");
        segmentItemForScreenMaster.setSegmentItemName("Age");
        segmentItemForScreenMaster.setCategory("largeClassification");
        segmentItemForScreenMaster.setDetailCategory("smallClassification");
        segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
        segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");

        SegmentItemForScreenMasterResponse response = new SegmentItemForScreenMasterResponse(segmentItemForScreenMaster);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
                "{" +
                "    \"segmentItemForScreenSequence\": 1001," +
                "    \"category\": \"largeClassification\"," +
                "    \"detailCategory\": \"smallClassification\"," +
                "    \"segmentItemName\": \"Age\"," +
                "    \"inputType\": \"4\"," +
                "    \"operatorList\": {\"operatorList\": [3, 4]}," +
                "    \"choicesList\": {\"choicesList\": [{\"name\":\"10y\", \"value\": \"10\"},{\"name\":\"20y\", \"value\": \"20\"}]}," +
                "    \"urlForApiAccess\": \"hoge.com\"" +
                "}");
    }

    @Test
    public void testSegmentItemForScreenMasterResponseWithNull() {
        SegmentItemForScreenMaster segmentItemForScreenMaster = new SegmentItemForScreenMaster();
        segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
        segmentItemForScreenMaster.setChoicesList(null);
        segmentItemForScreenMaster.setInputType("4");
        segmentItemForScreenMaster.setSegmentItemName("Age");
        segmentItemForScreenMaster.setCategory("largeClassification");
        segmentItemForScreenMaster.setDetailCategory("smallClassification");
        segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
        segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");

        SegmentItemForScreenMasterResponse response = new SegmentItemForScreenMasterResponse(segmentItemForScreenMaster);
        JsonAssert.assertJsonOutput(response).isSameContentAs(
            "{" +
                "    \"segmentItemForScreenSequence\": 1001," +
                "    \"category\": \"largeClassification\"," +
                "    \"detailCategory\": \"smallClassification\"," +
                "    \"segmentItemName\": \"Age\"," +
                "    \"inputType\": \"4\"," +
                "    \"operatorList\": {\"operatorList\": [3, 4]}," +
                "    \"choicesList\": null," +
                "    \"urlForApiAccess\": \"hoge.com\"" +
                "}");
    }
}
