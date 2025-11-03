package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.TwoWaysMapper;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.converter.AdoptionLevelConverter;
import com.example.gostsNaumen.entity.model.converter.HarmonizationLevelConverter;
import com.example.gostsNaumen.entity.model.converter.StatusConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Преобразует сущность госта из БД в сущность
 */
@Component
public class DocumentMapper implements TwoWaysMapper<Document, DocumentDtoRequest, DocumentDtoResponse> {

    private final StatusConverter statusConverter;
    private final HarmonizationLevelConverter harmonizationLevelConverter;
    private final AdoptionLevelConverter adoptionLevelConverter;


    @Autowired
    public DocumentMapper(
            StatusConverter statusConverter,
            HarmonizationLevelConverter harmonizationLevelConverter,
            AdoptionLevelConverter adoptionConverter
    ) {
        this.statusConverter = statusConverter;
        this.harmonizationLevelConverter = harmonizationLevelConverter;
        this.adoptionLevelConverter = adoptionConverter;
    }

    @Override
    public Document mapToEntity(DocumentDtoRequest dto) {
        return new Document().setFullName(dto.getFullName())
                .setDesignation(dto.getDesignation())
                .setCodeOKS(dto.getCodeOKS())
                .setActivityField(dto.getActivityField())
                .setAuthor(dto.getAuthor())
                .setApplicationArea(dto.getApplicationArea())
                .setContentLink(dto.getContentLink())
                .setAcceptanceYear(dto.getAcceptanceYear())
                .setCommissionYear(dto.getCommissionYear())
                .setKeyWords(dto.getKeyWords())
                //Контр интуитивно, но интерфейс предлагает именно такие названия, хотя по-хорошему тут нужно название
                //наоборот: convertToDatabaseColumn, потому что из русского атрибута мы получаем значение enum-а,
                //которое хранится в базе данных
                .setAdoptionLevel(adoptionLevelConverter.convertToEntityAttribute(dto.getAdoptionLevel()))
                .setStatus(statusConverter.convertToEntityAttribute(dto.getStatus()))
                .setHarmonization(harmonizationLevelConverter.convertToEntityAttribute(dto.getHarmonization()))
                .setReferences(dto.getReferences());
    }

    @Override
    public DocumentDtoResponse mapEntityToDto(Document fromWhat) {
        return new DocumentDtoResponse()
                .setId(fromWhat.getId())
                .setFullName(fromWhat.getFullName())
                .setDescription(fromWhat.getDesignation())
                .setCodeOKS(fromWhat.getCodeOKS())
                .setActivityField(fromWhat.getActivityField())
                .setAuthor(fromWhat.getAuthor())
                .setApplicationArea(fromWhat.getApplicationArea())
                .setContentLink(fromWhat.getContentLink())
                .setAcceptanceYear(fromWhat.getAcceptanceYear())
                .setCommissionYear(fromWhat.getCommissionYear())
                .setKeyWords(fromWhat.getKeyWords())
                //Аналогично верхнему случаю
                .setAdoptionLevel(adoptionLevelConverter.convertToDatabaseColumn(fromWhat.getAdoptionLevel()))
                .setStatus(statusConverter.convertToDatabaseColumn(fromWhat.getStatus()))
                .setHarmonization(harmonizationLevelConverter.convertToDatabaseColumn(fromWhat.getHarmonization()))
                .setReferences(fromWhat.getReferences());
    }
}