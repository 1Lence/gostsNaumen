package com.example.gostsNaumen.repository;

import com.example.gostsNaumen.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы со стандартами
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    /**
     * Поиск пользователя по полному имени записанному в БД
     *
     * @param fullName полное имя пользователя
     * @return сущность Document обернутую в {@link Optional}
     */
    Optional<Document> findByFullName(String fullName);
}