package com.fastretailing.marketingpf.controllers.segment.search;

import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationErrors;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import org.springframework.stereotype.Service;

@Service
public class SegmentSearchValidator {

    /**
     * Validate for Segment search request
     *
     * @param request
     */
    public void validate(SegmentSearchRequest request) {
        if (request.getNumberOfPeopleValue() != null) {
            if (request.getNumberOfPeopleValue() < 0) {
                throw new ValidationFailException(new ValidationErrors("NumberOfPeopleValue", "NumberOfPeopleValue must be positive number."));
            } else {
                if (request.getNumberOfPeopleConditionsAsEnum() == null) {
                    throw new ValidationFailException(new ValidationErrors("NumberOfPeopleConditions", "NumberOfPeopleConditions is invalid."));
                }
            }
        } else {
            if (request.getNumberOfPeopleConditionsAsEnum() != null) {
                throw new ValidationFailException(new ValidationErrors("NumberOfPeopleValue", "NumberOfPeopleValue is null or empty."));
            }
        }
        if (request.getEventTargetPeriodRelativeNumberValue() != null && request.getEventTargetPeriodRelativeNumberValue() < 0) {
            throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativeNumberValue", "EventTargetPeriodRelativeNumberValue must be positive number."));
        }
        if (request.getEventTargetPeriodRelativeNumberValue() == null && request.getEventTargetPeriodRelativePeriodUnitAsEnum() != null) {
            throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativeNumberValue", "eventTargetPeriodRelativeNumberValue is empty."));
        }
        if (request.getEventTargetPeriodRelativeNumberValue() != null && request.getEventTargetPeriodRelativePeriodUnitAsEnum() == null) {
            throw new ValidationFailException(new ValidationErrors("eventTargetPeriodRelativePeriodUnit", "eventTargetPeriodRelativePeriodUnit is empty."));
        }
    }
}
