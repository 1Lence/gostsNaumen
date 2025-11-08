package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.stereotype.Service;

@Service
public class LifeCycleService {
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
