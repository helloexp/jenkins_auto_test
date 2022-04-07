package com.fastretailing.marketingpf.controllers.segment.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentConditionRequestDto;
import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForScreenMasterMapper;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class SegmentBasicCreateValidatorTest extends BaseSpringBootTest {

    @Autowired
    public SegmentBasicCreateValidator segmentBasicCreateValidator;

    @Test
    public void testSegmentBasicCreateValidator() {
        SegmentCreateRequestDto segmentCreateRequestDto_1 = new SegmentCreateRequestDto();
        segmentCreateRequestDto_1.setTargetItemCategory("TargetItemCategory");
        segmentCreateRequestDto_1.setEventTypeList(Arrays.asList("1", "2", "3"));
        segmentCreateRequestDto_1.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
        segmentCreateRequestDto_1.setEventTargetPeriodStartDate(LocalDate.of(2021, 1, 1));
        segmentCreateRequestDto_1.setEventTargetPeriodEndDate(LocalDate.of(2021, 2, 2));
        segmentCreateRequestDto_1.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
        segmentBasicCreateValidator.validate(segmentCreateRequestDto_1);

        try {
            SegmentCreateRequestDto segmentCreateRequestDto_2 = new SegmentCreateRequestDto();
            segmentCreateRequestDto_1.setEventTypeList(Arrays.asList("1", "2", "3"));
            segmentCreateRequestDto_2.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segmentCreateRequestDto_2.setEventTargetPeriodStartDate(LocalDate.of(2021, 1, 1));
            segmentCreateRequestDto_2.setEventTargetPeriodEndDate(LocalDate.of(2021, 2, 2));
            segmentCreateRequestDto_2.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validate(segmentCreateRequestDto_2);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            SegmentCreateRequestDto segmentCreateRequestDto_3 = new SegmentCreateRequestDto();
            segmentCreateRequestDto_3.setTargetItemCategory("TargetItemCategory");
            segmentCreateRequestDto_3.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.ABSOLUTE_DATE.getValueAsString());
            segmentCreateRequestDto_3.setEventTargetPeriodStartDate(LocalDate.of(2021, 1, 1));
            segmentCreateRequestDto_3.setEventTargetPeriodEndDate(LocalDate.of(2021, 2, 2));
            segmentCreateRequestDto_3.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validate(segmentCreateRequestDto_3);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            SegmentCreateRequestDto segmentCreateRequestDto_4 = new SegmentCreateRequestDto();
            segmentCreateRequestDto_4.setTargetItemCategory("TargetItemCategory");
            segmentCreateRequestDto_1.setEventTypeList(Arrays.asList("1", "2", "3"));
            segmentCreateRequestDto_4.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validate(segmentCreateRequestDto_4);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            SegmentCreateRequestDto segmentCreateRequestDto_5 = new SegmentCreateRequestDto();
            segmentCreateRequestDto_5.setTargetItemCategory("TargetItemCategory");
            segmentCreateRequestDto_1.setEventTypeList(Arrays.asList("1", "2", "3"));
            segmentCreateRequestDto_5.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE.getValueAsString());
            segmentCreateRequestDto_5.setAdditionalConditionLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validate(segmentCreateRequestDto_5);
        } catch (ValidationFailException ignored) {
        }

        try {
            SegmentCreateRequestDto segmentCreateRequestDto = new SegmentCreateRequestDto();
            segmentCreateRequestDto.setTargetItemCategory("TargetItemCategory");
            segmentCreateRequestDto.setEventTypeList(Arrays.asList("1", "2", "3"));
            segmentCreateRequestDto.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE.getValueAsString());
            segmentCreateRequestDto.setEventTargetPeriodRelativeNumberValue(1001L);
            segmentCreateRequestDto.setEventTargetPeriodRelativePeriodUnit("99");
            segmentBasicCreateValidator.validate(segmentCreateRequestDto);
        } catch (ValidationFailException e) {
            assertThat(e.getValidationErrors().size()).isEqualTo(1);
            assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("eventTargetPeriodRelativePeriodUnit");
            assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("EventTargetPeriodRelativePeriodUnit is invalid");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testValidateSegmentCondition() {
        SegmentConditionRequestDto segmentCondition = new SegmentConditionRequestDto();
        try {
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setSegmentItemForScreenSequence(1001L);
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setOperatorType("1");
            segmentCondition.setComparisonValue(Arrays.asList("hoge", "fuga"));
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setComparisonValue(Arrays.asList("val_01"));
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setSegmentConditionBlockOrder(1);
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setLogicalOperatorType("1");
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check segmentItemForScreenMaster == null
        try {
            segmentCondition.setSegmentItemForScreenSequence(9999L);
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check operatorType is not in operatorTypeList of segmentItemForScreenMaster
        try {
            segmentCondition.setSegmentItemForScreenSequence(1L);
            segmentCondition.setOperatorType("10");
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check all conditions are passed
        segmentCondition.setOperatorType("3");
        segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
    }

    @Nested
    public class ValidateSegmentConditionTest {

        @Autowired
        public SegmentBasicCreateValidator segmentBasicCreateValidator;

        @MockBean
        public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

        @Test
        public void testValidateExtraCondition() {
            SegmentConditionRequestDto segmentCondition = new SegmentConditionRequestDto();
            SegmentItemForScreenMaster segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.TEXT_BOX_TEXT.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            segmentCondition.setSegmentItemForScreenSequence(1001L);
            segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            segmentCondition.setComparisonValue(Arrays.asList("1", "2"));
            segmentCondition.setSegmentConditionBlockOrder(1);
            segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);

            // check input type is not numerical value
            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("hoge", "1"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"male\", \"value\": \"male\"},{\"name\":\"female\", \"value\": \"female\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("hoge", "1"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.SINGLE_SELECTION_API_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("hoge", "1"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"male\", \"value\": \"male\"},{\"name\":\"female\", \"value\": \"female\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("hoge", "1"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_API_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("hoge", "1"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

            // check comparisonValue vs choicesList
            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"Man\", \"value\": \"1\"},{\"name\":\"Woman\", \"value\": \"2\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            segmentCondition.setSegmentItemForScreenSequence(1001L);
            segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
            segmentCondition.setComparisonValue(Arrays.asList("1", "2"));
            segmentCondition.setSegmentConditionBlockOrder(1);
            segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
            segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"Man\", \"value\": \"1\"},{\"name\":\"Woman\", \"value\": \"2\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.SINGLE_SELECTION_TEXT.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("1", "2", "3"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("There is element in comparisonValue [1, 2, 3] is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = 1001");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"Man\", \"value\": \"2\"},{\"name\":\"Woman\", \"value\": \"3\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("1", "2", "3"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("There is element in comparisonValue [1, 2, 3] is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = 1001");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"Man\", \"value\": \"1\"},{\"name\":\"Woman\", \"value\": \"2\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_TEXT.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("3"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("There is element in comparisonValue [3] is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = 1001");
            }

            segmentItemForScreenMaster = new SegmentItemForScreenMaster();
            segmentItemForScreenMaster.setSegmentItemForScreenSequence(1001L);
            segmentItemForScreenMaster.setChoicesList("{\"choicesList\": [{\"name\":\"Man\", \"value\": \"1\"},{\"name\":\"Woman\", \"value\": \"2\"}]}");
            segmentItemForScreenMaster.setInputType(INPUT_TYPE.MULTIPLE_SELECTION_NUMERICAL_VALUE.getValueAsString());
            segmentItemForScreenMaster.setSegmentItemName("Gender");
            segmentItemForScreenMaster.setCategory("largeClassification");
            segmentItemForScreenMaster.setDetailCategory("smallClassification");
            segmentItemForScreenMaster.setOperatorList("{\"operatorList\": [3, 4]}");
            segmentItemForScreenMaster.setUrlForApiAccess("hoge.com");
            doReturn(segmentItemForScreenMaster).when(segmentItemForScreenMasterMapper).findById(Mockito.anyLong());
            try {
                segmentCondition.setSegmentItemForScreenSequence(1001L);
                segmentCondition.setOperatorType(OPERATOR_TYPE.IN.getValueAsString());
                segmentCondition.setComparisonValue(Arrays.asList("5"));
                segmentCondition.setSegmentConditionBlockOrder(1);
                segmentCondition.setLogicalOperatorType(LOGICAL_OPERATOR_TYPE.AND.getValueAsString());
                segmentBasicCreateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("There is element in comparisonValue [5] is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = 1001");
            }
        }
    }
}
