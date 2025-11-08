package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.TwoWaysConverter;
import org.springframework.stereotype.Component;

/**
 * Класс, служащий для актуализации документа (по-сути метод Update)
 */
@Component
public class ActualizeDocumentMapper {

    private final TwoWaysConverter twoWaysConverter = new TwoWaysConverter();

    /**
     * Актуализирует поля стандарта
     *
     * @param document оригинальный документ
     * @param dto      правки в документе
     * @return document обновлённая сущность документа
     */
    public Document actualizeDocument(Document document, ActualizeDtoRequest dto) {
        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            document.setFullName(dto.getFullName());
        }
        if (dto.getDesignation() != null && !dto.getDesignation().isEmpty()) {
            document.setDesignation(dto.getDesignation());
        }
        if (dto.getCodeOKS() != null && !dto.getCodeOKS().isEmpty()) {
            document.setCodeOKS(dto.getCodeOKS());
        }
        if (dto.getActivityField() != null && !dto.getActivityField().isEmpty()) {
            document.setActivityField(dto.getActivityField());
        }
        if (dto.getAuthor() != null && !dto.getAuthor().isEmpty()) {
            document.setAuthor(dto.getAuthor());
        }
        if (dto.getApplicationArea() != null && !dto.getApplicationArea().isEmpty()) {
            document.setApplicationArea(dto.getApplicationArea());
        }
        if (dto.getContentLink() != null && !dto.getContentLink().isEmpty()) {
            document.setContentLink(dto.getContentLink());
        }
        if (dto.getAcceptanceYear() != null) {
            document.setAcceptanceYear(dto.getAcceptanceYear());
        }
        if (dto.getCommissionYear() != null) {
            document.setCommissionYear(dto.getCommissionYear());
        }
        if (dto.getKeyWords() != null && !dto.getKeyWords().isEmpty()) {
            document.setKeyWords(dto.getKeyWords());
        }
        if (dto.getAdoptionLevel() != null) {
            document.setAdoptionLevel(twoWaysConverter.convertToDatabaseColumn(dto.getAdoptionLevel(), AdoptionLevelEnum.class));
        }
        if (dto.getStatus() != null) {
            document.setStatus(twoWaysConverter.convertToDatabaseColumn(dto.getStatus(), StatusEnum.class));
        }
        if (dto.getHarmonization() != null) {
            document.setHarmonization(twoWaysConverter.convertToDatabaseColumn(dto.getHarmonization(), HarmonizationEnum.class));
        }

        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.REPLACED);

        if (dto.getReferences() != null && !dto.getReferences().isEmpty()) {
            document.setReferences(dto.getReferences());
        }

        return document;
    }

}
