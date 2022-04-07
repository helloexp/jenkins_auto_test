package com.fastretailing.marketingpf.domain.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
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
public class UserMaster extends BaseEntity {

    private String userId;
    private String userFullName;
    private String roleSequenceList;

    public UserMaster(String userId, String userFullName) {
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public UserMaster() {
    }
}
