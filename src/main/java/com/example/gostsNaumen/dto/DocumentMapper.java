package com.example.gostsNaumen.dto;

import com.example.gostsNaumen.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.AdoptionLevel;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.Harmonization;
import com.example.gostsNaumen.entity.Status;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.stereotype.Component;

/**
 * Преобразует сущность госта из БД в сущность
 */
@Component
public class DocumentMapper {

    public DocumentDtoResponse mapToDto(Document document) {
        return new DocumentDtoResponse()
                .setId(document.getId())
                .setFullName(document.getFullName())
                .setDescription(document.getDesignation())
                .setCodeOKS(document.getCodeOKS())
                .setActivityField(document.getActivityField())
                .setAuthor(document.getAuthor())
                .setApplicationArea(document.getApplicationArea())
                .setContentLink(document.getContentLink())
                .setAcceptanceYear(document.getAcceptanceYear())
                .setCommissionYear(document.getCommissionYear())
                .setKeyWords(document.getKeyWords())
                .setAdoptionLevel(document.getAdoptionLevel().getAdoptionLevel().toString())
                .setStatus(document.getStatus().getStatus().toString())
                .setHarmonization(document.getHarmonization().getHarmonizationType().toString())
                .setReferences(document.getReferences());
    }

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
                .setAdoptionLevel(new AdoptionLevel().setAdoptionLevel(AdoptionLevelEnum.valueOf(dto.getAdoptionLevel())))
                .setStatus(new Status().setStatus(StatusEnum.valueOf(dto.getStatus())))
                .setHarmonization(new Harmonization().setHarmonizationType(HarmonizationEnum.valueOf(dto.getHarmonization())))
                .setReferences(dto.getReferences());
    }
}