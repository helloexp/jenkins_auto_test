package com.fastretailing.marketingpf.controllers.batchJob.create;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.BatchJobService;
import com.fastretailing.marketingpf.services.SegmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobCreateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobCreateController.class);

    @Autowired
    public BatchJobService batchJobService;

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public BatchJobCreateValidator batchJobCreateValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = BatchJobCreateResponse.class))}),
    })
    @PostMapping("/request_calculation_number_of_people/")
    public ResponseEntity<Object> create(@RequestBody BatchJobCreateRequest request) {
        batchJobCreateValidator.validate(request.getSegmentSequence());
        String segmentQuery = segmentService.getSegmentQueryBySegmentSequence(request.getSegmentSequence());
        logger.info("segmentQuery = {}", segmentQuery);
        return ok(new BatchJobCreateResponse(batchJobService.create(request.getSegmentSequence(), segmentQuery, getLoginUserId())));
    }
}
