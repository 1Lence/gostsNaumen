--liquibase formatted sql

--changeset manakin:1

ALTER TABLE document
    RENAME COLUMN "references" TO references_list