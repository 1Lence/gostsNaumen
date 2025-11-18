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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
        documentRequest.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
        documentRequest.setDesignation("ГОСТ 34286-2017");
        documentRequest.setCodeOKS("13.340.10");
        documentRequest.setActivityField("Защитная одежда");
        documentRequest.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
        documentRequest.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
        documentRequest.setContentLink("https://www.google.com");
        documentRequest.setAcceptanceYear(2017);
        documentRequest.setCommissionYear(2019);
        documentRequest.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
                "структуры, показатель противоосколочной стойкости защитной структуры");
        documentRequest.setStatus("Актуальный");
        documentRequest.setAdoptionLevel("Национальный");
        documentRequest.setHarmonization("Гармонизированный");
        documentRequest.setAcceptedFirstTimeOrReplaced("ВВЕДЕН ВПЕРВЫЕ");
        documentRequest.setReferences(new HashSet<>() {{
            add("ГОСТ 28653—90");
            add("ГОСТ 3722—2014");
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
        document.setStatus(StatusEnum.CURRENT);
        document.setAdoptionLevel(AdoptionLevelEnum.NATIONAL);
        document.setHarmonization(HarmonizationEnum.HARMONIZED);
        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.FIRST_TIME);
        document.setReferences(new HashSet<>() {{
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
     */
    @Test
    void addDocumentShouldReturnAddedDocumentId() throws Exception {

        Mockito.when(documentMapper.createDocumentEntity(Mockito.any(DocumentDtoRequest.class))).thenReturn(document);
        Mockito.when(documentService.saveDocument(document)).thenReturn(document);

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
     */
    @Test
    void addDocumentShouldReturnEntityExistsException() throws Exception {

        Mockito.when(documentMapper.createDocumentEntity(Mockito.any(DocumentDtoRequest.class))).thenReturn(document);
        Mockito.when(documentService.saveDocument(document))
                .thenThrow(new EntityExistsException("Такой гост уже существует."));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(documentRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Такой гост уже существует."));

    }

    /**
     * Тест, проверяющий кейс, когда сервис успешно возвращает документ по переданным параметрам
     */
    @Test
    void getDocumentShouldReturnDocument() throws Exception {
        Mockito.when(documentMapper.mapEntityToDto(Mockito.any(Document.class))).thenReturn(documentResponse);
        Mockito.when(documentService.getDocumentById(Mockito.anyLong())).thenReturn(document);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName")
                        .value("БРОНЕОДЕЖДА Классификация и общие технические требования"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.designation")
                        .value("ГОСТ 34286-2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.codeOKS")
                        .value("13.340.10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activityField")
                        .value("Защитная одежда"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                        .value("МТК 391 «Средства физической защиты и материалы для их изготовления"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contentLink")
                        .value("https://www.google.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.acceptanceYear")
                        .value("2017"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commissionYear")
                        .value("2019"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keyWords")
                        .value("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной структуры, показатель противоосколочной стойкости защитной структуры"))
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
     * Тест, проверяющий кейс, когда метод получения документа выбрасывает ошибку {@link EntityNotFoundException}
     * в связи с тем, что по полученному id не найдена запись в бд
     */
    @Test
    void getDocumentShouldReturnNotFoundException() throws Exception {
        Long id = 1L;
        Mockito.when(documentService.getDocumentById(
                Mockito.anyLong())).thenThrow(new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id нет стандарта"));
    }

    /**
     * Тест, проверяющий кейс, когда происходит успешная попытка удаления документа
     */
    @Test
    void deleteDocumentShouldReturnOk() throws Exception {
        Long docId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standards/{id}", docId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Тест, проверяющий кейс, когда происходит попытка удаления по id к которому не относится ни один документ,
     * выбрасывается ошибка {@link EntityNotFoundException}
     */
    @Test
    void deleteDocumentShouldThrowEntityNotFoundException() throws Exception {
        Long docId = 1L;
        Mockito.doThrow(new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS))
                .when(documentService).deleteDocumentById(docId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/standards/{id}", docId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id нет стандарта"));
    }

    /**
     * Тест, проверяющий кейс, когда происходит попытка обновить поля документа, по id,
     * которому не соответствует ни одна запись в бд.
     * Выбрасывается ошибка {@link EntityNotFoundException}
     */
    @Test
    void updateDocumentShouldThrowNotFoundException() throws Exception {
        Long docId = 1L;
        Mockito.when(documentService.getDocumentById(docId)).thenThrow(
                new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", docId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(documentRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("По переданному id нет стандарта"));
    }

    /**
     * Тест, проверяющий корректное выполнение метода documentUpdate, возвращает 200 ok
     */
    @Test
    void updateDocumentShouldReturnOk() throws Exception {
        Long docId = 1L;
        ActualizeDtoRequest actualizeDtoRequest = new ActualizeDtoRequest()
                .setContentLink("https://new_link.ru")
                .setHarmonization("Модифицированный")
                .setAcceptanceYear(2010);

        Mockito.when(documentService.getDocumentById(docId)).thenReturn(document);
        Mockito.when(documentFieldsActualizer.setNewValues(
                Mockito.any(Document.class), Mockito.any(ActualizeDtoRequest.class))).thenReturn(updatedDocument);
        Mockito.when(documentService.updateDocument(updatedDocument)).thenReturn(updatedDocument);
        Mockito.when(documentMapper.mapEntityToDto(updatedDocument)).thenReturn(
                new DocumentDtoResponse(
                        updatedDocument.getId(),
                        updatedDocument.getFullName(),
                        updatedDocument.getDesignation(),
                        updatedDocument.getCodeOKS(),
                        updatedDocument.getActivityField(),
                        updatedDocument.getAuthor(),
                        updatedDocument.getApplicationArea(),
                        updatedDocument.getContentLink(),
                        updatedDocument.getAcceptanceYear(),
                        updatedDocument.getCommissionYear(),
                        updatedDocument.getKeyWords(),
                        "Национальный",
                        "Актуальный",
                        "Модифицированный",
                        "ИЗМЕНЁН",
                        updatedDocument.getReferences()
                ));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/standards/{id}", docId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(actualizeDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.contentLink").value("https://new_link.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.harmonization").value("Модифицированный"))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.acceptanceYear").value(2010));
    }

    /**
     * Тест, покрывающий кейс, когда получается невалидное дто
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
}
