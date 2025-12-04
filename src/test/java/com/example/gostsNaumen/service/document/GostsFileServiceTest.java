package com.example.gostsNaumen.service.document;

import com.example.gostsNaumen.entity.Document;
import com.example.gostsNaumen.exception.BusinessException;
import com.example.gostsNaumen.exception.ErrorCode;
import com.example.gostsNaumen.repository.DocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

/**
 * Класс для тестов сервиса по работе с файлами ГОСТов {@link GostsFileService}.<br>
 * Тестирует следующие методы:
 * <ul>
 *     <li>{@code saveFile}</li>
 *     <li>{@code findById}</li>
 *     <li>{@code updateFile}</li>
 *     <li>{@code deleteFile}</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class GostsFileServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private GostsFileService gostsFileService;

    private Document documentWithFile;
    private Document documentWithoutFile;
    private final Long EXISTING_DOC_ID = 1L;
    private final Long NON_EXISTING_DOC_ID = 999L;
    private final byte[] FILE_DATA = "test file content".getBytes();

    /**
     * Перед тестами создаём тестовые документы
     */
    @BeforeEach
    void setUp() {
        // Документ без файла
        documentWithoutFile = new Document();
        documentWithoutFile.setId(EXISTING_DOC_ID);
        documentWithoutFile.setFullName("Test Document Without File");
        documentWithoutFile.setFileData(null);

        // Документ с файлом
        documentWithFile = new Document();
        documentWithFile.setId(EXISTING_DOC_ID);
        documentWithFile.setFullName("Test Document With File");
        documentWithFile.setFileData(FILE_DATA);
    }

    /**
     * Проверка сохранения файла к существующему ГОСТу без файла
     */
    @Test
    void saveFileShouldSaveFileWhenDocumentExistsAndHasNoFile() throws IOException, BusinessException {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithoutFile));
        Mockito.when(multipartFile.getBytes()).thenReturn(FILE_DATA);
        Mockito.when(documentRepository.save(any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Document result = gostsFileService.saveFile(EXISTING_DOC_ID, multipartFile);

        Assertions.assertNotNull(result);
        Assertions.assertArrayEquals(FILE_DATA, result.getFileData());
        Mockito.verify(documentRepository, times(1)).findById(EXISTING_DOC_ID);
        Mockito.verify(documentRepository, times(1)).save(documentWithoutFile);
    }

    /**
     * Проверка выброса исключения при сохранении файла к несуществующему ГОСТу
     */
    @Test
    void saveFileShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(NON_EXISTING_DOC_ID))
                .thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.saveFile(NON_EXISTING_DOC_ID, multipartFile));

        Assertions.assertEquals(ErrorCode.STANDARD_BY_ID_NOT_EXISTS, exception.getErrorCode());
    }

    /**
     * Проверка выброса исключения при сохранении файла к ГОСТу, у которого уже есть файл
     */
    @Test
    void saveFileShouldThrowBusinessExceptionWhenDocumentAlreadyHasFile() {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithFile));

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.saveFile(EXISTING_DOC_ID, multipartFile));

        Assertions.assertEquals(ErrorCode.FILE_ALREADY_EXIST, exception.getErrorCode());
    }

    /**
     * Проверка получения файла существующего ГОСТа с файлом
     */
    @Test
    void findByIdShouldReturnDocumentWhenDocumentExistsAndHasFile() throws BusinessException {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithFile));

        Document result = gostsFileService.findById(EXISTING_DOC_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(EXISTING_DOC_ID, result.getId());
        Assertions.assertArrayEquals(FILE_DATA, result.getFileData());
        Mockito.verify(documentRepository, times(1)).findById(EXISTING_DOC_ID);
    }

    /**
     * Проверка выброса исключения при получении файла несуществующего ГОСТа
     */
    @Test
    void findByIdShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(NON_EXISTING_DOC_ID))
                .thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.findById(NON_EXISTING_DOC_ID));

        Assertions.assertEquals(ErrorCode.STANDARD_BY_ID_NOT_EXISTS, exception.getErrorCode());
    }

    /**
     * Проверка выброса исключения при получении файла ГОСТа без файла
     */
    @Test
    void findByIdShouldThrowBusinessExceptionWhenDocumentHasNoFile() {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithoutFile));

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.findById(EXISTING_DOC_ID));

        Assertions.assertEquals(ErrorCode.NULL_FILE_DATA_RESPONSE, exception.getErrorCode());
    }

    /**
     * Проверка обновления файла существующего ГОСТа с файлом
     */
    @Test
    void updateFileShouldUpdateFileWhenDocumentExistsAndHasFile() throws IOException, BusinessException {
        byte[] newFileData = "new file content".getBytes();
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithFile));
        Mockito.when(multipartFile.getBytes()).thenReturn(newFileData);
        Mockito.when(documentRepository.save(any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Document result = gostsFileService.updateFile(EXISTING_DOC_ID, multipartFile);

        Assertions.assertNotNull(result);
        Assertions.assertArrayEquals(newFileData, result.getFileData());
        Mockito.verify(documentRepository, times(1)).findById(EXISTING_DOC_ID);
        Mockito.verify(documentRepository, times(1)).save(documentWithFile);
    }

    /**
     * Проверка выброса исключения при обновлении файла несуществующего ГОСТа
     */
    @Test
    void updateFileShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(NON_EXISTING_DOC_ID))
                .thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.updateFile(NON_EXISTING_DOC_ID, multipartFile));

        Assertions.assertEquals(ErrorCode.STANDARD_BY_ID_NOT_EXISTS, exception.getErrorCode());
    }

    /**
     * Проверка выброса исключения при обновлении файла ГОСТа без файла
     */
    @Test
    void updateFileShouldThrowBusinessExceptionWhenDocumentHasNoFile() {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithoutFile));

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.updateFile(EXISTING_DOC_ID, multipartFile));

        Assertions.assertEquals(ErrorCode.NULL_FILE_DATA_RESPONSE, exception.getErrorCode());
    }

    /**
     * Проверка удаления файла существующего ГОСТа с файлом
     */
    @Test
    void deleteFileShouldDeleteFileWhenDocumentExistsAndHasFile() throws BusinessException {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithFile));
        Mockito.when(documentRepository.save(any(Document.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        gostsFileService.deleteFile(EXISTING_DOC_ID);

        Mockito.verify(documentRepository, times(1)).findById(EXISTING_DOC_ID);
        Mockito.verify(documentRepository, times(1)).save(documentWithFile);
        Assertions.assertNull(documentWithFile.getFileData());
    }

    /**
     * Проверка выброса исключения при удалении файла несуществующего ГОСТа
     */
    @Test
    void deleteFileShouldThrowBusinessExceptionWhenDocumentDoesNotExist() {
        Mockito.when(documentRepository.findById(NON_EXISTING_DOC_ID))
                .thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.deleteFile(NON_EXISTING_DOC_ID));

        Assertions.assertEquals(ErrorCode.STANDARD_BY_ID_NOT_EXISTS, exception.getErrorCode());
    }

    /**
     * Проверка выброса исключения при удалении файла ГОСТа без файла
     */
    @Test
    void deleteFileShouldThrowBusinessExceptionWhenDocumentHasNoFile() {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithoutFile));

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> gostsFileService.deleteFile(EXISTING_DOC_ID));

        Assertions.assertEquals(ErrorCode.NULL_FILE_DATA_RESPONSE, exception.getErrorCode());
    }

    /**
     * Проверка IOException при сохранении файла
     */
    @Test
    void saveFileShouldThrowIOExceptionWhenFileGetBytesFails() throws IOException {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithoutFile));
        Mockito.when(multipartFile.getBytes()).thenThrow(new IOException("File read error"));

        Assertions.assertThrows(IOException.class,
                () -> gostsFileService.saveFile(EXISTING_DOC_ID, multipartFile));
    }

    /**
     * Проверка IOException при обновлении файла
     */
    @Test
    void updateFileShouldThrowIOExceptionWhenFileGetBytesFails() throws IOException {
        Mockito.when(documentRepository.findById(EXISTING_DOC_ID))
                .thenReturn(Optional.of(documentWithFile));
        Mockito.when(multipartFile.getBytes()).thenThrow(new IOException("File read error"));

        Assertions.assertThrows(IOException.class,
                () -> gostsFileService.updateFile(EXISTING_DOC_ID, multipartFile));
    }
}