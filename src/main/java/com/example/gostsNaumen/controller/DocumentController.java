package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.DocumentFieldsActualizer;
import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.controller.dto.response.StandardIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.EntityNotFoundException;
import com.example.gostsNaumen.service.document.DocumentService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public List<DocumentDtoResponse> getAll() {
        return documentService.findAll().stream().map(documentMapper::mapEntityToDto).toList();
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
    @PreAuthorize("hasAuthority('user:read')")
    @Transactional
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
     * @return <ul>
     * <li>В случае успешного поиска документа по id, возвращает {@link DocumentDtoResponse}</li>
     * <li>В случае, если документ не был найден, возвращается {@link com.example.gostsNaumen.handler.ErrorResponse}
     * с кодом {@code 404 NOT FOUND}</li>
     * <li>В случае передачи некорректного {@code /{docId}} возвращается
     * {@link com.example.gostsNaumen.handler.ErrorResponse} с кодом {@code 400 BAD REQUEST}</li>
     * </ul>
     */
    @GetMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:read')")
    @Transactional
    public DocumentDtoResponse getDocument(@PathVariable Long docId) {
        Document document = documentService.getDocumentById(docId).orElseThrow(
                () -> new EntityNotFoundException("По id - %d документ не найден!".formatted(docId))
        );

        return documentMapper.mapEntityToDto(document);
    }

    /**
     * Удаление ГОСТа по ID
     * <p>
     * В случае успешного удаления возвращается HTTP-ответ со статусом 200 OK
     *
     * @param docId id Документа
     */
    @DeleteMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:write')")
    @Transactional
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
    @Transactional
    public DocumentDtoResponse updateDocument(
            @PathVariable Long docId,
            @RequestBody @Valid ActualizeDtoRequest dtoWithNewValues
    ) {
        Document oldDocument = documentService.getDocumentById(docId).orElseThrow(
                () -> new EntityNotFoundException("По id - %d документ не найден!".formatted(docId))
        );
        Document documentWithNewFieldsValues = documentFieldsActualizer.setNewValues(oldDocument, dtoWithNewValues);

        return documentMapper.mapEntityToDto(documentService.updateDocument(documentWithNewFieldsValues));
    }
}