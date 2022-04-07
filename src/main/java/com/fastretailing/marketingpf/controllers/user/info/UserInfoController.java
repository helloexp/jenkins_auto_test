package com.fastretailing.marketingpf.controllers.user.info;

import com.fastretailing.marketingpf.controllers.base.BaseController;
import com.fastretailing.marketingpf.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController extends BaseController {

    @GetMapping("/user/info")
    public ResponseEntity<Object> info() {
        DefaultOidcUser oidcUser = AuthUtils.getLoginOidcUser();
        return ok(new UserInfoResponse(oidcUser.getAttribute("oid"), oidcUser.getAttribute("name"), AuthUtils.hasSqlSegmentRole()));
    }
}
