--liquibase formatted sql

--changeset petrov:1
ALTER TABLE document
    add column file_data BYTEA