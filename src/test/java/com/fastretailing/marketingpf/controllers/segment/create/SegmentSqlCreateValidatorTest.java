package com.fastretailing.marketingpf.controllers.segment.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.controllers.segment.create.SegmentCreateRequest.SegmentCreateRequestDto;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

class SegmentSqlCreateValidatorTest extends BaseSpringBootTest {

    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));
    }

    @Autowired
    public SegmentSqlCreateValidator segmentSqlCreateValidator;

    @Test
    public void testSegmentSqlCreateValidator() {
        SegmentCreateRequestDto segmentCreateRequestDto_1 = new SegmentCreateRequestDto();
        segmentCreateRequestDto_1.setEditedSql("sql");
        segmentSqlCreateValidator.validate(segmentCreateRequestDto_1);

        try {
            SegmentCreateRequestDto segmentCreateRequestDto_2 = new SegmentCreateRequestDto();
            segmentSqlCreateValidator.validate(segmentCreateRequestDto_2);
            fail();
        } catch (ValidationFailException ignored) {
        }

        try {
            SegmentCreateRequestDto segmentCreateRequestDto = new SegmentCreateRequestDto();
            segmentCreateRequestDto.setEditedSql("select * form table;");
            segmentSqlCreateValidator.validate(segmentCreateRequestDto);
            fail();
        } catch (ValidationFailException e) {
           assertThat(e.getValidationErrors().size()).isEqualTo(1);
           assertThat(e.getValidationErrors().get(0).getField()).isEqualTo("editedSql");
           assertThat(e.getValidationErrors().get(0).getMessage()).isEqualTo("EditedSql has invalid character");
        }
    }
}
