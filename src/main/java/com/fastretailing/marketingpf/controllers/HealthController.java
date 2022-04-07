package com.fastretailing.marketingpf.controllers;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController extends BaseController {

    @GetMapping("/healthcheck")
    public ResponseEntity<Object> check() {
        return ResponseEntity.ok().body("{}");
    }
}
