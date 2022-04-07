package com.fastretailing.marketingpf.controllers.adAccount.search;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.domain.dtos.AdAccountList.AdAccountListDto;
import com.fastretailing.marketingpf.services.AdAccountService;
import com.fastretailing.marketingpf.services.ApiAuthenticationInformationService;
import com.fastretailing.marketingpf.services.UserMasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdAccountSearchController extends BaseController {

    @Autowired
    public ApiAuthenticationInformationService apiAuthenticationInformationService;

    @Autowired
    public AdAccountService adAccountService;

    @Autowired
    public UserMasterService userMasterService;

    @Operation(summary = "Get adAccount list", description = "Get adAccount list")
    @Parameters({
            @Parameter(name = "adsPlatformSequence", in = ParameterIn.QUERY, schema = @Schema(description = "Ads Platform Sequence", type = "Long")),
            @Parameter(name = "brandId", in = ParameterIn.QUERY, schema = @Schema(description = "Brand Id", type = "String")),
            @Parameter(name = "countryId", in = ParameterIn.QUERY, schema = @Schema(description = "Country Id", type = "String")),
            @Parameter(name = "adsPfLoginUserId", in = ParameterIn.QUERY, schema = @Schema(description = "AdsPF Login User ID", type = "String"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = AdAccountSearchResponse.class))})
    })
    @GetMapping("/ad_accounts/")
    public ResponseEntity<Object> search(@Parameter(hidden = true) @Valid @ModelAttribute AdAccountSearchRequest request) {
        // Search for ad account
        List<AdAccountListDto> adAccountList = adAccountService.searchWithAdsPfLoginUserId(request.getAdsPlatformSequence(), request.getBrandId(), request.getCountryId(), request.getAdsPfLoginUserId());
        // Get unique ApiAuthenticationInformationSequence list
        Set<Long> targetApiAuthSeqList = adAccountList.stream().map(e -> e.getApiAuthenticationInformationSequence()).collect(Collectors.toSet());
        // Get ApiAuthenticationInformationSequence to AdsPfLoginUserId map
        Map<Long, String> authSeqToAdsPfLoginUserIdMap = targetApiAuthSeqList.stream()
                .map(e -> apiAuthenticationInformationService.findById(e))
                .filter(e -> e != null)
                .collect(Collectors.toMap(e -> e.getApiAuthenticationInformationSequence(), e -> e.getAdsPfLoginUserId()));
        return ok(new AdAccountSearchResponse(adAccountList, authSeqToAdsPfLoginUserIdMap, userMasterService.getUserIdToUserNameMap()));
    }
}
