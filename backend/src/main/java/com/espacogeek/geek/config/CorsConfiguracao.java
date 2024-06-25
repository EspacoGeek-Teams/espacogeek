package com.espacogeek.geek.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguracao implements WebMvcConfigurer {
    // Permissão de CORS
    //https://www.alura.com.br/artigos/como-resolver-erro-de-cross-origin-resource-sharing#:~:text=O%20CORS%20(Cross%2Dorigin%20Resource,recursos%20de%20outra%20origem%20diferente.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST");
    }
}
