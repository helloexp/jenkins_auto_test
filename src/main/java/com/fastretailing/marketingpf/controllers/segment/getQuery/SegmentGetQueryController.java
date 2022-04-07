package com.fastretailing.marketingpf.controllers.segment.getQuery;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.utils.AuthUtils;
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
public class SegmentGetQueryController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @GetMapping("/create_query/{segmentSequence}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentGetQueryResponse.class))})
    })
    public ResponseEntity<Object> getQuery(@PathVariable Long segmentSequence) {
        AuthUtils.preAuthorizeForSqlSegmentRole();
        String query = segmentService.buildSqlBySegmentSequence(segmentSequence);
        return ok(new SegmentGetQueryResponse(segmentSequence, query));
    }
}
