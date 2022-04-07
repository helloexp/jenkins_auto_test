package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.enums.BRAND;
import com.fastretailing.marketingpf.domain.enums.BUSINESS_TYPE;
import com.fastretailing.marketingpf.domain.enums.COUNTRY;
import com.fastretailing.marketingpf.domain.enums.SUBMISSION_TIMING_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutboundSettingMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        public OutboundSettingMapper outboundSettingMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingMapperTest/FindByIdTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            OutboundSetting result = outboundSettingMapper.findById(1001L);
            assertThat(result.getOutboundSettingSequence()).isEqualTo(1001L);
            assertThat(result.getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER);
            assertThat(result.getBrandId()).isEqualTo("010");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION);
            assertThat(result.getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 1));
            assertThat(result.getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 2));
            assertThat(result.getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(1000);
            assertThat(result.getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(1);
            assertThat(result.getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(result.getOutboundSettingName()).isEqualTo("name_01");

            result = outboundSettingMapper.findById(1002L);
            assertThat(result.getOutboundSettingSequence()).isEqualTo(1002L);
            assertThat(result.getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.CRM);
            assertThat(result.getBrandId()).isEqualTo("010");
            assertThat(result.getCountryId()).isEqualTo("1");
            assertThat(result.getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.RESERVATION_SUBMISSION);
            assertThat(result.getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 1));
            assertThat(result.getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 2));
            assertThat(result.getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(2000);
            assertThat(result.getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(2);
            assertThat(result.getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(result.getOutboundSettingName()).isEqualTo("name_02");

            result = outboundSettingMapper.findById(9999L);
            assertThat(result).isEqualTo(null);

            result = outboundSettingMapper.findById(null);
            assertThat(result).isEqualTo(null);

        }
    }

    @Nested
    public class FindAllTest {

        @Autowired
        public OutboundSettingMapper outboundSettingMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingMapperTest/FindAllTest/data.sql")
        public void expectingFindListSuccess() {
            List<OutboundSetting> resultList = outboundSettingMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getOutboundSettingSequence()).isEqualTo(1001L);
            assertThat(resultList.get(1).getOutboundSettingSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getOutboundSettingSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public OutboundSettingMapper outboundSettingMapper;

        @Test
        @Sql("/domain/mappers/OutboundSettingMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            OutboundSetting outbound_03 = new OutboundSetting();
            outbound_03.setBusinessType("1");
            outbound_03.setBrandId("010");
            outbound_03.setCountryId("1");
            outbound_03.setSubmissionTimingType("1");
            outbound_03.setReserveSubmissionDateTime(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outbound_03.setRegularlySubmissionFrequencyDateTimeBasis(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outbound_03.setRegularlySubmissionFrequencyPeriodNumberValue(3000);
            outbound_03.setRegularlySubmissionFrequencyPeriodUnit(3);
            outbound_03.setSubmissionCompletionContact("{}");
            outbound_03.setOutboundSettingName("name_03");
            outbound_03.setDeletionFlagForAudit("f");
            outbound_03.setCreateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            outbound_03.setCreateUserIdForAudit("user_3");
            outbound_03.setUpdateDateTimeForAudit((LocalDateTime.of(2021, 3, 3, 0, 0, 0)));
            outbound_03.setUpdateUserIdForAudit("user_3");
            outboundSettingMapper.create(outbound_03);

            List<OutboundSetting> resultList = outboundSettingMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(resultList.get(0).getOutboundSettingSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER);
            assertThat(resultList.get(0).getBrandId()).isEqualTo(BRAND.UQ.getCode());
            assertThat(resultList.get(0).getCountryId()).isEqualTo(COUNTRY.JP.getCode());
            assertThat(resultList.get(0).getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION);
            assertThat(resultList.get(0).getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
            assertThat(resultList.get(0).getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(1000);
            assertThat(resultList.get(0).getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(1);
            assertThat(resultList.get(0).getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(resultList.get(0).getOutboundSettingName()).isEqualTo("name_01");
            assertThat(resultList.get(1).getOutboundSettingSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.CRM);
            assertThat(resultList.get(1).getBrandId()).isEqualTo(BRAND.UQ.getCode());
            assertThat(resultList.get(1).getCountryId()).isEqualTo(COUNTRY.JP.getCode());
            assertThat(resultList.get(1).getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.RESERVATION_SUBMISSION);
            assertThat(resultList.get(1).getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(resultList.get(1).getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 2, 2, 0, 0, 0));
            assertThat(resultList.get(1).getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(2000);
            assertThat(resultList.get(1).getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(2);
            assertThat(resultList.get(1).getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(resultList.get(1).getOutboundSettingName()).isEqualTo("name_02");
            // Check new records
            assertThat(resultList.get(2).getBusinessTypeAsEnum()).isEqualTo(BUSINESS_TYPE.ATTRACTING_EXTERNAL_CUSTOMER);
            assertThat(resultList.get(2).getBrandId()).isEqualTo(BRAND.UQ.getCode());
            assertThat(resultList.get(2).getCountryId()).isEqualTo(COUNTRY.JP.getCode());
            assertThat(resultList.get(2).getSubmissionTimingTypeAsEnum()).isEqualTo(SUBMISSION_TIMING_TYPE.IMMEDIATE_SUBMISSION);
            assertThat(resultList.get(2).getReserveSubmissionDateTime()).isEqualTo(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            assertThat(resultList.get(2).getRegularlySubmissionFrequencyDateTimeBasis()).isEqualTo(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            assertThat(resultList.get(2).getRegularlySubmissionFrequencyPeriodNumberValue()).isEqualTo(3000);
            assertThat(resultList.get(2).getRegularlySubmissionFrequencyPeriodUnit()).isEqualTo(3);
            assertThat(resultList.get(2).getSubmissionCompletionContact()).isEqualTo("{}");
            assertThat(resultList.get(2).getOutboundSettingName()).isEqualTo("name_03");
        }
    }
}
