package com.project.ems.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
      info = @Info(
            title = "Employee Management System REST API",
            description = "This is a sample REST API documentation of a employee management system server.",
            version = "1.0.0",
            contact = @Contact(name = "George Bacalu", email = "georgebacalu83@gmail.com")),
      tags = {
            @Tag(name = "employee", description = "Employee REST Controller operations"),
            @Tag(name = "experience", description = "Experience REST Controller operations"),
            @Tag(name = "feedback", description = "Feedback REST Controller operations"),
            @Tag(name = "mentor", description = "Mentor REST Controller operations"),
            @Tag(name = "role", description = "Role REST Controller operations"),
            @Tag(name = "studies", description = "Studies REST Controller operations"),
            @Tag(name = "user", description = "User REST Controller operations")})
public class OpenApiConfig {
}
