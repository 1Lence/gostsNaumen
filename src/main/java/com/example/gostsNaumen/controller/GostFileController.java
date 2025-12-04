package com.example.gostsNaumen.controller;

import com.example.gostsNaumen.controller.dto.response.FileDtoResponse;
import com.example.gostsNaumen.controller.dto.response.FileIdDtoResponse;
import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.service.document.GostsFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class GostFileController {
    private final GostsFileService gostsFileService;
    private final Logger log = LoggerFactory.getLogger(GostFileController.class);

    public GostFileController(
            GostsFileService gostsFileService
    ) {
        this.gostsFileService = gostsFileService;
    }

    /**
     * Сохранить новый документ к госту
     *
     * @param file массив байт файла
     * @return дто с Айди госта
     * @throws BusinessException в случае, когда файл не был подгружен
     */
    @PostMapping(path = "/{docId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('user:read')")
    public FileIdDtoResponse saveFile(@RequestPart("file") MultipartFile file,
                                      @PathVariable Long docId) throws BusinessException, IOException {
        if (file == null) {
            throw new BusinessException(ErrorCode.NULL_FILE_DATA_REQUEST);
        }

        Document document = gostsFileService.saveFile(docId, file);

        return new FileIdDtoResponse(document.getId());
    }

    /**
     * Скачать файл по указанному айди ГОСТа
     *
     * @param docId айди ГОСТа к которому прикреплен файл
     * @return ДТО с массивом байт файла
     * @throws BusinessException в случае, если нет ГОСТа по указанному айди или к документу не прикреплен файл
     */
    @GetMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:read')")
    public FileDtoResponse getFileById(@PathVariable Long docId) throws BusinessException {
        Document document = gostsFileService.findById(docId);

        return new FileDtoResponse(document.getId(), document.getFileData());
    }

    /**
     * Обновить документ прикрепленный к ГОСТу по айди
     *
     * @param docId айди ГОСТа
     * @param file  массив байт файла
     * @return ДТО с обновленным документов
     * @throws BusinessException в случае, когда не существует ГОСТа по переданному айди
     * @throws IOException       в случае, когда передан пустой файл
     */
    @PatchMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:read')")
    public FileDtoResponse updateFile(
            @PathVariable Long docId,
            @RequestPart("file") MultipartFile file
    ) throws BusinessException, IOException {
        if (file == null) {
            throw new BusinessException(ErrorCode.NULL_FILE_DATA_REQUEST);
        }

        Document document = gostsFileService.updateFile(docId, file);

        return new FileDtoResponse(document.getId(), document.getFileData());
    }

    /**
     * Удалить документ прикрепленный ГОСТу
     *
     * @param docId айди ГОСТа
     * @throws BusinessException в случае, когда не существует ГОСТа по переданному айди
     */
    @DeleteMapping("/{docId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteFileById(@PathVariable Long docId) throws BusinessException {
        gostsFileService.deleteFile(docId);
    }
}
