package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.dto.DocumentMapper;
import com.example.gostsNaumen.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
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
    private final DocumentMapper documentMapper;
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentMapper documentMapper, DocumentRepository documentRepository) {
        this.documentMapper = documentMapper;
        this.documentRepository = documentRepository;
    }

    /**
     * Сохранение сущности ГОСТа в БД
     *
     * @param documentDtoRequest Дто с данными о документе
     * @return сохраненная сущность в бд
     */
    @Transactional
    public DocumentDtoResponse saveDocument(DocumentDtoRequest documentDtoRequest) {

        if (documentRepository.findByFullName(documentDtoRequest.getFullName()).isPresent()) {
            throw new EntityExistsException("Такой гост уже существует.");
        }

        Document document = documentMapper.mapToEntity(documentDtoRequest);

        return documentMapper.mapToDto(documentRepository.save(document));
    }

    /**
     * Находит ГОСТ по ID
     *
     * @param id id ГОСТа
     * @return найденный по ID ГОСТ
     */
    @Transactional(readOnly = true)
    public DocumentDtoResponse getDocumentById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("Поиск по пустому ID");
        }

        Document document = documentRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Документ по ID: " + id + " не найден."));

        return documentMapper.mapToDto(document);
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