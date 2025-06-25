package org.example.hbookstore.author.service;

import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;

public interface AuthorService {
    AuthorResponse createAuthor(CreateAuthorRequest request);
    AuthorResponse getAuthor(Long id);
    AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request);
    void deleteAuthor(Long id) throws Exception;
}
