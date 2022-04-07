package com.fastretailing.marketingpf.controllers.segment.update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentConditionDto;
import com.fastretailing.marketingpf.controllers.segment.update.SegmentUpdateRequest.SegmentDto;
import com.fastretailing.marketingpf.domain.entities.SegmentItemForScreenMaster;
import com.fastretailing.marketingpf.domain.enums.EVENT_TARGET_PERIOD_TYPE;
import com.fastretailing.marketingpf.domain.enums.INPUT_TYPE;
import com.fastretailing.marketingpf.domain.enums.LOGICAL_OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.OPERATOR_TYPE;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.domain.mappers.SegmentItemForScreenMasterMapper;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

class SegmentUpdateValidatorTest extends BaseSpringBootTest {

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));
    }

    @Autowired
    public SegmentUpdateValidator segmentUpdateValidator;

    @Test
    @Sql("/controllers/SegmentUpdateControllerTest/SegmentUpdateValidatorTest/data.sql")
    public void testValidate() {
        SegmentDto s = new SegmentDto();
        s.setSqlEditFlag("0");
        segmentUpdateValidator.validate(1001L, s);

        segmentUpdateValidator.validate(1001L, new SegmentDto());

        try {
            segmentUpdateValidator.validate(9999L, s);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentUpdateValidator.validate(1002L, s);
            fail();
        } catch (ValidationFailException e) {}

        try {
            s.setSqlEditFlag("1");
            segmentUpdateValidator.validate(1003L, s);
            fail();
        } catch (ValidationFailException e) {}
    }

    @Test
    public void testValidateBasicSegment() {
        segmentUpdateValidator.validateBasicSegment(new SegmentDto());

        try {
            SegmentDto s_1 = new SegmentDto();
            s_1.setEventTargetPeriodType("1");
            segmentUpdateValidator.validateBasicSegment(s_1);
        } catch (ValidationFailException e) {}

        try {
            SegmentDto s_2 = new SegmentDto();
            s_2.setEventTargetPeriodType("2");
            segmentUpdateValidator.validateBasicSegment(s_2);
        } catch (ValidationFailException e) {}

        try {
            SegmentDto segmentDto = new SegmentDto();
            segmentDto.setEventTargetPeriodType(EVENT_TARGET_PERIOD_TYPE.RELATIVE_DATE.getValueAsString());
            segmentDto.setEventTargetPeriodRelativeNumberValue(1000L);
            segmentDto.setEventTargetPeriodRelativePeriodUnit("99");
            segmentUpdateValidator.validateBasicSegment(segmentDto);
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
        SegmentConditionDto segmentCondition = new SegmentConditionDto();
        try {
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setSegmentItemForScreenSequence(1001L);
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setOperatorType("1");
            segmentCondition.setComparisonValue(Arrays.asList("hoge", "fuga"));
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setComparisonValue(Arrays.asList("val_01"));
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setSegmentConditionBlockOrder(1);
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        try {
            segmentCondition.setLogicalOperatorType("1");
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check segmentItemForScreenMaster == null
        try {
            segmentCondition.setSegmentItemForScreenSequence(9999L);
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check operatorType is not in operatorTypeList of segmentItemForScreenMaster
        try {
            segmentCondition.setSegmentItemForScreenSequence(1L);
            segmentCondition.setOperatorType("10");
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);
            fail();
        } catch (ValidationFailException e) {}

        // Check all conditions are passed
        segmentCondition.setOperatorType("3");
        segmentUpdateValidator.validateSegmentCondition(segmentCondition);
    }

    @Test
    public void testValidateSqlSegment() {
        SegmentDto s = new SegmentDto();
        s.setEditedSql("sql");
        segmentUpdateValidator.validateSqlSegment(s);

        try {
            segmentUpdateValidator.validateSqlSegment(new SegmentDto());
            fail();
        } catch (ValidationFailException e) {}

        try {
            SegmentDto segmentDto = new SegmentDto();
            segmentDto.setEditedSql("select * from talbe;");
            segmentUpdateValidator.validateSqlSegment(segmentDto);
            fail();
        } catch (ValidationFailException e) {
            assertThat(e.getValidationErrors().size()).isEqualTo(1);
            assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("editedSql");
            assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("EditedSql has invalid character");
        }
    }

    @Nested
    public class ValidateSegmentConditionTest {

        @Autowired
        public SegmentUpdateValidator segmentUpdateValidator;

        @MockBean
        public SegmentItemForScreenMasterMapper segmentItemForScreenMasterMapper;

        @Test
        public void testValidateExtraCondition() {
            SegmentConditionDto segmentCondition = new SegmentConditionDto();
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
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);

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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("ComparisonValue must be number value");
            }

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
            segmentUpdateValidator.validateSegmentCondition(segmentCondition);

            // check comparisonValue vs choicesList
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
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
                segmentUpdateValidator.validateSegmentCondition(segmentCondition);
                fail();
            } catch (ValidationFailException e) {
                assertThat(e.getValidationErrors().size()).isEqualTo(1);
                assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("ComparisonValue");
                assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("There is element in comparisonValue [5] is not in ChoicesList of segmentItemForScreenMaster with SegmentItemForScreenSequence = 1001");
            }
        }
    }
}
