package com.fastretailing.marketingpf.controllers.userMaster.gets;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fastretailing.marketingpf.controllers.base.BaseAuditResponse;
import com.fastretailing.marketingpf.controllers.base.BaseResponse;
import com.fastretailing.marketingpf.domain.entities.UserMaster;

public class UserMasterGetResponse extends BaseResponse {
    public List<UserMasterDto> userMasterList = new ArrayList<>();
    
    public UserMasterGetResponse(List<UserMaster> userMasterList) {
        for (UserMaster userMaster : userMasterList) {
            this.userMasterList.add(new UserMasterDto(userMaster));
        }
    }
    @JsonPropertyOrder({
        "userId",
        "userFullName",
        "roleSequenceList",
        "deletionFlagForAudit",
        "createDateTimeForAudit",
        "createUserIdForAudit",
        "deletionDateTimeForAudit",
        "deletionUserIdForAudit",
        "updateDateTimeForAudit",
        "updateUserIdForAudit"
    })
    public static class UserMasterDto extends BaseAuditResponse {
        public String userId;
        public String userFullName;
        public String roleSequenceList;
        
        public UserMasterDto(UserMaster userMaster) {
            this.userId = userMaster.getUserId();
            this.userFullName = userMaster.getUserFullName();
            this.roleSequenceList = userMaster.getRoleSequenceList();
            this.deletionFlagForAudit = userMaster.getDeletionFlagForAudit();
            this.createDateTimeForAudit = userMaster.getCreateDateTimeForAudit();
            this.createUserIdForAudit = userMaster.getCreateUserIdForAudit();
            this.deletionDateTimeForAudit = userMaster.getDeletionDateTimeForAudit();
            this.deletionUserIdForAudit = userMaster.getDeletionUserIdForAudit();
            this.updateDateTimeForAudit = userMaster.getUpdateDateTimeForAudit();
            this.updateUserIdForAudit = userMaster.getUpdateUserIdForAudit();
        }
    }
}