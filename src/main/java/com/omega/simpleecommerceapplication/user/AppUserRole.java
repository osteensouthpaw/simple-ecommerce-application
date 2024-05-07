package com.omega.simpleecommerceapplication.user;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    CUSTOMER,
    ADMIN,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
