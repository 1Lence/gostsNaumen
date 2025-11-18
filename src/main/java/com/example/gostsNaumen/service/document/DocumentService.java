package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new BusinessException(ErrorCode.STANDARD_EXIST_BY_FULL_NAME,
                    "Гост c таким full_name: " + documentForSave.getFullName() + " уже существует!");
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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный аргумент: " + id);
        }

        return documentRepository
                .findById(id).orElseThrow(() -> new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS,
                        "По переданному id: %s нет стандарта".formatted(id)));
    }

    /**
     * Удаление ГОСТа по Id
     *
     * @param id id ГОСТа
     */
    @Transactional
    public void deleteDocumentById(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Некорректный аргумент: " + id);
        }
        if (!documentRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS, id);
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
    public Document updateDocument(Document document) {

        Long id = document.getId();

        if (!documentRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS);
        }

        return documentRepository.save(document);
    }
}