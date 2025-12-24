package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.CustomEntityExistsException;
import com.example.gostsNaumen.exception.CustomEntityNotFoundException;
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
     * Проверка на выброс {@link CustomEntityExistsException}
     * при попытке сохранить существующий документ.
     * Также проверяется текст исключения, он должен соответствовать
     * {@code "Гост c таким full_name: {имя документа} уже существует!"}
     */
    @Test
    void saveDocumentShouldThrowExceptionWhenDocumentAlreadyExists() {
        Mockito.when(documentRepository.findByFullName(document.getFullName()))
                .thenReturn(Optional.of(document));

        CustomEntityExistsException testException = Assertions.assertThrows(CustomEntityExistsException.class,
                () -> documentService.saveDocument(document));

        Assertions.assertEquals("Гост c таким full_name: " + document.getFullName() + " уже существует!",
                testException.getMessage());
    }

    /**
     * Проверка на Optional.empty при попытке получить документ не существующий по id
     */
    @Test
    void getDocumentShouldReturnOptionalEmptyWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());
        Assertions.assertTrue(documentService.getDocumentById(document.getId()).isEmpty());
    }

    /**
     * Проверка на выброс {@link IllegalArgumentException} при попытке получить гост по null id
     * Также проверяет текст ошибки, он должен соответствовать {@code Поиск по пустому ID}
     */
    @Test
    void getDocumentShouldThrowIllegalArgumentExceptionWhenProvidedIdIsNull() {

        Long id = null;

        IllegalArgumentException testException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> documentService.getDocumentById(null));
        Assertions.assertEquals("Некорректный аргумент: " + id, testException.getMessage());
    }

    /**
     * Проверка на выброс {@link CustomEntityNotFoundException} при попытке вызвать метод с id, запись с которым в
     * базе отсутствует.
     * Также проверяет текст ошибки, он должен соответствовать {@code По переданному id нет стандарта}
     */
    @Test
    void deleteDocumentShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.existsById(document.getId())).thenReturn(false);

        CustomEntityNotFoundException testException = Assertions.assertThrows(CustomEntityNotFoundException.class,
                () -> documentService.deleteDocumentById(document.getId()));

        Assertions.assertEquals("По переданному ID: 1, нет стандарта", testException.getMessage());
    }

    /**
     * Проверяет на выброс {@link CustomEntityNotFoundException}, при попытке актуализировать документ по несуществующему id
     * Также проверяет текст ошибки, он должен соответствовать {@code По переданному id нет стандарта}
     */
    @Test
    void updateDocumentShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Document testDocument = new Document();
        testDocument.setId(2L);

        Mockito.when(documentRepository.existsById(testDocument.getId())).thenReturn(false);

        CustomEntityNotFoundException testException = Assertions.assertThrows(CustomEntityNotFoundException.class,
                () -> documentService.updateDocument(testDocument));
        Assertions.assertEquals("По переданному ID: %d, нет стандарта".formatted(testDocument.getId()), testException
                .getMessage());
    }
}