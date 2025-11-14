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
    Optional<Document> findByFullName(String fullName);
}