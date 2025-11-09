package com.example.gostsNaumen.security.jwe;

import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

/**
 * Тестирование логики JWE сервиса
 */
class JweServiceTest {

    @InjectMocks
    private JweService jweService;

    private final String EMAIL = "example@example.com";
    private final Long ID = 1L;
    private final String SECRET = "tPpMbX+5QkX1qKp3iPh4mfrc5D0F3eG9HvA2BcD4Efg";

    /**
     * Подготовка к проведению тестов
     *
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        setSecret("jwsSecret", SECRET);
        setSecret("jweSecret", SECRET);
        setExpire("expireMinutes",1000);
    }

    //FIXME: Убрать рефлексию и добавить профили для приложения

    /**
     * Устанавливает секрет
     *
     * @param fieldName имя переменной
     * @param value     значение переменной
     * @throws Exception
     */
    private void setSecret(String fieldName, String value) throws Exception {
        Field field = JweService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(jweService, value);
    }

    /**
     * Устанавливает время истечения токена
     *
     * @param fieldName имя переменной
     * @param value     значение переменной
     * @throws Exception
     */
    private void setExpire(String fieldName, Integer value) throws Exception {
        Field field = JweService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(jweService, value);
    }

    /**
     * Проверка работы генерации токена, его валидности и наличия в нем почты
     * @throws JOSEException выбрасывается при неверных обработках токена
     */
    @Test
    void generateAuthTokenTest() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        Assertions.assertEquals(EMAIL, jweService.getEmailFromToken(jwtAuthenticationDto.token()));
        Assertions.assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    /**
     * Проверяется что почта достанется без ошибок
     * @throws JOSEException выбрасывается при неверных обработках токена
     */
    @Test
    void getEmailFromToken() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        Assertions.assertEquals(EMAIL, jweService.getEmailFromToken(jwtAuthenticationDto.token()));
    }

    /**
     * Проверяется корректность работы валидации токена
     * @throws JOSEException выбрасывается при неверных обработках токена
     */
    @Test
    void validateJweToken() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        Assertions.assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    /**
     * Проверяется обновление токена
     * @throws JOSEException выбрасывается при неверных обработках токена
     * @throws InterruptedException
     */
    @Test
    void refreshBaseToken() throws JOSEException, InterruptedException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);

        Thread.sleep(1000);

        JwtAuthDto jwtAuthenticationDtoNew = jweService
                .refreshBaseToken(EMAIL, jwtAuthenticationDto.refreshToken(), ID);

        Assertions.assertEquals(jwtAuthenticationDto.refreshToken(), jwtAuthenticationDtoNew.refreshToken());

        Assertions.assertNotEquals(jwtAuthenticationDtoNew.token(), jwtAuthenticationDto.token());
    }

    /**
     * Проверяется, что неверный токен не пройдет проверку
     */
    @Test
    void validateJwtTokenNegativeTest() {
        Assertions.assertFalse(jweService.validateJweToken("eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..Pyk2mfnP8" +
                "Hvd9M8D.Anm8FY0EJvISkDvn1FdkUhVPpEWtQFsZV-oic8pddkH4PRxVwcDRYTFmTKezhThzZGbg3n-l1Zrk26lMol_ilWJg3v" +
                "pEuz6eYYaHac6ZVZVDWLJZ9ASuvGyvUZsN1vlZ2dYkp1kOKa6IrhbOQtuvrVAah5U8cgX6QQ.0cVJ1kd43nE6aAa5HKVfhA"));
    }
}