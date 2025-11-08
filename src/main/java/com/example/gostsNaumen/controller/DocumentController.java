package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentIdStatusDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.controller.dto.response.GostIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.TwoWaysConverter;
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
    private final TwoWaysConverter twoWaysConverter;

    public DocumentController(DocumentService documentService, DocumentMapper documentMapper, TwoWaysConverter twoWaysConverter) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        this.twoWaysConverter = twoWaysConverter;
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
        StatusEnum status = twoWaysConverter.convertToDatabaseColumn(
                documentIdStatusDtoRequest.getStatus(), StatusEnum.class);

        Document updatedDocument = documentService.updateDocumentStatus(document, status);
        return documentMapper.mapEntityToDto(updatedDocument);
    }

    /**
     * Обновление полей госта
     *
     * @param docId              идентификатор госта
     * @param documentDtoRequest дто, содержащее новые значения полей
     * @return обновлённое дто госта
     */
    @PatchMapping("/actualize/{docId}")
    public DocumentDtoResponse actualizeDocument(
            @PathVariable Long docId,
            @RequestBody @Valid ActualizeDtoRequest documentDtoRequest
    ) {
        Document actualizedDocument = documentService.actualizeDocument(docId, documentDtoRequest);
        return documentMapper.mapEntityToDto(actualizedDocument);
    }
}