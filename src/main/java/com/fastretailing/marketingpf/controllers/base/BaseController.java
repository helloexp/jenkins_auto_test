package com.fastretailing.marketingpf.controllers.base;

import com.fastretailing.marketingpf.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("${mkpf.uriPrefix}")
public class BaseController {

    public ResponseEntity<Object> ok() {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> ok(BaseResponse response) {
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Object> found() {
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    /**
     * get login user id
     *
     * @return String
     */
    public String getLoginUserId() {
        return AuthUtils.getLoginOidcUser().getAttribute("oid");
    }
}
