package com.example.gostsNaumen.security.config;

import com.example.gostsNaumen.security.jwe.JweFilter;
import com.example.gostsNaumen.security.permission.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация слоя безопасности приложения.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JweFilter jweFilter;

    public SecurityConfig(JweFilter jweFilter) {
        this.jweFilter = jweFilter;
    }

    /**
     * Создание бина Security Filter Chain.
     * Позволяет настроить фильтры безопасности для обработки HTTP запроса.
     * @param http используется для настройки безопасности
     * @return бин Security Filter Chain
     * @throws Exception выбрасывает исключение, необходима обработка
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable).
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/v1/user/**").hasAuthority(Permission.USER_READ.getPermission())
                        .anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jweFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Создание бина для шифрования паролей
     * @return бин PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}