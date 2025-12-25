--liquibase formatted sql

--changeset manakin:1
create type accepted_first_time_or_replaced_enum as enum (
    'FIRST_TIME',
    'REPLACED'
    );

--changeset manakin:2
ALTER TABLE document
    ADD COLUMN accepted_first_time_or_replaced accepted_first_time_or_replaced_enum NOT NULL DEFAULT 'FIRST_TIME';