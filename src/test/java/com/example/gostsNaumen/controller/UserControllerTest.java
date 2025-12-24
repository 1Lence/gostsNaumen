package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.UserMapper;
import com.example.gostsNaumen.controller.dto.request.PasswordDtoRequest;
import com.example.gostsNaumen.controller.dto.request.UpdateUserDtoRequest;
import com.example.gostsNaumen.controller.dto.response.UserDtoResponse;
import com.example.gostsNaumen.controller.dto.response.UserIdDtoResponse;
import com.example.gostsNaumen.entity.User;
import com.example.gostsNaumen.security.permission.UserRoles;
import com.example.gostsNaumen.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

/**
 * Тестовый класс для {@link UserController}.
 *
 * <p>Содержит модульные тесты для проверки работы методов контроллера,
 * включая корректность возвращаемых данных, маппинг в DTO и взаимодействие с сервисом.</p>
 *
 * <p>Для тестирования используется {@link MockMvc} для симуляции HTTP-запросов.</p>
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserController userController;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private User testUser;

    public UserControllerTest(
            @Mock UserService userService
    ) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder(4);
        this.userMapper = new UserMapper(passwordEncoder);
        this.userController = new UserController(userService, userMapper);
        this.objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(
                1L,
                "testuser",
                "Test User",
                "hashedPassword",
                "test@example.com",
                UserRoles.USER
        );
    }

    /**
     * Проверяет, что метод возвращает корректный список DTO пользователей.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link UserController#getAllUsers()}
     * сервис возвращает список пользователей, который корректно маппится в список DTO,
     * и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void getAllShouldReturnMappedListOfDtoResponse() throws Exception {
        UserDtoResponse testUserDtoResponse = new UserDtoResponse(
                1L,
                "testuser",
                "Test User",
                "test@example.com",
                "USER"
        );

        Mockito.when(userService.findAll()).thenReturn(List.of(testUser, testUser));

        List<UserDtoResponse> correctUserDtos = List.of(testUserDtoResponse, testUserDtoResponse);
        List<UserDtoResponse> result = userController.getAllUsers();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/users")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(correctUserDtos, result);
    }

    /**
     * Проверяет, что метод обновления пользователя возвращает корректное DTO.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link UserController#updateUserData(Long, UpdateUserDtoRequest)}
     * сервис возвращает обновленного пользователя, который корректно маппится в DTO,
     * и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void updateUserDataShouldReturnMappedDtoResponse() throws Exception {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                "updateduser",
                "Updated User",
                "updated@example.com",
                "USER"
        );

        User updatedUser = new User(
                1L,
                "updateduser",
                "Updated User",
                "hashedPassword",
                "updated@example.com",
                UserRoles.USER
        );

        UserDtoResponse expectedDto = new UserDtoResponse(
                1L,
                "updateduser",
                "Updated User",
                "updated@example.com",
                "USER"
        );

        Mockito.when(userService.updateUserData(1L, request)).thenReturn(updatedUser);

        UserDtoResponse result = userController.updateUserData(1L, request);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(expectedDto, result);
    }

    /**
     * Проверяет, что метод обновления пользователя не позволит обновить пользвателя при невалидных полях и вернет
     * 400 BAD REQUEST
     */
    @Test
    void updateUserDataShouldReturnBadRequestWithInvalidData() throws Exception {
        UpdateUserDtoRequest request = new UpdateUserDtoRequest(
                "updateduser",
                "Updated User",
                "invalidEmail",
                "InvalidRole"
        );

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Проверяет, что метод обновления пароля возвращает корректное DTO с ID пользователя.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link UserController#updatePassword(PasswordDtoRequest)}
     * сервис возвращает ID пользователя, и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void updatePasswordShouldReturnUserIdDtoResponse() throws Exception {
        PasswordDtoRequest request = new PasswordDtoRequest("!Qweqweqwe123");
        UserIdDtoResponse expectedDto = new UserIdDtoResponse(1L);

        Mockito.when(userService.updatePassword(request)).thenReturn(1L);

        UserIdDtoResponse result = userController.updatePassword(request);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(expectedDto.id(), result.id());
    }

    /**
     * Проверяет, что метод обновления пароля не позволит обновить пароль пользователю, если он не прошел валидацию,
     * и что HTTP-ответ возвращает статус 400 BAD REQUEST
     */
    @Test
    void updatePasswordShouldReturnBadRequestWithNotValidPassword() throws Exception {
        PasswordDtoRequest request = new PasswordDtoRequest("not valid");

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Проверяет, что метод удаления пользователя возвращает статус 200 OK и никнейм удалённого пользователя.
     *
     * <P>Этот тест проверяет, что при вызове метода {@link UserController#deleteUserById(Long)}
     * сервис вызывает удаление пользователя по ID, и что HTTP-ответ возвращает статус 200 OK.</P>
     */
    @Test
    void deleteUserByIdShouldReturnStatusOk() throws Exception {
        Long userId = 1L;

        Mockito.when(userService.deleteUserById(userId)
        ).thenReturn("username");

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/user/{id}", userId)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Пользователь успешно удалён"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedUsername")
                        .value("username"));

        Mockito.verify(userService).deleteUserById(userId);
    }
}