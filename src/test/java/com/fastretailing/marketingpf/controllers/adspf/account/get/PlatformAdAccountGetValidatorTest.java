package com.fastretailing.marketingpf.controllers.adspf.account.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class PlatformAdAccountGetValidatorTest extends BaseSpringBootTest {

    @Autowired
    private PlatformAdAccountGetValidator platformAdAccountGetValidator;

    @Test
    @Sql("/controllers/adspf/account/get/PlatformAdAccountGetValidatorTest/t_api_auth_info.sql")
    public void testValidate() {
        assertQueryResult("SELECT api_auth_info_seq, ads_pltfrm_seq, ads_pltfrm_login_user_id, del_flg_f_adt, del_date_time_f_adt, del_user_id_f_adt FROM t_api_auth_info ORDER BY api_auth_info_seq ASC").isSameDataAs(
                "1001,1,user_01,f,2021-01-01 00:00:02,user1",
                "1002,2,user_02,f,2021-02-02 00:00:02,user2",
                "1003,1,user_02,t,2021-02-02 00:00:02,user2",
                "1004,4,user_02,f,2021-02-02 00:00:02,user2");

        platformAdAccountGetValidator.validate(1001L);
        platformAdAccountGetValidator.validate(1002L);

        try {
            platformAdAccountGetValidator.validate(1003L);
            fail();
        } catch (ResourceNotFoundException e) {
        }

        try {
            platformAdAccountGetValidator.validate(1004L);
            fail();
        } catch (ValidationFailException e) {
            assertThat(e.getValidationErrors().size()).isEqualTo(1);
            assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("adsPlatform");
            assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("adsPlatform invalid");
        }
    }
}
