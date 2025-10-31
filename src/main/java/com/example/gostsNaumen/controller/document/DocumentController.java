package com.example.gostsNaumen.controller.document;

import com.example.gostsNaumen.dto.request.DocumentDtoRequest;
import com.example.gostsNaumen.dto.response.DocumentDtoResponse;
import com.example.gostsNaumen.service.document.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер по работе с гостами
 */
@RestController
@RequestMapping("/api/standards")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{docId}")
    public DocumentDtoResponse getDocument(@PathVariable Long docId) {
        return documentService.getDocumentById(docId);
    }

    @PostMapping("/add")
    public DocumentDtoResponse addDocument(
            @RequestBody @Valid DocumentDtoRequest documentDtoRequest
    ) {
        return documentService.saveDocument(documentDtoRequest);
    }

    @DeleteMapping("/delete/docId")
    public void deleteDocument(@PathVariable Long docId) {

    }
}