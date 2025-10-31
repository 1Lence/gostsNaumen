package com.example.gostsNaumen.security.service;

import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.repository.UserRepository;
import com.example.gostsNaumen.security.dto.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса {@link UserDetailsService} Spring Security.
 * Загружает данные пользователя {@link CustomUserDetails} по его email.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws EntityNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User does not exists"));
        return new CustomUserDetails(user, user.getRoles().getAuthorities());
    }
}