package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class DocumentLifeCycleServiceTest {

    private Document canceledDocument;
    private Document replacedDocument;
    private Document currentDocument;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentLifeCycleService documentLifeCycleService;

    /**
     * Нам понадобится 3 документа с различными полями Status
     */
    @BeforeEach
    void setUp() {
        canceledDocument = new Document("testName", "testDesignation", "testCodeOKS",
                "testActivity", "testAuthor", "testApplication", "testContent",
                2017, 2019, "testKeywords", AdoptionLevelEnum.NATIONAL,
                StatusEnum.CANCELED, HarmonizationEnum.HARMONIZED, AcceptedFirstTimeOrReplacedEnum.FIRST_TIME,
                new HashSet<>() {{
                    add("ГОСТ 28653—90");
                    add("ГОСТ 3722—2014");
                }});
        canceledDocument.setId(1L);

        replacedDocument = new Document("testName", "testDesignation", "testCodeOKS",
                "testActivity", "testAuthor", "testApplication", "testContent",
                2017, 2019, "testKeywords", AdoptionLevelEnum.NATIONAL,
                StatusEnum.REPLACED, HarmonizationEnum.HARMONIZED, AcceptedFirstTimeOrReplacedEnum.FIRST_TIME,
                new HashSet<>() {{
                    add("ГОСТ 28653—90");
                    add("ГОСТ 3722—2014");
                }});
        replacedDocument.setId(2L);

        currentDocument = new Document("testName", "testDesignation", "testCodeOKS",
                "testActivity", "testAuthor", "testApplication", "testContent",
                2017, 2019, "testKeywords", AdoptionLevelEnum.NATIONAL,
                StatusEnum.CURRENT, HarmonizationEnum.HARMONIZED, AcceptedFirstTimeOrReplacedEnum.FIRST_TIME,
                new HashSet<>() {{
                    add("ГОСТ 28653—90");
                    add("ГОСТ 3722—2014");
                }});
        currentDocument.setId(3L);
    }

    /**
     * Метод, тестирующий успешный случай изменения статуса документа.
     * <p>Для возникновения успешного кейса должны соблюдаться условия:
     * <ul>
     *     <li>Переход из статуса А в статус В возможен</li>
     *     <li>При переходе в статус {@link StatusEnum#CURRENT} не должно существовать других документов с этим статусом и именем изменяемого документа</li>
     * </ul>
     * <p> Результатом работы метода служит {@link Document} с обновлённым полем Status у документа
     * <p> Также метод тестирует:
     * <ul>
     *     <li>Возвращение true методом {@link DocumentLifeCycleService#isTransitionAllowed(StatusEnum, StatusEnum)}</li>
     *     <li>Отсутствие выброса исключения методом
     *     {@link DocumentLifeCycleService#checkInterferingDocuments(Document, StatusEnum)}</li>
     * </ul>
     */
    @Test
    void doLifeCycleTransitionShouldReturnDocumentWhenTransitionOccur() {

        Mockito.when(documentRepository.save(Mockito.any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Document document = documentLifeCycleService.doLifeCycleTransition(currentDocument, StatusEnum.REPLACED);

        replacedDocument.setId(document.getId());

        Assertions.assertEquals(replacedDocument, document);
    }


    /**
     * Метод, тестирующий выброс {@link  BusinessException} при невозможном переходе по статусам
     * Невозможные переходы:
     * <ul>
     *     <li>Из {@link StatusEnum#CURRENT} в {@link StatusEnum#CANCELED} и наоборот</li>
     *     <li>Исключение также выбрасывается при попытке изменить статус документа на аналогичный текущему,
     *     например из {@link StatusEnum#CURRENT} в {@link StatusEnum#CURRENT}</li>
     * </ul>
     */
    @Test
    void doLifeCycleTransitionShouldThrowBusinessExceptionWhenTransitionIsNotPossible() {

        BusinessException businessExceptionFirst = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        canceledDocument,
                        StatusEnum.REPLACED));

        BusinessException businessExceptionSecond = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        canceledDocument,
                        StatusEnum.CANCELED));

        BusinessException businessExceptionThird = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        replacedDocument,
                        StatusEnum.CANCELED));

        BusinessException businessExceptionFourth = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        replacedDocument,
                        StatusEnum.REPLACED));

        Assertions.assertEquals(
                "Переход из статуса CANCELED в статус REPLACED невозможен",
                businessExceptionFirst.getFormattedMessage());
        Assertions.assertEquals(
                "Переход из статуса CANCELED в статус CANCELED невозможен",
                businessExceptionSecond.getFormattedMessage());
        Assertions.assertEquals(
                "Переход из статуса REPLACED в статус CANCELED невозможен",
                businessExceptionThird.getFormattedMessage());
        Assertions.assertEquals(
                "Переход из статуса REPLACED в статус REPLACED невозможен",
                businessExceptionFourth.getFormattedMessage());
    }

    /**
     * Метод, тестирующий случай выброса BusinessException.
     * <p>Условия кейса:
     * <ul>
     *     <li>Целевой статус замены это {@link StatusEnum#CURRENT}</li>
     *     <li>В базе данных уже существует документ с идентичным {@link Document#getFullName()} и статусом "Актуален"</li>
     * </ul>
     * <p>Должна вернуться ошибка {@link BusinessException} со следующими полями:</p>
     * <ul>
     *     <li>{@link BusinessException#getErrorCode()} равняется
     *     {@link com.example.gostsNaumen.exception.ErrorCode#OTHER_DOC_INTERFERES_WITH_TRANSITION}</li>
     *     <li>{@link BusinessException#getFormattedMessage()} равняется
     *     {@code "Другой документ не позволяет изменить статус текущего документа его id: (id документа)"}</li>
     * </ul>
     * <p> Исключение выбрасывается методом:
     * <ul>
     *     <li>{@link DocumentLifeCycleService#checkInterferingDocuments(Document, StatusEnum)}</li>
     * </ul>
     */
    @Test
    void doLifeCycleTransitionShouldThrowBusinessExceptionWhenExistInterferingDocuments() {

        Mockito.when(documentRepository.findByFullNameAndStatus("testName", StatusEnum.CURRENT))
                .thenReturn(Optional.of(currentDocument));

        BusinessException businessException = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        canceledDocument,
                        StatusEnum.CURRENT));

        Assertions.assertEquals(
                "Другой документ не позволяет изменить статус текущего документа его id: 3",
                businessException.getFormattedMessage());
    }
}