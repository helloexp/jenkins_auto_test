package com.fastretailing.marketingpf.controllers.segment.get;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.services.SegmentConditionService;
import com.fastretailing.marketingpf.services.SegmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentGetController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public SegmentConditionService segmentConditionService;

    @Autowired
    public SegmentGetValidator segmentGetValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentGetResponse.class))})
    })
    @GetMapping("/control_of_segment_related_tables/{segmentSequence}")
    public ResponseEntity<Object> get(@PathVariable Long segmentSequence) {
        segmentGetValidator.validate(segmentSequence);
        Segment segment = segmentService.findById(segmentSequence);
        List<SegmentCondition> segmentConditionList = segmentConditionService.findListBySegmentSequence(segmentSequence);
        return ok(new SegmentGetResponse(segment, segmentConditionList));
    }
}
