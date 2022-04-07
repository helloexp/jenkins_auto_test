package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import com.fastretailing.marketingpf.validators.Required;
import com.fastretailing.marketingpf.validators.SequenceValid;
import lombok.Data;

@Data
public class SchedulerUserListRequest {

    @SequenceValid
    @Required
    private Long outboundSettingSequence;
}
