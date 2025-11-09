package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.ActualizeDocumentMapper;
import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentIdStatusDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.controller.dto.response.GostIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
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
    private final RusEngEnumConverter rusEngEnumConverter;
    private final ActualizeDocumentMapper actualizeDocumentMapper;

    public DocumentController(
            DocumentService documentService,
            DocumentMapper documentMapper,
            RusEngEnumConverter rusEngEnumConverter,
            ActualizeDocumentMapper actualizeDocumentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        this.rusEngEnumConverter = rusEngEnumConverter;
        this.actualizeDocumentMapper = actualizeDocumentMapper;
    }

    /**
     * Добавление нового ГОСТа
     *
     * @param documentDtoRequest ДТО ГОСТа
     * @return id успешно добавленного ГОСТа
     */
    @PostMapping("/add")
    public GostIdDtoResponse addDocument(
            @RequestBody @Valid DocumentDtoRequest documentDtoRequest
    ) {
        Document document = documentMapper.mapToEntity(documentDtoRequest);

        return new GostIdDtoResponse(documentService.saveDocument(document).getId());
    }

    /**
     * Получение ГОСТа по ID
     *
     * @param docId ID приходящий в запросе
     * @return ДТО ГОСТа
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
    @DeleteMapping("/delete/{docId}")
    public void deleteDocument(@PathVariable Long docId) {
        documentService.deleteDocumentById(docId);
    }

    /**
     * Обновление статуса госта
     *
     * @param documentIdStatusDtoRequest содержит новый статус и id госта
     * @return дто госта с новым статусом
     */
    @PatchMapping("/change-status")
    public DocumentDtoResponse changeDocumentStatus(
            @RequestBody @Valid DocumentIdStatusDtoRequest documentIdStatusDtoRequest
    ) {
        Document document = documentService.getDocumentById(documentIdStatusDtoRequest.getId());
        StatusEnum status = rusEngEnumConverter.convertToEnglishValue(
                documentIdStatusDtoRequest.getStatus(), StatusEnum.class);

        Document updatedDocument = documentService.updateDocumentStatus(document, status);
        return documentMapper.mapEntityToDto(updatedDocument);
    }

    /**
     * Метод для обновления полей документа
     *
     * @param docId            идентификатор госта
     * @param dtoWithNewValues дто, содержащее новые значения полей
     * @return обновлённое дто госта
     */
    @PatchMapping("/actualize/{docId}")
    public DocumentDtoResponse updateDocument(
            @PathVariable Long docId,
            @RequestBody @Valid ActualizeDtoRequest dtoWithNewValues
    ) {
        Document oldDocument = documentService.getDocumentById(docId);
        Document documentWithNewFieldsValues = actualizeDocumentMapper.setNewValues(oldDocument, dtoWithNewValues);

        return documentMapper.mapEntityToDto(documentService.updateDocument(documentWithNewFieldsValues));
    }
}