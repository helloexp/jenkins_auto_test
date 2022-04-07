package com.fastretailing.marketingpf.configs.security;

import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends AADWebSecurityConfigurerAdapter {

    @Autowired
    private Config config;

    @Autowired
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private ForbiddenEntryPoint forbiddenEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String uriPrefix = config.getMkpf().getUriPrefix();
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(uriPrefix + "/scheduler/userlist/")
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(uriPrefix + "/login/oauth2/code/").permitAll()
                .antMatchers(uriPrefix + "/healthcheck").permitAll()
                .antMatchers(uriPrefix + "/error").permitAll()
                .antMatchers(uriPrefix + "/scheduler/userlist/").hasIpAddress(config.getMkpfSchedulerIp())
                .anyRequest().hasAnyRole(USER_ROLE.S_CDUMPF_ADMINISTRATOR.getRole(), USER_ROLE.S_CDUMPF_GENERAL_USER.getRole(), USER_ROLE.S_CDUMPF_SQL_SEGMENT_USER.getRole())
                .and()
                .logout()
                .logoutUrl(uriPrefix + "/user/logout")
                .logoutSuccessUrl(config.getMkpf().getBaseUrl())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "session")
                .and()
                .exceptionHandling().authenticationEntryPoint(forbiddenEntryPoint)
                .and()
                .oauth2Login()
                .loginProcessingUrl(uriPrefix + "/login/oauth2/code/")
                .loginPage(uriPrefix + "/login/oauth2")
                .authorizationEndpoint().baseUri(uriPrefix + "/login/oauth2/authorization")
                .and()
                .successHandler(loginSuccessHandler)
                .userInfoEndpoint()
                .oidcUserService(oidcUserService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedOrigins(Arrays.asList(config.getCors().getAllowedOrigins()));
        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add(HttpHeaders.CONTENT_TYPE);
        for (String extraAllowedHeaders : config.getCors().getAllowedHeaders()) {
            allowedHeaders.add(extraAllowedHeaders);
        }
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
