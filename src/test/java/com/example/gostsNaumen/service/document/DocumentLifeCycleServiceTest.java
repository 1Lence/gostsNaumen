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
    private Document replcaedDocument;
    private Document currentDocument;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentLifeCycleService documentLifeCycleService;

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

        replcaedDocument = new Document("testName", "testDesignation", "testCodeOKS",
                "testActivity", "testAuthor", "testApplication", "testContent",
                2017, 2019, "testKeywords", AdoptionLevelEnum.NATIONAL,
                StatusEnum.REPLACED, HarmonizationEnum.HARMONIZED, AcceptedFirstTimeOrReplacedEnum.FIRST_TIME,
                new HashSet<>() {{
                    add("ГОСТ 28653—90");
                    add("ГОСТ 3722—2014");
                }});
        replcaedDocument.setId(2L);

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

    @Test
    void doLifeCycleTransitionShouldReturnDocumentWhenTransitionOccur() {

        Mockito.when(documentRepository.findByFullNameAndStatus(currentDocument.getFullName(), StatusEnum.REPLACED))
                .thenReturn(Optional.of(canceledDocument));

        Document document = documentLifeCycleService.doLifeCycleTransition(currentDocument, StatusEnum.REPLACED);

        replcaedDocument.setId(document.getId());

        Assertions.assertEquals(replcaedDocument, document);
    }


    /**
     * Метод, тестирующий невозможные переходы по жизненному циклу, а также их сообщения
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
                        replcaedDocument,
                        StatusEnum.CANCELED));

        BusinessException businessExceptionFourth = Assertions.assertThrows(
                BusinessException.class,
                () -> documentLifeCycleService.doLifeCycleTransition(
                        replcaedDocument,
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

    @Test
    void checkInterferingDocumentsShouldReturnVoidWhenNoInterferingDocuments() {

        DocumentLifeCycleService service = Mockito.spy(documentLifeCycleService);

        Mockito.when(documentRepository.findByFullNameAndStatus("testName", StatusEnum.CURRENT))
                .thenReturn(Optional.empty());

        service.doLifeCycleTransition(canceledDocument, StatusEnum.CURRENT);

        Mockito.verify(service, Mockito.times(1))
                .checkInterferingDocuments(canceledDocument, StatusEnum.CURRENT);
    }
}