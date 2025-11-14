package com.example.gostsNaumen.security.service;

import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.security.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Сервис по управлению контекстом безопасности
 */
@Service
public class SecurityContextService {

    /**
     * Получает Id залогиненного пользователя, иначе выбрасывает ошибку
     *
     * @return id залогиненного из БД
     */
    public Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        Object userDetails = authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getId();
        } else {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }
}