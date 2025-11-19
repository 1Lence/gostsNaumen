package com.example.gostsNaumen.security.jwe;

import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.security.dto.CustomUserDetails;
import com.example.gostsNaumen.service.security.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр Spring Security для обработки и проверки JWE-токенов аутентификации.
 * Проверяет наличие токена в заголовке Authorization запроса, валидирует его
 * с помощью {@link JweService}, и если токен действителен, загружает
 * информацию о пользователе через {@link UserDetailServiceImpl} и устанавливает
 * его в {@link SecurityContextHolder}.
 *
 * <p>Фильтр обрабатывает только те запросы, которые содержат заголовок
 * {@code Authorization} с префиксом {@code Bearer}. Если токен недействителен
 * или истёк, фильтр отправляет ответ с кодом 401 (Unauthorized).</p>
 */
@Component
public class JweFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JweFilter.class);
    private final JweService jweService;
    private final UserDetailServiceImpl userDetailServiceIml;

    public JweFilter(
            JweService jweService,
            UserDetailServiceImpl userDetailServiceIml
    ) {
        this.jweService = jweService;
        this.userDetailServiceIml = userDetailServiceIml;
    }

    /**
     * Основной метод фильтрации. Извлекает токен из заголовка запроса,
     * проверяет его, и если он действителен, устанавливает аутентификацию
     * для текущего потока.
     *
     * @param request     HTTP-запрос.
     * @param response    HTTP-ответ.
     * @param filterChain Цепочка фильтров.
     * @throws ServletException в случае ошибки сервлета.
     * @throws IOException      в случае ошибки ввода-вывода.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null) {
            if (jweService.validateJweToken(token)) {
                setCustomUserDetailsToSecurityContextHolder(token);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Невалидный токен.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Извлекает объект {@link CustomUserDetails} по email из токена и устанавливает
     * его в {@link SecurityContextHolder} как текущую аутентификацию.
     *
     * <p>В случае ошибки при извлечении email или загрузке пользователя
     * записывает соответствующее сообщение в лог.</p>
     *
     * @param token Валидный JWE-токен, из которого будет извлечён email.
     */
    private void setCustomUserDetailsToSecurityContextHolder(String token) {
        try {
            String email = jweService.getEmailFromToken(token);

            CustomUserDetails customUserDetails = userDetailServiceIml.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            logger.error("Ошибка в деталях пользователя.", e);
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * Извлекает токен из заголовка {@code Authorization} HTTP-запроса.
     *
     * <p>Ожидает формат {@code Bearer <токен>}. Если заголовок отсутствует
     * или не начинается с {@code Bearer}, возвращает {@code null}.</p>
     *
     * @param request HTTP-запрос.
     * @return Токен без префикса {@code Bearer }, или {@code null}, если токен не найден.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}