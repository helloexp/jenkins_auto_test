package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.OutputPlatformMaster;
import com.fastretailing.marketingpf.domain.mappers.OutputPlatformMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputPlatformMasterService {

    @Autowired
    public OutputPlatformMasterMapper outputPlatformMasterMapper;

    /**
     * find OutputPlatformMaster by outputPlatformSequence
     *
     * @param outputPlatformSequence
     * @return OutputPlatformMaster
     */
    public OutputPlatformMaster findById(Long outputPlatformSequence) {
        return outputPlatformMasterMapper.findById(outputPlatformSequence);
    }
}
