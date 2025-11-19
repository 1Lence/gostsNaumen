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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Получение всех ГОСТов из БД
     *
     * @return список с ДТО ГОСТов
     */
    @GetMapping("/documents")
    @PreAuthorize("hasAuthority('user:read')")
    public List<DocumentDtoResponse> getAll() {
        return documentService.findAll().stream().map(documentMapper::mapEntityToDto).toList();
    }

    /**
     * Добавление нового ГОСТа
     *
     * @param documentDtoRequest {@link DocumentDtoRequest} ДТО ГОСТа
     * @return id успешно добавленного ГОСТа
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('user:read')")
    public StandardIdDtoResponse addDocument(
            @RequestBody @Valid DocumentDtoRequest documentDtoRequest
    ) {
        Document document = documentMapper.createDocumentEntity(documentDtoRequest);

        return new StandardIdDtoResponse(documentService.saveDocument(document).getId());
    }

    /**
     * Получение ГОСТа по ID
     *
     * @param docId ID приходящий в запросе
     * @return ДТО ГОСТа
     */
    @GetMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:read')")
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
    @PreAuthorize("hasAuthority('user:write')")
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
    @PreAuthorize("hasAuthority('user:write')")
    public DocumentDtoResponse updateDocument(
            @PathVariable Long docId,
            @RequestBody @Valid ActualizeDtoRequest dtoWithNewValues
    ) {
        Document oldDocument = documentService.getDocumentById(docId);
        Document documentWithNewFieldsValues = documentFieldsActualizer.setNewValues(oldDocument, dtoWithNewValues);

        return documentMapper.mapEntityToDto(documentService.updateDocument(documentWithNewFieldsValues));
    }
}