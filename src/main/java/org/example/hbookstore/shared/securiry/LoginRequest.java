package org.example.hbookstore.shared.securiry;

public record LoginRequest(
        String username,
        String password
) {
}
