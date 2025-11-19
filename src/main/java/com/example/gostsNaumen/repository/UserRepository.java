package com.example.gostsNaumen.repository;

import com.example.gostsNaumen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с пользовательскими данными.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользовательской записи по почте
     * Необходима обязательная обратка {@link Optional}
     *
     * @param email почта привязанная к пользователю
     * @return {@code Optional<User>}
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Поиск пользовательской записи по ник
     * Необходима обязательная обратка {@link Optional}
     *
     * @param username ник привязанный к пользователю
     * @return {@code Optional<User>}
     */
    Optional<User> findUserByUsername(String username);
}