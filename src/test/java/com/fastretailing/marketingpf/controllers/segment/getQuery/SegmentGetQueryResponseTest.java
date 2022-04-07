package com.fastretailing.marketingpf.controllers.segment.getQuery;

import com.fastretailing.marketingpf.tests.JsonAssert;
import org.junit.jupiter.api.Test;

class SegmentGetQueryResponseTest {

    @Test
    public void testSegmentGetQueryResponse() {
        SegmentGetQueryResponse response = new SegmentGetQueryResponse(1001L, "sql_1");
        JsonAssert.assertJsonOutput(response).isSameContentAs("{\"segmentSequence\":1001,\"extractionQuery\":\"sql_1\"}");
    }
}
