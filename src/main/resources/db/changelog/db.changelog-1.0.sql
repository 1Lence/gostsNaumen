--liquibase formatted sql

--changeset petrov:1
create type user_role as enum (
    'ADMIN',
    'USER'
    );

create table if not exists users
(
    id            BIGSERIAL primary key not null,
    user_name     varchar(128) unique   not null,
    password_hash varchar(256)          not null,
    email         varchar(128) unique   not null,
    telegram_tag  varchar(256) unique   null,
    roles         user_role
);
