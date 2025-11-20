package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentLifeCycleService {
    private final DocumentRepository documentRepository;

    public DocumentLifeCycleService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document doLifeCycleTransition(Document document, StatusEnum status) {

        if (!isTransitionAllowed(document.getStatus(), status)) {
            throw new BusinessException(
                    ErrorCode.INCORRECT_LIFECYCLE_TRANSITION,
                    "Переход из статуса %s в статус %s невозможен".formatted(document.getStatus(), status));
        }

        checkInterferingDocuments(document, status);

        document.setStatus(status);
        return documentRepository.save(document);
    }

    /**
     * Вспомогательный метод, необходим для проверки возникновения конфликтов в связи с изменением документа в базе данных.
     *
     * @param document документ, у которого хотят поменять статус
     */
    private void checkInterferingDocuments(Document document, StatusEnum targetStatus) {
        Optional<Document> interferingDocument = documentRepository.findByFullNameAndStatus(
                document.getFullName(), StatusEnum.CURRENT);

        if (interferingDocument.isPresent()) {
            if (Objects.equals(interferingDocument.get().getId(), document.getId())) {
                return;
            }
        }

        switch (targetStatus) {
            case CURRENT:
                if (interferingDocument.isPresent()) {
                    throw new BusinessException(
                            ErrorCode.OTHER_DOC_INTERFERES_WITH_TRANSITION,
                            "Другой документ не позволяет изменить статус текущего документа его id: "
                                    + interferingDocument.get().getId().toString());

                }
            case REPLACED:
        }

    }


    /**
     * Вспомогательный метод, необходимый для проверки возможности совершения транзакции на логическом уровне
     *
     * @param oldStatus старый статус документа
     * @param newStatus новый статус документа
     * @return <ul>
     * <li>{@code true} если переход возможен</li>
     * <li>{@code false} если переход невозможен</li>
     * </ul>
     */
    public boolean isTransitionAllowed(StatusEnum oldStatus, StatusEnum newStatus) {
        if (oldStatus == null || newStatus == null) return false;

        switch (oldStatus) {
            case CURRENT -> {
                return newStatus == StatusEnum.REPLACED
                        || newStatus == StatusEnum.CANCELED;
            }
            case REPLACED, CANCELED -> {
                return newStatus == StatusEnum.CURRENT;
            }
            default -> {
                return false;
            }
        }
    }
}

