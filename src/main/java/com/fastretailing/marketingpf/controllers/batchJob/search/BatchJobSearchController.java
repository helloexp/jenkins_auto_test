package com.fastretailing.marketingpf.controllers.batchJob.search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.controllers.batchJob.search.BatchJobSearchResponse.BatchJobSearchResponseDto;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList;
import com.fastretailing.marketingpf.domain.dtos.BatchJobList.BatchJobListDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.services.BatchJobService;
import com.fastretailing.marketingpf.services.OutboundSettingService;
import com.fastretailing.marketingpf.services.OutputPlatformOutboundSettingIntermediateService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobSearchController extends BaseController {

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public BatchJobService batchJobService;

    @Autowired
    public OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

    @Autowired
    public OutboundSettingService outboundSettingService;

    @Autowired
    public UserMasterService userMasterService;

    @Operation(summary = "Search batchJob List", description = "Search batchJob List")
    @Parameters({
            @Parameter(name = "batchJobType", in = ParameterIn.QUERY, schema = @Schema(description = "Batch Job Type", type = "String")),
            @Parameter(name = "segmentName", in = ParameterIn.QUERY, schema = @Schema(description = "Segment Name", type = "String")),
            @Parameter(name = "status", in = ParameterIn.QUERY, schema = @Schema(description = "Batch Job Status", type = "String")),
            @Parameter(name = "updateUserIdForAudit", in = ParameterIn.QUERY, schema = @Schema(description = "Update User Id For Audit", type = "String"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = BatchJobSearchResponse.class))})
    })
    @GetMapping("/request_get_jobs/")
    public ResponseEntity<Object> search(@Parameter(hidden = true) @ModelAttribute BatchJobSearchRequest request) {
        List<Segment> segmentList = null;
        BatchJobList jobList = null;
        if (StringUtils.isNotBlank(request.getSegmentName())) {
            segmentList = segmentService.findListBySegmentName(request.getSegmentName());
            if (CollectionUtils.isEmpty(segmentList)) {
                return ok(new BatchJobSearchResponse(new ArrayList<>()));
            } else {
                List<Long> segmentSequenceList = segmentList.stream().map(Segment::getSegmentSequence).collect(Collectors.toList());
                jobList = batchJobService.search(request.getBatchJobTypeAsEnum(), segmentSequenceList, request.getStatusAsEnum(), request.getUpdateUserIdForAudit());
            }
        } else {
            jobList = batchJobService.search(request.getBatchJobTypeAsEnum(), null, request.getStatusAsEnum(), request.getUpdateUserIdForAudit());
        }

        List<BatchJobSearchResponseDto> jobSearchResponseDtoList = new ArrayList<>();
        for (BatchJobListDto job : jobList.getJobList()) {
            List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList = outputPlatformOutboundSettingIntermediateService.findListByOutboundSettingSequence(job.getOutboundSettingSequence());
            OutboundSetting outboundSetting = outboundSettingService.findById(job.getOutboundSettingSequence());
            List<Long> segmentSequenceListOfJob = JsonUtils.fromJson(job.getSegmentSequenceList(), new TypeReference<List<Long>>(){});
            List<Segment> segmentListOfJob = segmentSequenceListOfJob.stream().map(e -> segmentService.findById(e)).filter(Objects::nonNull).collect(Collectors.toList());
            jobSearchResponseDtoList.add(new BatchJobSearchResponseDto(outputPlatformOutboundSettingIntermediateList, outboundSetting, job, segmentListOfJob, userMasterService.getUserIdToUserNameMap()));
        }

        return ok(new BatchJobSearchResponse(jobSearchResponseDtoList));
    }
}
