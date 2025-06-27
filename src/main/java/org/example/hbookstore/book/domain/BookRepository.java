package org.example.hbookstore.book.domain;

import org.example.hbookstore.book.domain.enums.BookCategory;
import org.example.hbookstore.book.domain.enums.BookFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    Page<Book> findByCategory(BookCategory category, Pageable pageable);

    Page<Book> findByFormat(BookFormat format, Pageable pageable);

    Page<Book> findByLanguage(String language, Pageable pageable);

    Page<Book> findByPublisher(String publisher, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrPublisherContainingIgnoreCase(
            String title,
            String description,
            String publisher,
            Pageable pageable
    );
}
