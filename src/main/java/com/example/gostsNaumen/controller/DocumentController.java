package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.DocumentFieldsActualizer;
import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.controller.dto.response.StandardIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.service.document.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер по работе с гостами
 */
@RestController
@RequestMapping("/api/standards")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentMapper documentMapper;
    private final DocumentFieldsActualizer documentFieldsActualizer;

    public DocumentController(
            DocumentService documentService,
            DocumentMapper documentMapper,
            DocumentFieldsActualizer documentFieldsActualizer) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        this.documentFieldsActualizer = documentFieldsActualizer;
    }

    /**
     * Добавление нового ГОСТа
     *
     * @param documentDtoRequest {@link DocumentDtoRequest} ДТО ГОСТа
     * @return <ul><li>В случае успешного добавления возвращается {"id":{@code id госта}}</li>
     * <li>В случае попытки добавления существующего стандарта возвращается
     * {@link com.example.gostsNaumen.handler.ErrorResponse} с кодом {@code 409 CONFLICT}</li>
     * <li>В случае некорректных полей возвращается {@link com.example.gostsNaumen.handler.ErrorResponse}
     * с перечислением ошибок валидации</li>
     * </ul>
     */
    @PostMapping()
    public StandardIdDtoResponse addDocument(
            @RequestBody @Valid DocumentDtoRequest documentDtoRequest
    ) {
        Document document = documentMapper.mapToEntity(documentDtoRequest);

        return new StandardIdDtoResponse(documentService.saveDocument(document).getId());
    }

    /**
     * Получение ГОСТа по ID
     *
     * @param docId ID приходящий в запросе
     * @return <ul>
     * <li>В случае успешного поиска документа по id, возвращает {@link DocumentDtoResponse}</li>
     * <li>В случае, если документ не был найден, возвращается {@link com.example.gostsNaumen.handler.ErrorResponse}
     * с кодом {@code 404 NOT FOUND}</li>
     * <li>В случае передачи некорректного {@code /{docId}} возвращается
     * {@link com.example.gostsNaumen.handler.ErrorResponse} с кодом {@code 400 BAD REQUEST}</li>
     * </ul>
     */
    @GetMapping("/{docId}")
    public DocumentDtoResponse getDocument(@PathVariable Long docId) {
        Document document = documentService.getDocumentById(docId);

        return documentMapper.mapEntityToDto(document);
    }

    /**
     * Удаление ГОСТа по ID
     *
     * @param docId id Документа
     */
    @DeleteMapping("/{docId}")
    public void deleteDocument(@PathVariable Long docId) {
        documentService.deleteDocumentById(docId);
    }

    /**
     * Метод для обновления полей документа
     *
     * @param docId            идентификатор госта
     * @param dtoWithNewValues дто, содержащее новые значения полей
     * @return обновлённое дто госта
     */
    @PatchMapping("/{docId}")
    public DocumentDtoResponse updateDocument(
            @PathVariable Long docId,
            @RequestBody @Valid ActualizeDtoRequest dtoWithNewValues
    ) {
        Document oldDocument = documentService.getDocumentById(docId);
        Document documentWithNewFieldsValues = documentFieldsActualizer.setNewValues(oldDocument, dtoWithNewValues);

        return documentMapper.mapEntityToDto(documentService.updateDocument(documentWithNewFieldsValues));
    }
}