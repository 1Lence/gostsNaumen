package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.dto.request.ActualizeDtoRequest;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Класс, служащий для актуализации документа (по-сути метод Update)
 */
@Component
public class DocumentFieldsActualizer {

    private final RusEngEnumConverter rusEngEnumConverter = new RusEngEnumConverter();

    /**
     * Актуализирует поля стандарта
     *
     * @param document оригинальный документ
     * @param dto      правки в документе
     * @return document обновлённая сущность документа
     */
    public Document setNewValues(Document document, ActualizeDtoRequest dto) {
        setIfNotEmpty(dto.getFullName(), document::setFullName);
        setIfNotEmpty(dto.getDesignation(), document::setDesignation);
        setIfNotEmpty(dto.getCodeOKS(), document::setCodeOKS);
        setIfNotEmpty(dto.getActivityField(), document::setActivityField);
        setIfNotEmpty(dto.getAuthor(), document::setAuthor);
        setIfNotEmpty(dto.getApplicationArea(), document::setApplicationArea);
        setIfNotEmpty(dto.getContentLink(), document::setContentLink);
        setIfNotEmpty(dto.getAcceptanceYear(), document::setAcceptanceYear);
        setIfNotEmpty(dto.getCommissionYear(), document::setCommissionYear);
        setIfNotEmpty(dto.getKeyWords(), document::setKeyWords);
        setIfNotEmpty(dto.getAdoptionLevel(), val -> rusEngEnumConverter.convertToEnglishValue(
                dto.getAdoptionLevel(), AdoptionLevelEnum.class));
        setIfNotEmpty(dto.getHarmonization(), val -> rusEngEnumConverter.convertToEnglishValue(
                dto.getHarmonization(), HarmonizationEnum.class));
        setIfNotEmpty(dto.getStatus(), val -> rusEngEnumConverter.convertToEnglishValue(
                dto.getStatus(), StatusEnum.class));

        document.setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum.REPLACED);
        setIfNotEmpty(dto.getReferences(), document::setReferences);

        return document;
    }

    /**
     * Вспомогательный метод, служащий для возможного обновления параметров,
     * если в дто присутствует новое значение поля
     *
     * @param value  новое значение
     * @param setter метод для установки нового значения
     */
    private <T> void setIfNotEmpty(T value, Consumer<T> setter) {
        switch (value) {
            case null -> {
                return;
            }
            case String s when s.isEmpty() -> {
                return;
            }
            case Collection<?> c when c.isEmpty() -> {
                return;
            }
            default -> {
            }
        }

        setter.accept(value);
    }
}
