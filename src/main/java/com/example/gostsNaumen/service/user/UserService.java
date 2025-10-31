package com.example.gostsNaumen.service.user;

import com.example.gostsNaumen.dto.request.UserDtoRequest;
import com.example.gostsNaumen.dto.response.UserDtoResponse;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.repository.UserRepository;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.security.jwe.JweService;
import com.nimbusds.jose.JOSEException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Optional;

/**
 * Сервис по работе с пользовательскими данными.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JweService jweService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JweService jweService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jweService = jweService;
    }

    /**
     * Вход в систему.
     * Сверка данных из БД, с введенными данными проходит в методе
     * {@link #checkUserCredentials(UserCredentialsDto userCredentialsDto)}
     *
     * @param userCredentialsDto данные введённые пользователями.
     * @return дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException           общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     * @see #checkUserCredentials
     */
    public JwtAuthDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException, JOSEException {
        User user = checkUserCredentials(userCredentialsDto);
        return jweService.generateAuthToken(user.getEmail(), user.getId());
    }

    /**
     * Обновление основного токена пользователя.
     *
     * @param refreshTokenDto токен обновления.
     * @return дто с токенами и айди пользователя.
     * @throws AuthenticationException какие-либо проблемы с аутентификацией.
     * @throws JOSEException           общая ошибка работы с JWT, проблемы с подписями, шифрованием.
     */
    public JwtAuthDto refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException, JOSEException {
        String refreshedToken = refreshTokenDto.refreshToken();

        if (refreshedToken != null && jweService.validateJweToken(refreshedToken)) {
            User user = findEntityByEmail(jweService.getEmailFromToken(refreshedToken));
            return jweService.refreshBaseToken(user.getEmail(), refreshedToken, user.getId());
        }
        throw new AuthenticationException("Невалидный токен обновления.");
    }

    /**
     * Сохранить сущность пользователя в БД.
     *
     * @param user дто с данными пользователя, которые он ввел при регистрации.
     * @return сохранённая сущность в БД.
     */
    @Transactional
    public UserDtoResponse saveUser(UserDtoRequest user) {
        String passwordHash = passwordEncoder.encode(user.password());
        User entity = new User(user.userName(), passwordHash, user.email());

        if (userRepository.findUserByEmail(user.email()).isPresent()) {
            throw new EntityExistsException("Пользователь с почтой: " + user.email() + " уже существует.");
        } else if (userRepository.findUserByUsername(user.userName()).isPresent()) {
            throw new EntityExistsException("Пользователь с ником: " + user.userName() + " уже существует.");
        }

        userRepository.save(entity);

        return new UserDtoResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRoles().toString()
        );
    }

    /**
     * Поиск пользователя в БД по почте.
     *
     * @param email почта пользователя.
     * @return дто с данными пользователя.
     */
    @Transactional(readOnly = true)
    public UserDtoResponse findUserByEmail(String email) {
        User user = findEntityByEmail(email);

        return new UserDtoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().toString()
        );
    }

    /**
     * Поиск пользователя по айди.
     *
     * @param id айди пользователя
     * @return дто с данными пользователя.
     */
    @Transactional
    public UserDtoResponse findUserById(Long id) {
        User user = findEntityById(id);

        return new UserDtoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().toString()
        );
    }

    /**
     * Сверка данных пользователя введенных при входе.
     *
     * @param userCredentialsDto данные пользователя.
     * @return сущность пользователя из БД.
     * @throws AuthenticationException ошибка сверки данных.
     */
    private User checkUserCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findUserByEmail(userCredentialsDto.email());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.password(), user.getPasswordHash())) {
                return user;
            } else {
                throw new AuthenticationException("Почта или пароль неверные.");
            }
        }
        throw new EntityNotFoundException("Пользователь с почтой: " + userCredentialsDto.email() + " не найден.");
    }

    /**
     * Поиск сущности пользователя в пределах пакета по почте.
     *
     * @param email почта пользователя.
     * @return сущность пользователя из БД.
     */
    User findEntityByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is not User with email: " + email));
    }

    /**
     * Поиск сущности пользователя в пределах пакета по айди.
     *
     * @param id айди пользователя
     * @return сущность пользователя из БД.
     */
    User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no User with id: " + id));
    }
}