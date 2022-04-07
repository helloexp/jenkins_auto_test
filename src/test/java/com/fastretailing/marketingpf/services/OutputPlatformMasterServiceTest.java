package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformMaster;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutputPlatformMasterServiceTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        public OutputPlatformMasterService outputPlatformMasterService;

        @Test
        @Sql("/services/OutputPlatformMasterServiceTest/FindByIdTest/m_otpt_pltfrm.sql")
        public void expectingSuccess() {
            assertQueryResult("SELECT * FROM m_otpt_pltfrm ORDER BY otpt_pltfrm_seq ASC").isSameDataAs(
                    "1001,1,outputPlatformName1,f,2021-01-01 00:00:00,user_01,2021-01-01 00:00:00,user_01,2021-01-01 00:00:00,user_01",
                    "1002,2,outputPlatformName2,f,2021-02-02 00:00:00,user_02,2021-02-02 00:00:00,user_02,2021-02-02 00:00:00,user_02",
                    "1003,1,outputPlatformName3,t,2021-02-02 00:00:00,user_03,2021-02-02 00:00:00,user_03,2021-02-02 00:00:00,user_03");

            OutputPlatformMaster outputPlatformMaster = outputPlatformMasterService.findById(1001L);
            assertThat(outputPlatformMaster.getOutputPlatformSequence()).isEqualTo(1001L);
            assertThat(outputPlatformMaster.getOutputPlatformType()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF.getValueAsString());
            assertThat(outputPlatformMaster.getOutputPlatformName()).isEqualTo("outputPlatformName1");

            outputPlatformMaster = outputPlatformMasterService.findById(1002L);
            assertThat(outputPlatformMaster.getOutputPlatformSequence()).isEqualTo(1002L);
            assertThat(outputPlatformMaster.getOutputPlatformType()).isEqualTo(OUTPUT_PLATFORM_TYPE.CRM_PF.getValueAsString());
            assertThat(outputPlatformMaster.getOutputPlatformName()).isEqualTo("outputPlatformName2");

            outputPlatformMaster = outputPlatformMasterService.findById(1003L);
            assertThat(outputPlatformMaster).isEqualTo(null);
        }
    }
}
