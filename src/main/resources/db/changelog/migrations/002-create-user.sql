--liquibase formatted sql
--changeset raquib:002

CREATE TABLE users(
    username varchar(100) PRIMARY KEY,
    password varchar(255) NOT NULL,
    role varchar(50) NOT NULL DEFAULT 'ROLE_USER'
);