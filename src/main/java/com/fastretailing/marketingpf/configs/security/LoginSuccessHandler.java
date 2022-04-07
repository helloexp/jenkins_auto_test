package com.fastretailing.marketingpf.configs.security;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.services.UserMasterService;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Autowired
    private UserMasterService userMasterService;

    @Autowired
    private Config config;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        // Save user master
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        try {
            userMasterService.upsert(oidcUser.getAttribute("oid"), oidcUser.getAttribute("name"), JsonUtils.toJson(authentication.getAuthorities()), LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Fail to register login user",
                    StructuredArguments.keyValue("error", e),
                    StructuredArguments.keyValue("requestUrl", request.getRequestURL()));
            throw e;
        }
        // Redirect to front end
        this.setDefaultTargetUrl(config.getMkpf().getBaseUrl());
        this.setAlwaysUseDefaultTargetUrl(true);
    }
}
