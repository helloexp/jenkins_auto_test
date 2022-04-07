package com.fastretailing.marketingpf.controllers.scheduler.userlist;

import com.fastretailing.marketingpf.domain.entities.OutboundSetting;
import com.fastretailing.marketingpf.domain.mappers.OutboundSettingMapper;
import com.fastretailing.marketingpf.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerUserListValidator {

    @Autowired
    public OutboundSettingMapper outboundSettingMapper;

    /**
     * Validate for SchedulerUserList request
     *
     * @param outboundSettingSequence
     */
    public void validate(Long outboundSettingSequence) {
        OutboundSetting outboundSetting = outboundSettingMapper.findById(outboundSettingSequence);
        if (outboundSetting == null) {
            throw new ResourceNotFoundException();
        }
    }
}
