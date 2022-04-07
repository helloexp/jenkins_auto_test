package com.fastretailing.marketingpf.utils;

import com.fastretailing.marketingpf.domain.enums.USER_ROLE;
import com.fastretailing.marketingpf.exceptions.InsufficientSqlSegmentRoleException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class AuthUtils {

    /**
     * Get authenticated oidc user
     *
     * @return DefaultOidcUser
     */
    public static DefaultOidcUser getLoginOidcUser() {
        return (DefaultOidcUser) getAuthentication().getPrincipal();
    }

    /**
     * Get Authentication
     *
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Check if user has permission to work on sql segment
     *
     * @return boolean
     */
    public static boolean hasSqlSegmentRole() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        if (authorities == null) {
            return false;
        }
        for (GrantedAuthority a : authorities) {
            USER_ROLE role = USER_ROLE.createFromAuthority(a.getAuthority());
            if (role != null && role.isHasSqlSegmentRole()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has sql segment role or not<br>
     * If user does not have sql segment role, InsufficientSqlSegmentRoleException is thrown
     */
    public static void preAuthorizeForSqlSegmentRole() {
        if (!hasSqlSegmentRole()) {
            throw new InsufficientSqlSegmentRoleException();
        }
    }
}
