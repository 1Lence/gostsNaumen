package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.TwoWaysMapper;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
import org.springframework.stereotype.Component;

/**
 * Преобразует сущность госта из БД в dto и наоборот
 */
@Component
public class DocumentMapper implements TwoWaysMapper<Document, DocumentDtoRequest, DocumentDtoResponse> {

    private final RusEngEnumConverter rusEngEnumConverter;

    public DocumentMapper(
            RusEngEnumConverter rusEngEnumConverter
    ) {
        this.rusEngEnumConverter = rusEngEnumConverter;
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
                .setAdoptionLevel(rusEngEnumConverter.convertToEnglishValue(dto.getAdoptionLevel(), AdoptionLevelEnum.class))
                .setStatus(rusEngEnumConverter.convertToEnglishValue(dto.getStatus(), StatusEnum.class))
                .setHarmonization(rusEngEnumConverter.convertToEnglishValue(dto.getHarmonization(), HarmonizationEnum.class))
                .setAcceptedFirstTimeOrReplaced(rusEngEnumConverter.convertToEnglishValue(dto.getAcceptedFirstTimeOrReplaced(), AcceptedFirstTimeOrReplacedEnum.class))
                .setReferences(dto.getReferences());
    }

    @Override
    public DocumentDtoResponse mapEntityToDto(Document fromWhat) {
        return new DocumentDtoResponse()
                .setId(fromWhat.getId())
                .setFullName(fromWhat.getFullName())
                .setDesignation(fromWhat.getDesignation())
                .setCodeOKS(fromWhat.getCodeOKS())
                .setActivityField(fromWhat.getActivityField())
                .setAuthor(fromWhat.getAuthor())
                .setApplicationArea(fromWhat.getApplicationArea())
                .setContentLink(fromWhat.getContentLink())
                .setAcceptanceYear(fromWhat.getAcceptanceYear())
                .setCommissionYear(fromWhat.getCommissionYear())
                .setKeyWords(fromWhat.getKeyWords())
                .setAdoptionLevel(rusEngEnumConverter.convertToRussianValue(fromWhat.getAdoptionLevel()))
                .setStatus(rusEngEnumConverter.convertToRussianValue(fromWhat.getStatus()))
                .setHarmonization(rusEngEnumConverter.convertToRussianValue(fromWhat.getHarmonization()))
                .setAcceptedFirstTimeOrReplaced(rusEngEnumConverter.convertToRussianValue(fromWhat.getAcceptedFirstTimeOrReplaced()))
                .setReferences(fromWhat.getReferences());
    }
}