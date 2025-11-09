package com.example.gostsNaumen.repository.specification;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Хранит различные фильтры для поиска ГОСТ-ов в БД.
 * В дальнейшем используется в репозитории для удобного построения динамических запросов
 */
@Component
public class DocumentSpecification {
    /**
     * Создает {@link Specification} для фильтрации сущностей {@code Document} по коду ОКС.
     * Если переданное значение ОКС ({@code codeOks}) равно {@code null},
     * спецификация не накладывает никаких ограничений (возвращает {@code null}).
     *
     * @param fullName полное название госта
     * @return {@link Specification} для фильтрации по полю {@code codeOks}.
     * Возвращает {@code null}, если {@code codeOks} равно {@code null}.
     */
    public Specification<Document> withFullName(String fullName) {
        return (root, query, cb) ->
                fullName == null
                        ? null
                        : cb.like(root.get("fullName"), "%" + fullName + "%");
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@code Document} по коду ОКС.
     * Если переданное значение ОКС ({@code codeOks}) равно {@code null},
     * спецификация не накладывает никаких ограничений (возвращает {@code null}).
     *
     * @param codeOks код ОКС
     * @return {@link Specification} для фильтрации по полю {@code codeOks}.
     * Возвращает {@code null}, если {@code codeOks} равно {@code null}.
     */
    public Specification<Document> withCodeOks(String codeOks) {
        return (root, query, cb) ->
                codeOks == null
                        ? null
                        : cb.like(root.get("codeOKS"), "%" + codeOks + "%");
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@code Document} по году принятия.
     * Если переданное значение года приемки ({@code acceptanceYear}) равно {@code null},
     * спецификация не накладывает никаких ограничений (возвращает {@code null}).
     *
     * @param acceptanceYear год принятия, по которому необходимо фильтровать.
     *                       Может быть {@code null}, в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code acceptanceYear}.
     * Возвращает {@code null}, если {@code acceptanceYear} равно {@code null}.
     */
    public Specification<Document> withAcceptanceYear(Integer acceptanceYear) {
        return (root, query, cb) ->
                acceptanceYear == null
                        ? null
                        : cb.equal(root.get("acceptanceYear"), acceptanceYear);
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по году комиссии.
     * Если переданное значение года приемки ({@code commissionYear}) равно {@code null},
     * спецификация не накладывает никаких ограничений (возвращает {@code null}).
     *
     * @param commissionYear год комиссии, по которому необходимо фильтровать.
     *                       Может быть {@code null}, в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code commissionYear}.
     * Возвращает {@code null}, если {@code commissionYear} равно {@code null}.
     */
    public Specification<Document> withCommissionYear(Integer commissionYear) {
        return (root, query, cb) ->
                commissionYear == null
                        ? null
                        : cb.equal(root.get("commissionYear"), commissionYear);
    }

    /**
     * Создает {@link Specification} для фильтрации документов по году принятия {@code acceptanceYear}
     * в зависимости от переданных граничных значений.
     * Поведение метода:
     * <ul>
     *   <li>Если заданы оба параметра {@code acceptanceYearBegin} и {@code acceptanceYearEnd},
     *       возвращается условие {@code BETWEEN begin AND end}.</li>
     *   <li>Если задан только {@code acceptanceYearBegin}, возвращается условие {@code >= begin}.</li>
     *   <li>Если задан только {@code acceptanceYearEnd}, возвращается условие {@code <= end}.</li>
     *   <li>Если оба параметра равны {@code null}, возвращается {@code null},
     *   что означает отсутствие фильтрации по этому критерию.</li>
     * </ul>
     *
     * @param acceptanceYearBegin Начальный год для фильтрации.
     *                            Может быть {@code null}.
     * @param acceptanceYearEnd   Конечный год для фильтрации (включительно).
     *                            Может быть {@code null}.
     * @return {@link Specification<Document>} - спецификация для фильтрации по году принятия
     * или {@code null}, если оба параметра {@code null}.
     */
    public Specification<Document> withAcceptanceYearBetweenOrGreaterOrLess(
            Integer acceptanceYearBegin,
            Integer acceptanceYearEnd
    ) {
        return (root, query, cb) -> {
            if (acceptanceYearBegin != null && acceptanceYearEnd != null) {
                return cb.between(root.get("acceptanceYear"), acceptanceYearBegin, acceptanceYearEnd);
            } else if (acceptanceYearBegin != null) {
                return cb.greaterThanOrEqualTo(root.get("acceptanceYear"), acceptanceYearBegin);
            } else if (acceptanceYearEnd != null) {
                return cb.lessThanOrEqualTo(root.get("acceptanceYear"), acceptanceYearEnd);
            } else {
                return null;
            }
        };
    }

    /**
     * Создает {@link Specification} для фильтрации документов по году комиссии {@code commissionYear}
     * в зависимости от переданных граничных значений.
     * Поведение метода:
     * <ul>
     *   <li>Если заданы оба параметра {@code commissionYearBegin} и {@code commissionYearEnd},
     *       возвращается условие {@code BETWEEN begin AND end}.</li>
     *   <li>Если задан только {@code commissionYearBegin}, возвращается условие {@code >= begin}.</li>
     *   <li>Если задан только {@code commissionYearEnd}, возвращается условие {@code <= end}.</li>
     *   <li>Если оба параметра равны {@code null}, возвращается {@code null},
     *   что означает отсутствие фильтрации по этому критерию.</li>
     * </ul>
     *
     * @param commissionYearBegin Начальный год для фильтрации.
     *                            Может быть {@code null}.
     * @param commissionYearEnd   Конечный год для фильтрации.
     *                            Может быть {@code null}.
     * @return {@code Specification<Document>} - спецификация для фильтрации по году принятия
     * или {@code null}, если оба параметра {@code null}.
     */
    public Specification<Document> withCommissionYearBetweenOrGreaterOrLess(
            Integer commissionYearBegin,
            Integer commissionYearEnd
    ) {
        return (root, query, cb) -> {
            if (commissionYearBegin != null && commissionYearEnd != null) {
                return cb.between(root.get("commissionYear"), commissionYearBegin, commissionYearEnd);
            } else if (commissionYearBegin != null) {
                return cb.greaterThanOrEqualTo(root.get("commissionYear"), commissionYearBegin);
            } else if (commissionYearEnd != null) {
                return cb.lessThanOrEqualTo(root.get("commissionYear"), commissionYearEnd);
            } else {
                return null;
            }
        };
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по автору.
     * Если переданное значение автора {@code author} равно {@code null},
     * спецификация не накладывает никаких ограничений, возвращает {@code null}.
     *
     * @param author автор, по которому необходимо фильтровать. Может быть {@code null},
     *               в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code author}.
     * Возвращает {@code null}, если {@code author} равно {@code null}.
     */
    public Specification<Document> withAuthor(String author) {
        return (root, query, cb) ->
                author == null
                        ? null
                        : cb.like(root.get("author"), "%" + author + "%");
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по статусу принятия.
     * Если переданное значение статуса принятия равно {@code null},
     * спецификация не накладывает никаких ограничений, возвращает {@code null}.
     *
     * @param value статус принятия, по которому необходимо фильтровать. Может быть {@code null},
     *              в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code acceptedFirstTimeOrReplacedEnum}.
     * Возвращает {@code null}, если значение равно {@code null}.
     */
    public Specification<Document> withAcceptedFirstTimeOrReplaced(AcceptedFirstTimeOrReplacedEnum value) {
        return (root, query, cb) ->
                value == null
                        ? null
                        : cb.equal(root.get("acceptedFirstTimeOrReplaced"), value);
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по ключевым словам.
     * Если переданное значение ключевых слов {@code keyWords} равно {@code null},
     * спецификация не накладывает никаких ограничений, возвращает {@code null}.
     *
     * @param keyWords ключевые слова, по которым необходимо фильтровать. Может быть {@code null},
     *                 в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code keyWords}.
     * Возвращает {@code null}, если {@code keyWords} равно {@code null}.
     */
    public Specification<Document> withKeyWords(String keyWords) {
        return (root, query, cb) ->
                keyWords == null
                        ? null
                        : cb.like(root.get("keyWords"), "%" + keyWords + "%");
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по уровню принятия.
     * Если переданное значение уровня принятия {@code adoptionLevel} равно {@code null},
     * спецификация не накладывает никаких ограничений, возвращает {@code null}.
     *
     * @param adoptionLevel уровни принятия, по которым необходимо фильтровать.
     *                      Может быть {@code null}, в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code adoptionLevel}.
     * Возвращает {@code null}, если {@code adoptionLevel} равно {@code null}.
     */
    public Specification<Document> withAdoptionLevel(AdoptionLevelEnum adoptionLevel) {
        return (root, query, cb) ->
                adoptionLevel == null
                        ? null
                        : cb.equal(root.get("adoptionLevel"), adoptionLevel);
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по статусу документа.
     * Если переданное значение статуса {@code status} равно {@code null},
     * спецификация не накладывает никаких ограничений, возвращает {@code null}.
     *
     * @param status статус документа, по которым необходимо фильтровать.
     *               Может быть {@code null}, в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code status}.
     * Возвращает {@code null}, если {@code status} равно {@code null}.
     */
    public Specification<Document> withStatus(StatusEnum status) {
        return (root, query, cb) ->
                status == null
                        ? null
                        : cb.equal(root.get("status"), status);
    }

    /**
     * Создает {@link Specification} для фильтрации сущностей {@link Document} по гармонизации.
     * Если переданное значение гармонизации ({@code harmonization}) равно {@code null},
     * спецификация не накладывает никаких ограничений (возвращает {@code null}).
     *
     * @param harmonization гармонизация, по которым необходимо фильтровать.
     *                      Может быть {@code null}, в этом случае фильтрация не применяется.
     * @return {@link Specification} для фильтрации по полю {@code harmonization}.
     * Возвращает {@code null}, если {@code harmonization} равно {@code null}.
     */
    public Specification<Document> withHarmonization(HarmonizationEnum harmonization) {
        return (root, query, cb) ->
                harmonization == null
                        ? null
                        : cb.equal(root.get("harmonization"), harmonization);
    }
}