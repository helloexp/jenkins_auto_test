package com.fastretailing.marketingpf.controllers.mastervalue.get;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.GetMasterValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterValueGetController extends BaseController {

    @Autowired
    public GetMasterValueService getMasterValueService;

    @Operation(summary = "Get master value", description = "Get master value")
    @Parameters({
            @Parameter(name = "urlForApiAccess", in = ParameterIn.QUERY, schema = @Schema(description = "URL For API Access", type = "String"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = MasterValueGetResponse.class))}),
    })
    @GetMapping("/get_master_value/")
    public ResponseEntity<Object> get(@Parameter(hidden = true) @Valid @ModelAttribute MasterValueGetRequest request) {
        String choicesList = getMasterValueService.getMasterValue(request.getUrlForApiAccess());
        return ok(new MasterValueGetResponse(choicesList));
    }
}
