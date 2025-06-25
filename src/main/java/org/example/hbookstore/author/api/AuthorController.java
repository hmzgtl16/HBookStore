package org.example.hbookstore.author.api;

import org.example.hbookstore.author.api.dto.AuthorResponse;
import org.example.hbookstore.author.api.dto.CreateAuthorRequest;
import org.example.hbookstore.author.api.dto.UpdateAuthorRequest;
import org.example.hbookstore.author.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(CreateAuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(@PathVariable Long id) {
        AuthorResponse response = authorService.getAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @RequestBody UpdateAuthorRequest request
    ) {
        AuthorResponse response = authorService.updateAuthor(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws Exception {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> getAllAuthors(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<AuthorResponse> response = authorService.getAllAuthors(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/nationality/{nationality}")
    public ResponseEntity<Page<AuthorResponse>> getAuthorsByNationality(
            @PathVariable String nationality,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<AuthorResponse> response = authorService.getAuthorsByNationality(nationality, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AuthorResponse>> searchAuthors(
            @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<AuthorResponse> response = authorService.searchAuthors(query, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
