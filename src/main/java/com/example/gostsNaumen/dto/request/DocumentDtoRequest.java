package com.example.gostsNaumen.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * Дто для приема госта извне
 */
public class DocumentDtoRequest {
    // TODO: Для всего класса, добавить валидацию на min max длину строки

    @NotEmpty
    private String fullName;
    @NotEmpty
    private String designation;
    @NotEmpty
    private String codeOKS;
    @NotEmpty
    private String activityField;
    @NotEmpty
    private String author;
    @NotEmpty
    private String applicationArea;
    @NotEmpty
    private String contentLink;
    @NotNull
    private Integer acceptanceYear;
    @NotNull
    private Integer commissionYear;
    @NotEmpty
    private String keyWords;
    @NotEmpty
    private String adoptionLevel;
    @NotEmpty
    private String status;
    @NotEmpty
    private String harmonization;
    @NotNull
    private Set<String> references;

    public DocumentDtoRequest() {
    }

    public String getFullName() {
        return fullName;
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