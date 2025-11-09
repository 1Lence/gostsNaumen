package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис по работе с гостами в БД
 */
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final LifeCycleService lifeCycleService;

    public DocumentService(DocumentRepository documentRepository, LifeCycleService lifeCycleService) {
        this.documentRepository = documentRepository;
        this.lifeCycleService = lifeCycleService;
    }

    /**
     * Сохранение сущности ГОСТа в БД
     *
     * @param documentForSave Сущность из БД
     * @return сохраненная сущность в бд
     */
    @Transactional
    public Document saveDocument(Document documentForSave) {

        if (documentRepository.findByFullName(documentForSave.getFullName()).isPresent()) {
            throw new EntityExistsException("Такой гост уже существует.");
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
    public Document getDocumentById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("Поиск по пустому ID");
        }

        return documentRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Документ по ID: " + id + " не найден."));
    }

    /**
     * Удаление ГОСТа по Id
     *
     * @param id id ГОСТа
     */
    @Transactional
    public void deleteDocumentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Получен null id");
        }
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Документа с таким " + id + " не существует");
        }
        documentRepository.deleteById(id);
    }

    /**
     * Метод для обновления статуса ГОСТа
     *
     * @param documentForUpdate документ, у которого будет изменён статус
     * @param status            новый статус документа {@link StatusEnum}
     */
    @Transactional
    public Document updateDocumentStatus(Document documentForUpdate, StatusEnum status) {
        if (documentForUpdate.getStatus().equals(status)) {
            throw new BusinessException(ErrorCode.STATUS_ALREADY_SET);
        }
        if (!lifeCycleService.canUpdateStatus(documentForUpdate.getStatus(), status)) {
            throw new BusinessException(ErrorCode.INVALID_LIFECYCLE_TRANSITION);
        }
        documentForUpdate.setStatus(status);
        return documentForUpdate;
    }

    /**
     * Метод для обновления полей ГОСТа
     *
     * @param document документ с уже обновлёнными полями, которые нужно сохранить
     * @return {@code document} – обновлённый документ
     */
    @Transactional
    public Document updateDocument(Document document) {

        Long id = document.getId();

        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Документа с таким id: " + id + ", не существует");
        }

        return documentRepository.save(document);
    }
}