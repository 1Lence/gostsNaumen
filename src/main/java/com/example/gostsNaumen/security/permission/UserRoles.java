package com.example.gostsNaumen.security.permission;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Набор констант с ролями пользователя.
 * Использует систему разрешений.
 * @see Permission
 */
public enum UserRoles implements GrantedAuthority {
    /**
     * Роль администратора, имеет все разрешения
     */
    ADMIN(Set.of(Permission.USER_READ, Permission.USER_WRITE)),
    /**
     * Роль обычного пользователя, имеет только разрешение на чтение
     */
    USER(Set.of(Permission.USER_READ));

    private final Set<Permission> permission;

    public Set<Permission> getPermission() {
        return permission;
    }

    UserRoles(Set<Permission> permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermission()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}