package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.get;

import com.fastretailing.marketingpf.domain.enums.ADS_PLATFORM;
import io.swagger.v3.oas.annotations.Hidden;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ApiAuthenticationInformationCreateRequest {

    public String code;

    @Pattern(regexp="^(1|2)$",message="Invalid state")
    public String state;

    @Hidden
    public ADS_PLATFORM getAdsPlatform() {
        return ADS_PLATFORM.create(Integer.parseInt(state));
    }
}
