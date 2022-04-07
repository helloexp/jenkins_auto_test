package com.fastretailing.marketingpf.controllers.adAccount.get;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.AdAccountService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdAccountGetController extends BaseController {

    @Autowired
    public AdAccountService adAccountService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = AdAccountGetResponse.class))}),
    })
    @GetMapping("/ad_account/{adAccountSequence}")
    public ResponseEntity<Object> get(@PathVariable Long adAccountSequence) {
        return ok(new AdAccountGetResponse(adAccountService.findById(adAccountSequence)));
    }
}
