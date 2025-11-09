package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

/**
 * Класс для тестов сервиса по работе с документами {@link DocumentService}.<br>
 * Тестирует следующие методы:
 * <ul>
 *     <li>{@code saveDocument}</li>
 *     <li>{@code getDocumentById}</li>
 *     <li>{@code deleteDocumentById}</li>
 *     <li>{@code updateDocumentStatus}</li>
 *     <li>{@code updateDocument}</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private LifeCycleService lifeCycleService;

    @InjectMocks
    private DocumentService documentService;

    private Document document;

    /**
     * Перед тестами создаём пример экземпляра госта
     */
    @BeforeEach
    void setUp() {
        document = new Document();
        document.setId(1L);
        document.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
        document.setDesignation("ГОСТ 34286-2017");
        document.setCodeOKS("13.340.10");
        document.setActivityField("Защитная одежда");
        document.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
        document.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
        document.setContentLink("https://www.google.com");
        document.setAcceptanceYear(2017);
        document.setCommissionYear(2019);
        document.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
                "структуры, показатель противоосколочной стойкости защитной структуры");
        document.setStatus(StatusEnum.REPLACED);
        document.setAdoptionLevel(AdoptionLevelEnum.NATIONAL);
        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.FIRST_TIME);
        document.setReferences(new HashSet<>() {{
            add("ГОСТ 28653—90");
            add("ГОСТ 3722—2014");
        }});
    }

    /**
     * Проверка на выброс {@link EntityExistsException} при попытке сохранить существующий документ.
     * Также проверяется текст исключения, он должен соответствовать {@code Такой гост уже существует}
     */
    @Test
    void saveDocumentShouldThrowExceptionWhenDocumentAlreadyExists() {
        Mockito.when(documentRepository.findByFullName(document.getFullName())).thenReturn(Optional.of(document));

        EntityExistsException testException = Assertions.assertThrows(EntityExistsException.class,
                () -> documentService.saveDocument(document));
        Assertions.assertEquals("Такой гост уже существует.", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке получить несуществующий документ
     * Также проверяет текст ошибки, он должен соответствовать {@code Документ по ID: {id документа} не найден}
     */
    @Test
    void getDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        EntityNotFoundException testException = Assertions.assertThrows(EntityNotFoundException.class,
                () -> documentService.getDocumentById(document.getId()));
        Assertions.assertEquals("Документ по ID: " + document.getId() + " не найден.", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке получить гост по null id
     * Также проверяет текст ошибки, он должен соответствовать {@code Поиск по пустому ID}
     */
    @Test
    void getDocumentShouldThrowEntityNotFoundExceptionWhenProvidedIdIsNull() {
        EntityNotFoundException testException = Assertions.assertThrows(EntityNotFoundException.class,
                () -> documentService.getDocumentById(null));
        Assertions.assertEquals("Поиск по пустому ID", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке вызвать метод с null id
     * Также проверяет текст ошибки, он должен соответствовать {@code Удаление по пустому ID}
     */
    @Test
    void deleteDocumentShouldThrowIllegalArgumentExceptionWhenDocumentDoesNotExist() {
        IllegalArgumentException testException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> documentService.deleteDocumentById(null));
        Assertions.assertEquals("Получен null id", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке вызвать метод с id, запись с которым в
     * базе отсутствует.
     * Также проверяет текст ошибки, он должен соответствовать {@code Удаление по пустому ID}
     */
    @Test
    void deleteDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.existsById(document.getId())).thenReturn(false);

        EntityNotFoundException testException = Assertions.assertThrows(EntityNotFoundException.class,
                () -> documentService.deleteDocumentById(document.getId()));
        Assertions.assertEquals("Документа с таким " + document.getId() + " не существует", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link BusinessException}, при попытке поменять статус документа на аналогичный
     * Также проверяется код ошибки, он должен соответствовать {@code ErrorCode.STATUS_ALREADY_SET}
     */
    @Test
    void updateDocumentStatusShouldThrowStatusAlreadySetExceptionWhenDocumentStatusMatchesTransmittedStatus() {
        BusinessException testException = Assertions.assertThrows(BusinessException.class,
                () -> documentService.updateDocumentStatus(document, document.getStatus()));
        Assertions.assertEquals(ErrorCode.STATUS_ALREADY_SET, testException.getErrorCode());
    }

    /**
     * Проверка на выброс {@link BusinessException}, при попытке совершить невозможный переход по жизненному циклу
     * Также проверяется код ошибки, он должен соответствовать {@code ErrorCode.INVALID_LIFECYCLE_TRANSITION}
     */
    @Test
    void updateDocumentStatusShouldThrowInvalidLifeCycleTransactionWhenStatusTransitionIsNotPossible() {
        Mockito.when(lifeCycleService.canUpdateStatus(document.getStatus(), StatusEnum.CURRENT)).thenReturn(false);
        BusinessException testException = Assertions.assertThrows(BusinessException.class,
                () -> documentService.updateDocumentStatus(document, StatusEnum.CURRENT));
        Assertions.assertEquals(ErrorCode.INVALID_LIFECYCLE_TRANSITION, testException.getErrorCode());
    }

    /**
     * Проверяет на выброс {@link EntityNotFoundException}, при попытке актуализировать документ по несуществующему id
     * Также проверяет текст ошибки, он должен соответствовать {@code Документ по ID: {id документа} не найден.}
     */
    @Test
    void updateDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        Document testDocument = new Document();
        testDocument.setId(2L);

        Mockito.when(documentRepository.existsById(testDocument.getId())).thenReturn(false);

        EntityNotFoundException testException = Assertions.assertThrows(EntityNotFoundException.class,
                () -> documentService.updateDocument(testDocument));
        Assertions.assertEquals("Документа с таким id: " + testDocument.getId() + ", не существует", testException.getMessage());
    }
}