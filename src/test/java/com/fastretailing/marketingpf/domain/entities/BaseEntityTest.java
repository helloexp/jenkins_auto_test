package com.fastretailing.marketingpf.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastretailing.marketingpf.tests.BaseSpringBootTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BaseEntityTest extends BaseSpringBootTest {

    @Test
    public void testSetAuditInfoForCreation() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setAuditInfoForCreation("user1", LocalDateTime.of(2021, 02, 02, 0, 0, 0));
        assertThat(baseEntity.getDeletionFlagForAudit()).isEqualTo("f");
        assertThat(baseEntity.getCreateUserIdForAudit()).isEqualTo("user1");
        assertThat(baseEntity.getCreateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 0));
        assertThat(baseEntity.getUpdateUserIdForAudit()).isEqualTo("user1");
        assertThat(baseEntity.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 02, 0, 0, 0));
    }

    @Test
    public void testSetAuditInfoForUpdate() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setAuditInfoForUpdate("user2", LocalDateTime.of(2021, 02, 03, 0, 0, 0));
        assertThat(baseEntity.getUpdateUserIdForAudit()).isEqualTo("user2");
        assertThat(baseEntity.getUpdateDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 03, 0, 0, 0));
    }

    @Test
    public void testSetAuditInfoForDeletion() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setAuditInfoForDeletion("user3", LocalDateTime.of(2021, 02, 04, 0, 0, 0));
        assertThat(baseEntity.getDeletionUserIdForAudit()).isEqualTo("user3");
        assertThat(baseEntity.getDeletionDateTimeForAudit()).isEqualTo(LocalDateTime.of(2021, 02, 04, 0, 0, 0));
    }
}
