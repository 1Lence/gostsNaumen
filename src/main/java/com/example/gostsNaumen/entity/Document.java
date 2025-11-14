package com.example.gostsNaumen.entity;

import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Сущность документа (ГОСТа) представляет собой ГОСТ со всеми необходимыми полями.<br>
 * Некоторые поля представлены связями с другими cущностями через внешние ключи.
 */
@Entity
public class Document {
    /**
     * Генерируемый id типа Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Полное название ГОСТа.<br>
     * Пример: "БРОНЕОДЕЖДА Классификация и общие технические требования"
     */
    @Column(name = "full_name")
    private String fullName;
    /**
     * Регистрационный номер ГОСТа.<br>
     * Пример: "ГОСТ 34286-2017"
     */
    private String designation;
    /**
     * Код ОКС (Общероссийский классификатор стандартов) ГОСТа.<br>
     * Пример: "13.340.10"
     */
    @Column(name = "code_oks")
    private String codeOKS;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Защитная одежда"
     */
    @Column(name = "activity_field")
    private String activityField;
    /**
     * Автор ГОСТа.<br>
     * Пример: "МТК 391 «Средства физической защиты и материалы для их изготовления"
     */
    private String author;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты туловища и конечностей
     * человека (за исключением стоп ног и кистей рук) (далее — человека) от воздействияхолодного и огнестрельного
     * стрелкового оружия, а также поражения осколками. Стандарт устанавливает классификацию бронеодежды и
     * общие технические требования к ней, необходимые для разработки, изготовления
     * и испытаний соответствующей продукции."
     */
    @Column(name = "application_area")
    private String applicationArea;
    /**
     * Ссылка на полный документ ГОСТа.<br>
     * Пример: <a href="https://meganorm.ru/Data2/1/4293734/4293734461.pdf">
     * https://meganorm.ru/Data2/1/4293734/4293734461.pdf</a>
     */
    @Column(name = "content_link")
    private String contentLink;
    /**
     * Год принятия ГОСТа.<br>
     * Пример: 2017
     */
    @Column(name = "acceptance_year")
    private Integer acceptanceYear;
    /**
     * Год введения ГОСТа в действие.<br>
     * Пример: 2019
     */
    @Column(name = "commission_year")
    private Integer commissionYear;
    /**
     * Ключевые слова ГОСТа.<br>
     * Пример: "Ключевые слова: бронеодежда, холодное оружие,
     * стрелковое оружие, защитная структура, класс защитной
     * структуры, заброневое воздействие поражающего элемента при непробитии защитной структуры,
     * показатель противоосколочной стойкости защитной структуры"
     */
    @Column(name = "key_words")
    private String keyWords;
    /**
     * Уровень стандартизации ГОСТа.<br>
     * Всего бывает 5 уровней:<br>
     * Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций.<br>
     */
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private AdoptionLevelEnum adoptionLevel;
    /**
     * Статус ГОСТа.<br>
     * Всего бывает 3 статуса:<br>
     * Актуальный, Отменённый, Заменённый<br>
     */
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    /**
     * Уровень гармонизации ГОСТа<br>
     * Всего бывает 3 варианта:<br>
     * Негармонизированный, Модифицированный, Гармонизированный<br>
     */
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private HarmonizationEnum harmonization;

    /**
     * Информация о том, был ли стандарт введён впервые, или был обновлён<br>
     * Бывает 2 состояния:<br>
     * Введён впервые, изменён
     */
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private AcceptedFirstTimeOrReplacedEnum acceptedFirstTimeOrReplaced;

    /**
     * Нормативные ссылки на другие ГОСТы
     * Пример: "ГОСТ 3722—2014", "ГОСТ 28653—90"
     */
    @Column(name = "references_list")
    private Set<String> references = new HashSet<>();

    public Document() {
    }

    public Document(
            String fullName,
            String designation,
            String codeOKS,
            String activityField,
            String author,
            String applicationArea,
            String contentLink,
            Integer acceptanceYear,
            Integer commissionYear,
            String keyWords,
            AdoptionLevelEnum adoptionLevel,
            StatusEnum status,
            HarmonizationEnum harmonization,
            AcceptedFirstTimeOrReplacedEnum acceptedFirstTimeOrReplaced,
            Set<String> references
    ) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public AcceptedFirstTimeOrReplacedEnum getAcceptedFirstTimeOrReplaced() {
        return acceptedFirstTimeOrReplaced;
    }

    public Document setAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum acceptedFirstTimeOrReplaced) {
        this.acceptedFirstTimeOrReplaced = acceptedFirstTimeOrReplaced;
        return this;
    }

    public Document setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public Document setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getCodeOKS() {
        return codeOKS;
    }

    public Document setCodeOKS(String codeOKS) {
        this.codeOKS = codeOKS;
        return this;
    }

    public String getActivityField() {
        return activityField;
    }

    public Document setActivityField(String activityField) {
        this.activityField = activityField;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Document setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public Document setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
        return this;
    }

    public String getContentLink() {
        return contentLink;
    }

    public Document setContentLink(String contentLink) {
        this.contentLink = contentLink;
        return this;
    }

    public int getAcceptanceYear() {
        return acceptanceYear;
    }

    public Document setAcceptanceYear(int acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
        return this;
    }

    public int getCommissionYear() {
        return commissionYear;
    }

    public Document setCommissionYear(int commissionYear) {
        this.commissionYear = commissionYear;
        return this;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public Document setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public AdoptionLevelEnum getAdoptionLevel() {
        return adoptionLevel;
    }

    public Document setAdoptionLevel(AdoptionLevelEnum adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Document setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public HarmonizationEnum getHarmonization() {
        return harmonization;
    }

    public Document setHarmonization(HarmonizationEnum harmonization) {
        this.harmonization = harmonization;
        return this;
    }

    public Set<String> getReferences() {
        return references;
    }

    public Document setReferences(Set<String> references) {
        this.references = references;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(fullName, document.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }
}
