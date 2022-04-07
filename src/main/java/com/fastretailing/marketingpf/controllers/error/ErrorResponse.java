package com.fastretailing.marketingpf.controllers.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "errorCode", "errorMessage", "errorDetail", "requestId", "timestamp", "validationErrors" })
public class ErrorResponse extends BaseResponse {

    public ERROR_CODE errorCode;

    public String errorMessage;

    public String errorDetail;

    public String requestId;

    public String timestamp;

    public List<ValidationError> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(ERROR_CODE errorCode, String requestId) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDesc();
        this.requestId = requestId;
        this.timestamp = Long.toString(System.currentTimeMillis());
    }

    public ErrorResponse withDetail(String errorDetail) {
        this.errorDetail = errorDetail;
        return this;
    }

    public ErrorResponse withValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
        return this;
    }

    @JsonInclude(Include.NON_NULL)
    @JsonPropertyOrder({ "field", "message" })
    @Data
    public static class ValidationError {

        private String field;

        private String message;

        public ValidationError() {
        }

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

    public static class ValidationErrors extends ArrayList<ValidationError> {

        private static final long serialVersionUID = 5779273580662736759L;

        public ValidationErrors() {
        }

        public ValidationErrors(String field, String message) {
            this.add(new ValidationError(field, message));
        }

        public ValidationErrors add(String field, String message) {
            this.add(new ValidationError(field, message));
            return this;
        }
    }
}
