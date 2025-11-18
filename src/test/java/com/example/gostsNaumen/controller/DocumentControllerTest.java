package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.DocumentFieldsActualizer;
import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.security.jwe.JweFilter;
import com.example.gostsNaumen.service.document.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

/**
 * Класс, предназначенный для тестирования контроллера документов
 */
@WebMvcTest(DocumentController.class)
@AutoConfigureMockMvc(addFilters = false)
class DocumentControllerTest {

    DocumentDtoRequest documentRequest;
    DocumentDtoResponse documentResponse;
    Document document;
    Document updatedDocument;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentMapper documentMapper;
    @MockitoBean
    private DocumentService documentService;
    @MockitoBean
    private RusEngEnumConverter rusEngEnumConverter;
    @MockitoBean
    private DocumentFieldsActualizer documentFieldsActualizer;
    @MockitoBean
    private JweFilter jweFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Нам понадобятся экземпляры dto и entity документа
     */
    @BeforeEach
    void setUp() {
        documentRequest = new DocumentDtoRequest();
        documentRequest.setFullName("testName");
        documentRequest.setDesignation("ГОСТ 34286-2017");
        documentRequest.setCodeOKS("13.340.10");
        documentRequest.setActivityField("testField");
        documentRequest.setAuthor("testAuthor");
        documentRequest.setApplicationArea("testApplicationArea");
        documentRequest.setContentLink("testLink");
        documentRequest.setAcceptanceYear(2017);
        documentRequest.setCommissionYear(2019);
        documentRequest.setKeyWords("testKeyWords");
        documentRequest.setStatus("Актуальный");
        documentRequest.setAdoptionLevel("Национальный");
        documentRequest.setHarmonization("Гармонизированный");
        documentRequest.setAcceptedFirstTimeOrReplaced("ВВЕДЕН ВПЕРВЫЕ");
        documentRequest.setReferences(new HashSet<>() {{
            add("test1");
            add("test2");
        }});


        document = new Document();
        document.setFullName("testName");
        document.setDesignation("ГОСТ 34286-2017");
        document.setCodeOKS("13.340.10");
        document.setActivityField("testField");
        document.setAuthor("testAuthor");
        document.setApplicationArea("testApplicationArea");
        document.setContentLink("testLink");
        document.setAcceptanceYear(2017);
        document.setCommissionYear(2019);
        document.setKeyWords("testKeyWords");
        document.setStatus(StatusEnum.CURRENT);
        document.setAdoptionLevel(AdoptionLevelEnum.NATIONAL);
        document.setHarmonization(HarmonizationEnum.HARMONIZED);
        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.FIRST_TIME);
        document.setReferences(new HashSet<>() {{
            add("test1");
            add("test2");
        }});

        documentResponse = new DocumentDtoResponse();
        documentResponse.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
        documentResponse.setDesignation("ГОСТ 34286-2017");
        documentResponse.setCodeOKS("13.340.10");
        documentResponse.setActivityField("Защитная одежда");
        documentResponse.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
        documentResponse.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
        documentResponse.setContentLink("https://www.google.com");
        documentResponse.setAcceptanceYear(2017);
        documentResponse.setCommissionYear(2019);
        documentResponse.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
                "структуры, показатель противоосколочной стойкости защитной структуры");
        documentResponse.setStatus("Актуальный");
        documentResponse.setAdoptionLevel("Национальный");
        documentResponse.setHarmonization("Гармонизированный");
        documentResponse.setAcceptedFirstTimeOrReplaced("ВВЕДЕН ВПЕРВЫЕ");
        documentResponse.setReferences(new HashSet<>() {{
            add("ГОСТ 28653—90");
            add("ГОСТ 3722—2014");
        }});

        updatedDocument = new Document();
        updatedDocument.setId(1L);
        updatedDocument.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
        updatedDocument.setDesignation("ГОСТ 34286-2017");
        updatedDocument.setCodeOKS("13.340.10");
        updatedDocument.setActivityField("Защитная одежда");
        updatedDocument.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
        updatedDocument.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
        updatedDocument.setContentLink("https://new_link.ru");
        updatedDocument.setAcceptanceYear(2010);
        updatedDocument.setCommissionYear(2019);
        updatedDocument.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
                "структуры, показатель противоосколочной стойкости защитной структуры");
        updatedDocument.setStatus(StatusEnum.CURRENT);
        updatedDocument.setAdoptionLevel(AdoptionLevelEnum.NATIONAL);
        updatedDocument.setHarmonization(HarmonizationEnum.MODIFIED);
        updatedDocument.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.FIRST_TIME);
        updatedDocument.setReferences(new HashSet<>() {{
            add("ГОСТ 28653—90");
            add("ГОСТ 3722—2014");
        }});
    }

    /**
     * Служебный метод, преобразует объект в json
     *
     * @param object элемент для конвертации в json
     * @return json строка
     */
    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации объекта в JSON", e);
        }
    }

    /**
     * Тест, проверяющий кейс с добавлением нового документа без ошибок
     * <ul>
     *     <li>Контроллер возвращает JSON с единственным полем id</li>
     * </ul>
     */
    @Test
    void addDocumentShouldReturnAddedDocumentId() throws Exception {

        Mockito.when(documentMapper.mapToEntity(Mockito.any(DocumentDtoRequest.class)))
                .thenReturn(document);
        Mockito.when(documentService.saveDocument(Mockito.any(Document.class)))
                .thenAnswer(invocation -> {
                    Document temp = invocation.getArgument(0);
                    temp.setId(1L);
                    return temp;
                });

        mockMvc.perform(MockMvcRequestBuilders.post("/api/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(documentRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    /**
     * Тест, проверяющий кейс с добавлением дублирующего существующий документа
     * <p>
     * Ответ должен быть:
     * <ul>
     * <li>DTO корректно маппится в экземпляр сущности</li>
     * <li>При попытке сохранения документа сервис выбрасывает ошибку</li>
     * <li>Контроллер возвращает JSON с полями:
     *     <ul>
     *         <li>status: CONFLICT</li>
     *         <li>message: Гост c таким full_name: {@code имя стандарта} уже существует!</li>
     *         <li>timestamp: {@code TimeStamp}</li>
     *         <li>url: /api/standards</li>
     *     </ul></li>
     * </ul>
     */
    @Test
    void addDocumentShouldReturnEntityExistsException() throws Exception {

        Mockito.when(documentMapper.mapToEntity(Mockito.any(DocumentDtoRequest.class)))
                .thenReturn(document);
        Mockito.when(documentService.saveDocument(Mockito.any(Document.class)))
                .thenThrow(new BusinessException(ErrorCode.STANDARD_EXIST_BY_FULL_NAME,
                        "Гост c таким full name: " + document.getFullName() + " уже существует!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(documentRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Гост c таким full name: " + document.getFullName() + " уже существует!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url")
                        .value("/api/standards"));

    }

    /**
     * Тест, покрывающий кейс, когда в параметр метода передаётся отрицательное значение id
     * <p>
     * Ответ должен быть:
     *
     * <ul>
     *         <li>status: BAD_REQUEST</li>
     *         <li>message: "Некорректный аргумент: {@code некорректное значение id}"</li>
     *         <li>timestamp: {@code TimeStamp}</li>
     *         <li>url: /api/standards/{@code некорректное значение id}</li>
     * </ul>
     */
    @Test
    void getDocumentShouldThrowIllegalArgumentExceptionWhenProvidedNegativeId() throws Exception {

        Mockito.when(documentService.getDocumentById(Mockito.anyLong()))
                .thenThrow(new IllegalArgumentException("Некорректное значение id: " + -2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", -2))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Некорректное значение id: " + -2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/standards/-2"));
    }


    /**
     * Тест, покрывающий кейс, когда в параметр метода передаётся строка вместо id
     * <ul>
     *         <li>status: BAD_REQUEST</li>
     *         <li>message: "Некорректный аргумент: {@code некорректное значение id}"</li>
     *         <li>timestamp: {@code TimeStamp}</li>
     *         <li>url: /api/standards/{@code некорректное значение id}</li>
     * </ul>
     */
    @Test
    void getDocumentShouldThrowMethodArgumentTypeMismatchExceptionWhenProvidedStringId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", "manakinko"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Некорректный аргумент: " + "manakinko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/standards/manakinko"));
    }

    /**
     * Тест, проверяющий кейс, когда сервис успешно возвращает документ по переданному идентификатору
     * <p>
     * Пример успешно выведенного документа:
     * <ul>
     *     <li>"id": 9,</li>
     *     <li>"fullName": "testName,"</li>
     *     <li>"designation": "ГОСТ 34286-2017,"</li>
     *     <li>"codeOKS": "13.340.10,"</li>
     *     <li>"activityField": "test Field,"</li>
     *     <li>"author": "testAuthor,"</li>
     *     <li>"applicationArea": "testApplicationArea,"</li>
     *     <li>"contentLink": "testLink,"</li>
     *     <li>"acceptanceYear": 2017,</li>
     *     <li>"commissionYear": 2019,</li>
     *     <li>"keyWords": "testKeyWords,"</li>
     *     <li>"adoptionLevel": "Национальный,"</li>
     *     <li>"status": "Актуальный,"</li>
     *     <li>"harmonization": "Не гармонизированный,"</li>
     *     <li>"acceptedFirstTimeOrReplaced": "ВВЕДЕН ВПЕРВЫЕ,"</li>
     *     <li>"references": [<br>
     *      test2",<br>
     *      test1"<br>
     *      ]</li>
     * </ul>
     */
    @Test
    void getDocumentShouldReturnDocument() throws Exception {
        Mockito.when(documentService.getDocumentById(Mockito.anyLong())).thenReturn(document);
        Mockito.when(documentMapper.mapEntityToDto(Mockito.any(Document.class)))
                .thenAnswer(invocation -> {
                    Document temp = invocation.getArgument(0);
                    return new DocumentDtoResponse(temp.getId(), temp.getFullName(), temp.getDesignation(),
                            temp.getCodeOKS(), temp.getActivityField(), temp.getAuthor(),
                            temp.getApplicationArea(), temp.getContentLink(), temp.getAcceptanceYear(),
                            temp.getCommissionYear(), temp.getKeyWords(), temp.getAdoptionLevel().getValue(),
                            temp.getStatus().getValue(), temp.getHarmonization().getValue(),
                            temp.getAcceptedFirstTimeOrReplaced().getValue(), temp.getReferences());
                });

        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName")
                        .value("testName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.designation")
                        .value("ГОСТ 34286-2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.codeOKS")
                        .value("13.340.10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activityField")
                        .value("testField"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                        .value("testAuthor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contentLink")
                        .value("testLink"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.applicationArea")
                        .value("testApplicationArea"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.acceptanceYear")
                        .value("2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commissionYear")
                        .value("2019"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keyWords")
                        .value("testKeyWords"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adoptionLevel")
                        .value("Национальный"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value("Актуальный"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.harmonization")
                        .value("Гармонизированный"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.acceptedFirstTimeOrReplaced")
                        .value("ВВЕДЕН ВПЕРВЫЕ"));
    }

    /**
     * Тест, проверяющий кейс, когда метод получения документа выбрасывает ошибку {@link BusinessException} с кодом
     * {@code ErrorCode.STANDARD_BY_ID_NOT_EXISTS}
     * в связи с тем, что по полученному id не найдена запись в бд
     * <p>
     * Ответ должен быть:
     *
     * <ul>
     *         <li>status: NOT_FOUND</li>
     *         <li>message: "По переданному id: {@code id стандарта} нет стандарта"</li>
     *         <li>timestamp: {@code TimeStamp}</li>
     *         <li>url: /api/standards/{@code значение id}</li>
     * </ul>
     */
    @Test
    void getDocumentShouldReturnBusinessException() throws Exception {
        Long id = 1L;
        Mockito.when(documentService.getDocumentById(
                Mockito.anyLong())).thenThrow(new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                "По переданному id: %s нет стандарта".formatted(id)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url")
                        .value("/api/standards/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id: %s нет стандарта".formatted(id)));
    }

    /**
     * Тест, проверяющий кейс, когда происходит успешная попытка удаления документа
     * <p>
     * Пользователь получает 200 OK
     */
    @Test
    void deleteDocumentShouldReturnOk() throws Exception {
        Long docId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standards/{id}", docId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Тест, покрывающий кейс, когда через {@code PathVariable} передаётся отрицательное значение id.
     * <p>
     * Пользователь получает {@link com.example.gostsNaumen.handler.ErrorResponse} со следующими значениями полей:
     * <ul>
     *     <li>status: {@code BAD_REQUEST}</li>
     *     <li>message: {@code Некорректное значение id: {переданный id}}</li>
     *     </li>
     * </ul>
     */
    @Test
    void deleteDocumentShouldThrowIllegalArgumentExceptionWhenProvidedIdIsNullOrNegative() throws Exception {
        int wrongId = -10;

        Mockito.doThrow(new IllegalArgumentException("Некорректное значение id: " + wrongId))
                .when(documentService).deleteDocumentById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standards/{id}", wrongId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Некорректное значение id: " + wrongId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/standards/%s".formatted(wrongId)));
    }

    /**
     * Тест, проверяющий кейс, когда происходит попытка удаления по id к которому не относится ни один документ.
     * <p>
     * Пользователь получает {@link com.example.gostsNaumen.handler.ErrorResponse} со следующими значениями полей:
     * <ul>
     *     <li>status: {@code NOT_FOUND}</li>
     *     <li>message: {@code По переданному id: {переданный id} нет стандарта}</li>
     *     </li>
     * </ul>
     */
    @Test
    void deleteDocumentShouldThrowBusinessExceptionWhenStandardByIdNotExist() throws Exception {
        Long docId = 1L;
        Mockito.doThrow(new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                        "По переданному id: %s нет стандарта".formatted(docId)))
                .when(documentService).deleteDocumentById(docId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standards/{id}", docId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id: %s нет стандарта".formatted(docId)));
    }

    /**
     * Тест, проверяющий кейс, когда происходит попытка обновить поля документа, по id,
     * которому не соответствует ни одна запись в бд.
     * <p>
     * Пользователь получает {@link com.example.gostsNaumen.handler.ErrorResponse} со следующими значениями полей:
     * <ul>
     *     <li>status: {@code NOT_FOUND}</li>
     *     <li>message: {@code По переданному id: {переданный id} нет стандарта}</li>
     * </ul>
     */
    @Test
    void updateDocumentShouldThrowBusinessExceptionWhenStandardByIdNotExist() throws Exception {
        Long docId = 1L;
        Mockito.when(documentService.getDocumentById(docId)).thenThrow(
                new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                        "По переданному id: %s нет стандарта".formatted(docId)));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", docId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(documentRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id: %s нет стандарта".formatted(docId)));
    }

    /**
     * Тест, проверяющий корректное выполнение метода documentUpdate, возвращает {@link DocumentDtoResponse}
     */
    @Test
    void updateDocumentShouldReturnOk() throws Exception {
        Long docId = 1L;
        ActualizeDtoRequest actualizeDtoRequest = new ActualizeDtoRequest()
                .setContentLink("https://new_link.ru")
                .setHarmonization("Модифицированный")
                .setAcceptanceYear(2010);

        document.setId(docId);

        Mockito.when(documentService.getDocumentById(docId)).thenReturn(document);
        Mockito.when(documentFieldsActualizer.setNewValues(
                        Mockito.any(Document.class), Mockito.any(ActualizeDtoRequest.class)))
                .thenAnswer(invocation -> {
                    Document tempDocument = invocation.getArgument(0);
                    ActualizeDtoRequest tempActualizeDtoRequest = invocation.getArgument(1);
                    tempDocument.setContentLink(tempActualizeDtoRequest.getContentLink());
                    tempDocument.setAcceptanceYear(tempActualizeDtoRequest.getAcceptanceYear());
                    tempDocument.setHarmonization(HarmonizationEnum.MODIFIED);
                    return tempDocument;
                });
        Mockito.when(documentService.updateDocument(Mockito.any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(documentMapper.mapEntityToDto(Mockito.any(Document.class)))
                .thenAnswer(invocation -> {
                    Document doc = invocation.getArgument(0);
                    return new DocumentDtoResponse(
                            doc.getId(), doc.getFullName(), doc.getDesignation(), doc.getCodeOKS(), doc.getActivityField(),
                            doc.getAuthor(), doc.getApplicationArea(), doc.getContentLink(), doc.getAcceptanceYear(),
                            doc.getCommissionYear(), doc.getKeyWords(), doc.getAdoptionLevel().getValue(),
                            doc.getStatus().getValue(), doc.getHarmonization().getValue(),
                            doc.getAcceptedFirstTimeOrReplaced().getValue(), doc.getReferences()
                    );
                });

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", docId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(actualizeDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.contentLink").value("https://new_link.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.harmonization").value("Модифицированный"))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.acceptanceYear").value(2010));
    }

    /**
     * Тест, покрывающий кейс, когда получается невалидное дто и возникают ошибки валидации
     * <p>
     * Пользователю должен возвращаться {@link com.example.gostsNaumen.handler.ErrorResponse}  со следующими значениями
     * полей:
     * <ul>
     *     <li>{@code message: По переданному id: {id стандарта} нет стандарта}</li>
     *     <li>{@code validationErrors: {список ошибок валидации}}</li>
     *     <li>{@code status: BAD_REQUEST}</li>
     * </ul>
     */
    @Test
    void updateDocumentShouldThrowValidationException() throws Exception {
        ActualizeDtoRequest actualizeDtoRequest = new ActualizeDtoRequest()
                .setAdoptionLevel("неправильный уровень")
                .setHarmonization("неправильный уровень");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(actualizeDtoRequest)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.validationErrors[?(@.field=='adoptionLevel')].message").value(
                        org.hamcrest.Matchers.hasItem(
                                "Должно содержать: Национальный, " +
                                        "Межгосударственный, Отраслевой, " +
                                        "Региональный, Стандарт Организаций"
                        )))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.validationErrors[?(@.field=='harmonization')].message").value(
                        org.hamcrest.Matchers.hasItem(
                                "Должно содержать: Не гармонизированный, Модифицированный или Гармонизированный"
                        )));

    }

    /**
     * Метод, проверяющий кейс, когда пользователь пытается обновить документ по id, которому не соответствует
     * ни один стандарт
     * <p>
     * Пользователю должен возвращаться {@link com.example.gostsNaumen.handler.ErrorResponse} со следующими значениями
     * полей:
     * <ul>
     *     <li>{@code message: По переданному id: {id стандарта} нет стандарта}</li>
     *     <li>{@code status: NotFound}</li>
     * </ul>
     */
    @Test
    void updateDocumentShouldThrowBusinessExceptionWhenDocumentByIdIsNotExist() throws Exception {

        Long docId = 1L;

        Mockito.when(documentService.getDocumentById(Mockito.anyLong()))
                .thenThrow(new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                        "По переданному id: %s нет стандарта".formatted(docId)));

        ActualizeDtoRequest actualizeDtoRequest = new ActualizeDtoRequest()
                .setContentLink("https://new_link.ru");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", docId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(actualizeDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id: %s нет стандарта".formatted(docId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url")
                        .value("/api/standards/%s".formatted(docId)));
    }
}
