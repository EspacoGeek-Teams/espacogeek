package com.espacogeek.geek.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WebTestClientConfig {

    @Bean
    public WebTestClientBuilderCustomizer webTestClientBuilderCustomizer() {
        return (builder) -> builder.defaultHeader("Authorization", "Basic dml0b3JAZ21haWwuY29tOnZpdG9yMTIzNA==");
    }
}
