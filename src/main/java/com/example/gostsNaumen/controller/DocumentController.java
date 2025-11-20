package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.DocumentFieldsActualizer;
import com.example.gostsNaumen.controller.dto.DocumentMapper;
import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.request.FilterDtoRequest;
import com.example.gostsNaumen.controller.dto.request.FilterDtoRequest;
import com.example.gostsNaumen.controller.dto.request.NewStatusDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.controller.dto.response.StandardIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.CustomEntityNotFoundException;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
import com.example.gostsNaumen.repository.specification.DocumentSpecificationMapper;
import com.example.gostsNaumen.service.document.DocumentLifeCycleService;
import com.example.gostsNaumen.exception.EntityNotFoundException;
import com.example.gostsNaumen.repository.specification.DocumentSpecificationMapper;
import com.example.gostsNaumen.service.document.DocumentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
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
    private final DocumentLifeCycleService documentLifeCycleService;
    private final DocumentSpecificationMapper documentSpecificationMapper;
    private final RusEngEnumConverter rusEngEnumConverter;

    public DocumentController(
            DocumentService documentService,
            DocumentMapper documentMapper,
            DocumentFieldsActualizer documentFieldsActualizer,
            DocumentSpecificationMapper documentSpecificationMapper,
            RusEngEnumConverter rusEngEnumConverter,
            DocumentLifeCycleService documentLifeCycleService) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
        this.documentFieldsActualizer = documentFieldsActualizer;
        this.documentSpecificationMapper = documentSpecificationMapper;
        this.rusEngEnumConverter = rusEngEnumConverter;
        this.documentLifeCycleService = documentLifeCycleService;
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
     * Добавление нового ГОСТа, в случае попытки замены существующего ГОСТ-а со статусом "Актуальный" новым документом
     * так же со статусом "Актуальный", вылетит ошибка, предупреждающая о необходимости отправить старый документ в
     * "Заменённый"
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
     * Поиск ГОСТ-ов по необходимым фильтрам.
     *
     * @param filterDtoRequest JSON с фильтрами
     * @return список DTO найденных по фильтрам
     */
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentDtoResponse> getAllDocumentsByFilters(FilterDtoRequest filterDtoRequest) {
        Specification<Document> specification = documentSpecificationMapper.mapFullSpecification(filterDtoRequest);

        List<Document> documentList = documentService.getAllDocumentsByFilters(specification);

        return documentList.stream().map(documentMapper::mapEntityToDto).toList();
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
                () -> new CustomEntityNotFoundException("По id - %d документ не найден!".formatted(docId))
        );

        return documentMapper.mapEntityToDto(document);
    }

    /**
     * Поиск ГОСТ-ов по необходимым фильтрам.
     *
     * @param filterDtoRequest JSON с фильтрами
     * @return список DTO найденных по фильтрам
     */
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentDtoResponse> getAllDocumentsByFilters(FilterDtoRequest filterDtoRequest) {
        Specification<Document> specification = documentSpecificationMapper.mapFullSpecification(filterDtoRequest);

        List<Document> documentList = documentService.getAllDocumentsByFilters(specification);

        return documentList.stream().map(documentMapper::mapEntityToDto).toList();
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
                () -> new CustomEntityNotFoundException("По id - %d документ не найден!".formatted(docId))
        );
        Document documentWithNewFieldsValues = documentFieldsActualizer.setNewValues(oldDocument, dtoWithNewValues);

        return documentMapper.mapEntityToDto(documentService.updateDocument(documentWithNewFieldsValues));
    }

    /**
     * Метод для обновления статуса документа
     *
     * @param docId     идентификатор документа, у которого мы меняем статус
     * @param newStatus новый статус документа
     * @return <ul>
     *     <li>В случае успеха возвращает {@link DocumentDtoResponse} с обновлённым статусом</li>
     *     <li>В случае ошибки возвращается {@link com.example.gostsNaumen.handler.ErrorResponse} с причиной ошибки</li>
     * </ul>
     */
    //TODO написать тесты
    @PatchMapping("/{docId}/status")
    @PreAuthorize("hasAuthority('user:write')")
    public DocumentDtoResponse updateDocumentStatus(
            @PathVariable Long docId,
            @RequestBody @Valid NewStatusDtoRequest newStatus
    ) {
        Document documentToUpdate = documentService.getDocumentById(docId);
        Document documentWithNewStatus = documentLifeCycleService.doLifeCycleTransition(
                documentToUpdate,
                rusEngEnumConverter.convertToEnglishValue(newStatus.newStatus(), StatusEnum.class));

        return documentMapper.mapEntityToDto(documentWithNewStatus);
    }

}