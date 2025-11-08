//package com.example.gostsNaumen.controller;
//
//import com.example.gostsNaumen.controller.dto.DocumentMapper;
//import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
//import com.example.gostsNaumen.entity.Document;
//import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
//import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
//import com.example.gostsNaumen.entity.model.StatusEnum;
//import com.example.gostsNaumen.service.document.DocumentService;
//import jakarta.persistence.EntityExistsException;
//import org.mockito.Mockito;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.HashSet;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers = DocumentController.class)
//class DocumentControllerTest {
//
//    private Document document;
//    private DocumentDtoRequest documentDto;
//    private DocumentDtoRequest documentDto;
//    private String jsonRequest;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private DocumentService documentService;
//
//    @MockitoBean
//    private DocumentMapper documentMapper;
//
//    @BeforeEach
//    void setUp() {
//        document = new Document();
//        document.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
//        document.setDesignation("ГОСТ 34286-2017");
//        document.setCodeOKS("13.340.10");
//        document.setActivityField("Защитная одежда");
//        document.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
//        document.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
//                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
//                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
//                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
//                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
//        document.setContentLink("https://www.google.com");
//        document.setAcceptanceYear(2017);
//        document.setCommissionYear(2019);
//        document.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
//                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
//                "структуры, показатель противоосколочной стойкости защитной структуры");
//        document.setStatus(StatusEnum.REPLACED);
//        document.setAdoptionLevel(AdoptionLevelEnum.NATIONAL);
//        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.FIRST_TIME);
//        document.setReferences(new HashSet<>() {{
//            add("ГОСТ 28653—90");
//            add("ГОСТ 3722—2014");
//        }});
//
//        documentDto = new DocumentDtoRequest();
//        documentDto.setFullName("БРОНЕОДЕЖДА Классификация и общие технические требования");
//        documentDto.setDesignation("ГОСТ 34286-2017");
//        documentDto.setCodeOKS("13.340.10");
//        documentDto.setActivityField("Защитная одежда");
//        documentDto.setAuthor("МТК 391 «Средства физической защиты и материалы для их изготовления");
//        documentDto.setApplicationArea("Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты " +
//                "туловища и конечностей человека (за исключением стоп ног и кистей рук) (далее — человека) от " +
//                "воздействия холодного и огнестрельного стрелкового оружия, а также поражения осколками." +
//                "Стандарт устанавливает классификацию бронеодежды и общие технические требования к ней, " +
//                "необходимые для разработки, изготовления и испытаний соответствующей продукции");
//        documentDto.setContentLink("https://www.google.com");
//        documentDto.setAcceptanceYear(2017);
//        documentDto.setCommissionYear(2019);
//        documentDto.setKeyWords("Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, " +
//                "класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной " +
//                "структуры, показатель противоосколочной стойкости защитной структуры");
//        documentDto.setStatus("Заменён");
//        documentDto.setAdoptionLevel("Национальный");
//        documentDto.setAcceptedFirstTimeOrReplaced("ВВЕДЁН ВПЕРВЫЕ");
//        documentDto.setReferences(new HashSet<>() {{
//            add("ГОСТ 28653—90");
//            add("ГОСТ 3722—2014");
//        }});
//        jsonRequest = """
//                {
//                    "designation": "ГОСТ 34286-2017",
//                    "fullName": "БРОНЕОДЕЖДА Классификация и общие технические требования",
//                    "codeOKS": "13.340.10",
//                    "activityField": "Защитная одежда",
//                    "acceptanceYear": 2017,
//                    "commissionYear": 2019,
//                    "author": "МТК 391 «Средства физической защиты и материалы для их изготовления",
//                    "acceptedFirstTimeOrReplaced": "ВВЕДЕН ВПЕРВЫЕ",
//                    "content": "Здесь будет содержание, в примере опустим из-за объёма",
//                    "keyWords": "Ключевые слова: бронеодежда, холодное оружие, стрелковое оружие, защитная структура, класс защитной структуры, заброневое воздействие поражающего элемента при непробитии защитной структу ры, показатель противоосколочной стойкости защитной структуры",
//                    "applicationArea": "Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты туловища и конечностей человека (за исключением стоп ног и кистей рук)...",
//                    "adoptionLevel": "Национальный",
//                    "contentLink": "Изначально тут будет ссылка на файл госта на гуглдиске компании",
//                    "status": "Актуальный",
//                    "harmonization": "Не гармонизированный",
//                    "references": [
//                        "ГОСТ 3722—2014",
//                        "ГОСТ 28653—90"
//                    ]
//                }
//                """;
//    }
//
//    @Test
//    void addDocumentShouldReturnAddedDocumentId() throws Exception {
//        Document savedDocument = new Document();
//        savedDocument.setId(1L);
//
//        Mockito.when(documentMapper.mapToEntity(documentDto)).thenReturn(document);
//        Mockito.when(documentService.saveDocument(document)).thenReturn(savedDocument);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/standards/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.id").value(1));
//    }
//
//    @Test
//    void addExistingDocumentShouldReturnException() throws Exception {
//        Mockito.when(documentService.saveDocument(document)).thenThrow(EntityExistsException.class);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/standards/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isConflict())
//                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.message").value("Такой гост уже существует."));
//    }
//
//    @Test
//    void getDocumentShouldReturnDocument() throws Exception {
//        Mockito.when(documentService.getDocumentById(1L)).thenReturn(document);
//        Mockito.when(documentMapper.mapEntityToDto(document)).thenReturn(documentDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/standards/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.id").value(1));
//    }
//}
