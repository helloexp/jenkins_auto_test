package com.fastretailing.marketingpf.controllers.segment.getQuery;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;

@JsonPropertyOrder({"segmentSequence, extractionQuery"})
public class SegmentGetQueryResponse extends BaseResponse {

    public Long segmentSequence;
    public String extractionQuery;

    public SegmentGetQueryResponse(Long segmentSequence, String query) {
        this.segmentSequence = segmentSequence;
        this.extractionQuery = query;
    }
}
