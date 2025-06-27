package org.example.hbookstore.shared.securiry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(UserDetails user) {
        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withIssuer("HBookStore")
                .withSubject(user.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.expirationTimeInMs()))
                .withClaim("authorities", authorities)
                .sign(Algorithm.HMAC256(jwtProperties.secretKey()));
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.secretKey()))
                .withIssuer("HBookStore")
                .build()
                .verify(token);
    }
}
