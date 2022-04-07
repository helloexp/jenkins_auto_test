package com.fastretailing.marketingpf.controllers.mastervalue.get;

import com.fastretailing.marketingpf.validators.Required;
import lombok.Data;

@Data
public class MasterValueGetRequest {

    @Required
    private String urlForApiAccess;
}
