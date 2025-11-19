--liquibase formatted sql

--changeset petrov:1
ALTER TABLE document
    DROP CONSTRAINT IF EXISTS fk_adoption_level;
ALTER TABLE document
    DROP CONSTRAINT IF EXISTS fk_status;
ALTER TABLE document
    DROP CONSTRAINT IF EXISTS fk_harmonization;

--changeset petrov:2
ALTER TABLE document
    DROP COLUMN adoption_level;
ALTER TABLE document
    DROP COLUMN status;
ALTER TABLE document
    DROP COLUMN harmonization;

--changeset petrov:3
ALTER TABLE document
    ADD COLUMN adoption_level adoption_level_enum NOT NULL;
ALTER TABLE document
    ADD COLUMN status status_enum NOT NULL;
ALTER TABLE document
    ADD COLUMN harmonization harmonization_enum NOT NULL;

--changeset petrov:4
DROP TABLE IF EXISTS adoption_level;
DROP TABLE IF EXISTS status_level;
DROP TABLE IF EXISTS harmonization;