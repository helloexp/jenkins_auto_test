package com.fastretailing.marketingpf.controllers.error;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.controllers.error.ErrorResponse.ValidationError;
import com.fastretailing.marketingpf.exceptions.InsufficientSqlSegmentRoleException;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import com.fastretailing.marketingpf.exceptions.ValidationFailException;
import com.fastretailing.marketingpf.utils.AuthUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import net.logstash.logback.argument.StructuredArguments;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@RestController
public class ErrorController extends BaseController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * This is fallback error handling
     *
     * @param request
     * @return ResponseEntity<Object>
     */
    @GetMapping("/error")
    public ResponseEntity<Object> handleDefaultUncatchError(HttpServletRequest request) {
        String requestId = (String) request.getAttribute("requestId");
        logger.error("Fallback error occurs", StructuredArguments.keyValue("XID", requestId));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E00500, requestId));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("No handler found", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00400, requestId));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Handler found but no method is support", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00400, requestId));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        String requestBody = "n/a";
        try {
            requestBody = IOUtils.toString(e.getHttpInputMessage().getBody(), StandardCharsets.UTF_8);
        } catch (Exception ioe) {// ignore
        }
        logger.debug("Request format is incorrect", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e),
                StructuredArguments.keyValue("requestBody", requestBody));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ERROR_CODE.E00400, requestId).withDetail("Request is not parsable!"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsExceptionException(BadCredentialsException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Bad credentials", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ERROR_CODE.E00401, requestId));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Access denied", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ERROR_CODE.E00403, requestId));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResouceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Resource not found", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ERROR_CODE.E00404, requestId));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("File size is two big", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00417, requestId)
                .withDetail("File size is too big"));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.error("Unhandle error occurs, msg = " + e.getMessage() + ", XID = " + requestId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E00500, requestId));
    }

    @ExceptionHandler(value = MkdbApiUpstreamFailureException.class)
    public ResponseEntity<Object> handleMkdbApiUpstreamFailureException(MkdbApiUpstreamFailureException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.error("Mkdb api response error, msg = " + e.getMessage() + ", XID = " + requestId, e);
        if (e.status == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ERROR_CODE.E00404, requestId).withDetail(e.response));
        }
        if (e.status == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00400, requestId).withDetail(e.response));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E01500, requestId)
                .withDetail(e.response));
    }

    @ExceptionHandler(value = WebClientRequestException.class)
    public ResponseEntity<Object> handleWebClientRequestExceptionException(WebClientRequestException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.error("Mkdb api request error, msg = " + e.getMessage() + ", XID = " + requestId, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E02500, requestId));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindingException(BindException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Validation failed", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        List<ValidationError> validationErrors = e.getBindingResult().getAllErrors().stream()
                .map((error) -> new ValidationError(((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00400, requestId)
                .withDetail("Validation failed")
                .withValidationErrors(validationErrors));
    }

    @ExceptionHandler(ValidationFailException.class)
    public ResponseEntity<Object> handleValidationFailException(ValidationFailException e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.debug("Validation failed", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("error", e));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ERROR_CODE.E00400, requestId)
                .withDetail("Validation failed")
                .withValidationErrors(e.getValidationErrors()));
    }

    @ExceptionHandler(InsufficientSqlSegmentRoleException.class)
    public ResponseEntity<Object> handleValidationFailException(InsufficientSqlSegmentRoleException e,  HttpServletRequest request) {
        String requestId = (String) request.getAttribute("requestId");
        logger.debug("Insufficient sql segment role", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("request_uri", request.getRequestURI()),
                StructuredArguments.keyValue("user_id", AuthUtils.getLoginOidcUser().getAttribute("oid")),
                StructuredArguments.keyValue("authorities", AuthUtils.getAuthentication().getAuthorities()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ERROR_CODE.E01403, requestId)
                .withDetail("Please contact admin for further information"));
    }
}
