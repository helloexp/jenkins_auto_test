package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.domain.entities.AdsPlatformMaster;
import com.fastretailing.marketingpf.domain.mappers.AdsPlatformMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsPlatformMasterService {

    @Autowired
    public AdsPlatformMasterMapper adsPlatformMasterMapper;

    /**
     * find AdsPlatformMaster by adsPlatformSequence
     *
     * @param adsPlatformSequence
     * @return AdsPlatformMaster
     */
    public AdsPlatformMaster findById(Long adsPlatformSequence) {
        return adsPlatformMasterMapper.findById(adsPlatformSequence);
    }
}
