package com.example.gostsNaumen.entity;

import jakarta.persistence.*;

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
 *
 */
@Entity
public class AdoptionLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adoptionLevel;

    public String getAdoptionLevel() {
        return adoptionLevel;
    }

    public void setAdoptionLevel(String adoptionLevel) {
        this.adoptionLevel = adoptionLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
