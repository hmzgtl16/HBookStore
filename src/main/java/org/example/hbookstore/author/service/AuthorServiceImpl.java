package org.example.hbookstore.author.service;

import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.example.hbookstore.author.domain.Author;
import org.example.hbookstore.author.domain.AuthorRepository;
import org.example.hbookstore.author.mapping.AuthorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

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

    @Transactional
    @Override
    public AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(); // Handle not found case

        Author updatedAuthor = authorMapper.updateEntity(author, request);
        return authorMapper.toResponse(authorRepository.save(updatedAuthor));
    }

    @Transactional
    @Override
    public void deleteAuthor(Long id) throws Exception {
        if (!authorRepository.existsById(id)) {
            throw new Exception("Author not found with id: $id");
        }
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorResponse> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(authorMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorResponse> getAuthorsByNationality(String nationality, Pageable pageable) {
        return authorRepository.findByNationality(nationality, pageable)
                .map(authorMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorResponse> searchAuthors(String query, Pageable pageable) {
        return authorRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable)
                .map(authorMapper::toResponse);
    }
}
