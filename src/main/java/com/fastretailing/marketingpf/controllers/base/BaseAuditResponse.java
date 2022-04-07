package com.fastretailing.marketingpf.controllers.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;

public class BaseAuditResponse extends BaseResponse {

    public String deletionFlagForAudit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime createDateTimeForAudit;

    public String createUserIdForAudit;

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime deletionDateTimeForAudit;

    @JsonInclude(Include.NON_NULL)
    public String deletionUserIdForAudit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updateDateTimeForAudit;

    public String updateUserIdForAudit;
}
