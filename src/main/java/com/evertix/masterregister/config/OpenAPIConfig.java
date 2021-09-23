package com.evertix.masterregister.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {
    @Bean(name = "FWalletOpenAPI")
    public OpenAPI FWalletOpenAPI() {
        // http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
        // http://master-register.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
        return new OpenAPI().components(new Components())
                .info(new Info().title("Master-Register API").description("Open API Documentation, implemented with Spring Boot RESTful"));
    }
}