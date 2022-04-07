package com.fastretailing.marketingpf.domain.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * This entity is used to map data from MKDB when get SegmentBreakdown.
 * SegmentBreakdown entity is actually being managed in MKDB.
 */
@Data
public class SegmentBreakdownList {

    private List<SegmentBreakdown> segmentBreakdownList = new ArrayList<>();
}
