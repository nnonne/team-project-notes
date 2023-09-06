CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       password VARCHAR(100),
                       username VARCHAR(50) UNIQUE
);

CREATE TABLE notes (
                       id UUID PRIMARY KEY,
                       access_type VARCHAR(255) NOT NULL,
                       content VARCHAR(10000),
                       name VARCHAR(100),
                       author BIGINT,
                       CONSTRAINT fk_author FOREIGN KEY (author) REFERENCES users(id)
);
