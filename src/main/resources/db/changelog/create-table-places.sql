-- liquibase formatted sql

-- changeset oj:1

DROP TABLE IF EXISTS location;

CREATE TABLE IF NOT EXISTS places (
    id UUID PRIMARY KEY,
    slug VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

