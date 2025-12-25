package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.exception.LifeCycleException;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.stereotype.Service;


/**
 * Сервис необходимый для управления переходами документов по жизненному циклу
 */
@Service
public class DocumentLifeCycleService {
    /**
     * Репозиторий для работы с документами
     */
    private final DocumentRepository documentRepository;

    public DocumentLifeCycleService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Метод для совершения перехода по жизненному циклу документа.
     * Сначала проверяется возможность совершения перехода методом
     * {@link DocumentLifeCycleService#isTransitionAllowed(StatusEnum, StatusEnum)}, затем вызывается метод проверки
     * наложений {@link DocumentLifeCycleService#checkInterferingDocuments(Document, StatusEnum)},
     * который в случае наложения статусов выбрасывает ошибку, если ошибка не была выброшена,
     * то метод успешно меняет статус документа и возвращает обновлённый экземпляр документа.
     *
     * @param document     экземпляр документа, который совершает переход
     * @param targetStatus целевой статус
     * @return документ с новым статусом
     * @throws LifeCycleException если переход из одного статуса в другой невозможен
     */
    public Document doLifeCycleTransition(Document document, StatusEnum targetStatus) {

        if (!isTransitionAllowed(document.getStatus(), targetStatus)) {
            throw new LifeCycleException(
                    "Переход из статуса %s в статус %s невозможен".formatted(document.getStatus(), targetStatus));
        }

        checkInterferingDocuments(document, targetStatus);

        document.setStatus(targetStatus);
        return documentRepository.save(document);
    }

    /**
     * Вспомогательный метод, служащий для проверки появления наложений в случае перехода документа по циклу.
     * <ul>
     *     <li>В случае если наложений нет, метод ничего не возвращает</li>
     *     <li>Если есть проблемы с наложением, код выбрасывает ошибку {@link LifeCycleException}</li>
     * </ul>
     *
     * @param document     документ, у которого хотят поменять статус
     * @param targetStatus целевой статус, на который производится попытка замены
     * @throws LifeCycleException если переходу мешает другой документ
     */
    public void checkInterferingDocuments(Document document, StatusEnum targetStatus) {

        switch (targetStatus) {
            case CURRENT:
                Document interferingDocument = documentRepository.findByFullNameAndStatus(
                        document.getFullName(), StatusEnum.CURRENT).orElse(null);
                if (interferingDocument == null || interferingDocument.getId().equals(document.getId())) {
                    return;
                } else {
                    throw new LifeCycleException(
                            "Другой документ не позволяет изменить статус текущего документа, его id: "
                                    + interferingDocument.getId().toString());
                }
            case REPLACED, CANCELED:
        }
    }


    /**
     * Вспомогательный метод, необходимый для проверки возможности совершения транзакции на уровне текущего и
     * целевого статуса
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

