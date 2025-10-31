package com.example.gostsNaumen.security.dto;

import com.example.gostsNaumen.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Содержит данные аутентифицированного пользователя
 * @param user сущность пользователя
 * @param authorities роли
 */
public record CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}