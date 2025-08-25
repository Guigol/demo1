package com.internaltools.demo1.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    MANAGER,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
