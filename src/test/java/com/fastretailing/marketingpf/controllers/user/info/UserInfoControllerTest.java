package com.fastretailing.marketingpf.controllers.user.info;

import static com.fastretailing.marketingpf.tests.JsonContentMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public class UserInfoControllerTest extends BaseSpringBootTest {

    @Nested
    public class InfoTest {

        @Autowired
        public MockMvc mvc;

        @BeforeEach
        public void setup() {
            Map<String, Object> claims = new HashMap<>();
            claims.put("oid", "oid");
            claims.put("name", "Fuga");
            claims.put("sub", "sub");
            OidcIdToken token = new OidcIdToken("hoge", null, null, claims);
            DefaultOidcUser principal = new DefaultOidcUser(new ArrayList<>(), token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", Arrays.asList(new SimpleGrantedAuthority("ROLE_" + USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        @Test
        public void testInfo() throws Exception {
            mvc.perform(get("/api/marketingpf/v1/fr/jp/user/info")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(json().contentEquals("{\"userId\":\"oid\",\"userFullName\":\"Fuga\",\"hasSqlSegmentRole\":true}"));
        }
    }
}
