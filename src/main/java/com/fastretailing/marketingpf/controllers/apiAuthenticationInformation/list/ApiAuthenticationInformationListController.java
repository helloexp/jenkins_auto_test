package com.fastretailing.marketingpf.controllers.apiAuthenticationInformation.list;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthenticationInformationListController extends BaseController {

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = ApiAuthenticationInformationListResponse.class))}),
    })
    @Operation(summary = "Get Api Authentication Information List", description = "Get Api Authentication Information List")
    @Parameters({
            @Parameter(name = "adsPlatformSequence", in = ParameterIn.QUERY, schema = @Schema(description = "Ads Platform Sequence", type = "Long")),
    })
    @GetMapping("/auth/api_auth_info_list/")
    public ResponseEntity<Object> findListByAdsPlatformSequence(@ModelAttribute ApiAuthenticationInformationListRequest request) {
        if (request.getAdsPlatformSequence() == null) {
            return ok(new ApiAuthenticationInformationListResponse(apiAuthenticationInformationService.findAll()));
        }
        return ok(new ApiAuthenticationInformationListResponse(apiAuthenticationInformationService.findListByAdsPlatformSequence(request.getAdsPlatformSequence())));
    }
}
