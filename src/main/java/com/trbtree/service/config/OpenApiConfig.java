package com.trbtree.service.config;
import io.swagger.v3.oas.annotations    .OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My API",
                version = "1.0",
                description = "Spring Boot REST API documentation"
        )
)
public class OpenApiConfig {
}
