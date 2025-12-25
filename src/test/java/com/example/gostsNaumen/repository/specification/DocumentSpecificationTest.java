package com.example.gostsNaumen.repository.specification;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

/**
 * Тестовый класс для {@link DocumentSpecification}.
 *
 * <p>Содержит модульные тесты для проверки корректности построения
 * спецификаций фильтрации для сущности {@link Document}.</p>
 */
@ExtendWith(MockitoExtension.class)
class DocumentSpecificationTest {

    private final DocumentSpecification documentSpecification;
    private final Root<Document> root;
    private final CriteriaQuery<?> query;
    private final CriteriaBuilder cb;
    private final Path<Object> path;
    private final Predicate predicate;

    public DocumentSpecificationTest(
            @Mock Root<Document> root,
            @Mock CriteriaQuery<?> query,
            @Mock CriteriaBuilder cb,
            @Mock Path<Object> path,
            @Mock Predicate predicate
    ) {
        this.documentSpecification = new DocumentSpecification();
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.path = path;
        this.predicate = predicate;
    }

    /**
     * Проверяет, что метод {@code withFullName} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withFullNameWhenFullNameIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withFullName(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withFullName} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withFullNameWhenFullNameIsNotNullShouldReturnSpecification() {
        String fullName = "ГОСТ";

        Mockito.when(root.get("fullName")).thenReturn(path);
        Mockito.when(cb.like(root.get("fullName"), "%" + fullName + "%")).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withFullName(fullName);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCodeOks} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withCodeOksWhenCodeOksIsNullShouldReturnSpecificationThatProducesNullPredicate() {
        Specification<Document> spec = documentSpecification.withCodeOks(null);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCodeOks} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withCodeOksWhenCodeOksIsNotNullShouldReturnSpecification() {
        String codeOks = "123";
        Mockito.when(root.get("codeOKS")).thenReturn(path);
        Mockito.when(cb.like(root.get("codeOKS"), "%" + codeOks + "%")).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withCodeOks(codeOks);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYear} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withAcceptanceYearWhenYearIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withAcceptanceYear(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYear} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withAcceptanceYearWhenYearIsNotNullShouldReturnSpecification() {
        Mockito.when(root.get("acceptanceYear")).thenReturn(path);
        Mockito.when(cb.equal(path, 2023)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAcceptanceYear(2023);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYear} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withCommissionYearWhenYearIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withCommissionYear(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYear} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withCommissionYearWhenYearIsNotNullShouldReturnSpecification() {
        Mockito.when(root.get("commissionYear")).thenReturn(path);
        Mockito.when(cb.equal(path, 2023)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withCommissionYear(2023);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с обоими {@code null}
     * возвращает {@code null}.
     */
    @Test
    void withAcceptanceYearBetweenOrGreaterOrLessWhenBothNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withAcceptanceYearBetweenOrGreaterOrLess(
                null,
                null
        );

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с заданным {@code begin}
     * и {@code null} для {@code end} возвращает предикат "больше или равно".
     */
    @Test
    void withAcceptanceYearBetweenOrGreaterOrLessWhenBeginOnlyShouldReturnGreaterOrEqual() {
        Mockito.when(root.get("acceptanceYear")).thenReturn(path);
        Mockito.when(cb.greaterThanOrEqualTo(root.get("acceptanceYear"), 2020)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAcceptanceYearBetweenOrGreaterOrLess(
                2020,
                null
        );
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с {@code null} для {@code begin}
     * и заданным {@code end} возвращает предикат "меньше или равно".
     */
    @Test
    void withAcceptanceYearBetweenOrGreaterOrLessWhenEndOnlyShouldReturnLessOrEqual() {
        Mockito.when(root.get("acceptanceYear")).thenReturn(path);
        Mockito.when(cb.lessThanOrEqualTo(root.get("acceptanceYear"), 2023)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAcceptanceYearBetweenOrGreaterOrLess(
                null,
                2023
        );
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptanceYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с обоими непустыми значениями
     * возвращает предикат "между".
     */
    @Test
    void withAcceptanceYearBetweenOrGreaterOrLessWhenBothNotNullShouldReturnBetween() {
        Mockito.when(root.get("acceptanceYear")).thenReturn(path);
        Mockito.when(cb.between(root.get("acceptanceYear"), 2020, 2023)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAcceptanceYearBetweenOrGreaterOrLess(
                2020,
                2023
        );
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с обоими {@code null}
     * возвращает {@code null}.
     */
    @Test
    void withCommissionYearBetweenOrGreaterOrLessWhenBothNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withCommissionYearBetweenOrGreaterOrLess(null, null);

        Predicate predicate = spec.toPredicate(root, query, cb);

        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с заданным {@code begin}
     * и {@code null} для {@code end} возвращает предикат "больше или равно".
     */
    @Test
    void withCommissionYearBetweenOrGreaterOrLessWhenBeginOnlyShouldReturnGreaterOrEqual() {
        Integer begin = 2020;
        Mockito.when(root.get("commissionYear")).thenReturn(path);
        Mockito.when(cb.greaterThanOrEqualTo(root.get("commissionYear"), begin)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withCommissionYearBetweenOrGreaterOrLess(begin, null);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с {@code null} для {@code begin}
     * и заданным {@code end} возвращает предикат "меньше или равно".
     */
    @Test
    void withCommissionYearBetweenOrGreaterOrLessWhenEndOnlyShouldReturnLessOrEqual() {
        Integer end = 2023;
        Mockito.when(root.get("commissionYear")).thenReturn(path);
        Mockito.when(cb.lessThanOrEqualTo(root.get("commissionYear"), end)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withCommissionYearBetweenOrGreaterOrLess(null, end);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withCommissionYearBetweenOrGreaterOrLess}
     * возвращает спецификацию, которая при вызове {@code toPredicate} с обоими непустыми значениями
     * возвращает предикат "между".
     */
    @Test
    void withCommissionYearBetweenOrGreaterOrLessWhenBothNotNullShouldReturnBetween() {
        Mockito.when(root.get("commissionYear")).thenReturn(path);
        Mockito.when(cb.between(root.get("commissionYear"), 2020, 2023)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withCommissionYearBetweenOrGreaterOrLess(
                2020,
                2023
        );

        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAuthor} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withAuthorWhenAuthorIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withAuthor(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAuthor} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withAuthorWhenAuthorIsNotNullShouldReturnSpecification() {
        String author = "Иванов";
        Mockito.when(root.get("author")).thenReturn(path);
        Mockito.when(cb.like(root.get("author"), "%" + author + "%")).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAuthor(author);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptedFirstTimeOrReplaced} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withAcceptedFirstTimeOrReplacedWhenValueIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withAcceptedFirstTimeOrReplaced(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAcceptedFirstTimeOrReplaced} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withAcceptedFirstTimeOrReplacedWhenValueIsNotNullShouldReturnSpecification() {
        AcceptedFirstTimeOrReplacedEnum value = AcceptedFirstTimeOrReplacedEnum.FIRST_TIME;
        Mockito.when(root.get("acceptedFirstTimeOrReplaced")).thenReturn(path);
        Mockito.when(cb.equal(path, value)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAcceptedFirstTimeOrReplaced(value);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withKeyWords} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withKeyWordsWhenKeyWordsIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withKeyWords(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withKeyWords} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withKeyWordsWhenKeyWordsIsNotNullShouldReturnSpecification() {
        String keyWords = "стандарт качество";
        Mockito.when(root.get("keyWords")).thenReturn(path);
        Mockito.when(cb.like(root.get("keyWords"), "%" + keyWords + "%")).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withKeyWords(keyWords);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAdoptionLevel} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withAdoptionLevelWhenAdoptionLevelIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withAdoptionLevel(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withAdoptionLevel} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withAdoptionLevelWhenAdoptionLevelIsNotNullShouldReturnSpecification() {
        AdoptionLevelEnum adoptionLevel = AdoptionLevelEnum.NATIONAL;
        Mockito.when(root.get("adoptionLevel")).thenReturn(path);
        Mockito.when(cb.equal(path, adoptionLevel)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withAdoptionLevel(adoptionLevel);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withStatus} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withStatusWhenStatusIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withStatus(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withStatus} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withStatusWhenStatusIsNotNullShouldReturnSpecification() {
        StatusEnum status = StatusEnum.CURRENT;
        Mockito.when(root.get("status")).thenReturn(path);
        Mockito.when(cb.equal(path, status)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withStatus(status);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withHarmonization} возвращает спецификацию,
     * которая при вызове {@code toPredicate} с {@code null} возвращает {@code null}.
     */
    @Test
    void withHarmonizationWhenHarmonizationIsNullShouldReturnNull() {
        Specification<Document> spec = documentSpecification.withHarmonization(null);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNull(predicate);
    }

    /**
     * Проверяет, что метод {@code withHarmonization} возвращает непустую спецификацию,
     * и что при вызове {@code toPredicate} с непустым значением возвращается непустой предикат.
     */
    @Test
    void withHarmonizationWhenHarmonizationIsNotNullShouldReturnSpecification() {
        HarmonizationEnum harmonization = HarmonizationEnum.HARMONIZED;
        Mockito.when(root.get("harmonization")).thenReturn(path);
        Mockito.when(cb.equal(path, harmonization)).thenReturn(predicate);

        Specification<Document> spec = documentSpecification.withHarmonization(harmonization);
        Assertions.assertNotNull(spec);

        Predicate predicate = spec.toPredicate(root, query, cb);
        Assertions.assertNotNull(predicate);
    }
}