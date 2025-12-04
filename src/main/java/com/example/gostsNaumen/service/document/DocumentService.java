package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с гостами в БД
 */
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Сохранение сущности ГОСТа в БД
     *
     * @param documentForSave Сущность из БД
     * @return сохраненная сущность в бд
     */
    @Transactional
    public Document saveDocument(Document documentForSave) throws BusinessException {

        Optional<Document> interferingDocument = documentRepository
                .findByFullNameAndStatus(documentForSave.getFullName(), StatusEnum.CURRENT);

        if (interferingDocument.isPresent()) {
            Long interferingDocumentId = interferingDocument.get().getId();
            throw new BusinessException(
                    ErrorCode.STANDARD_BY_NAME_WITH_CURRENT_STATUS_ALREADY_EXIST,
                    "Документ с таким fullName: %s и статусом \"Актуален\" уже существует. Пожалуйста, измените его статус перед сохранением новой версии. ID документа: %d"
                            .formatted(documentForSave.getFullName(), interferingDocumentId)

            );
        }

        return documentRepository.save(documentForSave);
    }

    /**
     * Находит ГОСТ по ID
     *
     * @param id id ГОСТа
     * @return найденный по ID ГОСТ
     */
    @Transactional
    public Document getDocumentById(Long id) throws BusinessException {
        if (id == null) {
            throw new IllegalArgumentException("Поиск по пустому ID");
        }

        return documentRepository
                .findById(id).orElseThrow(() -> new BusinessException(
                        ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                        String.format("По переданному ID: %s, нет стандарта", id)));
    }

    /**
     * Поиск документов в БД по фильтрам
     *
     * @param specification фильтры
     * @return список сущностей найденных по фильтрам
     */
    @Transactional(readOnly = true)
    public List<Document> getAllDocumentsByFilters(Specification<Document> specification) {
        return documentRepository.findAll(specification);
    }

    /**
     * Удаление ГОСТа по Id
     *
     * @param id id ГОСТа
     */
    @Transactional
    public void deleteDocumentById(Long id) throws BusinessException {
        if (id == null) {
            throw new IllegalArgumentException("Получен null id");
        }
        if (!documentRepository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                    String.format("По переданному ID: %s, нет стандарта", id)
            );
        }
        documentRepository.deleteById(id);
    }

    /**
     * Метод для обновления полей ГОСТа
     *
     * @param document документ с уже обновлёнными полями, которые нужно сохранить
     * @return {@code document} – обновлённый документ
     */
    @Transactional
    public Document updateDocument(Document document) throws BusinessException {

        Long id = document.getId();

        if (!documentRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS);
        }

        return documentRepository.save(document);
    }

    /**
     * Получение всех ГОСТов с БД
     *
     * @return список сущностей ГОСТов с БД
     */
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
}