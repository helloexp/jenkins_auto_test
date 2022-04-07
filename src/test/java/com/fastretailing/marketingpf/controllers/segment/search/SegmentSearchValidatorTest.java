package com.fastretailing.marketingpf.controllers.segment.search;

import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SegmentSearchValidatorTest extends BaseSpringBootTest {

    @Autowired
    public SegmentSearchValidator validator;

    @Test
    public void testValidate() {
        SegmentSearchRequest request = new SegmentSearchRequest();
        validator.validate(request);

        // Check normal request
        request.setNumberOfPeopleValue(1000L);
        request.setNumberOfPeopleConditions(1);
        validator.validate(request);

        request.setEventTargetPeriodRelativeNumberValue(200);
        request.setEventTargetPeriodRelativePeriodUnit("1");
        validator.validate(request);

        try {
            request.setEventTargetPeriodRelativeNumberValue(-1);
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        // Check numberOfPeopleValue < 0
        try {
            request.setNumberOfPeopleValue(-1L);
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {}

        // Check numberOfPeopleConditions is null
        try {
            request.setNumberOfPeopleValue(1000L);
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {}

        // Check numberOfPeopleValue is null
        try {
            request.setNumberOfPeopleValue(null);
            request.setNumberOfPeopleConditions(1);
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {}

        // Check eventTargetPeriodRelativeNumberValue is null
        try {
            request.setNumberOfPeopleConditions(null);
            request.setEventTargetPeriodRelativeNumberValue(null);
            request.setEventTargetPeriodRelativePeriodUnit("1");
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {}

        // Check eventTargetPeriodRelativePeriodUnit is null
        try {
            request.setNumberOfPeopleConditions(null);
            request.setEventTargetPeriodRelativeNumberValue(2000);
            request.setEventTargetPeriodRelativePeriodUnit(null);
            validator.validate(request);
            fail();
        } catch (ValidationFailException ignored) {}
    }
}
