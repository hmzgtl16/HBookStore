package org.example.hbookstore.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            String securitySchemeName = "bearerAuth";
            openApi
                    .info(
                            new Info()
                                    .title("HBookstore API")
                                    .version("1.0.0")
                                    .description("API documentation for HBookstore application")
                    )
                    .addSecurityItem(
                            new SecurityRequirement()
                                    .addList(securitySchemeName)
                    )
                    .components(
                            new Components()
                                    .addSecuritySchemes(
                                            securitySchemeName,
                                            new SecurityScheme()
                                                    .name(securitySchemeName)
                                                    .type(SecurityScheme.Type.HTTP)
                                                    .scheme("bearer")
                                                    .bearerFormat("JWT")
                                    )
                    )
                    .servers(
                            List.of(
                                    new Server()
                                            .url("http://localhost:8080")
                                            .description("Local development server"))
                    );
        };
    }
}
