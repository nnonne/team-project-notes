DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS users;

CREATE TABLE notes (
                       id uuid NOT NULL,
                       name VARCHAR(100),
                       content VARCHAR(10000),
                       access_type VARCHAR(255) CHECK (access_type IN ('PRIVATE','PUBLIC')),
                       PRIMARY KEY (id)
);

CREATE TABLE users (
                       id bigserial NOT NULL,
                       username VARCHAR(50),
                       password VARCHAR(100),
                       PRIMARY KEY (id),
                       CONSTRAINT UKr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);