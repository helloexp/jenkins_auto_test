package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingMapper;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboundSettingService {

    @Autowired
    public OutboundSettingMapper outboundSettingMapper;

    /**
     * Find by id
     *
     * @param outboundSettingSequence
     * @return OutboundSetting
     */
    public OutboundSetting findById(Long outboundSettingSequence) {
        return outboundSettingMapper.findById(outboundSettingSequence);
    }

    /**
     * Create new OutboundSetting
     *
     * @param businessType
     * @param brand
     * @param country
     * @param submissionTimingType
     * @param reserveSubmissionDateTime
     * @param regularlySubmissionFrequencyDateTimeBasis
     * @param regularlySubmissionFrequencyPeriodNumberValue
     * @param regularlySubmissionFrequencyPeriodUnit
     * @param submissionCompletionContact
     * @param outboundSettingName
     * @param createDateTimeForAudit
     * @param createUserIdForAudit
     * @param updateDateTimeForAudit
     * @param updateUserIdForAudit
     * @return OutboundSetting
     */
    public OutboundSetting create(
            BUSINESS_TYPE businessType,
            BRAND brand,
            COUNTRY country,
            SUBMISSION_TIMING_TYPE submissionTimingType,
            LocalDateTime reserveSubmissionDateTime,
            LocalDateTime regularlySubmissionFrequencyDateTimeBasis,
            Integer regularlySubmissionFrequencyPeriodNumberValue,
            Integer regularlySubmissionFrequencyPeriodUnit,
            String submissionCompletionContact,
            String outboundSettingName,
            LocalDateTime createDateTimeForAudit,
            String createUserIdForAudit) {
        OutboundSetting outboundSetting = new OutboundSetting();
        outboundSetting.setBusinessType(businessType.getValueAsString());
        outboundSetting.setBrandId(brand.getCode());
        outboundSetting.setCountryId(country.getCode());
        outboundSetting.setSubmissionTimingType(submissionTimingType.getValueAsString());
        outboundSetting.setReserveSubmissionDateTime(reserveSubmissionDateTime);
        outboundSetting.setRegularlySubmissionFrequencyDateTimeBasis(regularlySubmissionFrequencyDateTimeBasis);
        outboundSetting.setRegularlySubmissionFrequencyPeriodNumberValue(regularlySubmissionFrequencyPeriodNumberValue);
        outboundSetting.setRegularlySubmissionFrequencyPeriodUnit(regularlySubmissionFrequencyPeriodUnit);
        outboundSetting.setSubmissionCompletionContact(submissionCompletionContact);
        outboundSetting.setOutboundSettingName(outboundSettingName);
        outboundSetting.setAuditInfoForCreation(createUserIdForAudit, createDateTimeForAudit);
        outboundSettingMapper.create(outboundSetting);
        return outboundSetting;
    }
}
