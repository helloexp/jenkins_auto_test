package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutputPlatformOutboundSettingIntermediateMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindByOutboundSettingSequenceTest {

        @Autowired
        public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutputPlatformOutboundSettingIntermediateMapperTest/FindByOutboundSettingSequenceTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            OutputPlatformOutboundSettingIntermediate result = outputPlatformOutboundSettingIntermediateMapper.findByOutboundSettingSequence(2001L);
            assertThat(result.getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1001L);
            assertThat(result.getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(result.getOutputPlatformSequence()).isEqualTo(3001L);
            assertThat(result.getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(result.getAdAccountSequence()).isEqualTo(4001L);
            assertThat(result.getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(result.getCrmPlatformSequence()).isEqualTo(6001L);
            assertThat(result.getAudienceId()).isEqualTo("account_01");
            assertThat(result.getUserListName()).isEqualTo("name_01");
            assertThat(result.getExtractionTargetIdAsEnum()).isEqualTo(EXTRACTION_TARGET_ID.MAIL_ADDRESS);

            result = outputPlatformOutboundSettingIntermediateMapper.findByOutboundSettingSequence(2003L);
            assertThat(result.getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1003L);
            assertThat(result.getOutboundSettingSequence()).isEqualTo(2003L);
            assertThat(result.getOutputPlatformSequence()).isEqualTo(3003L);
            assertThat(result.getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(result.getAdAccountSequence()).isEqualTo(4003L);
            assertThat(result.getAdsPlatformSequence()).isEqualTo(5003L);
            assertThat(result.getCrmPlatformSequence()).isEqualTo(6003L);
            assertThat(result.getAudienceId()).isEqualTo("account_03");
            assertThat(result.getUserListName()).isEqualTo("name_03");
            assertThat(result.getExtractionTargetIdAsEnum()).isEqualTo(EXTRACTION_TARGET_ID.MAIL_ADDRESS);

            result = outputPlatformOutboundSettingIntermediateMapper.findByOutboundSettingSequence(9999L);
            assertThat(result).isEqualTo(null);
        }
    }

    @Nested
    public class FindAllTest {

        @Autowired
        public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutputPlatformOutboundSettingIntermediateMapperTest/FindAllTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            List<OutputPlatformOutboundSettingIntermediate> resultList = outputPlatformOutboundSettingIntermediateMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            assertThat(resultList.get(0).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(resultList.get(0).getOutputPlatformSequence()).isEqualTo(3001L);
            assertThat(resultList.get(0).getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(resultList.get(0).getAdAccountSequence()).isEqualTo(4001L);
            assertThat(resultList.get(0).getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(resultList.get(0).getCrmPlatformSequence()).isEqualTo(6001L);
            assertThat(resultList.get(0).getAudienceId()).isEqualTo("account_01");
            assertThat(resultList.get(0).getUserListName()).isEqualTo("name_01");
            assertThat(resultList.get(0).getExtractionTargetId()).isEqualTo("1");
            assertThat(resultList.get(1).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1002L);
            assertThat(resultList.get(2).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1003L);
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutputPlatformOutboundSettingIntermediateMapperTest/CreateTest/data.sql")
        public void expectingCreateSuccess_givingAvailableParams() {
            OutputPlatformOutboundSettingIntermediate output_03 = new OutputPlatformOutboundSettingIntermediate();
            output_03.setOutboundSettingSequence(2003L);
            output_03.setOutputPlatformSequence(3003L);
            output_03.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            output_03.setAdAccountSequence(4003L);
            output_03.setAdsPlatformSequence(5003L);
            output_03.setCrmPlatformSequence(6003L);
            output_03.setAudienceId("account_03");
            output_03.setUserListName("name_03");
            output_03.setExtractionTargetId("3");
            output_03.setDeletionFlagForAudit("f");
            output_03.setCreateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            output_03.setCreateUserIdForAudit("user_03");
            output_03.setUpdateDateTimeForAudit(LocalDateTime.of(2021, 3, 3, 0, 0, 0));
            output_03.setUpdateUserIdForAudit("user_03");
            outputPlatformOutboundSettingIntermediateMapper.create(output_03);

            List<OutputPlatformOutboundSettingIntermediate> resultList = outputPlatformOutboundSettingIntermediateMapper.findAll();
            assertThat(resultList.size()).isEqualTo(3);
            // Check origin records
            assertThat(resultList.get(0).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1001L);
            assertThat(resultList.get(0).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(resultList.get(0).getOutputPlatformSequence()).isEqualTo(3001L);
            assertThat(resultList.get(0).getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(resultList.get(0).getAdAccountSequence()).isEqualTo(4001L);
            assertThat(resultList.get(0).getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(resultList.get(0).getCrmPlatformSequence()).isEqualTo(6001L);
            assertThat(resultList.get(0).getAudienceId()).isEqualTo("account_01");
            assertThat(resultList.get(0).getUserListName()).isEqualTo("name_01");
            assertThat(resultList.get(0).getExtractionTargetId()).isEqualTo("1");
            assertThat(resultList.get(1).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1002L);
            assertThat(resultList.get(1).getOutboundSettingSequence()).isEqualTo(2002L);
            assertThat(resultList.get(1).getOutputPlatformSequence()).isEqualTo(3002L);
            assertThat(resultList.get(1).getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.CRM_PF);
            assertThat(resultList.get(1).getAdAccountSequence()).isEqualTo(4002L);
            assertThat(resultList.get(1).getAdsPlatformSequence()).isEqualTo(5002L);
            assertThat(resultList.get(1).getCrmPlatformSequence()).isEqualTo(6002L);
            assertThat(resultList.get(1).getAudienceId()).isEqualTo("account_02");
            assertThat(resultList.get(1).getUserListName()).isEqualTo("name_02");
            assertThat(resultList.get(1).getExtractionTargetId()).isEqualTo("2");
            // Check new records
            assertThat(resultList.get(2).getOutboundSettingSequence()).isEqualTo(2003L);
            assertThat(resultList.get(2).getOutputPlatformSequence()).isEqualTo(3003L);
            assertThat(resultList.get(2).getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(resultList.get(2).getAdAccountSequence()).isEqualTo(4003L);
            assertThat(resultList.get(2).getAdsPlatformSequence()).isEqualTo(5003L);
            assertThat(resultList.get(2).getCrmPlatformSequence()).isEqualTo(6003L);
            assertThat(resultList.get(2).getAudienceId()).isEqualTo("account_03");
            assertThat(resultList.get(2).getUserListName()).isEqualTo("name_03");
            assertThat(resultList.get(2).getExtractionTargetId()).isEqualTo("3");
        }
    }

    @Nested
    public class FindListByOutboundSettingSequenceTest {

        @Autowired
        public OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @Test
        @Sql("/domain/mappers/OutputPlatformOutboundSettingIntermediateMapperTest/FindListByOutboundSettingSequenceTest/t_otpt_pltfrm_outbound_stng_intrm.sql")
        public void expectingSuccess() {
            List<OutputPlatformOutboundSettingIntermediate> result = outputPlatformOutboundSettingIntermediateMapper.findListByOutboundSettingSequence(2001L);
            assertThat(result.size()).isEqualTo(3);
            assertThat(result.get(0).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1005L);
            assertThat(result.get(0).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(result.get(0).getAdAccountSequence()).isEqualTo(4001L);
            assertThat(result.get(0).getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(result.get(0).getAudienceId()).isEqualTo("456");
            assertThat(result.get(0).getUserListName()).isEqualTo("name_456");
            assertThat(result.get(0).getExtractionTargetIdAsEnum()).isEqualTo(EXTRACTION_TARGET_ID.MAIL_ADDRESS);
            assertThat(result.get(1).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1001L);
            assertThat(result.get(1).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(result.get(1).getAdAccountSequence()).isEqualTo(4001L);
            assertThat(result.get(1).getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(result.get(1).getAudienceId()).isEqualTo("123");
            assertThat(result.get(1).getUserListName()).isEqualTo("name_123");
            assertThat(result.get(1).getExtractionTargetIdAsEnum()).isEqualTo(EXTRACTION_TARGET_ID.ADID);
            assertThat(result.get(2).getOutputPlatformOutboundSettingIntermediateSequence()).isEqualTo(1004L);
            assertThat(result.get(2).getOutboundSettingSequence()).isEqualTo(2001L);
            assertThat(result.get(2).getAdAccountSequence()).isEqualTo(4001L);
            assertThat(result.get(2).getAdsPlatformSequence()).isEqualTo(5001L);
            assertThat(result.get(2).getAudienceId()).isEqualTo("789");
            assertThat(result.get(2).getUserListName()).isEqualTo("name_789");
            assertThat(result.get(2).getExtractionTargetIdAsEnum()).isEqualTo(EXTRACTION_TARGET_ID.IDFA);

            result = outputPlatformOutboundSettingIntermediateMapper.findListByOutboundSettingSequence(9999L);
            assertThat(result.size()).isEqualTo(0);
        }
    }
}
