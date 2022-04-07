package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class SchedulerUserListValidatorTest extends BaseSpringBootTest {

    @Autowired
    private SchedulerUserListValidator schedulerUserListValidator;

    @Test
    @Sql("/controllers/scheduler/userlist/SchedulerUserListValidatorTest/t_outbound_stng.sql")
    public void testValidate() {
        assertQueryResult("SELECT * FROM t_outbound_stng ORDER BY outbound_stng_seq ASC").isSameDataAs(
                "1001,1,010,1,1,2021-01-01 00:00:01,2021-01-01 00:00:02,1000,1,{},name_01,f,2021-01-01 00:00:00,user_01,2021-01-01 00:00:00,user_01,2021-01-01 00:00:00,user_01",
                "1002,2,010,1,2,2021-02-02 00:00:01,2021-02-02 00:00:02,2000,2,{},name_02,f,2021-02-02 00:00:00,user_02,2021-02-02 00:00:00,user_02,2021-02-02 00:00:00,user_02",
                "1003,1,010,1,3,2021-03-03 00:00:01,2021-03-03 00:00:02,3000,3,{},name_03,f,2021-03-03 00:00:00,user_03,2021-03-03 00:00:00,user_03,2021-03-03 00:00:00,user_03");

        schedulerUserListValidator.validate(1001L);
        schedulerUserListValidator.validate(1002L);

        try {
            schedulerUserListValidator.validate(9999L);
            fail();
        } catch (ResourceNotFoundException e) {
        }
    }
}
