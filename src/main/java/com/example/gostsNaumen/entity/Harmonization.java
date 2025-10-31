package com.example.gostsNaumen.entity;

import com.example.gostsNaumen.entity.model.HarmonizationEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Степень гармонизации ГОСТа<br><br>
 * Cущность содержит уникальный идентификатор и тип гармонизации, связанный с сущностью {@link Document} через связь
 * ManyToOne, связь осуществляется по колонке harmonization - внешний ключ в сущности {@link Document}<br><br>
 * Использование в Document:
 * <pre>
 *     {@code
 *     @ManyToOne(fetch = FetchType.LAZY)
 *     @JoinColumn(name = "harmonization")
 *     private Harmonization harmonization;
 *     }
 * </pre>
 */
@Entity
public class Harmonization {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Тип гармонизации, примеры:<br>
     * Негармонизированный, Модифицированный, Гармонизированный
     */
    @Column(name = "harmonization")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private HarmonizationEnum harmonizationType;

    public Integer getId() {
        return id;
    }

    public Harmonization setId(Integer id) {
        this.id = id;
        return this;
    }

    public HarmonizationEnum getHarmonizationType() {
        return harmonizationType;
    }

    public Harmonization setHarmonizationType(HarmonizationEnum harmonizationType) {
        this.harmonizationType = harmonizationType;
        return this;
    }
}
