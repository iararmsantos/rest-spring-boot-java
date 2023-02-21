package com.iarasantos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("RESTfull API with Java 19 and Spring Boot 3")
                        .version("v1")
                        .description("API that create a user.")
                        .termsOfService("https://www.google.com")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://www.google.com")
                        )
                );
    }
    //to access JSON: localhost:8080/v3/api-docs
    //to access browser: localhost:8080/swagger-ui/index.html

}
