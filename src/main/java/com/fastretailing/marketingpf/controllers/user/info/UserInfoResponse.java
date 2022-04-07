package com.fastretailing.marketingpf.controllers.user.info;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;

@JsonPropertyOrder({"userId", "userFullName", "hasSqlSegmentRole"})
public class UserInfoResponse extends BaseResponse {

    public String userId;
    public String userFullName;
    public boolean hasSqlSegmentRole;

    public UserInfoResponse(String userId, String userFullName, boolean hasSqlSegmentRole) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.hasSqlSegmentRole = hasSqlSegmentRole;
    }
}
