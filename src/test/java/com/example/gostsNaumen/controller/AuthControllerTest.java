package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.UserMapper;
import com.example.gostsNaumen.controller.dto.request.UserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.example.gostsNaumen.security.dto.RefreshTokenDto;
import com.example.gostsNaumen.security.dto.UserCredentialsDto;
import com.example.gostsNaumen.security.permission.UserRoles;
import com.example.gostsNaumen.service.user.AuthService;
import com.example.gostsNaumen.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Тестовый класс для {@link AuthController}.
 *
 * <p>Содержит модульные тесты для проверки работы методов контроллера,
 * включая корректность возвращаемых данных, маппинг в DTO и взаимодействие с сервисами.</p>
 *
 * <p>Для тестирования используется {@link MockMvc} для симуляции HTTP-запросов.</p>
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthController authController;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public AuthControllerTest(
            @Mock AuthService authService,
            @Mock UserService userService
    ) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = new UserMapper(new BCryptPasswordEncoder(4));
        this.authController = new AuthController(userService, userMapper, authService);
        this.objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * Проверяет, что метод логина возвращает корректное DTO с токенами и ID пользователя.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link AuthController#signIn(UserCredentialsDto)}
     * сервис возвращает DTO с токенами, и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void signInShouldReturnJwtAuthDto() throws Exception {
        UserCredentialsDto credentials = new UserCredentialsDto("test@example.com", "password123");
        JwtAuthDto expectedDto = new JwtAuthDto("access_token", "refresh_token", 1L);

        Mockito.when(authService.signIn(credentials)).thenReturn(expectedDto);

        JwtAuthDto result = authController.signIn(credentials);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(expectedDto, result);
    }

    /**
     * Проверяет, что метод логина возвращает 400 BAD REQUEST при невалидных данных.
     */
    @Test
    void signInShouldReturnBadRequestWithInvalidData() throws Exception {
        UserCredentialsDto credentials = new UserCredentialsDto("", "invalid");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Проверяет, что метод обновления токена возвращает корректное DTO с токенами и ID пользователя.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link AuthController#refresh(RefreshTokenDto)}
     * сервис возвращает DTO с токенами, и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void refreshShouldReturnJwtAuthDtoWithCreatedStatus() throws Exception {
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto("refresh_token");
        JwtAuthDto expectedDto = new JwtAuthDto("new_access_token", "new_refresh_token", 1L);

        Mockito.when(authService.refreshToken(refreshTokenDto)).thenReturn(expectedDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refreshTokenDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedDto.token()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value(expectedDto.refreshToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(expectedDto.userId()));
    }

    /**
     * Проверяет, что метод регистрации возвращает корректное DTO пользователя.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link AuthController#register(UserDtoRequest)}
     * пользователь корректно маппится в сущность, сохраняется через сервис, и результат маппится обратно в DTO,
     * и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void registerShouldReturnMappedUserDtoResponse() throws Exception {
        UserDtoRequest request = new UserDtoRequest(
                "newuser",
                "New User",
                "!Qweqweqwe123",
                "new@example.com"
        );

        User savedUser = new User(
                1L,
                "newuser",
                "New User",
                "hashedPassword",
                "new@example.com",
                UserRoles.USER
        );

        UserDtoResponse expectedDto = new UserDtoResponse(
                1L,
                "newuser",
                "New User",
                "new@example.com",
                "USER"
        );

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(savedUser);

        ResponseEntity<UserDtoResponse> result = authController.register(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertEquals(expectedDto, result.getBody());
    }

    /**
     * Проверяет, что метод регистрации возвращает 400 BAD REQUEST при невалидных данных.
     */
    @Test
    void registerShouldReturnBadRequestWithInvalidData() throws Exception {
        UserDtoRequest request = new UserDtoRequest(
                "",
                "New User",
                "invalid-email",
                "123"
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}