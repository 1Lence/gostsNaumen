--liquibase formatted sql

--changeset petrov:1
ALTER TABLE users
    DROP COLUMN IF EXISTS telegram_tag;

ALTER TABLE users
    add column if not exists full_name varchar(120) not null default 'fullName';