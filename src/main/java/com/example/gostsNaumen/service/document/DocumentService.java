package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.repository.DocumentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * Удаление госта по Id
     *
     * @param id id ГОСТа
     */
    @Transactional
    public void deleteDocumentById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("Удаление по пустому ID");
        }
        documentRepository.deleteById(id);
    }
}