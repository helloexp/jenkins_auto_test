package com.fastretailing.marketingpf.controllers.segmentitem.search;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.services.SegmentItemForScreenMasterService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegmentItemForScreenMasterSearchController extends BaseController {

    @Autowired
    public SegmentItemForScreenMasterService segmentItemForScreenMasterService;

    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SegmentItemForScreenMasterSearchResponse.class))})
    @GetMapping("/segment_item_for_screen_master/")
    public ResponseEntity<Object> findAll() {
        List<SegmentItemForScreenMaster> segmentItemForScreenMasterList = segmentItemForScreenMasterService.findAll();
        return ok(new SegmentItemForScreenMasterSearchResponse(segmentItemForScreenMasterList));
    }
}
