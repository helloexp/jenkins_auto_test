package com.fastretailing.marketingpf.controllers.segment.delete;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.entities.SegmentCondition;
import com.fastretailing.marketingpf.services.SegmentConditionService;
import com.fastretailing.marketingpf.services.SegmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentDeleteController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public SegmentConditionService segmentConditionService;

    @Autowired
    public SegmentDeleteValidator segmentDeleteValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentDeleteResponse.class))})
    })
    @DeleteMapping("/control_of_segment_related_tables/{segmentSequence}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> delete(@PathVariable Long segmentSequence) {
        segmentDeleteValidator.validate(segmentSequence);

        Segment deletedSegment = segmentService.delete(segmentSequence, LocalDateTime.now(), getLoginUserId());

        List<SegmentCondition> deletedSegmentConditionList = new ArrayList<>();
        for (SegmentCondition condition : segmentConditionService.findListBySegmentSequence(segmentSequence)) {
            deletedSegmentConditionList.add(segmentConditionService.delete(condition.getSegmentConditionSequence(), LocalDateTime.now(), getLoginUserId()));
        }

        return ok(new SegmentDeleteResponse(deletedSegment, deletedSegmentConditionList));
    }
}
