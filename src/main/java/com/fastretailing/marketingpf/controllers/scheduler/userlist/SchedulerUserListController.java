package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.OutboundSettingSegmentIntermediateService;
import com.fastretailing.marketingpf.services.OutboundSettingService;
import com.fastretailing.marketingpf.services.OutputPlatformOutboundSettingIntermediateService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserListUploadService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerUserListController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerUserListController.class);

    @Autowired
    public UserListUploadService userListUploadService;

    @Autowired
    public OutboundSettingService outboundSettingService;

    @Autowired
    public OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

    @Autowired
    public OutboundSettingSegmentIntermediateService outboundSettingSegmentIntermediateService;

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public AdAccountService adAccountService;

    @Autowired
    public SchedulerUserListValidator schedulerUserListValidator;

    @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = SchedulerUserListResponse.class))})
    @PostMapping("/scheduler/userlist/")
    public ResponseEntity<Object> create(@Valid @RequestBody SchedulerUserListRequest request) {
        schedulerUserListValidator.validate(request.getOutboundSettingSequence());
        List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList = outputPlatformOutboundSettingIntermediateService.findListByOutboundSettingSequence(request.getOutboundSettingSequence());
        List<OutboundSettingSegmentIntermediate> outboundSettingSegmentIntermediateList = outboundSettingSegmentIntermediateService.findByOutboundSettingSequence(request.getOutboundSettingSequence());
        List<Long> segmentSequenceList = outboundSettingSegmentIntermediateList.stream().map(s -> s.getSegmentSequence()).collect(Collectors.toList());
        List<EXTRACTION_TARGET_ID> extractionTargetIdList = outputPlatformOutboundSettingIntermediateList.stream().map(s -> EXTRACTION_TARGET_ID.createFromValue(s.getExtractionTargetId())).collect(Collectors.toList());
        Long adAccountSequence = outputPlatformOutboundSettingIntermediateList.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(OutputPlatformOutboundSettingIntermediate::getAdAccountSequence)
                .orElse(null);
        AdAccount adAccount = adAccountService.findById(adAccountSequence);
        String segmentQuery = segmentService.createMultiSegmentQuery(segmentSequenceList, BRAND.createFromCode(adAccount.getAdAccount().getBrandId()), COUNTRY.createFromCode(adAccount.getAdAccount().getCountryId()), extractionTargetIdList);
        logger.info("segmentQuery = {}", segmentQuery);
        return ok(new SchedulerUserListResponse(userListUploadService.uploadUserList(request.getOutboundSettingSequence(), segmentQuery, adAccount.getAdAccount().getApiAuthenticationInformationSequence(), "MKPF_SCHEDULER")));
    }
}
