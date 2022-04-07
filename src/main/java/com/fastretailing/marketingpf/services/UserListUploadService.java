package com.fastretailing.marketingpf.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fastretailing.marketingpf.domain.dtos.BatchJob;
import com.fastretailing.marketingpf.domain.dtos.CredentialInfoJsonDto;
import com.fastretailing.marketingpf.domain.dtos.SubmissionInfoJsonDto;
import com.fastretailing.marketingpf.domain.dtos.SubmissionInfoJsonDto.SubmissionDetail;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.FacebookApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster.GoogleApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.FacebookAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation.GoogleAuthApiTokenDto;
import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.entities.OutboundSettingSegmentIntermediate;
import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.mappers.AdsPlatformMasterMapper;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingMapper;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingSegmentIntermediateMapper;
import com.fastretailing.marketingpf.domain.mappers.OutputPlatformOutboundSettingIntermediateMapper;
import com.fastretailing.marketingpf.utils.AesUtils;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserListUploadService extends BaseUpstreamService {

    @Autowired
    public OutboundSettingMapper outboundSettingMapper;

    @Autowired
    public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

    @Autowired
    public OutboundSettingSegmentIntermediateMapper outboundSettingSegmentIntermediateMapper;

    @Autowired
    public AdsPlatformMasterMapper adsPlatformMasterMapper;

    @Autowired
    public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

    /**
     * upload user list
     *
     * @param outboundSettingSequence
     * @param sql
     * @param apiAuthenticationInformationSequence
     * @param loginUserId
     * @return BatchJob
     */
    public BatchJob uploadUserList(Long outboundSettingSequence, String sql, Long apiAuthenticationInformationSequence, String loginUserId) {
        OutboundSetting outboundSetting = outboundSettingMapper.findById(outboundSettingSequence);
        List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList = outputPlatformOutboundSettingIntermediateMapper.findListByOutboundSettingSequence(outboundSettingSequence);
        List<OutboundSettingSegmentIntermediate> outboundSettingSegmentIntermediateList = outboundSettingSegmentIntermediateMapper.findByOutboundSettingSequence(outboundSettingSequence);
        List<Long> segmentSequenceList = outboundSettingSegmentIntermediateList.stream().map(s -> s.getSegmentSequence()).collect(Collectors.toList());
        Long adsPlatformSequence = outputPlatformOutboundSettingIntermediateList.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(OutputPlatformOutboundSettingIntermediate::getAdsPlatformSequence)
                .orElse(null);
        Long adAccountSequence = outputPlatformOutboundSettingIntermediateList.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(OutputPlatformOutboundSettingIntermediate::getAdAccountSequence)
                .orElse(null);

        UserListUploadRequest request = new UserListUploadRequest();
        request.setSegmentSequenceList(segmentSequenceList);
        request.setOutboundSettingSequence(outboundSettingSequence);
        request.setSql(sql);
        request.setAdAccountSequence(adAccountSequence);
        request.setNotificationList(JsonUtils.fromJson(outboundSetting.getSubmissionCompletionContact(), new TypeReference<List<String>>(){}));
        request.setOutboundSettingName(outboundSetting.getOutboundSettingName());
        request.setAdsPlatformSequence(adsPlatformSequence);
        request.setSubmissionInfoJson(this.createSubmissionInfoJson(outputPlatformOutboundSettingIntermediateList));
        request.setCredentialInfoJson(this.createCredentialInfoJson(adsPlatformSequence, apiAuthenticationInformationSequence));
        request.setCreateUserIdForAudit(loginUserId);
        URI uri = UriComponentsBuilder.newInstance()
                .path(config.getMkdbApi().getUriPrefix() + "/userlist/")
                .build().toUri();
        return this.postForMono(uri, request, BatchJob.class);
    }

    /**
     * create submissionInfoJson
     *
     * @param outputPlatformOutboundSettingIntermediateList
     * @return SubmissionInfoJsonDto
     */
    public SubmissionInfoJsonDto createSubmissionInfoJson(List<OutputPlatformOutboundSettingIntermediate> outputPlatformOutboundSettingIntermediateList) {
        Map<EXTRACTION_TARGET_ID, SubmissionDetail> submissionMap = outputPlatformOutboundSettingIntermediateList.stream()
                .collect(Collectors.toMap(e -> e.getExtractionTargetIdAsEnum(), e -> new SubmissionDetail(e.getAudienceId(), e.getUserListName())));
        return new SubmissionInfoJsonDto(
                submissionMap.get(EXTRACTION_TARGET_ID.MAIL_ADDRESS),
                submissionMap.get(EXTRACTION_TARGET_ID.ADID),
                submissionMap.get(EXTRACTION_TARGET_ID.IDFA));
    }

    /**
     * create credentialInfoJson
     *
     * @param adsPlatformSequence
     * @param apiAuthenticationInformationSequence
     * @return CredentialInfoJsonDto
     */
    public CredentialInfoJsonDto createCredentialInfoJson(Long adsPlatformSequence, Long apiAuthenticationInformationSequence) {
        AdsPlatformMaster adsPlatformMaster = adsPlatformMasterMapper.findById(adsPlatformSequence);
        ApiAuthenticationInformation apiAuthenticationInformation = apiAuthenticationInformationMapper.findById(apiAuthenticationInformationSequence);
        if (adsPlatformMaster.getPlatformSequenceAsEnum() == ADS_PLATFORM.GOOGLE_ADS) {
            GoogleApiTokenDto googleApiTokenDto = adsPlatformMaster.getGoogleApiTokenDto();
            GoogleAuthApiTokenDto googleAuthApiTokenDto = apiAuthenticationInformation.getGoogleAuthApiTokenDto();
            return new CredentialInfoJsonDto(googleApiTokenDto.getClientId(), googleApiTokenDto.getClientSecret(), googleApiTokenDto.getDeveloperToken(), AesUtils.decrypt(config.getAes().getSecretKey(), googleAuthApiTokenDto.getRefeshToken()));
        }
        FacebookApiTokenDto facebookApiTokenDto = adsPlatformMaster.getFacebookApiTokenDto();
        FacebookAuthApiTokenDto facebookAuthApiTokenDto = apiAuthenticationInformation.getFacebookAuthApiTokenDto();
        return new CredentialInfoJsonDto(facebookApiTokenDto.getAppId(), facebookApiTokenDto.getAppSecret(), AesUtils.decrypt(config.getAes().getSecretKey(), facebookAuthApiTokenDto.getAccessToken()));
    }

    @Data
    public static class UserListUploadRequest {
        private List<Long> segmentSequenceList;
        private Long outboundSettingSequence;
        private String sql;
        private Long adAccountSequence;
        private List<String> notificationList;
        private String outboundSettingName;
        private Long adsPlatformSequence;
        private SubmissionInfoJsonDto submissionInfoJson;
        private CredentialInfoJsonDto credentialInfoJson;
        private String createUserIdForAudit;
    }
}
