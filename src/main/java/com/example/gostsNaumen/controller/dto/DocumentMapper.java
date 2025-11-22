package com.example.gostsNaumen.controller.dto;

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
public class DocumentMapper {

    private final RusEngEnumConverter rusEngEnumConverter;

    public DocumentMapper(
            RusEngEnumConverter rusEngEnumConverter
    ) {
        this.rusEngEnumConverter = rusEngEnumConverter;
    }

    /**
     * Создает сущность документа, которая в последующем используется ТОЛЬКО для сохранения нового ГОСТа
     *
     * @param dto ДТО, которое полностью имеет все те же поля, что и сущность, кроме Id
     * @return сущность ГОСТа, которая в дальнейшем будет сохранена в БД
     */
    public Document createDocumentEntity(DocumentDtoRequest dto) {
        return new Document(
                dto.getFullName(),
                dto.getDesignation(),
                dto.getCodeOKS(),
                dto.getActivityField(),
                dto.getAuthor(),
                dto.getApplicationArea(),
                dto.getContentLink(),
                dto.getAcceptanceYear(),
                dto.getCommissionYear(),
                dto.getKeyWords(),
                rusEngEnumConverter.convertToEnglishValue(dto.getAdoptionLevel(), AdoptionLevelEnum.class),
                rusEngEnumConverter.convertToEnglishValue(dto.getStatus(), StatusEnum.class),
                rusEngEnumConverter.convertToEnglishValue(dto.getHarmonization(), HarmonizationEnum.class),
                rusEngEnumConverter.convertToEnglishValue(dto.getAcceptedFirstTimeOrReplaced(), AcceptedFirstTimeOrReplacedEnum.class),
                dto.getReferences()
        );
    }

    /**
     * Преобразование из сущности с БД в ДТО для отправки наружу
     *
     * @param fromWhat сущность документа из ДТО
     * @return дто с документом
     */
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