package com.example.gostsNaumen.controller.dto.response;

/**
 * ДТО, которое хранит в себе айди к которому подкрепился документ
 *
 * @param docId    айди ГОСТа, к которому был прикреплен документ
 * @param fileData поток байт документа
 */
public record FileDtoResponse(
        Long docId,
        byte[] fileData
) {
}