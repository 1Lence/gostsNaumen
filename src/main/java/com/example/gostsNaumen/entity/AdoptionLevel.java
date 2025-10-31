package com.example.gostsNaumen.entity;

import com.example.gostsNaumen.entity.model.AdoptionLevelEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Степень стандартизации ГОСТа, отражает масштаб применения и уровень обязательности<br><br>
 * Cущность содержит уникальный идентификатор и степень стандартизации, связанный с сущностью {@link Document}
 * через связь
 * ManyToOne, связь осуществляется по колонке adoption_level - внешний ключ в сущности {@link Document}<br><br>
 * Использование в Document:
 * <pre>
 *     {@code
 * @ManyToOne(fetch = FetchType.LAZY)
 * @JoinColumn(name = "adoption_level")
 * private AdoptionLevel adoptionLevel;
 *     }
 * </pre>
 */
@Entity
public class AdoptionLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private AdoptionLevelEnum adoptionLevel;

    public AdoptionLevelEnum getAdoptionLevel() {
        return adoptionLevel;
    }

    public AdoptionLevel setAdoptionLevel(AdoptionLevelEnum adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AdoptionLevel setId(Long id) {
        this.id = id;
        return this;
    }
}
