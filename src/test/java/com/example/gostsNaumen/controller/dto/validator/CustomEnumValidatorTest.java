package com.example.gostsNaumen.controller.dto.validator;

import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
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
     * Экземпляр тестируемого валидатора
     */
    private final CustomEnumValidator validator;

    /**
     * Контекст валидации JSR-303 (Bean Validation).
     * Используется для передачи информации о контексте валидации валидатору.
     * Может быть использован для настройки сообщений об ошибках или других параметров.
     */
    private final ConstraintValidatorContext context;

    /**
     * Поле, необходимое для получения аннотации {@code CustomEnumValid} со значением
     * {@code enumClass = HarmonizationEnum.class}
     */
    @CustomEnumValid(enumClass = HarmonizationEnum.class)
    private String harmonizationField;

    /**
     * Поле, необходимое для получения аннотации {@code CustomEnumValid} со значением
     * {@code enumClass = StatusEnum.class}
     */
    @CustomEnumValid(enumClass = StatusEnum.class)
    private String statusField;

    /**
     * Поле, необходимое для получения аннотации {@code CustomEnumValid} со значением
     * {@code enumClass = AdoptionLevelEnum.class}
     */
    @CustomEnumValid(enumClass = AdoptionLevelEnum.class)
    private String adoptionLevelField;

    public CustomEnumValidatorTest(
            @Mock ConstraintValidatorContext context
    ) {
        this.validator = new CustomEnumValidator();
        this.context = context;
    }

    /**
     * Метод, покрывающий корректные вводные данные
     */
    @Test
    void shouldAcceptValidEnumValues() throws Exception {

        CustomEnumValid annotationHarmonization = this.getClass()
                .getDeclaredField("harmonizationField")
                .getAnnotation(CustomEnumValid.class);

        CustomEnumValid annotationStatus = this.getClass()
                .getDeclaredField("statusField")
                .getAnnotation(CustomEnumValid.class);

        CustomEnumValid annotationAdoption = this.getClass()
                .getDeclaredField("adoptionLevelField")
                .getAnnotation(CustomEnumValid.class);

        String[] validEnumValuesHarm = {
                "Не гармонизированный", "Модифицированный", "Гармонизированный"
        };

        String[] validEnumValuesStatus = {
                "Актуальный", "Отменённый", "Заменённый"
        };

        String[] validEnumValuesAdoption = {
                "Национальный", "Межгосударственный", "Отраслевой", "Региональный", "Стандарт Организаций"
        };

        validator.initialize(annotationHarmonization);
        for (String validEnumValue : validEnumValuesHarm) {
            Assertions.assertTrue(validator.isValid(validEnumValue, context));
        }

        validator.initialize(annotationStatus);
        for (String validEnumValue : validEnumValuesStatus) {
            Assertions.assertTrue(validator.isValid(validEnumValue, context));
        }

        validator.initialize(annotationAdoption);
        for (String validEnumValue : validEnumValuesAdoption) {
            Assertions.assertTrue(validator.isValid(validEnumValue, context));
        }
    }

    /**
     * Метод, проверяющий некорректные значения
     */
    @Test
    void shouldNotAcceptInvalidEnumValues() throws Exception {
        CustomEnumValid annotationHarmonization = this.getClass()
                .getDeclaredField("harmonizationField")
                .getAnnotation(CustomEnumValid.class);

        String[] validEnumValuesHarm = {
                "Неправильное значение!!!", "Ещё неправильное", "гармонизированный", "слева ошибка регистра"
        };

        validator.initialize(annotationHarmonization);
        for (String validEnumValue : validEnumValuesHarm) {
            Assertions.assertFalse(validator.isValid(validEnumValue, context));
        }
    }

    /**
     * Метод, проверяющий работу аннотации при передаче неверного класса enum-а
     */
    @Test
    void shouldNotAcceptCorrectValuesBecauseWrongEnumTypeInParam() throws Exception {
        CustomEnumValid annotationHarmonization = this.getClass()
                .getDeclaredField("statusField")
                .getAnnotation(CustomEnumValid.class);

        String[] validEnumValuesHarm = {
                "Не гармонизированный", "Модифицированный", "Гармонизированный"
        };

        validator.initialize(annotationHarmonization);

        for (String validEnumValue : validEnumValuesHarm) {
            Assertions.assertFalse(validator.isValid(validEnumValue, context));
        }
    }
}
