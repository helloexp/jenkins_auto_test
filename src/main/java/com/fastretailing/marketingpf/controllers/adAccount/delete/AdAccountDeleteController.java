package com.fastretailing.marketingpf.controllers.adAccount.delete;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.AdAccountService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdAccountDeleteController extends BaseController {

    @Autowired
    public AdAccountService adAccountService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = AdAccountDeleteResponse.class))}),
    })
    @DeleteMapping("/ad_account/{adAccountSequence}")
    public ResponseEntity<Object> delete(@PathVariable Long adAccountSequence) {
        return ok(new AdAccountDeleteResponse(adAccountService.delete(adAccountSequence, getLoginUserId())));
    }
}
