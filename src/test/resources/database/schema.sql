CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) UNIQUE NOT NULL,
    username   VARCHAR(100) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    role       VARCHAR(50)         NOT NULL,
    status     VARCHAR(50)         NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    status       VARCHAR(50)  NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS authors
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    nationality VARCHAR(100),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS books
(
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    author_id       BIGINT REFERENCES authors (id),
    isbn            VARCHAR(20),
    publisher       VARCHAR(255),
    published_date  DATE,
    language        VARCHAR(50),
    format          VARCHAR(50),
    category        VARCHAR(100),
    cover_image_url TEXT,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reviews
(
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers (id) ON DELETE CASCADE,
    book_id     BIGINT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    rating      INT    NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment     TEXT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS customers_id_seq;
DROP SEQUENCE IF EXISTS authors_id_seq;
DROP SEQUENCE IF EXISTS books_id_seq;
DROP SEQUENCE IF EXISTS reviews_id_seq;

CREATE SEQUENCE users_id_seq START 1;
CREATE SEQUENCE customers_id_seq START 1;
CREATE SEQUENCE authors_id_seq START 1;
CREATE SEQUENCE books_id_seq START 1;
CREATE SEQUENCE reviews_id_seq START 1;