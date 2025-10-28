package com.example.gostsNaumen.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Сущность документа (ГОСТа) представляет собой ГОСТ со всеми необходимыми полями.<br>
 * Некоторые поля представлены связями с другими cущностями через внешние ключи.
 *
 */
@Entity
public class Document {
    /**
     * Генерируемый id типа Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
     *
     */
    @Column(name = "code_oks")
    private String codeOKS;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Защитная одежда"
     *
     */
    @Column(name = "activity_field")
    private String activityField;
    /**
     * Автор ГОСТа.<br>
     * Пример: "МТК 391 «Средства физической защиты и материалы для их изготовления"
     *
     */
    private String author;
    /**
     * Область применения ГОСТа.<br>
     * Пример: "Настоящий стандарт распространяется на бронеодежду, предназначенную для защиты туловища и конечностей
     * человека (за исключением стоп ног и кистей рук) (далее — человека) от воздействияхолодного и огнестрельного
     * стрелкового оружия, а также поражения осколками. Стандарт устанавливает классификацию бронеодежды и
     * общие технические требования к ней, необходимые для разработки, изготовления
     * и испытаний соответствующей продукции."
     *
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
    private int acceptanceYear;
    /**
     * Год введения ГОСТа в действие.<br>
     * Пример: 2019
     *
     */
    @Column(name = "commission_year")
    private int commissionYear;
    /**
     * Ключевые слова ГОСТа.<br>
     * Пример: "Ключевые слова: бронеодежда, холодное оружие,
     * стрелковое оружие, защитная структура, класс защитной
     * структуры, заброневое воздействие поражающего элемента при непробитии защитной структуры,
     * показатель противоосколочной стойкости защитной структуры"
     *
     */
    @Column(name = "key_words")
    private String keyWords;
    /**
     * Уровень стандартизации ГОСТа.<br>
     * Всего бывает 5 уровней:<br>
     * Национальный, Межгосударственный, Отраслевой, Региональный, Стандарт Организаций.<br>
     * Значение вынесено в отдельную таблицу
     * {@link AdoptionLevel}
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_level")
    private AdoptionLevel adoptionLevel;
    /**
     * Статус ГОСТа.<br>
     * Всего бывает 3 статуса:<br>
     * Актуальный, Отменённый, Заменённый<br>
     * Значение вынесено в отдельную таблицу
     * {@link Status}
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private Status status;
    /**
     * Уровень гармонизации ГОСТа<br>
     * Всего бывает 3 варианта:<br>
     * Негармонизированный, Модифицированный, Гармонизированный<br>
     * Значение вынесено в отдельную таблицу
     * {@link Harmonization}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harmonization")
    private Harmonization harmonization;

    /**
     * Нормативные ссылки на другие ГОСТы
     * Пример: "ГОСТ 3722—2014", "ГОСТ 28653—90"
     * */
    private Set<String> references;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getAcceptanceYear() {
        return acceptanceYear;
    }

    public void setAcceptanceYear(int acceptanceYear) {
        this.acceptanceYear = acceptanceYear;
    }

    public int getCommissionYear() {
        return commissionYear;
    }

    public void setCommissionYear(int commissionYear) {
        this.commissionYear = commissionYear;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public AdoptionLevel getAdoptionLevel() {
        return adoptionLevel;
    }

    public void setAdoptionLevel(AdoptionLevel adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Harmonization getHarmonization() {
        return harmonization;
    }

    public void setHarmonization(Harmonization harmonization) {
        this.harmonization = harmonization;
    }

    public Set<String> getReferences() {
        return references;
    }

    public void setReferences(Set<String> references) {
        this.references = references;
    }
}
