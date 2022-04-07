package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.ApiAuthenticationInformation;
import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import com.fastretailing.marketingpf.domain.mappers.ApiAuthenticationInformationMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiAuthenticationInformationService {

    @Autowired
    public ApiAuthenticationInformationMapper apiAuthenticationInformationMapper;

    /**
     * Find by adsPlatformSequence and adsPfLoginUserId
     *
     * @param adsPlatformSequence
     * @param adsPfLoginUserId
     * @return ApiAuthenticationInformation
     */
    public ApiAuthenticationInformation findByAdsPlatformSequenceAndLoginUserId(Long adsPlatformSequence, String adsPfLoginUserId) {
        return apiAuthenticationInformationMapper.findByAdsPlatformSequenceAndLoginUserId(adsPlatformSequence, adsPfLoginUserId);
    }

    /**
     * Create ApiAuthenticationInformation
     *
     * @param apiToken
     * @param adsPfLoginUserId
     * @param dateTimeForAudit
     * @param userIdForAudit
     * @return ApiAuthenticationInformation
     */
    public ApiAuthenticationInformation upsert(
            ADS_PLATFORM adsPlatform,
            String apiToken,
            String adsPfLoginUserId,
            LocalDateTime dateTimeForAudit,
            String userIdForAudit) {
        ApiAuthenticationInformation apiAuthenticationInformation = new ApiAuthenticationInformation();
        apiAuthenticationInformation.setAdsPlatformSequence((long) adsPlatform.getValue());
        apiAuthenticationInformation.setApiToken(apiToken);
        apiAuthenticationInformation.setAdsPfLoginUserId(adsPfLoginUserId);
        apiAuthenticationInformation.setAuditInfoForCreation(userIdForAudit, dateTimeForAudit);
        apiAuthenticationInformation.setAuditInfoForUpdate(userIdForAudit, dateTimeForAudit);
        apiAuthenticationInformationMapper.upsert(apiAuthenticationInformation);
        return apiAuthenticationInformation;
    }

    /**
     * find ApiAuthenticationInformation by Id
     *
     * @param apiAuthenticationInformationSequence
     * @return ApiAuthenticationInformation
     */
    public ApiAuthenticationInformation findById(Long apiAuthenticationInformationSequence) {
        return apiAuthenticationInformationMapper.findById(apiAuthenticationInformationSequence);
    }

    /**
     * Find all ApiAuthenticationInformation
     *
     * @return List<ApiAuthenticationInformation>
     */
    public List<ApiAuthenticationInformation> findAll() {
        return apiAuthenticationInformationMapper.findAll();
    }

    /**
     * Find List By AdsPlatformSequence
     *
     * @param adsPlatformSequence
     * @return List<ApiAuthenticationInformation>
     */
    public List<ApiAuthenticationInformation> findListByAdsPlatformSequence(Long adsPlatformSequence) {
        return apiAuthenticationInformationMapper.findListByAdsPlatformSequence(adsPlatformSequence);
    }
}
