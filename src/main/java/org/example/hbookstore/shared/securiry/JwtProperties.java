package org.example.hbookstore.shared.securiry;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JwtProperties(
        String secretKey,
        long expirationTimeInMs
) {
}
