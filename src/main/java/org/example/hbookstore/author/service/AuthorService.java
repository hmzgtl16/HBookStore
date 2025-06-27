package org.example.hbookstore.author.service;

import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponse createAuthor(CreateAuthorRequest request);

    AuthorResponse getAuthor(Long id);

    AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request);

    void deleteAuthor(Long id) throws Exception;

    Page<AuthorResponse> getAllAuthors(Pageable pageable);

    Page<AuthorResponse> getAuthorsByNationality(String nationality, Pageable pageable);

    Page<AuthorResponse> searchAuthors(String query, Pageable pageable);
}
