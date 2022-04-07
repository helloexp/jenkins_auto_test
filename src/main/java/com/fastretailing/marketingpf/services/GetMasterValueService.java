package com.fastretailing.marketingpf.services;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class GetMasterValueService extends BaseUpstreamService {

    /**
     * get master value
     *
     * @param urlForApiAccess
     * @return String
     */
    public String getMasterValue(String urlForApiAccess) {
        return this.getAsMono(URI.create(urlForApiAccess), String.class);
    }
}
