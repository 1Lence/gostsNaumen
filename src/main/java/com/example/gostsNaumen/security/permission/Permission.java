package com.example.gostsNaumen.security.permission;

/**
 * Набор констант с разрешениями пользователя.
 */
public enum Permission {
    /**
     * Разрешение пользователю на чтение
     */
    USER_READ("user:read"),
    /**
     * Разрешение пользователю на изменения
     */
    USER_WRITE("user:write");

    private String permission;

    public String getPermission() {
        return permission;
    }

    Permission(String permission) {
        this.permission = permission;
    }
}