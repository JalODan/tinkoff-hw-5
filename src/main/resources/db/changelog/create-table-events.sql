-- liquibase formatted sql

-- changeset oj:1

CREATE TABLE events (

    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    place_id UUID NOT NULL REFERENCES places(id)
);
