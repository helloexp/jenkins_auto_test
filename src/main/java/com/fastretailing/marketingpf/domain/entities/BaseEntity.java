package com.fastretailing.marketingpf.domain.entities;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseEntity {

    private String deletionFlagForAudit;
    private LocalDateTime createDateTimeForAudit;
    private String createUserIdForAudit;
    private LocalDateTime deletionDateTimeForAudit;
    private String deletionUserIdForAudit;
    private LocalDateTime updateDateTimeForAudit;
    private String updateUserIdForAudit;

    /**
     * Update audit information for creation
     *
     * @param createUserId
     * @param createDateTime
     */
    public void setAuditInfoForCreation(String createUserId, LocalDateTime createDateTime) {
        this.deletionFlagForAudit = "f";
        this.createDateTimeForAudit = createDateTime;
        this.createUserIdForAudit = createUserId;
        this.updateDateTimeForAudit = createDateTime;
        this.updateUserIdForAudit = createUserId;
    }

    /**
     * Update audit information for update
     *
     * @param updateUserId
     * @param updateDateTime
     */
    public void setAuditInfoForUpdate(String updateUserId, LocalDateTime updateDateTime) {
        this.updateUserIdForAudit = updateUserId;
        this.updateDateTimeForAudit = updateDateTime;
    }

    /**
     * Update audit information for delete
     *
     * @param deleteUserId
     * @param deleteDateTime
     */
    public void setAuditInfoForDeletion(String deleteUserId, LocalDateTime deleteDateTime) {
        this.deletionFlagForAudit = "t";
        this.deletionDateTimeForAudit = deleteDateTime;
        this.deletionUserIdForAudit = deleteUserId;
    }
}
