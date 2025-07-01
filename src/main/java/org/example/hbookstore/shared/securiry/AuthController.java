package org.example.hbookstore.shared.securiry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(
            Authentication authentication
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }
}
