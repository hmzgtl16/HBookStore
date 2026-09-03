-- USERS
INSERT INTO users (id, email, username, password, role, status, created_at, updated_at) VALUES
  (1, 'admin@example.com', 'admin', 'adminpass', 'ADMIN', 'ACTIVE', NOW(), NOW()),
  (2, 'jane.doe@example.com', 'janedoe', 'password123', 'USER', 'ACTIVE', NOW(), NOW()),
  (3, 'john.smith@example.com', 'johnsmith', 'password456', 'USER', 'ACTIVE', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- CUSTOMERS
INSERT INTO customers (id, first_name, last_name, phone_number, status, category, created_at, updated_at, user_id) VALUES
  (1, 'Jane', 'Doe', '123-456-7890', 'ACTIVE', 'REGULAR', NOW(), NOW(), 2),
  (2, 'John', 'Smith', '987-654-3210', 'ACTIVE', 'ENTERPRISE', NOW(), NOW(), 3)
ON CONFLICT (id) DO NOTHING;

-- AUTHORS
INSERT INTO authors (id, first_name, last_name, nationality, created_at, updated_at) VALUES
  (1, 'George', 'Orwell', 'British', NOW(), NOW()),
  (2, 'J.K.', 'Rowling', 'British', NOW(), NOW()),
  (3, 'Harper', 'Lee', 'American', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- BOOKS
INSERT INTO books (id, title, description, author_id, isbn, publisher, published_date, language, format, category, cover_image_url, created_at, updated_at) VALUES
  (1, '1984', 'A dystopian social science fiction novel', 1, '9780451524935', 'Secker & Warburg', '1949-06-08', 'English', 'PAPERBACK', 'FICTION', NULL, NOW(), NOW()),
  (2, 'Harry Potter and the Philosopher''s Stone', 'Fantasy novel, first in the series', 2, '9780747532699', 'Bloomsbury', '1997-06-26', 'English', 'HARDCOVER', 'FANTASY', NULL, NOW(), NOW()),
  (3, 'To Kill a Mockingbird', 'Pulitzer Prize-winning novel', 3, '9780061120084', 'J.B. Lippincott & Co.', '1960-07-11', 'English', 'EBOOK', 'FICTION', NULL, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- REVIEWS
INSERT INTO reviews (id, customer_id, book_id, rating, comment, created_at, updated_at) VALUES
  (1, 1, 1, 5, 'Absolutely chilling and thought-provoking.', NOW(), NOW()),
  (2, 1, 2, 4, 'Magical and imaginative.', NOW(), NOW()),
  (3, 2, 3, 5, 'A powerful and emotional read.', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;