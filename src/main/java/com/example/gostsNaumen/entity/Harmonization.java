package com.example.gostsNaumen.entity;

import jakarta.persistence.*;

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
 *
 */
@Entity
public class Harmonization {
    /**
     * Уникальный идентификатор
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Тип гармонизации, примеры:<br>
     * Негармонизированный, Модифицированный, Гармонизированный
     * */
    @Column(name = "harmonization")
    private String harmonizationType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHarmonizationType() {
        return harmonizationType;
    }

    public void setHarmonizationType(String harmonizationType) {
        this.harmonizationType = harmonizationType;
    }
}
