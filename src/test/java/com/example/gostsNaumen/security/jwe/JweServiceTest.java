package com.example.gostsNaumen.security.jwe;

import com.example.gostsNaumen.security.dto.JwtAuthDto;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JweServiceTest {

    @InjectMocks
    private JweService jweService;

    private final String EMAIL = "example@example.com";
    private final Long ID = 1L;
    private final String SECRET = "tPpMbX+5QkX1qKp3iPh4mfrc5D0F3eG9HvA2BcD4Efg";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        setField("jwsSecret", SECRET);
        setField("jweSecret", SECRET);
    }

    private void setField(String fieldName, String value) throws Exception {
        Field field = JweService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(jweService, value);
    }

    @Test
    void generateAuthTokenTest() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        assertEquals(EMAIL, jweService.getEmailFromToken(jwtAuthenticationDto.token()));
        assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    @Test
    void getEmailFromToken() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        assertEquals(EMAIL, jweService.getEmailFromToken(jwtAuthenticationDto.token()));
    }

    @Test
    void validateJweToken() throws JOSEException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);
        assertTrue(jweService.validateJweToken(jwtAuthenticationDto.token()));
    }

    @Test
    void refreshBaseToken() throws JOSEException, InterruptedException {
        JwtAuthDto jwtAuthenticationDto = jweService.generateAuthToken(EMAIL, ID);

        Thread.sleep(1000);

        JwtAuthDto jwtAuthenticationDtoNew = jweService
                .refreshBaseToken(EMAIL, jwtAuthenticationDto.refreshToken(), ID);

        assertEquals(jwtAuthenticationDto.refreshToken(),jwtAuthenticationDtoNew.refreshToken());

        assertNotEquals(jwtAuthenticationDtoNew.token(),jwtAuthenticationDto.token());
    }

    @Test
    void validateJwtTokenNegativeTest() {
        assertFalse(jweService.validateJweToken("eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..Pyk2mfnP8" +
                "Hvd9M8D.Anm8FY0EJvISkDvn1FdkUhVPpEWtQFsZV-oic8pddkH4PRxVwcDRYTFmTKezhThzZGbg3n-l1Zrk26lMol_ilWJg3v" +
                "pEuz6eYYaHac6ZVZVDWLJZ9ASuvGyvUZsN1vlZ2dYkp1kOKa6IrhbOQtuvrVAah5U8cgX6QQ.0cVJ1kd43nE6aAa5HKVfhA"));
    }
}