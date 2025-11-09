package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.TwoWaysMapper;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.TwoWaysConverter;
import org.springframework.stereotype.Component;

/**
 * Преобразует сущность госта из БД в сущность
 */
@Component
public class DocumentMapper implements TwoWaysMapper<Document, DocumentDtoRequest, DocumentDtoResponse> {

    private final TwoWaysConverter twoWaysConverter;

    public DocumentMapper(TwoWaysConverter twoWaysConverter) {
        this.twoWaysConverter = twoWaysConverter;
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
                .setAdoptionLevel(twoWaysConverter.convertToDatabaseColumn(dto.getAdoptionLevel(), AdoptionLevelEnum.class))
                .setStatus(twoWaysConverter.convertToDatabaseColumn(dto.getStatus(), StatusEnum.class))
                .setHarmonization(twoWaysConverter.convertToDatabaseColumn(dto.getHarmonization(), HarmonizationEnum.class))
                .setAcceptedFirstTimeOrReplaced(twoWaysConverter.convertToDatabaseColumn(dto.getAcceptedFirstTimeOrReplaced(), AcceptedFirstTimeOrReplacedEnum.class))
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
                .setAdoptionLevel(twoWaysConverter.convertToEntityAttribute(fromWhat.getAdoptionLevel()))
                .setStatus(twoWaysConverter.convertToEntityAttribute(fromWhat.getStatus()))
                .setHarmonization(twoWaysConverter.convertToEntityAttribute(fromWhat.getHarmonization()))
                .setAcceptedFirstTimeOrReplaced(twoWaysConverter.convertToEntityAttribute(fromWhat.getAcceptedFirstTimeOrReplaced()))
                .setReferences(fromWhat.getReferences());
    }
}