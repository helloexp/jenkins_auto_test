package com.fastretailing.marketingpf.configs.security;

import com.fastretailing.marketingpf.controllers.error.ERROR_CODE;
import com.fastretailing.marketingpf.controllers.error.ErrorController;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse;
import com.fastretailing.marketingpf.utils.JsonUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class ForbiddenEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        logger.debug("Unauthorized", StructuredArguments.keyValue("error", e),
                StructuredArguments.keyValue("requestURI", request.getRequestURI()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().println(JsonUtils.toJson(new ErrorResponse(ERROR_CODE.E01401, "")));
    }
}
