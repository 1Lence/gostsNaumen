package com.example.gostsNaumen.controller.dto.request;

/**
 * Дто принимающая в себя строки по которым происходит фильтрация
 */
public class FilterDtoRequest {
    private String fullName;
    private String codeOks;
    private Integer acceptanceYear;
    private Integer commissionYear;
    private String author;
    private String acceptedFirstTimeOrReplaced;
    private String keyWords;
    private String adoptionLevel;
    private String status;
    private String harmonization;
    private Integer acceptanceYearBegin;
    private Integer acceptanceYearEnd;
    private Integer commissionYearBegin;
    private Integer commissionYearEnd;

    public String getFullName() {
        return fullName;
    }

    public FilterDtoRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCodeOks() {
        return codeOks;
    }

    public FilterDtoRequest setCodeOks(String codeOks) {
        this.codeOks = codeOks;
        return this;
    }

    public Integer getAcceptanceYear() {
        return acceptanceYear;
    }

    public FilterDtoRequest setAcceptanceYear(Integer acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public Integer getCommissionYear() {
        return commissionYear;
    }

    public FilterDtoRequest setCommissionYear(Integer commissionYear) {
        this.commissionYear = commissionYear;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public FilterDtoRequest setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public FilterDtoRequest setAcceptedFirstTimeOrReplaced(String acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        return this;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public FilterDtoRequest setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public FilterDtoRequest setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FilterDtoRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getHarmonization() {
        return harmonization;
    }

    public FilterDtoRequest setHarmonization(String harmonization) {
        this.harmonization = harmonization;
        return this;
    }

    public Integer getAcceptanceYearBegin() {
        return acceptanceYearBegin;
    }

    public FilterDtoRequest setAcceptanceYearBegin(Integer acceptanceYearBegin) {
        this.acceptanceYearBegin = acceptanceYearBegin;
        return this;
    }

    public Integer getAcceptanceYearEnd() {
        return acceptanceYearEnd;
    }

    public FilterDtoRequest setAcceptanceYearEnd(Integer acceptanceYearEnd) {
        this.acceptanceYearEnd = acceptanceYearEnd;
        return this;
    }

    public Integer getCommissionYearBegin() {
        return commissionYearBegin;
    }

    public FilterDtoRequest setCommissionYearBegin(Integer commissionYearBegin) {
        this.commissionYearBegin = commissionYearBegin;
        return this;
    }

    public Integer getCommissionYearEnd() {
        return commissionYearEnd;
    }

    public FilterDtoRequest setCommissionYearEnd(Integer commissionYearEnd) {
        this.commissionYearEnd = commissionYearEnd;
        return this;
    }
}