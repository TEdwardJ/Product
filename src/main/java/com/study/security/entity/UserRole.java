package com.study.security.entity;

public enum UserRole {
    GUEST("GUEST"),
    ADMIN("ADMIN"),
    USER("USER");

    String role;

    UserRole(String role) {
        this.role = role;
    }
}
