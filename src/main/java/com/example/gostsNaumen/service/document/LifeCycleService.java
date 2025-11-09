package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.stereotype.Service;

/**
 * Заготовка под сервис управления жизненным циклом гостов.
 */
@Service
public class LifeCycleService {
    //TODO реализовать полноценный переход по жизненному циклу

    /**
     * Метод для проверки возможности обновления статуса госта
     *
     * @param currentStatus текущий статус госта
     * @param newStatus     новый статус госта
     * @return {@code true} если переход возможен, {@code false} если невозможен
     */
    public boolean canUpdateStatus(StatusEnum currentStatus, StatusEnum newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }
        return switch (currentStatus) {
            case StatusEnum.CURRENT -> newStatus == StatusEnum.CANCELED || newStatus == StatusEnum.REPLACED;
            case StatusEnum.CANCELED -> newStatus == StatusEnum.CURRENT;
            case StatusEnum.REPLACED -> newStatus == StatusEnum.CURRENT;
        };
    }
}
