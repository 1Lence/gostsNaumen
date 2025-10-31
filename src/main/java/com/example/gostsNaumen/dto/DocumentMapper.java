package com.example.gostsNaumen.dto;

import com.example.gostsNaumen.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.entity.Document;
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

        // TODO: Необходима реализация после изменения энтити
        return null;
    }
}