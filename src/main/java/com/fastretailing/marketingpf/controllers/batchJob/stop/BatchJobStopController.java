package com.fastretailing.marketingpf.controllers.batchJob.stop;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.BatchJobService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobStopController extends BaseController {

    @Autowired
    public BatchJobService batchJobService;

    @ApiResponse(responseCode = "200", description = "Success")
    @PutMapping("/request_stop_job/{batchJobSequence}")
    public ResponseEntity<Object> stop(@PathVariable Long batchJobSequence) {
        batchJobService.stop(batchJobSequence);
        return ok();
    }
}
