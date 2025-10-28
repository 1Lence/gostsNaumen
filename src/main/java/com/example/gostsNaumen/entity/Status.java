package com.example.gostsNaumen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Статус ГОСТа<br>
 * Сущность содержит идентификатор и статус госта, связанный с сущностью {@link Document} через связь
 * ManyToOne, связь осуществляется по колонке status - внешний ключ в сущности {@link Document}<br><br>
 * Использование в Document:
 * <pre>
 *     {@code
 *     @ManyToOne(fetch = FetchType.LAZY)
 *     @JoinColumn(name = "status")
 *     private Status status;
 *     }
 * </pre>
 */
@Entity
public class Status {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Статус ГОСТа, примеры: <br>
     * Актуальный, Отменённый, Заменённый
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
