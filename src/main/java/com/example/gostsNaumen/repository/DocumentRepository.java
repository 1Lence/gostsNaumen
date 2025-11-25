package com.example.gostsNaumen.repository;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.entity.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы со стандартами
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Optional<Document> findByFullName(String fullName);

    Optional<List<Document>> findAllByFullName(String fullName);

    /**
     * Метод, необходимый для поиска стандарта по его полному имени и статусу
     */
    Optional<Document> findByFullNameAndStatus(String fullName, StatusEnum status);
}