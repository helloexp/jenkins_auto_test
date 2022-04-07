package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.dtos.AdAccount;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.GoogleApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.FacebookAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.GoogleAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformMaster;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.SEGMENT_STATUS;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.AdsPlatformMasterService;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.FacebookAdsService;
import com.fastretailing.marketingpf.services.GoogleAdsService;
import com.fastretailing.marketingpf.services.OutboundSettingSegmentIntermediateService;
import com.fastretailing.marketingpf.services.OutboundSettingService;
import com.fastretailing.marketingpf.services.OutputPlatformMasterService;
import com.fastretailing.marketingpf.services.OutputPlatformOutboundSettingIntermediateService;
import com.fastretailing.marketingpf.services.SegmentService;
import com.fastretailing.marketingpf.services.UserListUploadService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutboundSettingSegmentIntermediateCreateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OutboundSettingSegmentIntermediateCreateController.class);

    @Autowired
    public OutboundSettingService outboundSettingService;

    @Autowired
    public OutboundSettingSegmentIntermediateService outboundSettingSegmentIntermediateService;

    @Autowired
    public OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

    @Autowired
    public SegmentService segmentService;

    @Autowired
    public UserListUploadService userListUploadService;

    @Autowired
    public AdsPlatformMasterService adsPlatformMasterService;

    @Autowired
    public OutputPlatformMasterService outputPlatformMasterService;

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public AdAccountService adAccountService;

    @Autowired
    public GoogleAdsService googleAdsService;

    @Autowired
    public FacebookAdsService facebookAdsService;

    @Autowired
    private OutboundSettingSegmentIntermediateCreateValidator validator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = OutboundSettingSegmentIntermediateCreateResponse.class))}),
    })
    @PostMapping("/outbound_setting_segment_intermediate/")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> create(@Valid @RequestBody OutboundSettingSegmentIntermediateCreateRequest request) {
        validator.validate(request);
        // Create OutboundSetting
        OutboundSetting outboundSetting = outboundSettingService.create(
                request.getBusinessTypeAsEnum(),
                request.getBrandAsEnum(),
                request.getCountryAsEnum(),
                request.getSubmissionTimingTypeAsEnum(),
                request.getReserveSubmissionDateTime(),
                request.getRegularlySubmissionFrequencyDateTimeBasis(),
                request.getRegularlySubmissionFrequencyPeriodNumberValue(),
                request.getRegularlySubmissionFrequencyPeriodUnit(),
                request.getSubmissionCompletionContactListAsString(),
                request.getOutboundSettingName(),
                LocalDateTime.now(),
                getLoginUserId());

        // Create outboundSettingSegment
        List<OutboundSettingSegmentIntermediate> outboundSettingSegmentIntermediateList = new ArrayList<>();
        for (Long segmentSequence : request.getSegmentSequenceList()) {
            outboundSettingSegmentIntermediateList.add(outboundSettingSegmentIntermediateService.create(
                    outboundSetting.getOutboundSettingSequence(),
                    segmentSequence,
                    LocalDateTime.now(),
                    getLoginUserId()));
        }

        AdsPlatformMaster adsPlatformMaster = adsPlatformMasterService.findById(request.getAdsPlatformSequence());
        OutputPlatformMaster outputPlatformMaster = outputPlatformMasterService.findById(adsPlatformMaster.getOutputPlatformSequence());
        ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationService.findByAdsPlatformSequenceAndLoginUserId(request.getAdsPlatformSequence(), request.getAdsPfLoginUserId());
        AdAccount adAccount = adAccountService.findById(request.getAdAccountSequence());
        // Create OutputPlatformOutboundSettingIntermediate
        List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList = new ArrayList<>();
        for (EXTRACTION_TARGET_ID extractionTargetId : request.getExtractionTargetIdList()) {
            String userListName = request.getOutboundSettingName() + "_" + extractionTargetId.getName();
            String audienceId = null;
            if (ADS_PLATFORM.create(request.getAdsPlatformSequence().intValue()) == ADS_PLATFORM.GOOGLE_ADS) {
                GoogleApiTokenDto googleApiTokenDto = adsPlatformMaster.getGoogleApiTokenDto();
                GoogleAuthApiTokenDto googleAuthApiTokenDto = apiAuthenticationInformation.getGoogleAuthApiTokenDto();
                audienceId = googleAdsService.createAudience(
                        googleApiTokenDto.getDeveloperToken(),
                        googleApiTokenDto.getClientId(),
                        googleApiTokenDto.getClientSecret(),
                        googleAuthApiTokenDto.getRefeshToken(),
                        Long.valueOf(adAccount.getAdAccount().getLoginCustomerId()),
                        Long.valueOf(adAccount.getAdAccount().getAdsPlatformAccountId()),
                        userListName,
                        "",
                        extractionTargetId,
                        request.getBrandAsEnum(),
                        request.getCountryAsEnum());
            } else {
                FacebookAuthApiTokenDto facebookAuthApiTokenDto = apiAuthenticationInformation.getFacebookAuthApiTokenDto();
                audienceId = facebookAdsService.createCustomUserProvidedAudience(
                        facebookAuthApiTokenDto.getAccessToken(),
                        adAccount.getAdAccount().getAdsPlatformAccountId(),
                        userListName,
                        "").getId();
            }
            outputPlatformOutboundSettingIntermediateList.add(outputPlatformOutboundSettingIntermediateService.create(
                    outboundSetting.getOutboundSettingSequence(),
                    outputPlatformMaster.getOutputPlatformSequence(),
                    outputPlatformMaster.getOutputPlatformTypeAsEnum(),
                    request.getAdAccountSequence(),
                    request.getAdsPlatformSequence(),
                    null,
                    audienceId,
                    userListName,
                    request.getOutboundSettingName(),
                    extractionTargetId,
                    LocalDateTime.now(),
                    getLoginUserId())
            );
        }

        if(request.getSubmissionTimingTypeAsEnum() == SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION) {
            // Request to upload user list from MKDB
            String segmentQuery = segmentService.createMultiSegmentQuery(request.getSegmentSequenceList(), request.getBrandAsEnum(), request.getCountryAsEnum(), request.getExtractionTargetIdList());
            logger.info("segmentQuery = {}", segmentQuery);
            userListUploadService.uploadUserList(outboundSetting.getOutboundSettingSequence(), segmentQuery, adAccount.getAdAccount().getApiAuthenticationInformationSequence(), getLoginUserId());
        }

        // update segment status to Uploaded
        for (Long segmentSequence : request.getSegmentSequenceList()) {
            segmentService.updateStatusBySegmentSequence(segmentSequence, SEGMENT_STATUS.UPLOADED, this.getLoginUserId(), LocalDateTime.now());
        }

        return ok(new OutboundSettingSegmentIntermediateCreateResponse(outboundSetting, outboundSettingSegmentIntermediateList, outputPlatformOutboundSettingIntermediateList));
    }
}
