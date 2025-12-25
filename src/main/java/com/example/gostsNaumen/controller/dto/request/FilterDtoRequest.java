package com.example.gostsNaumen.controller.dto.request;

/**
 * Дто принимающая в себя строки по которым происходит фильтрация
 */
public class FilterDtoRequest {
    /**
     * Полное имя стандарта
     */
    private String fullName;
    /**
     * Код окс
     */
    private String codeOks;
    /**
     * Год принятия
     */
    private Integer acceptanceYear;
    /**
     * Год введения
     */
    private Integer commissionYear;
    /**
     * Автор госта
     */
    private String author;
    /**
     * ГОСТ введён впервые или изменён?
     */
    private String acceptedFirstTimeOrReplaced;
    /**
     * Ключевые слова
     */
    private String keyWords;
    /**
     * Уровень принятия
     */
    private String adoptionLevel;
    /**
     * Статус госта
     */
    private String status;
    /**
     * Уровень гармонизации госта
     */
    private String harmonization;
    /**
     * Начало промежутка года приянтия госта
     */
    private Integer acceptanceYearBegin;
    /**
     * Конец промежутка года приянтия госта
     */
    private Integer acceptanceYearEnd;
    /**
     * Начало промежутка года введения госта
     */
    private Integer commissionYearBegin;
    /**
     * Конец промежутка года введения госта
     */
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