package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.CustomEntityExistsException;
import com.example.gostsNaumen.exception.CustomEntityNotFoundException;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.stereotype.Service;

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
     * @throws CustomEntityExistsException при существовании госта по полному имени выбрасывается ошибка
     * @return сохраненная сущность в бд
     */
    public Document saveDocument(Document documentForSave) {

        if (documentRepository.findByFullName(documentForSave.getFullName()).isPresent()) {
            throw new CustomEntityExistsException(
                    "Гост c таким full_name: " + documentForSave.getFullName() + " уже существует!");
        }

        return documentRepository.save(documentForSave);
    }

    /**
     * Находит ГОСТ по ID
     *
     * @param id id ГОСТа
     * @throws IllegalArgumentException если получен id null
     * @return найденный по ID ГОСТ
     */
    public Optional<Document> getDocumentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Некорректный аргумент: " + id);
        }

        return documentRepository
                .findById(id);
    }

    /**
     * Удаление ГОСТа по Id
     *
     * @param id id ГОСТа
     * @throws IllegalArgumentException если получен id null
     * @throws CustomEntityNotFoundException по переданному id нет стандарта
     */
    public void deleteDocumentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Получен null id");
        }
        if (!documentRepository.existsById(id)) {
            throw new CustomEntityNotFoundException(
                    String.format("По переданному ID: %s, нет стандарта", id)
            );
        }
        documentRepository.deleteById(id);
    }

    /**
     * Метод для обновления полей ГОСТа
     *
     * @param document документ с уже обновлёнными полями, которые нужно сохранить
     * @throws CustomEntityNotFoundException по переданному id нет стандарта
     * @return {@code document} – обновлённый документ
     */
    public Document updateDocument(Document document) {

        Long id = document.getId();

        if (!documentRepository.existsById(id)) {
            throw new CustomEntityNotFoundException(
                    String.format("По переданному ID: %s, нет стандарта", id));
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