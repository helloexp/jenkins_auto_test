package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class OutboundSettingServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @MockBean
        public OutboundSettingMapper outboundSettingMapper;

        @Autowired
        public OutboundSettingService outboundSettingService;

        @Test
        public void expectingFindSuccess() {
            OutboundSetting outboundSetting = new OutboundSetting();
            outboundSetting.setOutboundSettingSequence(1001L);
            outboundSetting.setBusinessType(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER.getValueAsString());
            outboundSetting.setBrandId(BRAND.UQ.getCode());
            outboundSetting.setCountryId(COUNTRY.JP.getCode());
            outboundSetting.setSubmissionTimingType(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION.getValueAsString());
            outboundSetting.setReserveSubmissionDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            outboundSetting.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            outboundSetting.setRegularlySubmissionFrequencyPeriodNumberValue(1000);
            outboundSetting.setRegularlySubmissionFrequencyPeriodUnit(1);
            outboundSetting.setSubmissionCompletionContact("{}");
            outboundSetting.setOutboundSettingName("name_01");
            Mockito.doReturn(outboundSetting).when(outboundSettingMapper).findById(Mockito.anyLong());

            OutboundSetting result = outboundSettingService.findById(1001L);
            assertThat(result.getOutboundSettingSequence()).isEqualTo(1001L);
            assertThat(result.getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER);
            assertThat(result.getBrandId()).isEqualTo("010");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION);
            assertThat(result.getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(result.getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(1000);
            assertThat(result.getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(1);
            assertThat(result.getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(result.getOutboundSettingName()).isEqualTo("name_01");
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        private OutboundSettingService outboundSettingService;

        @Test
        @Sql("/services/OutboundSettingServiceTest/CreateTest/t_outbound_stng.sql")
        public void expectingCreateSuccess() {
            outboundSettingService.create(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER, BRAND.UQ, COUNTRY.JP, SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION, LocalDateTime.of(2021, 3, 3, 0, 0, 0),
                    LocalDateTime.of(2021, 3, 3, 0, 0, 0), 3000, 3, "{}", "name_03", LocalDateTime.of(2021, 3, 3, 0, 0, 0), "user_03");
            assertQueryResult("SELECT * FROM t_outbound_stng ORDER BY outbound_stng_seq ASC").isSameDataAs(
                    "1001,1,010,1,1,2021-01-01 00:00:00,2021-01-01 00:00:00,1000,1,{},name_01,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2,010,1,2,2021-02-02 00:00:00,2021-02-02 00:00:00,2000,2,{},name_02,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,1,010,1,1,2021-03-03 00:00:00,2021-03-03 00:00:00,3000,3,{},name_03,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03");

            outboundSettingService.create(BUSINESS_TYPE.CRM, BRAND.GU, COUNTRY.JP, SUBMISSION_TIMING_TYPE.REGULAR_SUBMISSION, LocalDateTime.of(2021, 3, 4, 0, 0, 0),
                    LocalDateTime.of(2021, 3, 4, 0, 0, 0), 3000, 3, "{}", "name_04", LocalDateTime.of(2021, 3, 4, 0, 0, 0), "user_04");
            assertQueryResult("SELECT * FROM t_outbound_stng ORDER BY outbound_stng_seq ASC").isSameDataAs(
                    "1001,1,010,1,1,2021-01-01 00:00:00,2021-01-01 00:00:00,1000,1,{},name_01,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2,010,1,2,2021-02-02 00:00:00,2021-02-02 00:00:00,2000,2,{},name_02,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,1,010,1,1,2021-03-03 00:00:00,2021-03-03 00:00:00,3000,3,{},name_03,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03",
                    "1004,2,090,1,3,2021-03-04 00:00:00,2021-03-04 00:00:00,3000,3,{},name_04,f,2021-03-04 00:00:00,user_04,null,null,2021-03-04 00:00:00,user_04");
        }
    }
}
