package com.fastretailing.marketingpf.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.exceptions.InsufficientSqlSegmentRoleException;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;


public class AuthUtilsTest extends BaseSpringBootTest {

    @Nested
    public class GetLoginOidcUserTest {

        @Test
        public void testGetAuthentication() {
            Map<String, Object> claims = new HashMap<>();
            claims.put("oid", "oid");
            claims.put("name", "Fuga");
            claims.put("sub", "sub");
            OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
            DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            assertThat(AuthUtils.getLoginOidcUser().getClaimAsString("oid")).isEqualTo("oid");
            assertThat(AuthUtils.getLoginOidcUser().getClaimAsString("name")).isEqualTo("Fuga");
            assertThat(AuthUtils.getLoginOidcUser().getClaimAsString("sub")).isEqualTo("sub");
        }
    }

    @Nested
    public class GetAuthenticationTest {

        @Test
        public void testGetLoginOidcUser() {
            Authentication authentication = new UsernamePasswordAuthenticationToken("hoge", "", new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            assertThat(AuthUtils.getAuthentication().getPrincipal()).isEqualTo("hoge");
        }
    }

    @Nested
    public class HasSqlSegmentRoleTest {

        @Test
        public void testHasSqlSegmentRole() {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_ADMINISTRATOR.getRole()))));
            assertThat(AuthUtils.hasSqlSegmentRole()).isTrue();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));
            assertThat(AuthUtils.hasSqlSegmentRole()).isTrue();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_GENERAL_USER.getRole()))));
            assertThat(AuthUtils.hasSqlSegmentRole()).isFalse();
        }
    }

    @Nested
    public class PreAuthorizeForSqlSegmentRoleTest {

        @Test
        public void testPreAuthorizeForSqlSegmentRole() {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_ADMINISTRATOR.getRole()))));
            AuthUtils.preAuthorizeForSqlSegmentRole();

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole()))));
            AuthUtils.preAuthorizeForSqlSegmentRole();

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("hoge", "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_GENERAL_USER.getRole()))));
            try {
                AuthUtils.preAuthorizeForSqlSegmentRole();
                fail();
            } catch (InsufficientSqlSegmentRoleException e) {

            } catch (Exception e) {
                fail(e);
            }
        }
    }
}
