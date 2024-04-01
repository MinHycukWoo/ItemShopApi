package com.example.itemShopApi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {



        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");


        return new OpenAPI()

                .components(new Components().addSecuritySchemes("bearerAuth" , securityScheme))
                .security(Arrays.asList(securityRequirement));
                //.info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("ItemShopApi Swagger")
                .description("ItemShopApi Portfolio RESTful API")
                .version("1.0.0");
    }
}
