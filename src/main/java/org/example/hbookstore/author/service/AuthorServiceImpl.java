package org.example.hbookstore.author.service;

import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.example.hbookstore.author.domain.Author;
import org.example.hbookstore.author.domain.AuthorRepository;
import org.example.hbookstore.author.mapping.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional
    @Override
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        Author author = authorMapper.toEntity(request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorResponse getAuthor(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toResponse)
                .orElseThrow(); // Consider throwing an exception instead of returning null
    }

    @Override
    public AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request) {
        return null;
    }

    @Override
    public void deleteAuthor(Long id) {

    }
}
