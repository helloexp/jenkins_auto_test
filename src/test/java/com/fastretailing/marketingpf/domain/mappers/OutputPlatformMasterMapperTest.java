package com.fastretailing.marketingpf.domain.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformMaster;
import com.fastretailing.marketingpf.domain.enums.OUTPUT_PLATFORM_TYPE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class OutputPlatformMasterMapperTest extends BaseSpringBootTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        public OutputPlatformMasterMapper outputPlatformMasterMapper;

        @Test
        @Sql("/domain/mappers/OutputPlatformMasterMapperTest/FindByIdTest/data.sql")
        public void expectingFindSuccess_givingAvailableParams() {
            OutputPlatformMaster result = outputPlatformMasterMapper.findById(1001L);
            assertThat(result.getOutputPlatformSequence()).isEqualTo(1001L);
            assertThat(result.getOutputPlatformTypeAsEnum()).isEqualTo(OUTPUT_PLATFORM_TYPE.ADS_PF);
            assertThat(result.getOutputPlatformName()).isEqualTo("name_01");

            result = outputPlatformMasterMapper.findById(9999L);
            assertThat(result).isEqualTo(null);
        }
    }
}
