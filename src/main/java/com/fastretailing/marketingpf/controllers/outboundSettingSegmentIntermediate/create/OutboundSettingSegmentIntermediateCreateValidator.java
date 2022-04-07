package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.domain.entities.Segment;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.SQL_EDIT_FLAG;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.domain.mappers.SegmentMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboundSettingSegmentIntermediateCreateValidator {

    @Autowired
    private SegmentMapper segmentMapper;

    /**
     * Validate request
     *
     * @param request
     */
    public void validate(OutboundSettingSegmentIntermediateCreateRequest request) {
        for (Long targetSegmentId : request.getSegmentSequenceList()) {
            Segment segment = segmentMapper.findById(targetSegmentId);
            if (segment == null) {
                throw new ResourceNotFoundException();
            }
            if (segment.getSqlEditFlagAsEnum() == SQL_EDIT_FLAG.SQL_SEGMENT) {
                AuthUtils.preAuthorizeForSqlSegmentRole();
            }
        }

        this.validateForRequest(request);
    }

    /**
     * Validate data for request
     *
     * @param request
     */
    public void validateForRequest(OutboundSettingSegmentIntermediateCreateRequest request) {
        // Validate value based on SubmissionTimingType
        if (request.getSubmissionTimingTypeAsEnum() == SUBMISSION_TIMING_TYPE.RESERVATION_SUBMISSION) {
            if (request.getReserveSubmissionDateTime() == null) {
                throw new ValidationFailException(new ValidationErrors("ReserveSubmissionDateTime", "ReserveSubmissionDateTime is null"));
            }
        } else if (request.getSubmissionTimingTypeAsEnum() == SUBMISSION_TIMING_TYPE.REGULAR_SUBMISSION) {
            if (request.getRegularlySubmissionFrequencyDateTimeBasis() == null) {
                throw new ValidationFailException(new ValidationErrors("RegularlySubmissionFrequencyDateTimeBasis", "RegularlySubmissionFrequencyDateTimeBasis is null"));
            }
            if (request.getRegularlySubmissionFrequencyPeriodNumberValue() == null) {
                throw new ValidationFailException(new ValidationErrors("RegularlySubmissionFrequencyPeriodNumberValue", "RegularlySubmissionFrequencyPeriodNumberValue is null"));
            }
            if (request.getRegularlySubmissionFrequencyPeriodUnit() == null) {
                throw new ValidationFailException(new ValidationErrors("RegularlySubmissionFrequencyPeriodUnit", "RegularlySubmissionFrequencyPeriodUnit is null"));
            }
        }

        // Validate extractionTargetId based on EXTRACTION_TARGET_ID enum
        for (Long value : request.getExtractionTargetId()) {
            if (EXTRACTION_TARGET_ID.createFromValue(value.toString()) == null) {
                throw new ValidationFailException(new ValidationErrors("ExtractionTargetId", "ExtractionTargetId is invalid"));
            }
        }
    }
}
