package com.example.gostsNaumen.entity;

import com.example.gostsNaumen.entity.model.StatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public Status setId(Integer id) {
        this.id = id;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Status setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
