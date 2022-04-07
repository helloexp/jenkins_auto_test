package com.fastretailing.marketingpf.controllers.userMaster.gets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.services.UserMasterService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserMasterGetController extends BaseController {

    @Autowired
    public UserMasterService userMasterService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = UserMasterGetResponse.class))})
    })
    @GetMapping("/users/")
    public ResponseEntity<Object> gets() {
        return ok(new UserMasterGetResponse(userMasterService.findAll()));
    }
}
