package com.example.gostsNaumen.dto.response;

import java.util.Set;

/**
 * Дто для передачи госта наружу
 */
public class DocumentDtoResponse {
    private Long id;
    private String fullName;
    private String title;
    private String description;
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
    private Set<String> references;

    public DocumentDtoResponse() {
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

    public String getTitle() {
        return title;
    }

    public DocumentDtoResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DocumentDtoResponse setDescription(String description) {
        this.description = description;
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

    public int getAcceptanceYear() {
        return acceptanceYear;
    }

    public DocumentDtoResponse setAcceptanceYear(int acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public int getCommissionYear() {
        return commissionYear;
    }

    public DocumentDtoResponse setCommissionYear(int commissionYear) {
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

    public Set<String> getReferences() {
        return references;
    }

    public DocumentDtoResponse setReferences(Set<String> references) {
        this.references = references;
        return this;
    }
}