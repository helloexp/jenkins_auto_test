package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformOutboundSettingIntermediate;
import com.fastretailing.marketingpf.domain.enums.EXTRACTION_TARGET_ID;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.domain.mappers.OutputPlatformOutboundSettingIntermediateMapper;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

class OutputPlatformOutboundSettingIntermediateServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByOutboundSettingSequenceTest {

        @MockBean
        OutputPlatformOutboundSettingIntermediateMapper outputPlatformOutboundSettingIntermediateMapper;

        @Autowired
        OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

        @Test
        public void expectingFindSuccess() {
            OutputPlatformOutboundSettingIntermediate outputPlatformOutboundSetting = new OutputPlatformOutboundSettingIntermediate();
            outputPlatformOutboundSetting.setOutputPlatformOutboundSettingIntermediateSequence(1001L);
            outputPlatformOutboundSetting.setOutboundSettingSequence(2001L);
            outputPlatformOutboundSetting.setOutputPlatformSequence(3001L);
            outputPlatformOutboundSetting.setOutputPlatformType(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            outputPlatformOutboundSetting.setAdAccountSequence(4001L);
            outputPlatformOutboundSetting.setAdsPlatformSequence(5001L);
            outputPlatformOutboundSetting.setCrmPlatformSequence(6001L);
            outputPlatformOutboundSetting.setAudienceId("account_01");
            outputPlatformOutboundSetting.setUserListName("name_01");
            outputPlatformOutboundSetting.setExtractionTargetId(EXTRACTION_TARGET_ID.MAIL_ADDRESS.getValueAsString());
            Mockito.doReturn(outputPlatformOutboundSetting).when(outputPlatformOutboundSettingIntermediateMapper).findByOutboundSettingSequence(Mockito.anyLong());

            OutputPlatformOutboundSettingIntermediate result = outputPlatformOutboundSettingIntermediateService.findByOutboundSettingSequence(2001L);
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
        }
    }

    @Nested
    public class CreateTest {

        @Autowired
        private OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

        @Test
        @Sql("/services/OutputPlatformOutboundSettingIntermediateServiceTest/CreateTest/t_otpt_pltfrm_outbound_stng_intrm.sql")
        public void expectingCreateSuccess() {

            outputPlatformOutboundSettingIntermediateService.create(2003L, 3003L, OUTPUT_PLATFORM_TYPE.ADS_PF, 4003L, 5003L, 6003L, "account_03", "name_03_IDFA", "name_03",
                    EXTRACTION_TARGET_ID.IDFA, LocalDateTime.of(2021, 3, 3, 0, 0, 0), "user_03");
            assertQueryResult("SELECT * FROM t_otpt_pltfrm_outbound_stng_intrm ORDER BY otpt_pltfrm_outbound_stng_intrm_seq ASC").isSameDataAs(
                    "1001,2001,3001,1,4001,5001,6001,account_01,name_01,1,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2002,3002,2,4002,5002,6002,account_02,name_02,2,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,2003,3003,1,4003,5003,6003,account_03,name_03_IDFA,3,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03");

            outputPlatformOutboundSettingIntermediateService.create(2004L, 3001L, OUTPUT_PLATFORM_TYPE.CRM_PF, 4003L, 5003L, 6003L, "account_04", "name_04_Mail Address", "name_04",
                    EXTRACTION_TARGET_ID.MAIL_ADDRESS, LocalDateTime.of(2021, 3, 4, 0, 0, 0), "user_04");
            assertQueryResult("SELECT * FROM t_otpt_pltfrm_outbound_stng_intrm ORDER BY otpt_pltfrm_outbound_stng_intrm_seq ASC").isSameDataAs(
                    "1001,2001,3001,1,4001,5001,6001,account_01,name_01,1,f,2021-01-01 00:00:00,user_01,null,null,2021-01-01 00:00:00,user_01",
                    "1002,2002,3002,2,4002,5002,6002,account_02,name_02,2,f,2021-02-02 00:00:00,user_02,null,null,2021-02-02 00:00:00,user_02",
                    "1003,2003,3003,1,4003,5003,6003,account_03,name_03_IDFA,3,f,2021-03-03 00:00:00,user_03,null,null,2021-03-03 00:00:00,user_03",
                    "1004,2004,3001,2,4003,5003,6003,account_04,name_04_Mail Address,1,f,2021-03-04 00:00:00,user_04,null,null,2021-03-04 00:00:00,user_04");
        }
    }

    @Nested
    public class FindListByOutboundSettingSequenceTest {

        @Autowired
        public OutputPlatformOutboundSettingIntermediateService outputPlatformOutboundSettingIntermediateService;

        @Test
        @Sql("/services/OutputPlatformOutboundSettingIntermediateServiceTest/FindListByOutboundSettingSequenceTest/t_otpt_pltfrm_outbound_stng_intrm.sql")
        public void expectingSuccess() {
            List<OutputPlatformOutboundSettingIntermediate> result = outputPlatformOutboundSettingIntermediateService.findListByOutboundSettingSequence(2001L);
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

            result = outputPlatformOutboundSettingIntermediateService.findListByOutboundSettingSequence(9999L);
            assertThat(result.size()).isEqualTo(0);
        }
    }
}
