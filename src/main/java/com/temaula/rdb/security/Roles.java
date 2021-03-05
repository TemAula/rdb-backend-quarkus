package com.temaula.rdb.security;

import java.util.function.Supplier;

public enum Roles implements Supplier<String> {
    ADMIN("admin"), USER("user");
    private final String role;

    Roles(String role) {
        this.role = role;
    }

    @Override
    public String get() {
        return role;
    }
}
