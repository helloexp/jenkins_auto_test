package com.fastretailing.marketingpf.controllers.outboundSettingSegmentIntermediate.create;

import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OutboundSettingSegmentIntermediateCreateValidatorTest extends BaseSpringBootTest {

    @Autowired
    public OutboundSettingSegmentIntermediateCreateValidator validator;

    @Test
    public void testValidateForRequest() {
        OutboundSettingSegmentIntermediateCreateRequest request = new OutboundSettingSegmentIntermediateCreateRequest();

        try {
            request.setSubmissionTimingType("2");
            validator.validateForRequest(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            request.setSubmissionTimingType("3");
            validator.validateForRequest(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            request.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2022, 1, 1, 1, 1, 1));
            validator.validateForRequest(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            request.setRegularlySubmissionFrequencyPeriodNumberValue(9999);
            validator.validateForRequest(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            request.setExtractionTargetId(Arrays.asList(111L, 222L, 333L));
            validator.validateForRequest(request);
            fail();
        } catch (ValidationFailException ignored) {
        }

        request.setExtractionTargetId(Arrays.asList(1L, 2L, 3L));
        request.setRegularlySubmissionFrequencyPeriodUnit(1);
        validator.validateForRequest(request);
    }
}
