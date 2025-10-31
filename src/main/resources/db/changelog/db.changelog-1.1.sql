--liquibase formatted sql

--changeset manakin:1

create type adoption_level_enum as enum (
    'National' ,
    'Interstate ',
    'Sectoral ',
    'Regional',
    'Organizational Standard'
    );

create type harmonization_enum as enum (
    'Non_harmonized',
    'Modified',
    'Harmonized'
    );

create type status_enum as enum (
    'Current',
    'Canceled',
    'Replaced'
    );

create table if not exists adoption_level
(
    id             BIGSERIAL primary key,
    adoption_level adoption_level_enum
);

create table if not exists harmonization
(
    id            BIGSERIAL primary key,
    harmonization harmonization_enum
);

create table if not exists status_level
(
    id     BIGSERIAL primary key,
    status status_enum
);

create table if not exists document
(
    id               BIGSERIAL primary key not null,
    full_name        varchar(512)          not null,
    designation      varchar(128)          not null,
    code_oks         varchar(64)           not null,
    activity_field   varchar(128)          not null,
    author           varchar(256)          not null,
    application_area varchar(1024)         not null,
    content_link     varchar(256)          null,
    acceptance_year  int                   not null,
    commission_year  int                   not null,
    key_words        varchar(512)          not null,
    adoption_level   BIGINT                not null,
    status           bigint                not null,
    harmonization    bigint                not null,
    "references"     TEXT[],
    constraint fk_adoption_level foreign key (adoption_level)
        references adoption_level (id),
    constraint fk_status foreign key (status)
        references status_level (id),
    constraint fk_harmonization foreign key (harmonization)
        references harmonization (id)
)