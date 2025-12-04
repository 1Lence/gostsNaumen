package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Отвечает за работу с файлами ГОСТа
 */
@Service
public class GostsFileService {
    private final DocumentRepository documentRepository;

    public GostsFileService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Сохранение файла к ГОСТу
     *
     * @param file массив байт файла
     * @return Сущность ГОСТа к которому был загружен файл
     */
    public Document saveFile(Long docId, MultipartFile file) throws BusinessException, IOException {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS)
        );

        if (document.getFileData() != null) {
            throw new BusinessException(ErrorCode.FILE_ALREADY_EXIST);
        }
        document.setFileData(file.getBytes());

        return documentRepository.save(document);
    }

    /**
     * Поиск и скачивание по айди документа
     *
     * @param docId айди госта к которому
     * @return Сущность ГОСТа
     * @throws BusinessException в случае, если нет ГОСТа по указанному айди или к документу не прикреплен файл
     */
    public Document findById(Long docId) throws BusinessException {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS
                ));

        if (document.getFileData() == null) {
            throw new BusinessException(ErrorCode.NULL_FILE_DATA_RESPONSE);
        }

        return document;
    }

    /**
     * Обновление файла прикрепленного к ГОСТу
     *
     * @param docId айди ГОСТа
     * @param file  массив байт файла
     * @return сущность ГОСТа
     * @throws BusinessException в случае, когда не существует ГОСТа по переданному айди
     * @throws IOException       в случае, когда не был передан файл
     */
    public Document updateFile(Long docId, MultipartFile file) throws BusinessException, IOException {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS
                ));

        if (document.getFileData() == null) {
            throw new BusinessException(ErrorCode.NULL_FILE_DATA_RESPONSE);
        }

        document.setFileData(file.getBytes());

        return documentRepository.save(document);
    }

    /**
     * Удалить файл по айди ГОСТа
     *
     * @param docId айди ГОСТа
     * @throws BusinessException в случае, когда не существует ГОСТа по переданному айди
     */
    public void deleteFile(Long docId) throws BusinessException {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new BusinessException(ErrorCode.STANDARD_BY_ID_NOT_EXISTS
                ));

        if (document.getFileData() == null) {
            throw new BusinessException(ErrorCode.NULL_FILE_DATA_RESPONSE);
        }

        document.setFileData(null);
        documentRepository.save(document);
    }
}