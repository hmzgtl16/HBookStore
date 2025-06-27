INSERT INTO users (id, username, password, role, status, created_at, updated_at)
VALUES (1, 'admin', 'adminpass', 'ADMIN', 'ACTIVE', NOW(), NOW()),
       (2, 'user1', 'userpass1', 'USER', 'ACTIVE', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO customers (id, first_name, last_name, email, phone_number, status, category, created_at, updated_at, user_id)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', '1234567890', 'ACTIVE', 'REGULAR', NOW(), NOW(),2)
ON CONFLICT (id) DO NOTHING;

INSERT INTO authors (id, first_name, last_name, nationality, created_at, updated_at)
VALUES (1, 'George', 'Orwell', 'British', NOW(), NOW()),
       (2, 'J.K.', 'Rowling', 'British', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO books (id, title, description, author_id, isbn, publisher, published_date, language, format, category, cover_image_url, created_at, updated_at)
VALUES (1, '1984', 'Dystopian novel', 1, '9780451524935', 'Secker & Warburg', '1949-06-08', 'English', 'PAPERBACK','FICTION', NULL, NOW(), NOW()),
       (2, 'Harry Potter and the Sorcerer''s Stone', 'Fantasy novel', 2, '9780747532699', 'Bloomsbury', '1997-06-26','English', 'HARDCOVER', 'FANTASY', NULL, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO reviews (id, customer_id, book_id, rating, comment, created_at, updated_at)
VALUES (1, 1, 1, 5, 'Incredible book — timeless classic!', NOW(), NOW()),
       (2, 1, 2, 4, 'Fun and imaginative.', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

