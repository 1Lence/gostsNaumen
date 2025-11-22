package com.example.gostsNaumen.controller.dto.request;

import com.example.gostsNaumen.controller.dto.validator.CustomEnumValid;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;

import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * dto служащее для получения обновлений полей документа, в отличие от обычного
 * {@link  DocumentDtoRequest} не имеет аннотаций @NotNull и @NotEmpty
 */
public class ActualizeDtoRequest {
    /**
     * Полное название ГОСТа.<br>
     * Пример: "БРОНЕОДЕЖДА Классификация и общие технические требования"
     * <ul>
     *     <li>Максимальная длина <b>512</b> символов;</li>
     * </ul>
     */
    @Size(min = 1, max = 512)
    private String fullName;
    /**
     * Регистрационный номер ГОСТа.<br>
     * Пример: "ГОСТ 34286-2017"
     * <ul>
     *     <li>Максимальная длина <b>128</b> символов;</li>
     * </ul>
     */
    @Size(min = 1, max = 128)
    private String designation;
    /**
     * Код ОКС (Общероссийский классификатор стандартов) ГОСТа.<br>
     * Пример: "13.340.10"
     * <ul>
     *     <li>Максимальная длина <b>64</b> символа</li>
     * </ul>
     */
    @Size(min = 1, max = 64)
    private String codeOKS;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Защитная одежда"
     * <ul>
     *     <li>Максимальная длина <b>128</b> символов</li>
     * </ul>
     */
    @Size(min = 1, max = 128)
    private String activityField;
    /**
     * Автор ГОСТа.<br>
     * Пример: "МТК 391 «Средства физической защиты и материалы для их изготовления"
     * <ul>
     *     <li>Максимальная длина <b>256</b> символов</li>
     * </ul>
     */
    @Size(min = 1, max = 256)
    private String author;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты туловища и конечностей
     * человека (за исключением стоп ног и кистей рук) (далее — человека) от воздействияхолодного и огнестрельного
     * стрелкового оружия, а также поражения осколками. Стандарт устанавливает классификацию бронеодежды и
     * общие технические требования к ней, необходимые для разработки, изготовления
     * и испытаний соответствующей продукции."
     * <ul>
     *     <li>Максимальная длина <b>1024</b> символа</li>
     * </ul>
     */
    @Size(min = 1, max = 1024)
    private String applicationArea;
    /**
     * Ссылка на полный документ ГОСТа.<br>
     * Пример: <a href="https://meganorm.ru/Data2/1/4293734/4293734461.pdf">
     * https://meganorm.ru/Data2/1/4293734/4293734461.pdf</a>
     * <ul>
     *     <li>Максимальная длина <b>256</b> символов</li>
     * </ul>
     */
    @Size(min = 1, max = 256)
    private String contentLink;
    /**
     * Год принятия ГОСТа.<br>
     * Пример: 2017
     */
    private Integer acceptanceYear;
    /**
     * Год введения ГОСТа в действие.<br>
     * Пример: 2019
     */
    private Integer commissionYear;
    /**
     * Ключевые слова ГОСТа.<br>
     * Пример: "Ключевые слова: бронеодежда, холодное оружие,
     * стрелковое оружие, защитная структура, класс защитной
     * структуры, заброневое воздействие поражающего элемента при непробитии защитной структуры,
     * показатель противоосколочной стойкости защитной структуры"
     * <ul>
     *     <li>Максимальная длина <b>512</b> символов</li>
     * </ul>
     */
    @Size(min = 1, max = 512)
    private String keyWords;
    /**
     * Уровень стандартизации ГОСТа.<br>
     * Всего бывает 5 уровней:<br>
     * Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций.<br>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символа</li>
     *     <li>Значение должно соответствовать одному из значений enum-а, переданного в аннотацию</li>
     * </ul>
     */
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AdoptionLevelEnum.class, message = "Должно содержать: Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций")
    private String adoptionLevel;
    /**
     * Статус ГОСТа.<br>
     * Всего бывает 3 статуса:<br>
     * Актуальный, Отменённый, Заменённый<br>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символа</li>
     *     <li>Значение должно соответствовать одному из значений enum-а, переданного в аннотацию</li>
     * </ul>
     */
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = StatusEnum.class, message = "Должно содержать: Актуальный, Отменённый или Заменённый")
    private String status;
    /**
     * Информация о том, был ли стандарт введён впервые, или был обновлён<br>
     * Бывает 2 состояния:<br>
     * Введён впервые, изменён
     * <ul>
     *     <li>Максимальная длина <b>32</b> символа</li>
     *     <li>Значение должно соответствовать одному из значений enum-а, переданного в аннотацию</li>
     * </ul>
     */
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = AcceptedFirstTimeOrReplacedEnum.class, message = "Должно содержать: ВВЕДЁН ВПЕРВЫЕ, ИЗМЕНЁН")
    private String acceptedFirstTimeOrReplaced;
    /**
     * Уровень гармонизации ГОСТа<br>
     * Всего бывает 3 варианта:<br>
     * Негармонизированный, Модифицированный, Гармонизированный<br>
     * <ul>
     *     <li>Максимальная длина <b>32</b> символа</li>
     *     <li>Значение должно соответствовать одному из значений enum-а, переданного в аннотацию</li>
     * </ul>
     */
    @Size(min = 1, max = 32)
    @CustomEnumValid(enumClass = HarmonizationEnum.class, message = "Должно содержать: Не гармонизированный, Модифицированный или Гармонизированный")
    private String harmonization;
    /**
     * Нормативные ссылки на другие ГОСТы
     * Пример: "ГОСТ 3722—2014", "ГОСТ 28653—90"
     */
    private Set<@Size(min = 1, max = 128) String> references;

    public String getFullName() {
        return fullName;
    }

    public ActualizeDtoRequest setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public ActualizeDtoRequest setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getCodeOKS() {
        return codeOKS;
    }

    public ActualizeDtoRequest setCodeOKS(String codeOKS) {
        this.codeOKS = codeOKS;
        return this;
    }

    public String getActivityField() {
        return activityField;
    }

    public ActualizeDtoRequest setActivityField(String activityField) {
        this.activityField = activityField;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ActualizeDtoRequest setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public ActualizeDtoRequest setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
        return this;
    }

    public String getContentLink() {
        return contentLink;
    }

    public ActualizeDtoRequest setContentLink(String contentLink) {
        this.contentLink = contentLink;
        return this;
    }

    public Integer getAcceptanceYear() {
        return acceptanceYear;
    }

    public ActualizeDtoRequest setAcceptanceYear(Integer acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public Integer getCommissionYear() {
        return commissionYear;
    }

    public ActualizeDtoRequest setCommissionYear(Integer commissionYear) {
        this.commissionYear = commissionYear;
        return this;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public ActualizeDtoRequest setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public ActualizeDtoRequest setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ActualizeDtoRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getHarmonization() {
        return harmonization;
    }

    public ActualizeDtoRequest setHarmonization(String harmonization) {
        this.harmonization = harmonization;
        return this;
    }

    public String getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public ActualizeDtoRequest setAcceptedFirstTimeOrReplaced(String acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        return this;
    }

    public Set<String> getReferences() {
        return references;
    }

    public ActualizeDtoRequest setReferences(Set<String> references) {
        this.references = references;
        return this;
    }
}
