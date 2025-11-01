package com.example.gostsNaumen.controller.dto;

import com.example.gostsNaumen.controller.TwoWaysMapper;
import com.example.gostsNaumen.controller.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.controller.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.stereotype.Component;

/**
 * Преобразует сущность госта из БД в сущность
 */
@Component
public class DocumentMapper implements TwoWaysMapper<Document, DocumentDtoRequest, DocumentDtoResponse> {

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
                .setAdoptionLevel(AdoptionLevelEnum.valueOf(dto.getAdoptionLevel()))
                .setStatus(StatusEnum.valueOf(dto.getStatus()))
                .setHarmonization(HarmonizationEnum.valueOf(dto.getHarmonization()))
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
                .setAdoptionLevel(fromWhat.getAdoptionLevel().toString())
                .setStatus(fromWhat.getStatus().toString())
                .setHarmonization(fromWhat.getHarmonization().toString())
                .setReferences(fromWhat.getReferences());
    }

}