package com.example.gostsNaumen.repository.specification;

import com.example.gostsNaumen.controller.dto.request.FilterDtoRequest;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.AcceptedFirstTimeOrReplacedEnum;
import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import com.example.gostsNaumen.entity.model.StatusEnum;
import com.example.gostsNaumen.entity.model.converter.RusEngEnumConverter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Занимается созданием спецификации по необходимым фильтрам
 */
@Component
public class DocumentSpecificationMapper {
    private final DocumentSpecification specification;
    private final RusEngEnumConverter rusEngEnumConverter;

    public DocumentSpecificationMapper(DocumentSpecification specification, RusEngEnumConverter rusEngEnumConverter) {
        this.specification = specification;
        this.rusEngEnumConverter = rusEngEnumConverter;
    }

    /**
     * Создает спецификацию JPA для комплексного фильтра документа на основе переданных параметров.
     * Метод объединяет несколько спецификаций, каждая из которых отвечает за фильтрацию
     * по определенному полю или набору полей сущности {@link Document}. Документ должен
     * соответствовать всем указанным в {@code filterDtoRequest} критериям.
     *
     * @param filterDtoRequest DTO, содержащий значения фильтров для построения спецификации.
     *                         Поля, значения которых равны {@code null}, будут проигнорированы
     *                         в соответствующих методах.
     * @return {@code Specification<Document>} - объединенная спецификация, готовая к использованию
     * в репозитории Spring Data JPA.
     */
    public Specification<Document> mapFullSpecification(FilterDtoRequest filterDtoRequest) {
        return Specification.allOf(specification.withCodeOks(filterDtoRequest.getCodeOks()))
                .and(specification.withFullName(filterDtoRequest.getFullName()))
                .and(specification.withAcceptanceYear(filterDtoRequest.getAcceptanceYear()))
                .and(specification.withCommissionYear(filterDtoRequest.getCommissionYear()))
                .and(specification.withAuthor(filterDtoRequest.getAuthor()))
                .and(specification.withAcceptedFirstTimeOrReplaced(
                        filterDtoRequest.getAcceptedFirstTimeOrReplaced() != null
                                ? rusEngEnumConverter.convertToEnglishValue(
                                filterDtoRequest.getAcceptedFirstTimeOrReplaced(),
                                AcceptedFirstTimeOrReplacedEnum.class
                        )
                                : null
                ))
                .and(specification.withKeyWords(filterDtoRequest.getKeyWords()))
                .and(specification.withAdoptionLevel(
                        filterDtoRequest.getAdoptionLevel() != null
                                ? rusEngEnumConverter.convertToEnglishValue(
                                filterDtoRequest.getAdoptionLevel(),
                                AdoptionLevelEnum.class
                        )
                                : null
                ))
                .and(specification.withStatus(
                        filterDtoRequest.getStatus() != null
                                ? rusEngEnumConverter.convertToEnglishValue(
                                filterDtoRequest.getStatus(),
                                StatusEnum.class
                        )
                                : null
                ))
                .and(specification.withHarmonization(
                        filterDtoRequest.getHarmonization() != null
                                ? rusEngEnumConverter.convertToEnglishValue(
                                filterDtoRequest.getHarmonization(),
                                HarmonizationEnum.class
                        )
                                : null)
                )
                .and(specification.withAcceptanceYearBetweenOrGreaterOrLess(
                        filterDtoRequest.getAcceptanceYearBegin(), filterDtoRequest.getAcceptanceYearEnd())
                )
                .and(specification.withCommissionYearBetweenOrGreaterOrLess(
                        filterDtoRequest.getCommissionYearBegin(), filterDtoRequest.getCommissionYearEnd())
                );
    }
}