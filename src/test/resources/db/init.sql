DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    public_username VARCHAR(26)  NOT NULL UNIQUE,
    email           VARCHAR(256) NOT NULL UNIQUE,
    password        VARCHAR(160),
    first_name      VARCHAR(64),
    last_name       VARCHAR(64),
    role            VARCHAR(10)
);