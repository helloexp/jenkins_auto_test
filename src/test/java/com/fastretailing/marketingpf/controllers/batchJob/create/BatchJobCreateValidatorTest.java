package com.fastretailing.marketingpf.controllers.batchJob.create;

import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

class BatchJobCreateValidatorTest extends BaseSpringBootTest {

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));
    }

    @Autowired
    public BatchJobCreateValidator batchJobCreateValidator;

    @Test
    @Sql("/controllers/BatchJobCreateControllerTest/data.sql")
    public void testValidate() {
        batchJobCreateValidator.validate(1001L);

        try {
            batchJobCreateValidator.validate(9999L);
            fail();
        } catch (ResourceNotFoundException ignored) {
        }

        try {
            batchJobCreateValidator.validate(null);
            fail();
        } catch (RuntimeException ignored) {
        }
    }
}
