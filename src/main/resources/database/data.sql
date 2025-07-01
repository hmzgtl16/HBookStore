INSERT INTO users (username, password, role, status, created_at, updated_at)
VALUES ('admin', '$2a$10$ZP78VMqGXA974b.hpmEFd.ONT8YU8kWEffy9vcNf1iuGQIQyisDse', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       ('user1', '$2a$10$2bfu5i8/KCN6chzFZUOjD.eEQlaJb.kEPukC.hxoGCbW0FzoF71PC', 'USER', 'ACTIVE', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO customers (first_name, last_name, email, phone_number, status, category, created_at, updated_at, user_id)
VALUES ('David', 'Smith', 'david.smith@example.org', '0123456789', 'ACTIVE', 'PREMIUM', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 1),
       ('John', 'Doe', 'john.doe@example.com', '1234567890', 'ACTIVE', 'REGULAR', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 2)
ON CONFLICT DO NOTHING;

INSERT INTO authors (first_name, last_name, nationality, created_at, updated_at)
VALUES ('George', 'Orwell', 'British', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('J.K.', 'Rowling', 'British', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO books (title, description, author_id, isbn, publisher, published_date, language, format, category,
                   cover_image_url, created_at, updated_at)
VALUES ('1984', 'Dystopian novel', 1, '9780451524935', 'Secker & Warburg', '1949-06-08',
        'English', 'PAPERBACK', 'FICTION', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Harry Potter and the Sorcerer''s Stone', 'Fantasy novel', 2, '9780747532699', 'Bloomsbury', '1997-06-26',
        'English', 'HARDCOVER', 'FANTASY', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO reviews (customer_id, book_id, rating, comment, created_at, updated_at)
VALUES (1, 1, 5, 'Incredible book — timeless classic!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 2, 4, 'Fun and imaginative.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

