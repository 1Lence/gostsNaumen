package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Дто для приема госта извне
 */
public class DocumentDtoRequest {
    @NotEmpty
    @Size(min = 1, max = 512)
    private String fullName;
    @NotEmpty
    @Size(min = 1, max = 128)
    private String designation;
    @NotEmpty
    @Size(min = 1, max = 64)
    private String codeOKS;
    @NotEmpty
    @Size(min = 1, max = 128)
    private String activityField;
    @NotEmpty
    @Size(min = 1, max = 256)
    private String author;
    @NotEmpty
    @Size(min = 1, max = 1024)
    private String applicationArea;
    @NotEmpty
    @Size(min = 1, max = 256)
    private String contentLink;
    @NotNull
    private Integer acceptanceYear;
    @NotNull
    private Integer commissionYear;
    @NotEmpty
    @Size(min = 1, max = 512)
    private String keyWords;
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AdoptionLevelEnum.class, message = "Должно содержать: Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций")
    private String adoptionLevel;
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = StatusEnum.class, message = "Должно содержать: Актуальный, Отменённый или Заменённый")
    private String status;
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = HarmonizationEnum.class, message = "Должно содержать: Не гармонизированный, Модифицированный или Гармонизированный")
    private String harmonization;
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AcceptedFirstTimeOrReplacedEnum.class, message = "Должно содержать: ВВЕДЁН ВПЕРВЫЕ, ИЗМЕНЁН")
    private String acceptedFirstTimeOrReplaced;
    @NotNull
    private Set<@Size(min = 1, max = 128) String> references;

    public DocumentDtoRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public DocumentDtoRequest setAcceptedFirstTimeOrReplaced(String acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        return this;
    }

    public DocumentDtoRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public DocumentDtoRequest setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getCodeOKS() {
        return codeOKS;
    }

    public DocumentDtoRequest setCodeOKS(String codeOKS) {
        this.codeOKS = codeOKS;
        return this;
    }

    public String getActivityField() {
        return activityField;
    }

    public DocumentDtoRequest setActivityField(String activityField) {
        this.activityField = activityField;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public DocumentDtoRequest setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public DocumentDtoRequest setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
        return this;
    }

    public String getContentLink() {
        return contentLink;
    }

    public DocumentDtoRequest setContentLink(String contentLink) {
        this.contentLink = contentLink;
        return this;
    }

    public int getAcceptanceYear() {
        return acceptanceYear;
    }

    public DocumentDtoRequest setAcceptanceYear(int acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public int getCommissionYear() {
        return commissionYear;
    }

    public DocumentDtoRequest setCommissionYear(int commissionYear) {
        this.commissionYear = commissionYear;
        return this;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public DocumentDtoRequest setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public DocumentDtoRequest setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DocumentDtoRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getHarmonization() {
        return harmonization;
    }

    public DocumentDtoRequest setHarmonization(String harmonization) {
        this.harmonization = harmonization;
        return this;
    }

    public Set<String> getReferences() {
        return references;
    }

    public DocumentDtoRequest setReferences(Set<String> references) {
        this.references = references;
        return this;
    }
}