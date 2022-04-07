package com.fastretailing.marketingpf.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;

public class AesUtilsTest extends BaseSpringBootTest {

    @Test
    public void testEncrypt() {
        assertThat(AesUtils.encrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "hoge"))
                .isEqualTo("Vd7GPAtz0l+im5mt/H81dw==");
        assertThat(AesUtils.encrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "誕生日 おめでとう"))
                .isEqualTo("YIho47DTXV1RDMVuHXIbY1VFtDMFkJyuCWI9Lal+Wag=");
        assertThat(AesUtils.encrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./><?"))
                .isEqualTo("UIz39GxtzZISP31tzeKpqu8NoWTfBJtGri1nWu/6hGdTiDavU6JMG0T4EadGbn0V");
    }

    @Test
    public void testDecrypt() {
        assertThat(AesUtils.decrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "Vd7GPAtz0l+im5mt/H81dw=="))
                .isEqualTo("hoge");
        assertThat(AesUtils.decrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "YIho47DTXV1RDMVuHXIbY1VFtDMFkJyuCWI9Lal+Wag="))
                .isEqualTo("誕生日 おめでとう");
        assertThat(AesUtils.decrypt("TJCaCskWShauqvzKExXMOhuiMfVjCe5r", "UIz39GxtzZISP31tzeKpqu8NoWTfBJtGri1nWu/6hGdTiDavU6JMG0T4EadGbn0V"))
                .isEqualTo("`1234567890-=~!@#$%^&*()_+[]\\{}|;':\",./><?");
    }
}
