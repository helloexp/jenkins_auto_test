package com.fastretailing.marketingpf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.net.URI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;

class GetMasterValueServiceTest extends BaseSpringBootTest {

    @Nested
    public class GetMasterValueTest {

        @MockBean
        private GetMasterValueService getMasterValueService;

        @Test
        public void expectingSuccess() {
            ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
            doReturn("[3, 4]").when(getMasterValueService).getAsMono(uriCaptor.capture(), any());
            doCallRealMethod().when(getMasterValueService).getMasterValue(any());
            String result = getMasterValueService.getMasterValue("hoge.com");
            assertThat(uriCaptor.getValue().toString()).isEqualTo("hoge.com");
            assertThat(result).isEqualTo("[3, 4]");
        }
    }
}
