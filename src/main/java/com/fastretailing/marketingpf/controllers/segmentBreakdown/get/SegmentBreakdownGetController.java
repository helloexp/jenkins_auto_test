package com.fastretailing.marketingpf.controllers.segmentBreakdown.get;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.SegmentBreakdownService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentBreakdownGetController extends BaseController {

    @Autowired
    public SegmentBreakdownService segmentBreakdownService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentBreakdownGetResponse.class))})
    })
    @GetMapping("/segment_breakdown/{segmentSequence}")
    public ResponseEntity<Object> findBySegmentSequence(@PathVariable Long segmentSequence) {
        return ok(new SegmentBreakdownGetResponse(segmentBreakdownService.findBySegmentSequence(segmentSequence)));
    }
}
