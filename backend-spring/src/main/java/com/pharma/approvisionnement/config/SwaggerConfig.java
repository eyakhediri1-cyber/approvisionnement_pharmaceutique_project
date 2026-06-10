/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.models.Components
 *  io.swagger.v3.oas.models.OpenAPI
 *  io.swagger.v3.oas.models.info.Contact
 *  io.swagger.v3.oas.models.info.Info
 *  io.swagger.v3.oas.models.security.SecurityRequirement
 *  io.swagger.v3.oas.models.security.SecurityScheme
 *  io.swagger.v3.oas.models.security.SecurityScheme$Type
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.pharma.approvisionnement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI().info(new Info().title("API Approvisionnement Pharmaceutique").version("1.0.0").description("Backend Spring Boot rempla\u00e7ant le PHP \u2014 Gestion stock, commandes, grossistes").contact(new Contact().name("Projet Stage Pharmacie").email("contact@pharma.com"))).addSecurityItem(new SecurityRequirement().addList("bearerAuth")).components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT").description("Entrez le token JWT obtenu depuis POST /api/auth/login")));
    }
}

