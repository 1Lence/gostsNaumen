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
 * Сущность документа (ГОСТа) представляет собой ГОСТ со всеми необходимыми полями.
 */
public class DocumentDtoRequest {
    /**
     * Полное название ГОСТа.
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>512</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 512)
    private String fullName;
    /**
     * Регистрационный номер ГОСТа.
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>128</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 128)
    private String designation;
    /**
     * Код ОКС (Общероссийский классификатор стандартов) ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>64</b> символа;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 64)
    private String codeOKS;
    /**
     * Область применения ГОСТа.
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>128</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 128)
    private String activityField;
    /**
     * Автор ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>256</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 256)
    private String author;
    /**
     * Область применения ГОСТа.
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>1024</b> символа;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 1024)
    private String applicationArea;
    /**
     * Ссылка на полный документ ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>256</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 256)
    private String contentLink;
    /**
     * Год принятия ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotNull
    private Integer acceptanceYear;
    /**
     * Год введения ГОСТа в действие.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotNull
    private Integer commissionYear;
    /**
     * Ключевые слова ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>512</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 512)
    private String keyWords;
    /**
     * Уровень стандартизации ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AdoptionLevelEnum.class, message = "Должно содержать: Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций")
    private String adoptionLevel;
    /**
     * Статус ГОСТа.<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = StatusEnum.class, message = "Должно содержать: Актуальный, Отменённый или Заменённый")
    private String status;
    /**
     * Уровень гармонизации ГОСТа<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = HarmonizationEnum.class, message = "Должно содержать: Не гармонизированный, Модифицированный или Гармонизированный")
    private String harmonization;
    /**
     * Информация о том, был ли стандарт введён впервые, или был обновлён<br>
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символов;</li>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotEmpty
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AcceptedFirstTimeOrReplacedEnum.class, message = "Должно содержать: ВВЕДЁН ВПЕРВЫЕ, ИЗМЕНЁН")
    private String acceptedFirstTimeOrReplaced;
    /**
     * Нормативные ссылки на другие ГОСТы
     * <h5>Требования:</h5>
     * <ul>
     *     <li>Не может быть пустым</li>
     * </ul>
     */
    @NotNull
    private Set<@Size(min = 1, max = 128) String> references;

    public DocumentDtoRequest() {
    }

    public DocumentDtoRequest(String fullName,
                              String designation,
                              String codeOKS,
                              String activityField,
                              String author,
                              String applicationArea,
                              String contentLink,
                              Integer acceptanceYear,
                              Integer commissionYear,
                              String keyWords,
                              String adoptionLevel,
                              String status,
                              String harmonization,
                              String acceptedFirstTimeOrReplaced,
                              Set<@Size(min = 1, max = 128) String> references) {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCodeOKS() {
        return codeOKS;
    }

    public void setCodeOKS(String codeOKS) {
        this.codeOKS = codeOKS;
    }

    public String getActivityField() {
        return activityField;
    }

    public void setActivityField(String activityField) {
        this.activityField = activityField;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public void setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
    }

    public String getContentLink() {
        return contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public Integer getAcceptanceYear() {
        return acceptanceYear;
    }

    public void setAcceptanceYear(Integer acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
    }

    public Integer getCommissionYear() {
        return commissionYear;
    }

    public void setCommissionYear(Integer commissionYear) {
        this.commissionYear = commissionYear;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public void setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHarmonization() {
        return harmonization;
    }

    public void setHarmonization(String harmonization) {
        this.harmonization = harmonization;
    }

    public String getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public void setAcceptedFirstTimeOrReplaced(String acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
    }

    public Set<String> getReferences() {
        return references;
    }

    public void setReferences(Set<String> references) {
        this.references = references;
    }
}