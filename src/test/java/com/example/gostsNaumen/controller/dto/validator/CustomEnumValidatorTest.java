package com.example.gostsNaumen.controller.dto.validator;

import com.example.gostsNaumen.controller.dto.validator.FakeClasses.FakeCustomEnumValid;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Класс, служащий для проверки корректности работы валидационной аннотации {@code @CustomEnumValid()},
 * служащей для проверки соответствия входящего значения от пользователя и возможного значения enum-а.
 */
@ExtendWith(MockitoExtension.class)
public class CustomEnumValidatorTest {

    /**
     * Контекст валидации JSR-303 (Bean Validation).
     * Используется для передачи информации о контексте валидации валидатору.
     * Может быть использован для настройки сообщений об ошибках или других параметров.
     */
    @Mock
    private ConstraintValidatorContext context;

    /**
     * Экземпляр тестируемого валидатора.
     */
    private CustomEnumValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CustomEnumValidator();
    }

    /**
     * Метод, проверяющий успешные случаи валидации
     */
    @Test
    void shouldAcceptValidEnumValues() {

        FakeCustomEnumValid fakeCustomEnumValidHarmonization = new FakeCustomEnumValid(HarmonizationEnum.class);
        FakeCustomEnumValid fakeCustomEnumValidStatus = new FakeCustomEnumValid(StatusEnum.class);
        FakeCustomEnumValid fakeCustomEnumValidAdoption = new FakeCustomEnumValid(AdoptionLevelEnum.class);

        String[] harmonizationValues = new String[]{"Гармонизированный", "Не гармонизированный", "Модифицированный"};
        String[] statusValues = new String[]{"Актуальный", "Заменённый", "Отменённый"};
        String[] adoptionValues = new String[]{"Национальный", "Межгосударственный", "Отраслевой",
                "Региональный", "Стандарт Организаций"};

        validator.initialize(fakeCustomEnumValidHarmonization);
        for (String harmonization : harmonizationValues) {
            Assertions.assertTrue(validator.isValid(harmonization, context));
        }

        validator.initialize(fakeCustomEnumValidStatus);
        for (String status : statusValues) {
            Assertions.assertTrue(validator.isValid(status, context));
        }

        validator.initialize(fakeCustomEnumValidAdoption);
        for (String adoption : adoptionValues) {
            Assertions.assertTrue(validator.isValid(adoption, context));
        }
    }

    /**
     * Метод, проверяющий некорректные значения
     */
    @Test
    void shouldNotAcceptInvalidEnumValues() {
        FakeCustomEnumValid fakeCustomEnumValidHarmonization = new FakeCustomEnumValid(HarmonizationEnum.class);

        validator.initialize(fakeCustomEnumValidHarmonization);

        Assertions.assertFalse(validator.isValid("неправильное значение", context));
        Assertions.assertFalse(validator.isValid("sdfsdf", context));
    }
}
