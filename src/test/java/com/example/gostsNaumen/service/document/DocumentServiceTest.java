package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private LifeCycleService lifeCycleService;

    @InjectMocks
    private DocumentService documentService;

    private Document document;

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
        when(documentRepository.findByFullName(document.getFullName())).thenReturn(Optional.of(document));

        EntityExistsException testException = assertThrows(EntityExistsException.class,
                () -> documentService.saveDocument(document));
        assertEquals("Такой гост уже существует.", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке получить несуществующий документ
     * Также проверяет текст ошибки, он должен соответствовать {@code Документ по ID: {id документа} не найден}
     */
    @Test
    void getDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        EntityNotFoundException testException = assertThrows(EntityNotFoundException.class,
                () -> documentService.getDocumentById(document.getId()));
        assertEquals("Документ по ID: " + document.getId() + " не найден.", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке получить гост по null id
     * Также проверяет текст ошибки, он должен соответствовать {@code Поиск по пустому ID}
     */
    @Test
    void getDocumentShouldThrowEntityNotFoundExceptionWhenProvidedIdIsNull() {
        EntityNotFoundException testException = assertThrows(EntityNotFoundException.class,
                () -> documentService.getDocumentById(null));
        assertEquals("Поиск по пустому ID", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке вызвать метод с null id
     * Также проверяет текст ошибки, он должен соответствовать {@code Удаление по пустому ID}
     */
    @Test
    void deleteDocumentShouldThrowIllegalArgumentExceptionWhenDocumentDoesNotExist() {
        IllegalArgumentException testException = assertThrows(IllegalArgumentException.class,
                () -> documentService.deleteDocumentById(null));
        assertEquals("Получен null id", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link EntityNotFoundException} при попытке вызвать метод с id, запись с которым в
     * базе отсутствует.
     * Также проверяет текст ошибки, он должен соответствовать {@code Удаление по пустому ID}
     */
    @Test
    void deleteDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        when(documentRepository.existsById(document.getId())).thenReturn(false);

        EntityNotFoundException testException = assertThrows(EntityNotFoundException.class,
                () -> documentService.deleteDocumentById(document.getId()));
        assertEquals("Документа с таким " + document.getId() + " не существует", testException.getMessage());
    }

    /**
     * Проверка на выброс {@link BusinessException}, при попытке поменять статус документа на аналогичный
     * Также проверяется код ошибки, он должен соответствовать {@code ErrorCode.STATUS_ALREADY_SET}
     */
    @Test
    void updateDocumentStatusShouldThrowStatusAlreadySetExceptionWhenDocumentStatusMatchesTransmittedStatus() {
        BusinessException testException = assertThrows(BusinessException.class,
                () -> documentService.updateDocumentStatus(document, document.getStatus()));
        assertEquals(ErrorCode.STATUS_ALREADY_SET, testException.getErrorCode());
    }

    /**
     * Проверка на выброс {@link BusinessException}, при попытке совершить невозможный переход по жизненному циклу
     * Также проверяется код ошибки, он должен соответствовать {@code ErrorCode.INVALID_LIFECYCLE_TRANSITION}
     */
    @Test
    void updateDocumentStatusShouldThrowInvalidLifeCycleTransactionWhenStatusTransitionIsNotPossible() {
        when(lifeCycleService.canUpdateStatus(document.getStatus(), StatusEnum.CURRENT)).thenReturn(false);
        BusinessException testException = assertThrows(BusinessException.class,
                () -> documentService.updateDocumentStatus(document, StatusEnum.CURRENT));
        assertEquals(ErrorCode.INVALID_LIFECYCLE_TRANSITION, testException.getErrorCode());
    }

    /**
     * Проверяет на выброс {@link EntityNotFoundException}, при попытке актуализировать документ по несуществующему id
     * Также проверяет текст ошибки, он должен соответствовать {@code Документ по ID: {id документа} не найден.}
     */
    @Test
    void actualizeDocumentShouldThrowEntityNotFoundExceptionWhenDocumentDoesNotExist() {
        Long id = 2L;
        when(documentRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException testException = assertThrows(EntityNotFoundException.class,
                () -> documentService.actualizeDocument(id, new ActualizeDtoRequest()));
        assertEquals("Документ по ID: " + id + " не найден.", testException.getMessage());
    }
}