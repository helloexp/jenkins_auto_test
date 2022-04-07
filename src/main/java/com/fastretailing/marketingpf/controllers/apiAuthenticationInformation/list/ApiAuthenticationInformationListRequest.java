package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.list;

import com.fastretailing.marketingpf.validators.SequenceValid;
import lombok.Data;

@Data
public class ApiAuthenticationInformationListRequest {

    @SequenceValid
    private Long adsPlatformSequence;
}
