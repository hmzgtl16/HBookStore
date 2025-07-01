# HBookStore

A comprehensive bookstore management system built with Spring Boot, providing RESTful APIs for managing books, authors,
customers, and reviews.

## Features

- **User Management**: Secure user authentication and authorization with JWT and RSA keys
- **Book Management**: CRUD operations for books with search and filtering capabilities
- **Author Management**: Manage author information
- **Customer Management**: Track customer information and categorize customers
- **Review System**: Allow customers to leave reviews and ratings for books
- **RESTful API**: Well-structured API following REST principles

## Technologies Used

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** with JWT authentication
- **Spring Data JDBC**
- **PostgreSQL** database
- **Docker Compose** for development environment
- **Gradle** for build management

## Project Structure

The project follows a clean architecture with domain-driven design principles:

```
src/main/java/org/example/hbookstore/
├── author/             # Author domain
├── book/               # Book domain
├── customer/           # Customer domain
├── review/             # Review domain
├── user/               # User domain
└── shared/             # Shared components
    ├── config/         # Application configuration
    ├── error/          # Error handling
    └── security/       # Security configuration
```

Each domain package contains:

- `api/`: Controllers and DTOs
- `domain/`: Domain models and repositories
- `service/`: Business logic
- `mapping/`: Object mappers

## Setup and Installation

### Prerequisites

- Java 21 or higher
- Docker and Docker Compose

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/HBookStore.git
   cd HBookStore
   ```

2. Start the PostgreSQL database using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Build and run the application:
   ```bash
   ./gradlew bootRun
   ```

4. The application will be available at `http://localhost:8080`

## API Documentation

### Books API

- `GET /api/v1/books`: Get all books (paginated)
- `GET /api/v1/books/{id}`: Get a specific book
- `POST /api/v1/books`: Create a new book
- `PUT /api/v1/books/{id}`: Update a book
- `DELETE /api/v1/books/{id}`: Delete a book
- `GET /api/v1/books/author/{authorId}`: Get books by author
- `GET /api/v1/books/category/{category}`: Get books by category
- `GET /api/v1/books/format/{format}`: Get books by format
- `GET /api/v1/books/language/{language}`: Get books by language
- `GET /api/v1/books/publisher/{publisher}`: Get books by publisher
- `GET /api/v1/books/search?query={query}`: Search books

Similar endpoints are available for authors, customers, reviews, and users.

## Configuration

The application can be configured through the `application.properties` file. For development, you can use the
`application-dev.properties` file.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

GATTAL Hamza