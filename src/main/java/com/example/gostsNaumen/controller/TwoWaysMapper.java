package com.example.gostsNaumen.controller;

/**
 * Позволяет преобразовать сущность из БД в Дто и наоборот
 *
 * @param <E> энтити
 * @param <D> ДТО с запроса
 * @param <Z> ДТО для ответа
 */
public interface TwoWaysMapper<E, D, Z> {
    /**
     * Преобразование ДТО в сущность для БД
     *
     * @param fromWhat ДТО
     * @return сущность для БД
     */
    E mapToEntity(D fromWhat);


    /**
     * Преобразование сущности из БД в ДТО для ответа по запросу
     *
     * @param fromWhat сущность из БД
     * @return ДТО
     */
    Z mapEntityToDto(E fromWhat);
}