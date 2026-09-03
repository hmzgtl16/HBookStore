package org.example.hbookstore.shared.securiry;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rsa")
public record RsaProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {}
