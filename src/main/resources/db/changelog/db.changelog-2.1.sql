--liquibase formatted sql

--changeset manakin:1
ALTER TABLE document
    DROP COLUMN IF EXISTS adoption_level;
ALTER TABLE document
    DROP COLUMN IF EXISTS status;
ALTER TABLE document
    DROP COLUMN IF EXISTS harmonization;

--changeset manakin:2
DROP TYPE adoption_level_enum;
DROP TYPE harmonization_enum;
DROP TYPE status_enum;

--changeset manakin:3
create type adoption_level_enum as enum (
    'NATIONAL' ,
    'INTERSTATE',
    'SECTORAL',
    'REGIONAL',
    'ORGANIZATIONAL_STANDARD'
    );

create type harmonization_enum as enum (
    'NON_HARMONIZED',
    'MODIFIED',
    'HARMONIZED'
    );

create type status_enum as enum (
    'CURRENT',
    'CANCELED',
    'REPLACED'
    );

--changeset manakin:4
ALTER TABLE document
    ADD COLUMN adoption_level adoption_level_enum NOT NULL DEFAULT 'REGIONAL';
ALTER TABLE document
    ADD COLUMN status status_enum NOT NULL DEFAULT 'CURRENT';
ALTER TABLE document
    ADD COLUMN harmonization harmonization_enum NOT NULL DEFAULT 'NON_HARMONIZED';