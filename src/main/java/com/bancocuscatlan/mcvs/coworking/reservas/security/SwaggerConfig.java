package com.bancocuscatlan.mcvs.coworking.reservas.security;

import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import java.util.List;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springdoc.core.customizers.OpenApiCustomizer;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer loginCustomizer() {

        return openApi -> {

            Schema<?> loginSchema = new ObjectSchema()
                    .addProperty("correo", new StringSchema())
                    .addProperty("contrasenia", new StringSchema());

            Operation login = new Operation()
                    .tags(List.of("Autenticación"))
                    .summary("Iniciar sesión")
                    .description("Autentica al usuario y devuelve un JWT")
                    .security(Collections.emptyList())
                    .requestBody(new RequestBody()
                            .required(true)
                            .content(new Content()
                                    .addMediaType("application/json",
                                            new MediaType().schema(loginSchema))))
                    .responses(new ApiResponses()
                            .addApiResponse("200",
                                    new ApiResponse()
                                            .description("JWT generado correctamente"))
                            .addApiResponse("401",
                                    new ApiResponse()
                                            .description("Credenciales incorrectas")));

            openApi.path("/login", new PathItem().post(login));
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API Reservas Coworking")
                        .version("1.0")
                        .description("Microservicio de reservas"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }
}