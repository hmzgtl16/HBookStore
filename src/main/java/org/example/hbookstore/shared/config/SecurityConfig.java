package org.example.hbookstore.shared.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.hbookstore.shared.securiry.CustomAccessDeniedHandler;
import org.example.hbookstore.shared.securiry.CustomAuthenticationEntryPoint;
import org.example.hbookstore.shared.securiry.GrantedAuthoritiesConverter;
import org.example.hbookstore.shared.securiry.RsaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final RsaProperties rsaProperties;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(
            RsaProperties rsaProperties,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.rsaProperties = rsaProperties;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/auth/**").permitAll()

                                .requestMatchers("/api/v1/users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/books").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/books").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/v1/books/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/books/**").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/authors").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/authors").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/v1/authors/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/authors/**").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/reviews").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/reviews").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/v1/reviews/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/reviews/**").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/customers").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/customers").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/v1/customers/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/customers/**").hasAnyRole("USER", "ADMIN")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(configurer ->
                        configurer
                                .jwt(jwtConfigurer ->
                                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
                                )
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaProperties.publicKey()).privateKey(rsaProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
        return jwtConverter;
    }
}
