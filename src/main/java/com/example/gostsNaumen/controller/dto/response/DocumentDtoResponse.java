package com.example.gostsNaumen.controller.dto.response;

import java.util.Set;

/**
 * DTO используемый для передачи информации о документе
 * в ответ на запрос получения ГОСТа или его создания. Содержит все атрибуты документа
 */
public class DocumentDtoResponse {
    private Long id;
    private String fullName;
    private String designation;
    private String codeOKS;
    private String activityField;
    private String author;
    private String applicationArea;
    private String contentLink;
    private Integer acceptanceYear;
    private Integer commissionYear;
    private String keyWords;
    private String adoptionLevel;
    private String status;
    private String harmonization;
    private String acceptedFirstTimeOrReplaced;
    private Set<String> references;

    public DocumentDtoResponse() {
    }

    public DocumentDtoResponse(Long id, String fullName, String designation,
                               String codeOKS, String activityField, String author,
                               String applicationArea, String contentLink, Integer acceptanceYear,
                               Integer commissionYear, String keyWords, String adoptionLevel,
                               String status, String harmonization, String acceptedFirstTimeOrReplaced,
                               Set<String> references) {
        this.id = id;
        this.fullName = fullName;
        this.designation = designation;
        this.codeOKS = codeOKS;
        this.activityField = activityField;
        this.author = author;
        this.applicationArea = applicationArea;
        this.contentLink = contentLink;
        this.acceptanceYear = acceptanceYear;
        this.commissionYear = commissionYear;
        this.keyWords = keyWords;
        this.adoptionLevel = adoptionLevel;
        this.status = status;
        this.harmonization = harmonization;
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        this.references = references;
    }

    public Long getId() {
        return id;
    }

    public DocumentDtoResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public DocumentDtoResponse setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public DocumentDtoResponse setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getCodeOKS() {
        return codeOKS;
    }

    public DocumentDtoResponse setCodeOKS(String codeOKS) {
        this.codeOKS = codeOKS;
        return this;
    }

    public String getActivityField() {
        return activityField;
    }

    public DocumentDtoResponse setActivityField(String activityField) {
        this.activityField = activityField;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public DocumentDtoResponse setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public DocumentDtoResponse setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
        return this;
    }

    public String getContentLink() {
        return contentLink;
    }

    public DocumentDtoResponse setContentLink(String contentLink) {
        this.contentLink = contentLink;
        return this;
    }

    public Integer getAcceptanceYear() {
        return acceptanceYear;
    }

    public DocumentDtoResponse setAcceptanceYear(Integer acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public Integer getCommissionYear() {
        return commissionYear;
    }

    public DocumentDtoResponse setCommissionYear(Integer commissionYear) {
        this.commissionYear = commissionYear;
        return this;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public DocumentDtoResponse setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public DocumentDtoResponse setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DocumentDtoResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getHarmonization() {
        return harmonization;
    }

    public DocumentDtoResponse setHarmonization(String harmonization) {
        this.harmonization = harmonization;
        return this;
    }

    public String getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public DocumentDtoResponse setAcceptedFirstTimeOrReplaced(String acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        return this;
    }

    public Set<String> getReferences() {
        return references;
    }

    public DocumentDtoResponse setReferences(Set<String> references) {
        this.references = references;
        return this;
    }
}